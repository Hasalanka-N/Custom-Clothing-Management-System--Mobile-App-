package com.example.custom_clothing_order_manager.database;

import android.util.Log;

import com.example.custom_clothing_order_manager.models.Messages;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagesDB {

    private static final String TAG = "MessagesDB";
    private FirebaseFirestore db;

    public MessagesDB() {
        db = FirebaseFirestore.getInstance();
    }

    public Task<Void> sendMessage(Messages messages) {
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("id", messages.getId());
        messageData.put("cusId", messages.getCusId());
        messageData.put("tailorId", messages.getTailorId());
        messageData.put("message", messages.getMessage());
        messageData.put("timestamp", messages.getTimestamp());
        messageData.put("senderRole", messages.getSenderRole());

        return db.collection("messages").document(messages.getId()).set(messageData);
    }

    public void getMessagesByCustomerAndTailor(String cusId, String tailorId, FirestoreCallback<List<Messages>> callback) {
        Log.d("MessagesDB", "Querying messages - cusId: " + cusId + ", tailorId: " + tailorId);

        db.collection("messages")
                .whereEqualTo("cusId", cusId)
                .whereEqualTo("tailorId", tailorId)
                .orderBy("timestamp")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Messages> messages = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Messages message = document.toObject(Messages.class);
                            messages.add(message);
                        }
                        callback.onCallback(messages);
                    } else {
                        Log.e(TAG, "Error getting messages", task.getException());
                        callback.onCallback(new ArrayList<>());
                    }
                });
    }

    public void getLatestMessagesForChats(String senderId, FirestoreCallback<List<Messages>> callback) {
        Log.d(TAG, "Fetching latest messages for senderId: " + senderId);

        db.collection("messages")
                .whereEqualTo("cusId", senderId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task1 -> {
                    if (!task1.isSuccessful()) {
                        Log.e(TAG, "Error fetching messages as sender", task1.getException());
                        callback.onCallback(new ArrayList<>());
                        return;
                    }

                    db.collection("messages")
                            .whereEqualTo("tailorId", senderId)
                            .orderBy("timestamp", Query.Direction.DESCENDING)
                            .get()
                            .addOnCompleteListener(task2 -> {
                                if (!task2.isSuccessful()) {
                                    Log.e(TAG, "Error fetching messages as receiver", task2.getException());
                                    callback.onCallback(new ArrayList<>());
                                    return;
                                }

                                List<Messages> allMessages = new ArrayList<>();
                                Map<String, Messages> latestMessagesMap = new HashMap<>();

                                for (QueryDocumentSnapshot document : task1.getResult()) {
                                    Messages message = document.toObject(Messages.class);
                                    String chatPartner = message.getTailorId();
                                    latestMessagesMap.putIfAbsent(chatPartner, message);
                                }

                                for (QueryDocumentSnapshot document : task2.getResult()) {
                                    Messages message = document.toObject(Messages.class);
                                    String chatPartner = message.getCusId();
                                    latestMessagesMap.putIfAbsent(chatPartner, message);
                                }

                                allMessages.addAll(latestMessagesMap.values());
                                allMessages.sort((m1, m2) -> m2.getTimestamp().compareTo(m1.getTimestamp()));

                                callback.onCallback(allMessages);
                            });
                });
    }




    public interface FirestoreCallback<T> {
        void onCallback(T result);
    }
}
