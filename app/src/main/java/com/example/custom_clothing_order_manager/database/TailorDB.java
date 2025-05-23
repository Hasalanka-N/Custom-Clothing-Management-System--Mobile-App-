package com.example.custom_clothing_order_manager.database;

import android.util.Log;

import com.example.custom_clothing_order_manager.models.Tailors;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class TailorDB {

    private static final String TAG = "TailorDatabaseHelper";
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    public TailorDB() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public Task<Void> insertTailor(Tailors tailor) {

        Map<String, Object> tailorData = new HashMap<>();
        tailorData.put("email", tailor.getEmail());
        tailorData.put("imagePath", tailor.getImagePath());
        tailorData.put("latitude", tailor.getLatitude());
        tailorData.put("longitude", tailor.getLongitude());
        tailorData.put("name", tailor.getName());
        tailorData.put("shopName", tailor.getShopName());
        tailorData.put("specializations", tailor.getSpecializations());

        return db.collection("tailors").document(tailor.getId()).set(tailorData);
    }

    public Task<AuthResult> loginTailor(String email, String password) {
        return auth.signInWithEmailAndPassword(email, password);
    }

    public void getTailorById(String tailorId, OnTailorOperationCompleteListener listener) {
        DocumentReference docRef = db.collection("tailors").document(tailorId);

        docRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Tailors tailors = documentSnapshot.toObject(Tailors.class);
                        if (tailors != null) {
                            listener.onSuccess(tailors);
                        } else {
                            listener.onFailure("Failed to convert document to Tailors object.");
                            Log.e(TAG, "Failed to convert document to Tailors object.");
                        }
                    } else {
                        listener.onFailure("Tailor not found.");
                        Log.d(TAG, "No such document");
                    }
                })
                .addOnFailureListener(e -> {
                    listener.onFailure("Error getting Tailor: " + e.getMessage());
                    Log.e(TAG, "Error getting Tailor", e);
                });
    }

    public Task<QuerySnapshot> getAllTailors() {
        return db.collection("tailors").get();
    }

    public interface OnTailorOperationCompleteListener {
        void onSuccess(Tailors tailor);
        void onFailure(String errorMessage);
    }
}


