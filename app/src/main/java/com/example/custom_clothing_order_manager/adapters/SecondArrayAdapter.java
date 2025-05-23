package com.example.custom_clothing_order_manager.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.models.Orders;
import com.example.custom_clothing_order_manager.ui.UpdateOderStatus;

import java.util.List;

public class SecondArrayAdapter extends RecyclerView.Adapter<SecondArrayAdapter.SecondOrderViewHolder> {
    private final List<Orders> orders;
    private String userId;
    private final Context context;

    public SecondArrayAdapter(Context context, List<Orders> orders, String userId) {
        this.orders = orders;
        this.userId = userId;
        this.context = context;
    }

    @NonNull
    @Override
    public SecondOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.oder_list_item, parent, false);
        return new SecondOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SecondOrderViewHolder holder, int position) {
        Orders order = orders.get(position);
        holder.itemNameTextView.setText(order.getItemType());
        holder.itemPriceTextView.setText(order.getPhone());
        String orderId = String.valueOf(order.getId());
        String shortOrderId = orderId.length() > 10 ? orderId.substring(0, 10) : orderId;
        holder.orderIdTextView.setText("#"+shortOrderId);
        holder.oderDatetxt.setText(order.getDate());
        holder.oderStatusTxt.setText(order.getStatus());
        if (order.getCusName() != null && !order.getCusName().isEmpty()) {
            holder.nameTxt.setText(order.getCusName());
        } else {
            holder.nameTxt.setText("No Name");
        }

        holder.itemView.setOnClickListener(v -> {
            Orders clickedOrder = orders.get(position);

            Intent intent = new Intent(holder.itemView.getContext(), UpdateOderStatus.class);
            Bundle bundle = new Bundle();
            bundle.putString("user_Id",userId);
            bundle.putString("orderId", clickedOrder.getId());
            bundle.putString("itemName", clickedOrder.getItemType());
            bundle.putString("phone", clickedOrder.getPhone());
            bundle.putString("date", clickedOrder.getDate());
            bundle.putString("status", clickedOrder.getStatus());
            bundle.putString("cusName", clickedOrder.getCusName());
            bundle.putString("measurements", clickedOrder.getMeasurement());
            bundle.putString("metirial", clickedOrder.getMaterial());
            bundle.putString("color", clickedOrder.getColor());
            intent.putExtras(bundle);
            context.startActivity(intent);

            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class SecondOrderViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView itemPriceTextView;
        TextView orderIdTextView;
        TextView oderDatetxt;
        TextView oderStatusTxt;
        TextView nameTxt;


        public SecondOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemPriceTextView = itemView.findViewById(R.id.itemPriceTextView);
            orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            oderDatetxt = itemView.findViewById(R.id.oderDatetxt);
            oderStatusTxt = itemView.findViewById(R.id.textView42);
            nameTxt = itemView.findViewById(R.id.textView44);
        }
    }
}
