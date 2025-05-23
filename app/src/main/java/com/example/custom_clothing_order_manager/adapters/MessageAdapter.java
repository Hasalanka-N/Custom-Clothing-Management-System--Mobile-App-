package com.example.custom_clothing_order_manager.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.database.CustomerDB;
import com.example.custom_clothing_order_manager.database.CustomersDB;
import com.example.custom_clothing_order_manager.models.Customer;
import com.example.custom_clothing_order_manager.models.Customers;
import com.example.custom_clothing_order_manager.models.Messages;
import com.example.custom_clothing_order_manager.models.Tailors;
import com.example.custom_clothing_order_manager.ui.ChatActivity;
import com.example.custom_clothing_order_manager.ui.CusChat;

import java.util.List;
import java.util.concurrent.Executors;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private int tailorID;
    private String userEmail, senderName, userID, cusName;
    private final List<Messages> messages;
    private final LayoutInflater inflater;
    private final Context context;

    public MessageAdapter(Context context, List<Messages> messages, String userID) {
        this.context = context;
        this.messages = messages;
        this.inflater = LayoutInflater.from(context);
        this.userID = userID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Messages message = messages.get(position);
        holder.lastMessage.setText(message.getMessage());
        holder.time.setText(message.getTimestamp());

        String senderID = message.getCusId();
        holder.name.setText("Loading...");

        CustomersDB customersDB = new CustomersDB();
        customersDB.getCustomerById(context, senderID, new CustomersDB.OnCustomerDataFetchedListener() {
            @Override
            public void onSuccess(Customers customer) {
                if (customer != null) {
                    holder.name.setText(customer.getName());
                    senderName = customer.getName();
                    Log.d("MessageAdapter", "Customer name found: " + customer.getName());
                } else {
                    holder.name.setText("Unknown Customer");
                    Log.d("MessageAdapter", "Customer object is null");
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                holder.name.setText("Unknown Customer");
                Log.e("MessageAdapter", "Error fetching customer: " + errorMessage);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ChatActivity.class);
            Bundle bundle3 = new Bundle();
            bundle3.putInt("key",123);
            bundle3.putString("resever_ID",senderID);
            bundle3.putString("userName",senderName);
            bundle3.putString("user_id", userID);
            intent.putExtras(bundle3);
            v.getContext().startActivity(intent);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView name;
        TextView lastMessage;
        TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            name = itemView.findViewById(R.id.userName);
            lastMessage = itemView.findViewById(R.id.lastMessage);
            time = itemView.findViewById(R.id.messageTime);
        }
    }
}

