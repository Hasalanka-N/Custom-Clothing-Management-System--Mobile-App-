package com.example.custom_clothing_order_manager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.database.TailorDB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TailorLogin extends AppCompatActivity {

    private static final String TAG = "TailorLogin";
    EditText username;
    EditText password;
    Button loginButton;
    TextView signupText;
    private TailorDB tailorDB;
    private FirebaseAuth auth;
    String tailorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_login);

        auth = FirebaseAuth.getInstance();
        tailorDB = new TailorDB();

        username = findViewById(R.id.tailorusername);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signupText = findViewById(R.id.signupText);

        loginButton.setOnClickListener(view -> {
            tailorEmail = username.getText().toString().trim();
            String userPassword = password.getText().toString().trim();

            if (tailorEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(TailorLogin.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d(TAG, "Attempting login with email: " + tailorEmail);

            tailorDB.loginTailor(tailorEmail, userPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                String userId = user.getUid();

                                Log.d(TAG, "Login successful! User ID: " + userId); // Log user ID

                                Toast.makeText(TailorLogin.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(TailorLogin.this, dashboardOder.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("user_email", tailorEmail);
                                bundle.putString("user_id", userId);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            } else {
                                Log.e(TAG, "User is null after successful login task.");
                                Toast.makeText(TailorLogin.this, "Login successful, but user is null!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e(TAG, "Login failed: " + task.getException().getMessage()); // Log the exception!
                            Toast.makeText(TailorLogin.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        signupText.setOnClickListener(view -> {
            Intent intent = new Intent(TailorLogin.this, dashboard.class);
            startActivity(intent);
        });
    }
}