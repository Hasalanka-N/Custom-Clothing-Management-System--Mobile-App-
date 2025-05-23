package com.example.custom_clothing_order_manager.database;

import android.util.Log;

import com.example.custom_clothing_order_manager.models.Orders;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersDB {

    private static final String TAG = "OdersDB";
    private FirebaseFirestore db;

    public OrdersDB() {
        db = FirebaseFirestore.getInstance();
    }

    public Task<Void> placeOder(Orders orders) {
        Map<String, Object> odersData = new HashMap<>();
        odersData.put("id", orders.getId());
        odersData.put("itemType", orders.getItemType());
        odersData.put("material", orders.getMaterial());
        odersData.put("color", orders.getColor());
        odersData.put("note", orders.getNote());
        odersData.put("cusName", orders.getCusName());
        odersData.put("phone", orders.getPhone());
        odersData.put("street", orders.getStreet());
        odersData.put("city", orders.getCity());
        odersData.put("country", orders.getCountry());
        odersData.put("customerId", orders.getCustomerId());
        odersData.put("tailorId", orders.getTailorId());
        odersData.put("date", orders.getDate());
        odersData.put("measurement", orders.getMeasurement());
        odersData.put("status", orders.getStatus());

        return db.collection("orders").document(orders.getId()).set(odersData);

    }

    public void getOrdersByCustomerId(String customerId, OnOrdersFetchedListener listener) {
        Query query = db.collection("orders").whereEqualTo("customerId", customerId);

        query.get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Orders> orderList = new ArrayList<>();
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        Orders order = document.toObject(Orders.class);
                        if (order != null) {
                            orderList.add(order);
                        } else {
                            Log.e(TAG, "Error converting document to Oders object: " + document.getId());
                        }
                    }
                    listener.onSuccess(orderList);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error fetching orders", e);
                    listener.onFailure(e.getMessage());
                });
    }

    public void getOdersByTailorId(String tailorId, OnOrdersFetchedListener listener){
        Query query = db.collection("orders")
                .whereEqualTo("tailorId", tailorId)
                .whereEqualTo("status", "Pending");

        query.get()
                .addOnSuccessListener(querySnapshots -> {
                    List<Orders> ordersList = new ArrayList<>();
                    for (DocumentSnapshot document : querySnapshots.getDocuments()){
                        Orders orders = document.toObject(Orders.class);
                        if (orders != null) {
                            ordersList.add(orders);
                        } else {
                            Log.e(TAG, "Error converting document to Orders object: " + document.getId());
                        }
                    }
                    listener.onSuccess(ordersList);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error fetching orders", e);
                    listener.onFailure(e.getMessage());
                });
    }

    public void getOdersByTailor(String tailorId, OnOrdersFetchedListener listener){
        Query query = db.collection("orders")
                .whereEqualTo("tailorId", tailorId);

        query.get()
                .addOnSuccessListener(querySnapshots -> {
                    List<Orders> ordersList = new ArrayList<>();
                    for (DocumentSnapshot document : querySnapshots.getDocuments()){
                        Orders orders = document.toObject(Orders.class);
                        if (orders != null) {
                            ordersList.add(orders);
                        } else {
                            Log.e(TAG, "Error converting document to Orders object: " + document.getId());
                        }
                    }
                    listener.onSuccess(ordersList);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error fetching orders", e);
                    listener.onFailure(e.getMessage());
                });
    }

    public void updateOrderStatus(String orderId, String newStatus, OnOrderStatusUpdateListener listener) {
        db.collection("orders").document(orderId)
                .update("status", newStatus)
                .addOnSuccessListener(aVoid -> listener.onSuccess())
                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
    }

    public interface OnOrderStatusUpdateListener {
        void onSuccess();
        void onFailure(String errorMessage);
    }



    public interface OnOrdersFetchedListener {
        void onSuccess(List<Orders> orders);
        void onFailure(String errorMessage);
    }

}
