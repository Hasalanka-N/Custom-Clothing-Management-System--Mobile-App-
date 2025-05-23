package com.example.custom_clothing_order_manager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.database.OrdersDB;

public class UpdateOderStatus extends AppCompatActivity {

    private String orderId, measurements, itemName, phone, date, status, cusName, userId;
    private Spinner statusSpinner;
    private OrdersDB ordersDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_oder_status);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ordersDB = new OrdersDB();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderId = bundle.getString("orderId");
            itemName = bundle.getString("itemName");
            phone = bundle.getString("phone");
            date = bundle.getString("date");
            status = bundle.getString("status");
            cusName = bundle.getString("cusName");
            measurements = bundle.getString("measurements");
            userId = bundle.getString("user_Id");
        }

        TextView measurementsTxt = findViewById(R.id.textViewMeasurements);
        measurementsTxt.setText("Measurements: " + measurements);
        TextView orderIdTxt = findViewById(R.id.textViewOrderId);
        orderIdTxt.setText("Order ID: #" + orderId);

        statusSpinner = findViewById(R.id.statusSpinner);
        String[] statusOptions = {"Pending", "Accepted", "Design", "Ready", "Delivery", "conform"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);

        int position = adapter.getPosition(status);
        if (position != -1) {
            statusSpinner.setSelection(position);
        }

        Button updateButton = findViewById(R.id.buttonUpdateStatus);
        updateButton.setOnClickListener(v -> updateOrderStatus());
    }

    private void updateOrderStatus() {
        String newStatus = statusSpinner.getSelectedItem().toString();

        ordersDB.updateOrderStatus(orderId, newStatus, new OrdersDB.OnOrderStatusUpdateListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(UpdateOderStatus.this, "Order status updated successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateOderStatus.this, dashboardOder.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("user_id", userId);
                intent.putExtras(bundle1);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(UpdateOderStatus.this, "Failed to update status: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        ImageView backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateOderStatus.this, dashboardOder.class);
            Bundle bundle1 = new Bundle();
            bundle1.putInt("key1", 123);
            userId = bundle1.getString("user_id");
            intent.putExtras(bundle1);
            startActivity(intent);
        });
    }
}
