package com.example.custom_clothing_order_manager.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.models.Message;
import com.example.custom_clothing_order_manager.models.Messages;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<Messages> messages;
    private String userID;

    public ChatAdapter(List<Messages> messages, String userID) {
        this.messages = messages;
        this.userID = userID;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        Messages message = messages.get(position);
       // holder.textViewMessage.setText(message.getMessage());
        holder.textViewMessage.setText(message.getMessage());

        if (message.getSenderRole().equals("tailor")) {
            holder.textViewMessage.setBackgroundResource(R.drawable.bg_message_user);
            holder.textViewMessage.setTextColor(Color.WHITE);

            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.textViewMessage.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.UNSET;
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            holder.textViewMessage.setLayoutParams(params);
        } else {
            holder.textViewMessage.setBackgroundResource(R.drawable.bg_message_receiver);
            holder.textViewMessage.setTextColor(Color.BLACK);

            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.textViewMessage.getLayoutParams();
            params.endToEnd = ConstraintLayout.LayoutParams.UNSET;
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            holder.textViewMessage.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessage(Messages message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewMessage;
        public TextView messageText2;

        public ChatViewHolder(View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.messageText);
        }
    }
}

