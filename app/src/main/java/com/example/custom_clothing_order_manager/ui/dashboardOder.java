package com.example.custom_clothing_order_manager.ui;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.adapters.OderReqAdapter;
import com.example.custom_clothing_order_manager.adapters.SecondArrayAdapter;
import com.example.custom_clothing_order_manager.database.CustomersDB;
import com.example.custom_clothing_order_manager.database.OrdersDB;
import com.example.custom_clothing_order_manager.database.TailorDB;
import com.example.custom_clothing_order_manager.database.TailorDatabaseHelper;
import com.example.custom_clothing_order_manager.models.Customers;
import com.example.custom_clothing_order_manager.models.Orders;
import com.example.custom_clothing_order_manager.models.Tailor;
import com.example.custom_clothing_order_manager.models.Tailors;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class dashboardOder extends AppCompatActivity {

    Orders orders;
    SecondArrayAdapter adapter;
    private List<Orders> oderList = new ArrayList<>();
    private String tailorEmail, tailorName, shopName, tailorCategory, userID;
    private int tailorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.oder_dashboard_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.categorytxt), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            int value1 = bundle.getInt("key1", 0);
            tailorEmail = bundle.getString("user_email", "No Name Provided");
            userID = bundle.getString("user_id","");
        }

        TailorDB tailorDB = new TailorDB();
        tailorDB.getTailorById(userID, new TailorDB.OnTailorOperationCompleteListener() {
            @Override
            public void onSuccess(Tailors tailors) {
                if (!isFinishing() && tailors != null) {
                    tailorName = tailors.getName();
                    shopName = tailors.getShopName();
                    tailorCategory = tailors.getSpecializations();

                    String imagePath = tailors.getImagePath();

                    ImageView tailorImageView = findViewById(R.id.imageView3);

                    if (imagePath != null && !imagePath.isEmpty()) {
                        File imageFile = new File(imagePath);
                        if (imageFile.exists()) {
                            try {
                                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                                tailorImageView.setImageBitmap(bitmap);
                            } catch (Exception e) {
                                Log.e("ImageLoading", "Error loading image: " + e.getMessage());
                            }
                        } else {
                            Log.e("ImageLoading", "Image file not found: " + imagePath);
                        }
                    } else {
                        Log.w("ImageLoading", "Image path is null or empty.");
                    }



                    //Toast.makeText(dashboardOder.this, "Welcome " + tailorName, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("dashboardOder", "Tailor is null or Activity is finishing");
                    Toast.makeText(dashboardOder.this, "Tailor data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                if (!isFinishing()) {
                    Log.e("dashboardOder", "Error fetching tailor data: " + errorMessage);
                    Toast.makeText(dashboardOder.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });



        RecyclerView recyclerView = findViewById(R.id.oder_view);
        TextView completeOdersTxt = findViewById(R.id.completed_oder);
        TextView inprogressTxt = findViewById(R.id.pendingtxt);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        OrdersDB ordersDB = new OrdersDB();
        ordersDB.getOdersByTailor(userID, new OrdersDB.OnOrdersFetchedListener() {
            @Override
            public void onSuccess(List<Orders> orders) {
                oderList = orders;
                adapter = new SecondArrayAdapter(dashboardOder.this, oderList, userID);
                recyclerView.setAdapter(adapter);
                if (!oderList.isEmpty()) {
                    recyclerView.post(() -> recyclerView.scrollToPosition(oderList.size() - 1));

                    long completedOrderCount = orders.stream()
                            .filter(order -> "Confirm".equalsIgnoreCase(order.getStatus()))
                            .count();
                    completeOdersTxt.setText("" + completedOrderCount);

                    if (!oderList.isEmpty()) {
                        recyclerView.post(() -> recyclerView.scrollToPosition(oderList.size() - 1));
                    }

                    long filteredOrderCount = orders.stream()
                            .filter(order -> {
                                String status = order.getStatus();
                                return "Accepted".equalsIgnoreCase(status) ||
                                        "Design".equalsIgnoreCase(status) ||
                                        "Ready".equalsIgnoreCase(status) ||
                                        "Delivery".equalsIgnoreCase(status);
                            })
                            .count();

                    inprogressTxt.setText("" + filteredOrderCount);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e(TAG, "Error fetching orders: " + errorMessage);
                Toast.makeText(dashboardOder.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            Intent intent = null;

            if (itemId == R.id.nav_home) {
                intent = new Intent(dashboardOder.this, dashboardOder.class);
                Bundle bundle4 = new Bundle();
                bundle4.putString("tailor_email", tailorEmail);
                bundle4.putString("user_id",userID);
                intent.putExtras(bundle4);
            } else if (itemId == R.id.nav_chats) {
                intent = new Intent(dashboardOder.this, ChatListActivity.class);
                Bundle bundle4 = new Bundle();
                bundle4.putString("tailor_email", tailorEmail);
                bundle4.putString("user_id",userID);
                intent.putExtras(bundle4);
            } else if (itemId == R.id.nav_oder) {
                intent = new Intent(dashboardOder.this, OderReqTailor.class);
                Bundle bundle4 = new Bundle();
                bundle4.putString("tailor_email", tailorEmail);
                bundle4.putString("user_id",userID);
                intent.putExtras(bundle4);
            } else if (itemId == R.id.nav_logout) {
                intent = new Intent(dashboardOder.this, MainActivity2.class);
            } else {
                intent = new Intent(dashboardOder.this, dashboardOder.class);
            }

            if (intent != null) {
                startActivity(intent);
            }
            return true;
        });
    }
}