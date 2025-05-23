package com.example.custom_clothing_order_manager.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.custom_clothing_order_manager.R;

import java.util.Arrays;
import java.util.List;

public class OderDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_oder_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.categorytxt), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.orderRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> orderIds = Arrays.asList("#12345", "#67890", "#11223");
        List<String> orderDates = Arrays.asList("December 1, 2024", "December 2, 2024", "December 3, 2024");

        // Set up adapter
        //ArrayAdapter  adapter = new ArrayAdapter(orderIds, orderDates); // Ensure the correct adapter name
        //recyclerView.setAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int value1 = bundle.getInt("key1", 0);
        }
    }
}
