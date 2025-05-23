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
import com.example.custom_clothing_order_manager.adapters.TailorAdapter;
import com.example.custom_clothing_order_manager.database.TailorDatabaseHelper;
import com.example.custom_clothing_order_manager.models.Tailor;

import java.util.List;

public class ViewAllTailorsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TailorAdapter tailorAdapter;
    private TailorDatabaseHelper tailorDatabaseHelper;
    private List<Tailor> tailorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_all_tailors);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.categorytxt), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int value1 = bundle.getInt("key1", 0);
        }

        recyclerView = findViewById(R.id.recyclerViewTailors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tailorDatabaseHelper = new TailorDatabaseHelper(this);

        tailorList = tailorDatabaseHelper.getAllTailors();

        tailorAdapter = new TailorAdapter(tailorList);
        recyclerView.setAdapter(tailorAdapter);
    }


}