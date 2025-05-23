package com.example.custom_clothing_order_manager.database;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.custom_clothing_order_manager.models.Customers;
import com.example.custom_clothing_order_manager.models.Tailors;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class CustomersDB {

    private static final String TAG = "CustomersDB";
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    public CustomersDB() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public Task<AuthResult> registerCustomer(String email, String password) {
        return auth.createUserWithEmailAndPassword(email, password);
    }

    public Task<Void> insertCustomer(Customers customer) {

        Map<String, Object> customerData = new HashMap<>();
        customerData.put("userId", customer.getId());
        customerData.put("name", customer.getName());
        customerData.put("email", customer.getEmail());
        customerData.put("phone", customer.getPhone());

        return db.collection("customers").document(customer.getId()).set(customerData);
    }

    public Task<AuthResult> loginCustomer(String email, String password) {
        return auth.signInWithEmailAndPassword(email, password);
    }

    public Task<DocumentSnapshot> getCustomerByEmail(String email) {
        return db.collection("customers")
                .whereEqualTo("email", email)
                .get()
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (!querySnapshot.isEmpty()) {
                            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                            return document;
                        } else {
                            throw new Exception("No customer found with the provided email.");
                        }
                    } else {
                        throw task.getException();
                    }
                });
    }

    public void getCustomerById(Context context, String customerId, OnCustomerDataFetchedListener listener) {
        DocumentReference docRef = db.collection("customers").document(customerId);

        docRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Customers customer = documentSnapshot.toObject(Customers.class);
                        if (customer != null) {
                            listener.onSuccess(customer);
                        } else {
                            listener.onFailure("Failed to convert document to Customers object.");
                            Log.e(TAG, "Failed to convert document to Customers object.");
                        }
                    } else {
                        listener.onFailure("Customer not found.");
                        Log.d(TAG, "No such document");
                    }
                })
                .addOnFailureListener(e -> {
                    listener.onFailure("Error getting customer: " + e.getMessage());
                    Log.e(TAG, "Error getting customer", e);
                });
    }


    public interface OnCustomerDataFetchedListener {
        void onSuccess(Customers customer);
        void onFailure(String errorMessage);
    }

}
