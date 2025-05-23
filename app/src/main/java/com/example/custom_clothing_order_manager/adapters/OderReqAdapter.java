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
import com.example.custom_clothing_order_manager.database.OrdersDB;
import com.example.custom_clothing_order_manager.models.Orders;
import com.example.custom_clothing_order_manager.ui.ChatListActivity;
import com.example.custom_clothing_order_manager.ui.OderReqTailor;
import com.example.custom_clothing_order_manager.ui.OderRequest;
import com.example.custom_clothing_order_manager.ui.dashboardOder;
import com.example.custom_clothing_order_manager.ui.odersTrack;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OderReqAdapter extends RecyclerView.Adapter<OderReqAdapter.OrderViewHolder> {
    private final List<Orders> oders;
    private final String userId;
    private final Context context;
    OrdersDB odersDB;

    public OderReqAdapter(Context context,List<Orders> oders, String userId) {
        this.oders = oders;
        this.context = context;
        this.userId = userId;
        this.odersDB = new OrdersDB();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_oder_req, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Orders order = oders.get(position);
        holder.orderIdTextView.setText(order.getItemType());
        holder.materialTextView.setText(order.getMaterial());
        holder.colorTxtView.setText(order.getColor());
        holder.noteTxtView.setText(order.getNote());
        String oderId = order.getId();

        OrdersDB odersDB = new OrdersDB();

        holder.acceptBtn.setOnClickListener(v -> {
            odersDB.updateOrderStatus(oderId, "Accepted", new OrdersDB.OnOrderStatusUpdateListener() {
                @Override
                public void onSuccess() {
                    order.setStatus("Accepted");
                    notifyDataSetChanged();
                    Intent intent = new Intent(context, OderReqTailor.class);
                    Bundle bundle4 = new Bundle();
                    bundle4.putString("user_id",userId);
                    intent.putExtras(bundle4);
                    context.startActivity(intent);
                }

                @Override
                public void onFailure(String errorMessage) {
                }
            });
        });

    }

    @Override
    public int getItemCount() {
        return oders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTextView;
        TextView materialTextView;
        Button acceptBtn;
        Button rejectBtn;
        TextView colorTxtView;
        TextView noteTxtView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.textView55);
            materialTextView = itemView.findViewById(R.id.materialTxt);
            acceptBtn = itemView.findViewById(R.id.buttonAccept);
            colorTxtView = itemView.findViewById(R.id.colorTxt);
            noteTxtView = itemView.findViewById(R.id.noteTxt);
            rejectBtn = itemView.findViewById(R.id.buttonReject);
        }
    }
}


