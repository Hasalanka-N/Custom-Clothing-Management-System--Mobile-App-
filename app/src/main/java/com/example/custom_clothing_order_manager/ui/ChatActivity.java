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
import com.example.custom_clothing_order_manager.adapters.ChatAdapter;
import com.example.custom_clothing_order_manager.database.MessagesDB;
import com.example.custom_clothing_order_manager.models.Messages;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {

    private MessagesDB messageDB;
    private List<Messages> messageList = new ArrayList<>();
    private String receiverId, userID;
    private String userEmail, tailorEmail;
    private ChatAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.categorytxt), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            receiverId = bundle.getString("resever_ID", "");
            userID = bundle.getString("user_id", "");
            tailorEmail = bundle.getString("tailor_email", "No Name Provided");
            userEmail = bundle.getString("customer_email", "No Name Provided");
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

       /* Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("userName");
            userName.setText(name != null ? name : "Unknown");
            receiverId = intent.getIntExtra("resever_ID", 0);
        }*/

        backBtn.setOnClickListener(v -> {
            Intent intent1 = new Intent(ChatActivity.this, ChatListActivity.class);
            Bundle bundle1 = new Bundle();
            bundle1.putInt("key1", 123);
            bundle1.putString("tailor_email", tailorEmail);
            bundle1.putString("user_id",userID);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });

        loadMessages();

        sendBtn.setOnClickListener(v -> {
            String messageText = inputTxt.getText().toString().trim();
            if (!messageText.isEmpty()) {
                String messageId = UUID.randomUUID().toString();
                String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                String SenderRole = "tailor";

                Messages messages;
                if (currentUserId.equals(userID)) {
                    messages = new Messages(messageId, receiverId, userID, messageText, timestamp, SenderRole);
                } else {
                    messages = new Messages(messageId, receiverId, userID, messageText, timestamp, SenderRole);
                }

                Log.d("CusChat", "Current User UID: " + currentUserId);
                Log.d("CusChat", "Message object: " + messages.toString());

                messageDB.sendMessage(messages);
                inputTxt.setText("");

                loadMessages();
            }
        });
    }

    private void loadMessages() {
        messageDB.getMessagesByCustomerAndTailor(receiverId, userID, messages -> {
            messageList = messages;
            adapter = new ChatAdapter(messageList, userID);
            recyclerView.setAdapter(adapter);
            if (!messageList.isEmpty()) {
                recyclerView.post(() -> recyclerView.scrollToPosition(messageList.size() - 1));
            }

            for (Messages msg : messages) {
                Log.d("ChatActivity", "Loaded Message - cusId: " + msg.getCusId() + ", tailorId: " + msg.getTailorId());
            }
        });
    }
}
