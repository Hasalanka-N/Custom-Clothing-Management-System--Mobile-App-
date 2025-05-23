package com.example.custom_clothing_order_manager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.database.OrdersDB;
import com.example.custom_clothing_order_manager.models.Orders;
import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDate;
import java.util.UUID;

public class Measurement extends AppCompatActivity {
    private String clothingType;
    private String itemType, material, color, note, name, phone, street, city, country, tailorId, userId;
    private String tailorEmail, userEmail;

    private EditText edtWaist, edtHip, edtInseam, edtOutseam, edtThigh, edtKnee, edtRise, edtLegOpening;
    private Button btnSave;
    private OrdersDB ordersDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            itemType = bundle.getString("clothingItemName");
            material = bundle.getString("material");
            color = bundle.getString("color");
            note = bundle.getString("note");
            name = bundle.getString("name");
            phone = bundle.getString("phone");
            street = bundle.getString("street");
            city = bundle.getString("city");
            country = bundle.getString("country");
            tailorId = bundle.getString("tailor_id");
            userId = bundle.getString("user_id");
        }

        if ("Men's Trousers".equals(itemType)) {
            setContentView(R.layout.activity_measurement);
        } else if ("Women's Blouse".equals(itemType)) {
            setContentView(R.layout.activity_measurement_input);
        } else if ("Men's Shirt".equals(itemType)) {
            setContentView(R.layout.activity_measurement_input);
        }else if ("Women's Dress".equals(itemType)){
            setContentView(R.layout.womens_dress);
        }else if ("Men's Shorts".equals(itemType)){
            setContentView(R.layout.short_m_w);
        }else if ("Women's Shorts".equals(itemType)){
            setContentView(R.layout.short_m_w);
        } else if ("Women's Skirt".equals(itemType)) {
            setContentView(R.layout.women_skirt);
        }

        edtWaist = findViewById(R.id.edtWaist);
        edtHip = findViewById(R.id.edtHip);
        edtInseam = findViewById(R.id.edtInseam);
        edtOutseam = findViewById(R.id.edtOutseam);
        edtThigh = findViewById(R.id.edtThigh);
        edtKnee = findViewById(R.id.edtKnee);
        edtRise = findViewById(R.id.edtRise);
        edtLegOpening = findViewById(R.id.edtLegOpening);
        btnSave = findViewById(R.id.button);
        ordersDB = new OrdersDB();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String measurements = "";

                if ("Men's Trousers".equals(itemType)) {
                    measurements = "waist " + edtWaist.getText().toString() + ", " +
                            "hip " + edtHip.getText().toString() + ", " +
                            "inseam " + edtInseam.getText().toString() + ", " +
                            "outseam " + edtOutseam.getText().toString() + ", " +
                            "thigh " + edtThigh.getText().toString() + ", " +
                            "knee " + edtKnee.getText().toString() + ", " +
                            "rise " + edtRise.getText().toString() + ", " +
                            "leg opening " + edtLegOpening.getText().toString();
                }
                else if ("Women's Blouse".equals(itemType)) {
                    EditText edtBust = findViewById(R.id.edtBust);
                    EditText edtWaistBlouse = findViewById(R.id.edtWaistBlouse);
                    EditText edtShoulder = findViewById(R.id.edtShoulder);
                    EditText edtSleeveLength = findViewById(R.id.edtSleeveLength);

                    measurements = "bust " + edtBust.getText().toString() + ", " +
                            "waist " + edtWaistBlouse.getText().toString() + ", " +
                            "shoulder " + edtShoulder.getText().toString() + ", " +
                            "sleeve length " + edtSleeveLength.getText().toString();

                } else if ("Women's Dress".equals(itemType)) {
                    EditText edtBust = findViewById(R.id.bust_size);
                    EditText edtWaist = findViewById(R.id.waist_size);
                    EditText edtHip = findViewById(R.id.hip_size);
                    EditText edtShoulder = findViewById(R.id.shoulder_width);
                    EditText edtSleeveLength = findViewById(R.id.sleeve_length);
                    EditText edtDressLength = findViewById(R.id.dress_length);

                    measurements = "bust " + edtBust.getText().toString() + ", " +
                            "waist " + edtWaist.getText().toString() + ", " +
                            "hip " + edtHip.getText().toString() + ", " +
                            "shoulder " + edtShoulder.getText().toString() + ", " +
                            "sleeve length " + edtSleeveLength.getText().toString() + ", " +
                            "dress length " + edtDressLength.getText().toString();

                }else if ("Men's Shorts".equals(itemType)) {
                    EditText edtWaist = findViewById(R.id.waist_size);
                    EditText edtHip = findViewById(R.id.hip_size);
                    EditText edtInseam = findViewById(R.id.inseam_length);
                    EditText edtOutseam = findViewById(R.id.outseam_length);
                    EditText edtThigh = findViewById(R.id.thigh_width);
                    EditText edtKnee = findViewById(R.id.knee_width);

                    measurements = "waist " + edtWaist.getText().toString() + ", " +
                            "hip " + edtHip.getText().toString() + ", " +
                            "inseam " + edtInseam.getText().toString() + ", " +
                            "outseam " + edtOutseam.getText().toString() + ", " +
                            "thigh " + edtThigh.getText().toString() + ", " +
                            "knee " + edtKnee.getText().toString();

                }else if ("Women's Shorts".equals(itemType)) {
                    EditText edtWaist = findViewById(R.id.waist_size);
                    EditText edtHip = findViewById(R.id.hip_size);
                    EditText edtInseam = findViewById(R.id.inseam_length);
                    EditText edtOutseam = findViewById(R.id.outseam_length);
                    EditText edtThigh = findViewById(R.id.thigh_width);
                    EditText edtKnee = findViewById(R.id.knee_width);

                    measurements = "waist " + edtWaist.getText().toString() + ", " +
                            "hip " + edtHip.getText().toString() + ", " +
                            "inseam " + edtInseam.getText().toString() + ", " +
                            "outseam " + edtOutseam.getText().toString() + ", " +
                            "thigh " + edtThigh.getText().toString() + ", " +
                            "knee " + edtKnee.getText().toString();

                }else if ("Women's Skirt".equals(itemType)) {
                    EditText edtWaist = findViewById(R.id.waist_size);
                    EditText edtHip = findViewById(R.id.hip_size);
                    EditText edtSkirtLength = findViewById(R.id.skirt_length);
                    EditText edtHemWidth = findViewById(R.id.hem_width);

                    measurements = "waist " + edtWaist.getText().toString() + ", " +
                            "hip " + edtHip.getText().toString() + ", " +
                            "skirt length " + edtSkirtLength.getText().toString() + ", " +
                            "hem width " + edtHemWidth.getText().toString();

                }else if ("Men's Shirt".equals(itemType)) {
                    EditText edtBust = findViewById(R.id.edtBust);
                    EditText edtWaistBlouse = findViewById(R.id.edtWaistBlouse);
                    EditText edtShoulder = findViewById(R.id.edtShoulder);
                    EditText edtSleeveLength = findViewById(R.id.edtSleeveLength);

                    measurements = "bust " + edtBust.getText().toString() + ", " +
                            "waist " + edtWaistBlouse.getText().toString() + ", " +
                            "shoulder " + edtShoulder.getText().toString() + ", " +
                            "sleeve length " + edtSleeveLength.getText().toString();

                }


                String orderId = UUID.randomUUID().toString();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String currentDate = getTodayDate();

                Orders order = new Orders(
                        orderId,
                        itemType,
                        material,
                        color,
                        note,
                        name,
                        phone,
                        street,
                        city,
                        country,
                        userId,
                        tailorId,
                        currentDate,
                        measurements,
                        "Pending"
                );

                ordersDB.placeOder(order).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Measurement.this, "Order saved successfully!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Measurement.this, ClothingActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("user_id", userId);
                        intent.putExtras(bundle1);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Measurement.this, "Failed to save order.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public String getTodayDate() {
        LocalDate today = LocalDate.now();
        return today.toString();
    }
}

