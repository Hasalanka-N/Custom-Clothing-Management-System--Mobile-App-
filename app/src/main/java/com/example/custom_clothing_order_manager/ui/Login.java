package com.example.custom_clothing_order_manager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.database.CustomersDB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText username, password;
    private Button loginButton;
    private TextView signupText;
    private CustomersDB customersDB;
    private FirebaseAuth auth;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        customersDB = new CustomersDB();

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signupText = findViewById(R.id.signupText);

        loginButton.setOnClickListener(view -> {
            userEmail = username.getText().toString().trim();
            String userPassword = password.getText().toString().trim();

            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(Login.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            customersDB.loginCustomer(userEmail, userPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                String userId = user.getUid();

                                Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Login.this, MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("customer_email", userEmail);
                                bundle.putString("user_id", userId);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Toast.makeText(Login.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        signupText.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, CustomerRegistration.class);
            startActivity(intent);
        });
    }
}
