package com.example.custom_clothing_order_manager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.adapters.CusChatAdapter;
import com.example.custom_clothing_order_manager.database.MessagesDB;
import com.example.custom_clothing_order_manager.models.Messages;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CusChat extends AppCompatActivity {

    private MessagesDB messageDB;
    private String userEmail, tailorEmail, userId, tailorId;
    private RecyclerView recyclerView;
    private CusChatAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<Messages> messageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cus_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tailorId = bundle.getString("resever_ID", "");
            userId = bundle.getString("user_id", "");
            tailorEmail = bundle.getString("tailor_email", "No Name Provided");
            userEmail = bundle.getString("customer_email", "No Name Provided");
            tailorEmail = bundle.getString("userName");
        }

        messageDB = new MessagesDB();

        recyclerView = findViewById(R.id.chatRecyclerView);

        layoutManager = new LinearLayoutManager(this);

        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);



        TextView userName = findViewById(R.id.chatUserName);

        ImageView sendBtn = findViewById(R.id.sendButton);

        EditText inputTxt = findViewById(R.id.messageInput);

        ImageView backBtn = findViewById(R.id.backBtn);

        userName.setText(tailorEmail);

        backBtn.setOnClickListener(v -> {
            Intent intent1 = new Intent(CusChat.this, CustomerChatList.class);
            intent1.putExtra("customer_email", userEmail);
            intent1.putExtra("user_id", userId);
            startActivity(intent1);
        });

        loadMessages();

        sendBtn.setOnClickListener(v -> {
            String messageText = inputTxt.getText().toString().trim();
            if (!messageText.isEmpty()) {
                String messageId = UUID.randomUUID().toString();
                String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String SenderRole = "customer";

                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                Messages message;
                if (currentUserId.equals(userId)) {
                    message = new Messages(messageId, userId, tailorId, messageText, timestamp, SenderRole);
                } else {
                    message = new Messages(messageId, tailorId, userId, messageText, timestamp, SenderRole);
                }

                Log.d("CusChat", "Current User UID: " + currentUserId);
                Log.d("CusChat", "Message object: " + message.toString());

                messageDB.sendMessage(message);
                inputTxt.setText("");
                loadMessages();
            }
        });
    }

    private void loadMessages() {
        messageDB.getMessagesByCustomerAndTailor(userId, tailorId, messages -> {
            messageList = messages;
            adapter = new CusChatAdapter(messageList);
            recyclerView.setAdapter(adapter);
            if (!messageList.isEmpty()) {
                recyclerView.post(() -> recyclerView.scrollToPosition(messageList.size() - 1));
            }

            for (Messages msg : messages) {
                Log.d("CusChat", "Loaded Message - cusId: " + msg.getCusId() + ", tailorId: " + msg.getTailorId());
            }
        });
    }
}