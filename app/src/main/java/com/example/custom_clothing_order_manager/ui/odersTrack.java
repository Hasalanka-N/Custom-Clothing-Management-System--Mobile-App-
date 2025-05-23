package com.example.custom_clothing_order_manager.ui;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.custom_clothing_order_manager.R;
import android.graphics.Color;

public class odersTrack extends AppCompatActivity {
    private String oderId, status, street, country, oderDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_oders_track);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.categorytxt), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            int value1 = bundle.getInt("key1", 0);
            oderId = bundle.getString("orderId");
            status = bundle.getString("status");
            street = bundle.getString("street");
            country = bundle.getString("country");
            oderDate = bundle.getString("oderDate");
        }

        TextView oderIdTxt = findViewById(R.id.textView17);
        TextView oderDateTxt = findViewById(R.id.textView18);
        TextView oderSteetTxt = findViewById(R.id.textView32);
        TextView oderCountryTxt = findViewById(R.id.textView33);

        View oderAccept = findViewById(R.id.view5);
        View design = findViewById(R.id.view8);
        View redy = findViewById(R.id.view11);
        View dilevery = findViewById(R.id.view13);
        View conform = findViewById(R.id.view15);
        View oderAcceptL = findViewById(R.id.view6);
        View designL = findViewById(R.id.view9);
        View radyL = findViewById(R.id.view12);
        View diliveryL = findViewById(R.id.view14);

        oderIdTxt.setText(oderId);
        oderDateTxt.setText(oderDate);
        oderSteetTxt.setText(street);
        oderCountryTxt.setText(country);

        if ("Accepted".equalsIgnoreCase(status)) {
            oderAccept.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            oderAcceptL.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
        } else if ("Design".equalsIgnoreCase(status)) {
            oderAccept.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            oderAcceptL.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            design.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            designL.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
        } else if ("Ready".equalsIgnoreCase(status)) {
            oderAccept.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            oderAcceptL.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            design.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            designL.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            redy.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            radyL.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
        } else if ("Delivery".equalsIgnoreCase(status)) {
            oderAccept.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            oderAcceptL.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            design.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            designL.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            redy.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            radyL.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            dilevery.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            diliveryL.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
        } else if ("conform".equalsIgnoreCase(status)) {
            oderAccept.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            oderAcceptL.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            design.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            designL.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            redy.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            radyL.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            dilevery.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            diliveryL.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            conform.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
        }
    }
}