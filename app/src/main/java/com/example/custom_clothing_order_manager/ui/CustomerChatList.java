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

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.adapters.CusMessageAdapter;
import com.example.custom_clothing_order_manager.database.MessagesDB;
import com.example.custom_clothing_order_manager.models.Messages;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CustomerChatList extends AppCompatActivity {
    String userEmail, userID;
    FloatingActionButton tailorBtn;
    CusMessageAdapter adapter;
    private int cusid, rid;
    private MessagesDB messageDB;
    private List<Messages> messageList = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_chat_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userEmail = bundle.getString("customer_email", "No Name Provided");
            userID = bundle.getString("user_id");
            cusid = bundle.getInt("cus_id", 0);
        }

        messageDB = new MessagesDB();

        recyclerView = findViewById(R.id.chatlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageDB.getLatestMessagesForChats(userID, messages -> {
            messageList = messages;
            adapter = new CusMessageAdapter(this, messageList, userID);
            recyclerView.setAdapter(adapter);
            if (!messageList.isEmpty()) {
                recyclerView.post(() -> recyclerView.scrollToPosition(messageList.size() - 1));
            }
        });




        tailorBtn = findViewById(R.id.findTailorBtn);
        tailorBtn.setOnClickListener(v -> {
            Intent intent = new Intent(CustomerChatList.this, MapsActivity.class);
            Bundle bundle1 = new Bundle();
            bundle1.putString("customer_email", userEmail);
            bundle1.putInt("cus_id", cusid);
            bundle1.putString("user_id", userID);
            intent.putExtras(bundle1);
            startActivity(intent);
        });

        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener((v -> {
            Intent intent = new Intent(CustomerChatList.this, MainActivity.class);
            Bundle bundle2 = new Bundle();
            bundle2.putInt("key", 123);
            bundle2.putString("user_id", userID);
            intent.putExtras(bundle2);
            startActivity(intent);
        }));

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.item_2);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            Intent intent = null;

            if (itemId == R.id.home) {
                intent = new Intent(CustomerChatList.this, MainActivity.class);
                Bundle bundle4 = new Bundle();
                bundle4.putInt("key", 123);
                bundle4.putString("customer_email", userEmail);
                bundle4.putInt("cus_id",cusid);
                bundle4.putString("user_id", userID);
                intent.putExtras(bundle4);
            } else if (itemId == R.id.item_2) {
                intent = new Intent(CustomerChatList.this, CustomerChatList.class);
                Bundle bundle4 = new Bundle();
                bundle4.putInt("key", 123);
                bundle4.putString("customer_email", userEmail);
                bundle4.putInt("cus_id",cusid);
                bundle4.putString("user_id", userID);
                intent.putExtras(bundle4);
            } else if (itemId == R.id.item_3) {
                intent = new Intent(CustomerChatList.this, odersTrack.class);
                Bundle bundle4 = new Bundle();
                bundle4.putInt("key", 123);
                bundle4.putString("customer_email", userEmail);
                bundle4.putInt("cus_id",cusid);
                bundle4.putString("user_id", userID);
                intent.putExtras(bundle4);
            } else if (itemId == R.id.item_4) {
                intent = new Intent(CustomerChatList.this, MainActivity2.class);
                Bundle bundle4 = new Bundle();
                bundle4.putInt("key", 123);
                bundle4.putString("customer_email", userEmail);
                bundle4.putInt("cus_id",cusid);
                bundle4.putString("user_id", userID);
                intent.putExtras(bundle4);
            } else {
                intent = new Intent(CustomerChatList.this, MainActivity.class);
            }

            if (intent != null) {
                startActivity(intent);
            }
            return true;
        });
    }
}