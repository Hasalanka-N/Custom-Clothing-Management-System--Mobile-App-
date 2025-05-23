package com.example.custom_clothing_order_manager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.models.Tailor;

import java.util.List;

public class TailorAdapter extends RecyclerView.Adapter<TailorAdapter.TailorViewHolder> {

    private List<Tailor> tailorList;

    public TailorAdapter(List<Tailor> tailorList) {
        this.tailorList = tailorList;
    }

    @NonNull
    @Override
    public TailorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tailor, parent, false);
        return new TailorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TailorViewHolder holder, int position) {
        Tailor tailor = tailorList.get(position);

        holder.tailorName.setText(tailor.getName());
        holder.tailorEmail.setText(tailor.getEmail());
        holder.shopName.setText(tailor.getShopName());
        holder.specializations.setText(tailor.getSpecializations());
        holder.imagePath.setText(tailor.getImagePath());
        holder.location.setText("Lat: " + tailor.getLatitude() + ", Lon: " + tailor.getLongitude());
    }

    @Override
    public int getItemCount() {
        return tailorList.size();
    }

    public static class TailorViewHolder extends RecyclerView.ViewHolder {

        TextView tailorName;
        TextView tailorEmail;
        TextView shopName;
        TextView specializations;
        TextView imagePath;
        TextView location;

        public TailorViewHolder(View itemView) {
            super(itemView);

            tailorName = itemView.findViewById(R.id.tailorName);
            tailorEmail = itemView.findViewById(R.id.tailorEmail);
            shopName = itemView.findViewById(R.id.shopName);
            specializations = itemView.findViewById(R.id.specializations);
            imagePath = itemView.findViewById(R.id.imagePath);
            location = itemView.findViewById(R.id.location);
        }
    }
}
