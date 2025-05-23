package com.example.custom_clothing_order_manager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.custom_clothing_order_manager.R;

public class MeasurementInput extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_measurement_input);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.categorytxt), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            int value1 = bundle.getInt("key1", 0);
        }

      /*  ImageView homebtn = findViewById(R.id.homebtn);
        homebtn.setOnClickListener(v -> {
            Intent intent2 = new Intent(MeasurementInput.this, Order_dashboard.class);
            Bundle bundle2 = new Bundle();
            bundle2.putInt("key1",123);

            intent2.putExtras(bundle2);
            startActivity(intent2);*/
       // });
    }
}