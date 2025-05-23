package com.example.custom_clothing_order_manager.ui;



import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import com.example.custom_clothing_order_manager.adapters.ArrayAdapter;
import com.example.custom_clothing_order_manager.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.custom_clothing_order_manager.database.CustomersDB;
import com.example.custom_clothing_order_manager.database.OrdersDB;
import com.example.custom_clothing_order_manager.databinding.ActivityMainBinding;
import com.example.custom_clothing_order_manager.models.Customers;
import com.example.custom_clothing_order_manager.models.Orders;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    String userEmail, cusname, cusphone, userID;
    String cusid;
    Orders orders;
    ArrayAdapter arrayAdapter;
    private List<Orders> oderList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.categorytxt), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        FloatingActionButton tailorBtn = findViewById(R.id.findTailorBtn);
        View pendingView = findViewById(R.id.view);
        View progress = findViewById(R.id.view3);
        View comView = findViewById(R.id.view2);

        Bundle bundle1 = getIntent().getExtras();
        if (bundle1 != null) {
            int value1 = bundle1.getInt("key1", 0);
            userEmail = bundle1.getString("customer_email", "No Name Provided");
            userID = bundle1.getString("user_id");
        }

        CustomersDB customersDB = new CustomersDB();
        customersDB.getCustomerById(this, userID, new CustomersDB.OnCustomerDataFetchedListener() {
            @Override
            public void onSuccess(Customers customer) {
                if (!isFinishing() && customer != null) {
                    cusname = customer.getName();
                    cusid = customer.getId();
                    cusphone = customer.getPhone();

                    TextView nameTxt = findViewById(R.id.textView2);
                    nameTxt.setText(cusname);

                    TextView idTxt = findViewById(R.id.textView61);
                    idTxt.setText(cusid);

                    Toast.makeText(MainActivity.this, "Welcome " + cusname, Toast.LENGTH_SHORT).show();

                } else {
                    Log.d("MainActivity", "Customer is null or Activity is finishing");
                    Toast.makeText(MainActivity.this, "Customer data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                if (!isFinishing()) {
                    Log.e("MainActivity", "Error fetching customer data: " + errorMessage);
                    Toast.makeText(MainActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        OrdersDB ordersDB = new OrdersDB();
        ordersDB.getOrdersByCustomerId(userID, new OrdersDB.OnOrdersFetchedListener() {
            @Override
            public void onSuccess(List<Orders> orders) {
                oderList = orders;
                arrayAdapter = new ArrayAdapter(MainActivity.this, oderList, userID);
                recyclerView.setAdapter(arrayAdapter);
                if (!oderList.isEmpty()) {
                    recyclerView.post(() -> recyclerView.scrollToPosition(oderList.size() - 1));
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e(TAG, "Error fetching orders: " + errorMessage);
                Toast.makeText(MainActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        pendingView.setOnClickListener(v -> filterPendingOrders());
        progress.setOnClickListener(v -> filterProgressOrders());
        comView.setOnClickListener(v -> filterComOrders());

        tailorBtn.setOnClickListener(v ->
        {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            //Intent intent = new Intent(MainActivity.this,dashboard.class);
            //Intent intent = new Intent(MainActivity.this,Login.class);
            Bundle bundle = new Bundle();
            bundle.putString("customer_email", userEmail);
            bundle.putString("user_id",userID);
            bundle.putInt("key1", 123);

            intent.putExtras(bundle);
            startActivity(intent);
        });


        BottomNavigationView bottomNav = findViewById(R.id.BottomNavigationView);
        bottomNav.setSelectedItemId(R.id.home);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            Intent intent = null;

            if (itemId == R.id.home) {
                intent = new Intent(MainActivity.this, MainActivity.class);
                Bundle bundle4 = new Bundle();
                bundle4.putInt("key", 123);
                bundle4.putString("customer_email", userEmail);
                bundle4.putString("user_id",userID);
                intent.putExtras(bundle4);
            } else if (itemId == R.id.item_2) {
                intent = new Intent(MainActivity.this, CustomerChatList.class);
                Bundle bundle4 = new Bundle();
                bundle4.putInt("key", 123);
                bundle4.putString("customer_email", userEmail);
                bundle4.putString("user_id",userID);
                intent.putExtras(bundle4);
            } else if (itemId == R.id.item_3) {
                intent = new Intent(MainActivity.this, odersTrack.class);
                Bundle bundle4 = new Bundle();
                bundle4.putInt("key", 123);
                bundle4.putString("customer_email", userEmail);
                bundle4.putString("cus_id",cusid);
                intent.putExtras(bundle4);
            } else if (itemId == R.id.item_4) {
                intent = new Intent(MainActivity.this, MainActivity2.class);
                Bundle bundle4 = new Bundle();
                bundle4.putInt("key", 123);
                bundle4.putString("customer_email", userEmail);
                bundle4.putString("cus_id",cusid);
                intent.putExtras(bundle4);
            } else {
                intent = new Intent(MainActivity.this, MainActivity.class);
            }

            if (intent != null) {
                startActivity(intent);
            }
            return true;
        });
    }

    private void filterPendingOrders() {

        List<Orders> progressOrders = new ArrayList<>();
        for (Orders order : oderList) {
            if ("Pending".equalsIgnoreCase(order.getStatus())) {
                progressOrders.add(order);
            }
        }

        if (progressOrders.isEmpty()) {
            Toast.makeText(this, "No pending orders found", Toast.LENGTH_SHORT).show();
        }

        arrayAdapter.updateList(progressOrders);
    }

    private void filterProgressOrders() {
        List<Orders> completedOrders  = new ArrayList<>();
        for (Orders order : oderList) {
            if ("Accepted".equalsIgnoreCase(order.getStatus()) || "Design".equalsIgnoreCase(order.getStatus()) || "Ready".equalsIgnoreCase(order.getStatus()) || "Delivery".equalsIgnoreCase(order.getStatus())) {
                completedOrders .add(order);
            }
        }

        if (completedOrders .isEmpty()) {
            Toast.makeText(this, "No Progress orders found", Toast.LENGTH_SHORT).show();
        }

        arrayAdapter.updateList(completedOrders );
    }

    private void filterComOrders() {
        List<Orders> pendingOrders = new ArrayList<>();
        for (Orders order : oderList) {
            if ("conform".equalsIgnoreCase(order.getStatus())) {
                pendingOrders.add(order);
            }
        }

        if (pendingOrders.isEmpty()) {
            Toast.makeText(this, "No Completed orders found", Toast.LENGTH_SHORT).show();
        }

        arrayAdapter.updateList(pendingOrders);
    }
}