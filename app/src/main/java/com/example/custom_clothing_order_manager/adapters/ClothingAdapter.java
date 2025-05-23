package com.example.custom_clothing_order_manager.adapters;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.models.ClothingItem;
import com.example.custom_clothing_order_manager.ui.OderRequest;

import java.util.List;

public class ClothingAdapter extends RecyclerView.Adapter<ClothingAdapter.ClothingViewHolder> {

    private List<ClothingItem> clothingItems;
    private String tailorID, userID;
    private int cusid;

    public ClothingAdapter(List<ClothingItem> clothingItems,String tailorID,String userID) {
        this.clothingItems = clothingItems;
        this.tailorID = tailorID;
        this.userID = userID;
    }

    @Override
    public ClothingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_clothing, parent, false);
        return new ClothingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClothingViewHolder holder, int position) {
        ClothingItem item = clothingItems.get(position);
        holder.nameTextView.setText(item.getName());
        holder.categoryTextView.setText(item.getCategory());
        holder.clothImageView.setImageResource(item.getImageResource());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), OderRequest.class);
            intent.putExtra("clothingItemName", item.getName());
            intent.putExtra("clothingCategory", item.getCategory());
            intent.putExtra("tailor_id", tailorID);
            intent.putExtra("user_id", userID);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return clothingItems.size();
    }

    public static class ClothingViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, categoryTextView;
        ImageView clothImageView;

        public ClothingViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            clothImageView = itemView.findViewById(R.id.clothimg);
        }
    }
}
