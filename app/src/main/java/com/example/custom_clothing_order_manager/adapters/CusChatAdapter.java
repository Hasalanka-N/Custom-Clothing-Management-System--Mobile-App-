package com.example.custom_clothing_order_manager.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.models.Message;
import com.example.custom_clothing_order_manager.models.Messages;

import java.util.List;

public class CusChatAdapter extends RecyclerView.Adapter<CusChatAdapter.ChatViewHolder> {

    private List<Messages> messages;

    public CusChatAdapter(List<Messages> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Messages message = messages.get(position);
        holder.messageText.setText(message.getMessage());

        if (message.getSenderRole().equals("customer")) {
            holder.messageText.setBackgroundResource(R.drawable.bg_message_user);
            holder.messageText.setTextColor(Color.WHITE);

            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.messageText.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.UNSET;
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            holder.messageText.setLayoutParams(params);
        } else {
            holder.messageText.setBackgroundResource(R.drawable.bg_message_receiver);
            holder.messageText.setTextColor(Color.BLACK);

            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.messageText.getLayoutParams();
            params.endToEnd = ConstraintLayout.LayoutParams.UNSET;
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            holder.messageText.setLayoutParams(params);
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void updateMessages(List<Messages> newMessages) {
        this.messages = newMessages;
        notifyDataSetChanged();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        public ChatViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
        }
    }
}
