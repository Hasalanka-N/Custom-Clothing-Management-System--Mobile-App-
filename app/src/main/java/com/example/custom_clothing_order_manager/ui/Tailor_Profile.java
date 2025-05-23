package com.example.custom_clothing_order_manager.ui;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.adapters.SecondArrayAdapter;
import com.example.custom_clothing_order_manager.database.OrdersDB;
import com.example.custom_clothing_order_manager.database.TailorDB;
import com.example.custom_clothing_order_manager.models.Orders;
import com.example.custom_clothing_order_manager.models.Tailors;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Tailor_Profile extends AppCompatActivity {

    private String tailorCategory;
    private String tailorName, tailorID, userID;
    private String tailorEmail, userEmail;
    private String shopName;
    private int  cusid;
    private TailorDB tailorDB;
    private List<Orders> orderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tailor_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.categorytxt), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tailorDB = new TailorDB();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            int value1 = bundle.getInt("key1", 0);
            tailorEmail = bundle.getString("tailor_email","No Name Provided");
            userEmail = bundle.getString("customer_email", "No Name Provided");
            userID = bundle.getString("user_id","");
            tailorID = bundle.getString("tailor_id","");
        }


        TextView tailorNameTextView = findViewById(R.id.tailorNameTextView);
        TextView shopNameTextView = findViewById(R.id.shopnamelbl);
        TextView emailTextView = findViewById(R.id.textView47);
        TextView categoryTextView = findViewById(R.id.textView8);
        TextView idtxtview = findViewById(R.id.textView48);
        TextView complet = findViewById(R.id.textView51);


        tailorDB.getTailorById(tailorID, new TailorDB.OnTailorOperationCompleteListener() {
            @Override
            public void onSuccess(Tailors tailor) {
                tailorName = tailor.getName();
                shopName = tailor.getShopName();
                tailorCategory = tailor.getSpecializations();
                String imagePath = tailor.getImagePath();

                ImageView tailorImageView = findViewById(R.id.imageView13);

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

                runOnUiThread(() -> {
                    tailorNameTextView.setText(tailorName);
                    shopNameTextView.setText(shopName);
                    categoryTextView.setText(tailorCategory);
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                runOnUiThread(() -> {
                    tailorNameTextView.setText("Error Loading");
                    shopNameTextView.setText("Error Loading");
                    categoryTextView.setText("Error Loading");
                });
            }
        });

        OrdersDB ordersDB = new OrdersDB();
        ordersDB.getOdersByTailor(tailorID, new OrdersDB.OnOrdersFetchedListener() {
            @Override
            public void onSuccess(List<Orders> orders) {
                List<Orders> completedOrders = new ArrayList<>();
                for (Orders order : orders) {
                    if ("Confirm".equalsIgnoreCase(order.getStatus())) {
                        completedOrders.add(order);
                    }
                }

                long completedOrderCount = completedOrders.size();
                complet.setText(String.valueOf(completedOrderCount));

                orderList.clear();
                orderList.addAll(completedOrders);
            }

            @Override
            public void onFailure(String errorMessage) {
                complet.setText("Error Loading");
            }
        });


        tailorNameTextView.setText(tailorName);
        shopNameTextView.setText(shopName);
        emailTextView.setText(tailorEmail);
        categoryTextView.setText(tailorCategory);
        idtxtview.setText(tailorID);

        ImageView backbtn = findViewById(R.id.backBtn);

        backbtn.setOnClickListener(v -> {
            Intent intent = new Intent(Tailor_Profile.this, MapsActivity.class);
            Bundle bundle1 = new Bundle();
            bundle1.putInt("key1",123);
            bundle1.putString("customer_email", userEmail);
            bundle1.putString("user_id", userID);
            intent.putExtras(bundle1);
            startActivity(intent);
        });

        Button oderbtn = findViewById(R.id.oderbtn);

        oderbtn.setOnClickListener(v -> {
            Intent intent1 = new Intent(Tailor_Profile.this, ClothingActivity.class);
            Bundle bundle2 = new Bundle();
            bundle2.putInt("key",123);
            bundle2.putString("tailor_category",tailorCategory);
            bundle2.putString("tailor_id",tailorID);
            bundle2.putString("customer_email", userEmail);
            bundle2.putString("user_id", userID);
            intent1.putExtras(bundle2);
            startActivity(intent1);
        });

        ImageView msgBtn = findViewById(R.id.msgButton);

        msgBtn.setOnClickListener(v -> {
            Intent intent3 = new Intent(Tailor_Profile.this, CusChat.class);
            Bundle bundle3 = new Bundle();
            bundle3.putInt("key",123);
            bundle3.putString("resever_ID",tailorID);
            bundle3.putString("tailor_email",tailorEmail);
            bundle3.putString("customer_email", userEmail);
            bundle3.putString("userName",shopName);
            bundle3.putString("user_id", userID);
            intent3.putExtras(bundle3);
            startActivity(intent3);
        });

        BottomNavigationView bottomNav = findViewById(R.id.BottomNavigationView);
        bottomNav.setSelectedItemId(R.id.none);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Intent intent = null;

            if (itemId == R.id.home) {
                intent = new Intent(Tailor_Profile.this, MainActivity.class);
                Bundle bundle4 = new Bundle();
                bundle4.putInt("key", 123);
                bundle4.putString("customer_email", userEmail);
                intent.putExtras(bundle4);

            } else if (itemId == R.id.item_2) {
                intent = new Intent(Tailor_Profile.this, odersTrack.class);
            } else if (itemId == R.id.item_3) {
                intent = new Intent(Tailor_Profile.this, odersTrack.class);
            } else if (itemId == R.id.item_4) {
                intent = new Intent(Tailor_Profile.this, MainActivity2.class);
            } else {
                intent = new Intent(Tailor_Profile.this, Tailor_Profile.class);
            }

            if (intent != null) {
                startActivity(intent);
                finish();
            }

            return true;
        });

    }
}