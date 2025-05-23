package com.example.custom_clothing_order_manager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.models.Message;

import java.util.List;

public class AllMessageAdapter extends RecyclerView.Adapter<AllMessageAdapter.MessageViewHolder> {
    private List<Message> messageList;

    public AllMessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.txtMessage.setText(message.getMessage());
        holder.txtSenderReceiver.setText("Sender: " + message.getSenderId() + "  Receiver: " + message.getReceiverId());
        holder.txtTimestamp.setText(message.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView txtMessage, txtSenderReceiver, txtTimestamp;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMessage = itemView.findViewById(R.id.txtMessage);
            txtSenderReceiver = itemView.findViewById(R.id.txtSenderReceiver);
            txtTimestamp = itemView.findViewById(R.id.txtTimestamp);
        }
    }
}
