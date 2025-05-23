package com.example.custom_clothing_order_manager.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.custom_clothing_order_manager.database.TailorDB;
import com.example.custom_clothing_order_manager.models.Messages;
import com.example.custom_clothing_order_manager.models.Tailors;
import com.example.custom_clothing_order_manager.ui.CusChat;

import java.io.File;
import java.util.List;

public class CusMessageAdapter extends RecyclerView.Adapter<CusMessageAdapter.MessageViewHolder> {

    private final List<Messages> messages;
    private final LayoutInflater inflater;
    private final Context context;
    private String shopName, userID;

    public CusMessageAdapter(Context context, List<Messages> messages, String userID) {
        this.context = context;
        this.messages = messages;
        this.inflater = LayoutInflater.from(context);
        this.userID = userID;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.message_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Messages message = messages.get(position);
        holder.showmsgTxt.setText(message.getMessage());
        holder.msgtimeTxt.setText(message.getTimestamp());

        String senderID = message.getTailorId();

        TailorDB tailorDB = new TailorDB();
        tailorDB.getTailorById(senderID, new TailorDB.OnTailorOperationCompleteListener() {
            @Override
            public void onSuccess(Tailors tailor) {
                if (tailor != null) {
                    holder.messageText.setText(tailor.getName());
                    shopName = tailor.getShopName();

                    String imagePath = tailor.getImagePath();

                    if (imagePath != null && !imagePath.isEmpty()) {
                        File imageFile = new File(imagePath);
                        if (imageFile.exists()) {
                            try {
                                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                                holder.imageView11.setImageBitmap(bitmap);  // Use holder.tailorImageView
                            } catch (Exception e) {
                                Log.e("ImageLoading", "Error loading image: " + e.getMessage());
                            }
                        } else {
                            Log.e("ImageLoading", "Image file not found: " + imagePath);
                        }
                    } else {
                        Log.w("ImageLoading", "Image path is null or empty.");
                    }
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                holder.msgtimeTxt.setText("Unknown Tailor");
            }
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CusChat.class);
            Bundle bundle3 = new Bundle();
            bundle3.putInt("key",123);
            bundle3.putString("resever_ID",senderID);
            bundle3.putString("userName",shopName);
            bundle3.putString("user_id", userID);
            intent.putExtras(bundle3);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView msgtimeTxt;
        TextView showmsgTxt;
        ImageView imageView11;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.txtMessage);
            msgtimeTxt = itemView.findViewById(R.id.txtTimestamp);
            showmsgTxt = itemView.findViewById(R.id.txtSenderReceiver);
            imageView11 = itemView.findViewById(R.id.imageView11);
        }
    }
}
