package com.example.custom_clothing_order_manager.ui;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.adapters.ArrayAdapter;
import com.example.custom_clothing_order_manager.adapters.OderReqAdapter;
import com.example.custom_clothing_order_manager.database.OrdersDB;
import com.example.custom_clothing_order_manager.models.Orders;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class OderReqTailor extends AppCompatActivity {
    private String userID;
    Orders orders;
    OderReqAdapter adapter;
    private List<Orders> oderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_oder_req_tailor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            int value1 = bundle.getInt("key1", 0);
            userID = bundle.getString("user_id");
        }

        RecyclerView recyclerView = findViewById(R.id.recyleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        OrdersDB ordersDB = new OrdersDB();
        ordersDB.getOdersByTailorId(userID, new OrdersDB.OnOrdersFetchedListener() {
            @Override
            public void onSuccess(List<Orders> orders) {
                oderList = orders;
                adapter = new OderReqAdapter(OderReqTailor.this, oderList, userID);
                recyclerView.setAdapter(adapter);
                if (!oderList.isEmpty()) {
                    recyclerView.post(() -> recyclerView.scrollToPosition(oderList.size() - 1));
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e(TAG, "Error fetching orders: " + errorMessage);
                Toast.makeText(OderReqTailor.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        ImageView backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(OderReqTailor.this,dashboardOder.class);
            Bundle bundle1 = new Bundle();
            bundle1.putInt("key1",123);
            bundle1.putString("user_id",userID);
            intent.putExtras(bundle1);
            startActivity(intent);
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_oder);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            Intent intent = null;

            if (itemId == R.id.nav_home) {
                intent = new Intent(OderReqTailor.this, dashboardOder.class);
                Bundle bundle4 = new Bundle();
                bundle4.putString("user_id",userID);
                intent.putExtras(bundle4);
            } else if (itemId == R.id.nav_chats) {
                intent = new Intent(OderReqTailor.this, ChatListActivity.class);
                Bundle bundle4 = new Bundle();
                bundle4.putString("user_id",userID);
                intent.putExtras(bundle4);
            } else if (itemId == R.id.nav_oder) {
                intent = new Intent(OderReqTailor.this, OderReqTailor.class);
                Bundle bundle4 = new Bundle();
                bundle4.putString("user_id",userID);
                intent.putExtras(bundle4);
            } else if (itemId == R.id.nav_logout) {
                intent = new Intent(OderReqTailor.this, MainActivity2.class);
            } else {
                intent = new Intent(OderReqTailor.this, OderReqTailor.class);
            }

            if (intent != null) {
                startActivity(intent);
            }
            return true;
        });
    }
}