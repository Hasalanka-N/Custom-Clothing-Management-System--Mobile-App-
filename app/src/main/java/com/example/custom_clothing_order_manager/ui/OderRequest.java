package com.example.custom_clothing_order_manager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.models.Oder;

import java.io.Serializable;

public class OderRequest extends AppCompatActivity {

    private String selectedFabric = "";
    private String tailorID, userID;
    private String itemtype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_oder_request);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            int value1 = bundle.getInt("key1", 0);
            tailorID = bundle.getString("tailor_id","No Name Provided");
            itemtype = bundle.getString("clothingItemName","No Name Provided");
            userID = bundle.getString("user_id");
        }

        Spinner fabricMaterialSpinner = findViewById(R.id.spinner_fabric_material);

        String[] fabricMaterials = {"Select Material", "Cotton", "Linen", "Wool", "Silk",
                "Polyester", "Nylon", "Acrylic", "Denim", "Leather", "Velvet"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, fabricMaterials);
        fabricMaterialSpinner.setAdapter(adapter);

        fabricMaterialSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFabric = parent.getItemAtPosition(position).toString();
                if (position != 0) {
                    Toast.makeText(OderRequest.this, "Selected: " + selectedFabric, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner matirialtxt = findViewById(R.id.spinner_fabric_material);
        String material = matirialtxt.getSelectedItem().toString();

        EditText colorTxt = findViewById(R.id.colorText);
        String color = colorTxt.getText().toString();

        EditText noteTxt = findViewById(R.id.editTextNote);
        String note = noteTxt.getText().toString();

        EditText nameTxt = findViewById(R.id.nameText);
        String name = nameTxt.getText().toString();

        EditText phoneTxt = findViewById(R.id.phoneText);
        String phone = phoneTxt.getText().toString();

        EditText streetTxt = findViewById(R.id.streetText);
        String street = streetTxt.getText().toString();

        EditText cityTxt = findViewById(R.id.cityText);
        String city = cityTxt.getText().toString();

        EditText countyTxt = findViewById(R.id.countryText);
        String country = countyTxt.getText().toString();



        Button buttonnxt = findViewById(R.id.nextbtn);
        Button backBtn;


        buttonnxt.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), Measurement.class);

            Bundle bundle1 = new Bundle();

            bundle1.putString("clothingItemName", itemtype);
            bundle1.putString("material", selectedFabric);
            bundle1.putString("color", colorTxt.getText().toString());
            bundle1.putString("note", noteTxt.getText().toString());
            bundle1.putString("name", nameTxt.getText().toString());
            bundle1.putString("phone", phoneTxt.getText().toString());
            bundle1.putString("street", streetTxt.getText().toString());
            bundle1.putString("city", cityTxt.getText().toString());
            bundle1.putString("country", countyTxt.getText().toString());
            bundle1.putString("tailor_id", tailorID);
            bundle1.putString("user_id", userID);

            intent.putExtras(bundle1);

            v.getContext().startActivity(intent);
        });


    }

    public String getSelectedFabric() {
        return selectedFabric;
    }
}

