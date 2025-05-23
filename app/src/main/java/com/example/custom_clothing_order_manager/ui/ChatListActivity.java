package com.example.custom_clothing_order_manager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.custom_clothing_order_manager.adapters.MessageAdapter;
import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.database.MessagesDB;
import com.example.custom_clothing_order_manager.models.Messages;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {
    private String userID;
    private String tailorEmail;
    MessagesDB messageDB;
    RecyclerView recyclerView;
    private List<Messages> messageList = new ArrayList<>();

    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.chat_list_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.categorytxt), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            int value1 = bundle.getInt("key1", 0);
            tailorEmail = bundle.getString("tailor_email", "No Name Provided");
            userID = bundle.getString("user_id");
        }

        messageDB = new MessagesDB();

        recyclerView = findViewById(R.id.chatlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageDB.getLatestMessagesForChats(userID, messages -> {
            messageList = messages;
            adapter = new MessageAdapter(this, messageList, userID);
            recyclerView.setAdapter(adapter);
            if (!messageList.isEmpty()) {
                recyclerView.post(() -> recyclerView.scrollToPosition(messageList.size() - 1));
            }
        });


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_chats);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            Intent intent = null;

            if (itemId == R.id.nav_home) {
                intent = new Intent(ChatListActivity.this, dashboardOder.class);
                Bundle bundle4 = new Bundle();
                bundle4.putString("user_id",userID);
                intent.putExtras(bundle4);
            } else if (itemId == R.id.nav_chats) {
                intent = new Intent(ChatListActivity.this, ChatListActivity.class);
                Bundle bundle4 = new Bundle();
                bundle4.putString("user_id",userID);
                intent.putExtras(bundle4);
            } else if (itemId == R.id.nav_oder) {
                intent = new Intent(ChatListActivity.this, OderReqTailor.class);
                Bundle bundle4 = new Bundle();
                bundle4.putString("user_id",userID);
                intent.putExtras(bundle4);
            } else if (itemId == R.id.nav_logout) {
                intent = new Intent(ChatListActivity.this, MainActivity2.class);
            } else {
                intent = new Intent(ChatListActivity.this, OderReqTailor.class);
            }

            if (intent != null) {
                startActivity(intent);
            }
            return true;
        });

        ImageView backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ChatListActivity.this,dashboardOder.class);
            Bundle bundle1 = new Bundle();
            bundle1.putInt("key1",123);
            bundle1.putString("user_id",userID);
            intent.putExtras(bundle1);
            startActivity(intent);
        });

    }
}