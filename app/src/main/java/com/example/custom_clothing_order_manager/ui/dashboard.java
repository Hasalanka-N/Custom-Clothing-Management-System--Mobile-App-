/*package com.example.custom_clothing_order_manager.ui;

import com.example.custom_clothing_order_manager.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import java.io.IOException;

public class dashboard extends FragmentActivity implements OnMapReadyCallback {

    private MapView mapView;
    private TextView locationText;
    private static final int PICK_IMAGE = 100;
    private ImageView imageView;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mapView = findViewById(R.id.mapView2);
        locationText = findViewById(R.id.location_text);

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(this);
        }
        imageView = findViewById(R.id.imageView);
        Button selectImageButton = findViewById(R.id.selectImageButton);

        selectImageButton.setOnClickListener(v -> openGallery());

    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE && data != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                Log.e("ImageSelection", "Error loading image", e);
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMapClickListener(latLng -> {

            double latitude = latLng.latitude;
            double longitude = latLng.longitude;

            locationText.setText("Latitude: " + latitude + "\nLongitude: " + longitude);


            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }
}*/


package com.example.custom_clothing_order_manager.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.database.TailorDB;
import com.example.custom_clothing_order_manager.models.Customers;
import com.example.custom_clothing_order_manager.models.Tailors;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class dashboard extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private TextView locationText;
    private ImageView imageView;
    private Button selectImageButton;
    private Button signupButton;
    private String imagePath;
    private Double latitude;
    private Double longitude;
    private EditText tailorNameTxt;
    private EditText tailorEmailTxt;
    private EditText tailorPasswordTxt;
    private EditText shopNameTxt;
    private CheckBox womenClothing;
    private CheckBox menClothing;
    private CheckBox bridalClothing;
    private CheckBox childrenClothing;
    FirebaseAuth auth;
    TailorDB tailordb;

    private final ActivityResultLauncher<Intent> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                Uri imageUri = data.getData();
                                try {
                                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                                    imagePath = saveImageToInternalStorage(this, bitmap, "tailor_image.png");
                                    imageView.setImageBitmap(bitmap);
                                } catch (IOException e) {
                                    Log.e("ImageSelection", "Error loading image", e);
                                    Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mapView = findViewById(R.id.mapView2);
        locationText = findViewById(R.id.location_text);
        imageView = findViewById(R.id.imageView);
        selectImageButton = findViewById(R.id.selectImageButton);
        signupButton = findViewById(R.id.signupButton);

        tailorNameTxt = findViewById(R.id.tailorNameTxt);
        tailorEmailTxt = findViewById(R.id.tailorEmailTxt);
        tailorPasswordTxt = findViewById(R.id.tailorPasswordTxt);
        shopNameTxt = findViewById(R.id.shopNameTxt);
        womenClothing = findViewById(R.id.women_clothing);
        menClothing = findViewById(R.id.men_clothing);
        bridalClothing = findViewById(R.id.bridal_clothing);
        childrenClothing = findViewById(R.id.children_clothing);

        auth = FirebaseAuth.getInstance();
        tailordb = new TailorDB();

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(this);
        }

        selectImageButton.setOnClickListener(v -> openGallery());

        signupButton.setOnClickListener(v -> registerTailor());
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(galleryIntent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.setOnMapClickListener(latLng -> {
            latitude = latLng.latitude;
            longitude = latLng.longitude;

            locationText.setText("Latitude: " + latitude + "\nLongitude: " + longitude);

            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));
        });
    }

    private void registerTailor() {
        String tailorName = tailorNameTxt.getText().toString();
        String tailorEmail = tailorEmailTxt.getText().toString();
        String tailorPassword = tailorPasswordTxt.getText().toString();
        String shopName = shopNameTxt.getText().toString();

        List<String> specializations = new ArrayList<>();
        if (womenClothing.isChecked()) specializations.add("Women's Clothing");
        if (menClothing.isChecked()) specializations.add("Men's Clothing");
        if (bridalClothing.isChecked()) specializations.add("Bridal Wear");
        if (childrenClothing.isChecked()) specializations.add("Children's Clothing");

        if (tailorName.isEmpty() || tailorEmail.isEmpty() || tailorPassword.isEmpty() || shopName.isEmpty() || specializations.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(tailorEmail, tailorPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = auth.getCurrentUser();
                if (firebaseUser != null) {
                    String userId = firebaseUser.getUid();

                    Tailors tailors = new Tailors(userId, tailorName, tailorEmail, tailorPassword, shopName, specializations.toString(), imagePath, latitude, longitude);

                    // Save customer data to Firestore
                    tailordb.insertTailor(tailors).addOnCompleteListener(task1 -> { // FIX HERE
                        if (task1.isSuccessful()) {
                            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, Login.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Toast.makeText(this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void clearForm() {
        tailorNameTxt.setText("");
        tailorEmailTxt.setText("");
        tailorPasswordTxt.setText("");
        shopNameTxt.setText("");
        womenClothing.setChecked(false);
        menClothing.setChecked(false);
        bridalClothing.setChecked(false);
        childrenClothing.setChecked(false);
        imageView.setImageResource(android.R.drawable.ic_menu_gallery);
        imagePath = null;
        locationText.setText("Location not selected");
        latitude = null;
        longitude = null;
    }


    private String saveImageToInternalStorage(Context context, Bitmap bitmap, String fileName) throws IOException {
        File directory = context.getFilesDir();
        File imageFile = new File(directory, fileName);
        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        }
        return imageFile.getAbsolutePath();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}



