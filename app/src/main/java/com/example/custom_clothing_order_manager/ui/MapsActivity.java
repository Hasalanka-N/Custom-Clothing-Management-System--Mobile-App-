package com.example.custom_clothing_order_manager.ui;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.models.Tailors;
import com.example.custom_clothing_order_manager.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private String userEmail, userID;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userEmail = bundle.getString("customer_email", "No Name Provided");
            userID = bundle.getString("user_id", userID);
        }

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, MainActivity.class);
            Bundle bundle1 = new Bundle();
            bundle1.putString("customer_email", userEmail);
            bundle1.putString("user_id",userID);
            intent.putExtras(bundle1);
            startActivity(intent);
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }

        loadTailorsFromFirebase();
    }

    private void loadTailorsFromFirebase() {
        CollectionReference tailorsRef = db.collection("tailors");

        tailorsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Tailors> tailorList = new ArrayList<>();
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    for (DocumentSnapshot document : querySnapshot) {
                        Tailors tailor = document.toObject(Tailors.class);
                        if (tailor != null) {
                            tailorList.add(tailor);
                        }
                    }
                }
                displayTailorsOnMap(tailorList);
            } else {
                Toast.makeText(this, "Failed to load tailors!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayTailorsOnMap(List<Tailors> tailorList) {
        for (Tailors tailor : tailorList) {
            LatLng location = new LatLng(tailor.getLatitude(), tailor.getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(tailor.getName())
                    .snippet(tailor.getShopName()));
            marker.setTag(tailor);
            marker.showInfoWindow();
        }

        if (!tailorList.isEmpty()) {
            LatLng firstTailorLocation = new LatLng(tailorList.get(0).getLatitude(), tailorList.get(0).getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstTailorLocation, 10));
        }

        mMap.setOnMarkerClickListener(marker -> {
            Tailors tailor = (Tailors) marker.getTag();
            if (tailor != null) {
                Intent intent = new Intent(MapsActivity.this, Tailor_Profile.class);
                Bundle bundle = new Bundle();
                bundle.putString("tailor_name", tailor.getName());
                bundle.putString("tailor_details", tailor.getShopName());
                bundle.putString("tailor_email", tailor.getEmail());
                bundle.putString("tailor_id", tailor.getId());
                bundle.putString("shop_name", tailor.getShopName());
                bundle.putString("tailor_category", tailor.getSpecializations());
                bundle.putString("customer_email", userEmail);
                bundle.putString("user_id", userID);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            return false;
        });
    }

    private void enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableUserLocation();
            }
        }
    }
}

