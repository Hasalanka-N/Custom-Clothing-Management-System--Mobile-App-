package com.example.custom_clothing_order_manager.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.custom_clothing_order_manager.R;
import com.example.custom_clothing_order_manager.models.Orders;
import com.example.custom_clothing_order_manager.ui.odersTrack;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ArrayAdapter extends RecyclerView.Adapter<ArrayAdapter.OrderViewHolder> {

    private final List<Orders> oders;
    private String userId;
    private final Context context;

    public ArrayAdapter(Context context, List<Orders> oders, String userId) {
        this.oders = oders;
        this.userId = userId;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

        Orders order = oders.get(position);
        //holder.orderIdTextView.setText(order.getId());
        String orderId = String.valueOf(order.getId());
        String shortOrderId = orderId.length() > 10 ? orderId.substring(0, 10) : orderId;
        holder.orderIdTextView.setText("#"+shortOrderId);
        holder.orderDateTextView.setText(order.getDate());
        holder.oderItemTxtView.setText(order.getItemType());

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date orderDate = dateFormat.parse(order.getDate());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(orderDate);
            calendar.add(Calendar.DAY_OF_YEAR, 15);

            Date deliveryDate = calendar.getTime();
            String formattedDeliveryDate = dateFormat.format(deliveryDate);

            holder.deliveryDateTxtView.setText(formattedDeliveryDate);
        } catch (Exception e) {
            e.printStackTrace();
            holder.deliveryDateTxtView.setText("Error calculating delivery date");
        }

        holder.trackButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, odersTrack.class);
            Bundle bundle = new Bundle();
            bundle.putString("orderId", order.getId());
            bundle.putString("status", order.getStatus());
            bundle.putString("street",order.getStreet());
            bundle.putString("country",order.getCountry());
            bundle.putString("oderDate",order.getDate());
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return oders.size();
    }

    public void updateList(List<Orders> newOrders) {
        oders.clear();
        oders.addAll(newOrders);
        notifyDataSetChanged();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTextView;
        TextView orderDateTextView;
        Button trackButton;
        TextView oderItemTxtView;
        TextView deliveryDateTxtView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.oderIdTxt);
            orderDateTextView = itemView.findViewById(R.id.orderDateTxt);
            trackButton = itemView.findViewById(R.id.trackBtn);
            oderItemTxtView = itemView.findViewById(R.id.oderItemTxt);
            deliveryDateTxtView = itemView.findViewById(R.id.deliveryDateTxt);


        }
    }
}

