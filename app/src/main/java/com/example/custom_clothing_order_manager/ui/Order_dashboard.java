package com.example.custom_clothing_order_manager.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.adapters.SecondArrayAdapter;

import java.util.Arrays;
import java.util.List;

public class Order_dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oder_dashboard_activity);

        RecyclerView recyclerView = findViewById(R.id.oder_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> orderIds = Arrays.asList("#12345", "#67890", "#11223");
        List<String> itemNames = Arrays.asList("Shirt", "Trousers", "Jacket");
        List<String> itemPrices = Arrays.asList("$50", "$40", "$100");
        List<String> orderDates = Arrays.asList("December 1, 2024", "December 2, 2024", "December 3, 2024");

       /* SecondArrayAdapter adapter = new SecondArrayAdapter(itemNames, itemPrices, orderIds, orderDates);
        recyclerView.setAdapter(adapter);*/

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            int value1 = bundle.getInt("key1", 0);
        }
    }
}
