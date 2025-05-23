package com.example.custom_clothing_order_manager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.custom_clothing_order_manager.R;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.categorytxt), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button clientButton = findViewById(R.id.clientbtn);
        Button tailorButton = findViewById(R.id.tailorbtn);

        clientButton.setOnClickListener(V ->{
            Intent intent = new Intent(MainActivity2.this, Login.class);
            Bundle bundle = new Bundle();
            bundle.putInt("key1",123);

            intent.putExtras(bundle);
            startActivity(intent);
        });

        tailorButton.setOnClickListener(V -> {
            Intent intent = new Intent(MainActivity2.this, TailorLogin.class);
            Bundle bundle = new Bundle();
            bundle.putInt("key1",123);

            intent.putExtras(bundle);
            startActivity(intent);
        });


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            int value1 = bundle.getInt("key1", 0);
        }

    }
}