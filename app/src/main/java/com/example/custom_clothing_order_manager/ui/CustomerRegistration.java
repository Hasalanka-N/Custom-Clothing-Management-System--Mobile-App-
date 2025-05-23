package com.example.custom_clothing_order_manager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.database.CustomerDB;
import com.example.custom_clothing_order_manager.database.CustomersDB;
import com.example.custom_clothing_order_manager.models.Customer;
import com.example.custom_clothing_order_manager.models.Customers;
import com.example.custom_clothing_order_manager.ui.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.FirebaseApp;

public class CustomerRegistration extends AppCompatActivity {

    EditText nameTxt, emailTxt, phoneTxt, passwordTxt, cpasswordTxt;
    Button submitBtn;
    FirebaseAuth auth;
    CustomersDB customerDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        auth = FirebaseAuth.getInstance();
        customerDB = new CustomersDB();

        nameTxt = findViewById(R.id.nameTxt);
        emailTxt = findViewById(R.id.emailTxt);
        phoneTxt = findViewById(R.id.phoneTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        cpasswordTxt = findViewById(R.id.confirmPasswordTxt);
        submitBtn = findViewById(R.id.btnRegister);

        submitBtn.setOnClickListener(v -> registerCustomer());
    }

    public void registerCustomer() {
        String name = nameTxt.getText().toString().trim();
        String email = emailTxt.getText().toString().trim();
        String phone = phoneTxt.getText().toString().trim();
        String password = passwordTxt.getText().toString().trim();
        String cpassword = cpasswordTxt.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || cpassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(cpassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = auth.getCurrentUser();
                if (firebaseUser != null) {
                    String userId = firebaseUser.getUid();
                    Customers customer = new Customers(userId, name, email, phone, password);

                    // Save customer data to Firestore
                    customerDB.insertCustomer(customer).addOnCompleteListener(task1 -> { // FIX HERE
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
}



   /* public void registerCustomer() {
        String name = nameTxt.getText().toString().trim();
        String email = emailTxt.getText().toString().trim();
        String phone = phoneTxt.getText().toString().trim();
        String password = passwordTxt.getText().toString().trim();
        String cpassword = cpasswordTxt.getText().toString().trim();

        if (name == null || email == null || phone == null || password == null || cpassword == null ||
                name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || cpassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(cpassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        Customer customer = new Customer(0, name, email, phone, password);

        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Password: " + password);


        long insertedId = customerDB.insertCustomer(customer);

        System.out.println("Inserted ID: " + insertedId);

        if (insertedId > 0) {
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            nameTxt.getText().clear();
            emailTxt.getText().clear();
            phoneTxt.getText().clear();
            passwordTxt.getText().clear();
            cpasswordTxt.getText().clear();

            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Email already exists or Registration failed", Toast.LENGTH_SHORT).show();
        }
    }
}*/

