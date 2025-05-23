package com.example.custom_clothing_order_manager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.adapters.ClothingAdapter;
import com.example.custom_clothing_order_manager.models.ClothingItem;

import java.util.ArrayList;
import java.util.List;

public class ClothingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ClothingAdapter adapter;
    private List<ClothingItem> clothingItems;
    private String tailorEmail, userEmail;
    private int cusid;
    private String userID, tailorID;
    private String tailorCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_clothing);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.categorytxt), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            int value1 = bundle.getInt("key1", 0);
            tailorCategory = bundle.getString("tailor_category","No Name Provided");
            tailorID = bundle.getString("tailor_id","No Name Provided");
            userEmail = bundle.getString("customer_email");
            userID = bundle.getString("user_id");
        }

        recyclerView = findViewById(R.id.recyclerView);


        clothingItems = new ArrayList<>();
        clothingItems.add(new ClothingItem("Men's Trousers", "Men's Clothing",R.drawable.mentrouser));
        clothingItems.add(new ClothingItem("Women's Dress", "Women's Clothing",R.drawable.womendress));
        clothingItems.add(new ClothingItem("Men's Shorts", "Men's Clothing",R.drawable.menshort));
        clothingItems.add(new ClothingItem("Women's Skirt", "Women's Clothing",R.drawable.skirt));
        clothingItems.add(new ClothingItem("Women's Shorts", "Women's Clothing",R.drawable.womenshort));
        clothingItems.add(new ClothingItem("Men's Shirt", "Men's Clothing",R.drawable.menshirt));
        clothingItems.add(new ClothingItem("Women's Blouse", "Women's Clothing",R.drawable.blouse));
        clothingItems.add(new ClothingItem("Kid's Trousers", "Children's Clothing",R.drawable.mentrouser));
        clothingItems.add(new ClothingItem("Kid's Shirt", "Children's Clothing",R.drawable.mentrouser));
        clothingItems.add(new ClothingItem("Kid's Jacket", "Children's Clothing",R.drawable.mentrouser));

        List<ClothingItem> filteredItems = filterClothingByCategory(clothingItems, tailorCategory);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ClothingAdapter(filteredItems, tailorID, userID);
        recyclerView.setAdapter(adapter);

        ImageView backbtn = findViewById(R.id.backBtn);

        backbtn.setOnClickListener(v -> {
            Intent intent = new Intent(ClothingActivity.this, Tailor_Profile.class);
            Bundle bundle1 = new Bundle();
            bundle1.putInt("key1",123);
            bundle1.putString("tailor_id",tailorID);
            bundle1.putString("customer_email", userEmail);
            bundle1.putString("user_id", userID);
            intent.putExtras(bundle1);
            startActivity(intent);
        });

    }

    private List<ClothingItem> filterClothingByCategory(List<ClothingItem> items, String category) {
        List<ClothingItem> filteredItems = new ArrayList<>();

        // Remove brackets and unnecessary spaces
        category = category.replace("[", "").replace("]", "").trim();
        String[] categories = category.split(",");

        for (int i = 0; i < categories.length; i++) {
            categories[i] = categories[i].trim();
        }

        for (ClothingItem item : items) {
            for (String cat : categories) {
                if (item.getCategory().equalsIgnoreCase(cat)) {
                    filteredItems.add(item);
                    break;
                }
            }
        }

        return filteredItems;
    }

}

