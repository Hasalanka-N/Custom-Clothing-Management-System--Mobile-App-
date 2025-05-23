package com.example.custom_clothing_order_manager.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.adapters.AllMessageAdapter;
import com.example.custom_clothing_order_manager.adapters.MessageAdapter;
import com.example.custom_clothing_order_manager.database.MessageDB;
import com.example.custom_clothing_order_manager.models.Message;

import java.util.List;

public class ViewAllMessages extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AllMessageAdapter adapter;
    private MessageDB messageDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_all_messages);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerViewMessages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageDB = new MessageDB(this);
        List<Message> messages = messageDB.getAllMessages();

        adapter = new AllMessageAdapter(messages);
        recyclerView.setAdapter(adapter);
    }
}