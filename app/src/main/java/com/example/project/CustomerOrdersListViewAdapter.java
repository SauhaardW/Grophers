package com.example.project;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomerOrdersListViewAdapter extends RecyclerView.Adapter<CustomerOrdersListViewAdapter.ViewHolder> {

    Context context;
    ArrayList<Order> list;

    public CustomerOrdersListViewAdapter (Context context, ArrayList<Order> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.past_orders_card, parent, false);
        return new CustomerOrdersListViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerOrdersListViewAdapter.ViewHolder holder, int position) {
        Order order = list.get(position);

        Date date = new Date(order.getTimestamp());
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateFormatted = formatter.format(date);

        double total = 0.0;
        for (CartItem item : order.getCart()) {
            total += item.getPrice();
        }

        holder.storeName.setText(order.getStoreName());
        holder.orderSubtitle.setText(dateFormatted + " • " + String.format("$%.2f", total));
        holder.orderStatus.setText(order.getStatus());
        switch (order.getStatus()) {
            case "Processing": holder.orderStatus.setBackgroundTintList(context.getResources().getColorStateList(R.color.ProcessingGray));
            case "Canceled": holder.orderStatus.setBackgroundTintList(context.getResources().getColorStateList(R.color.CanceledGray));
            case "Complete": holder.orderStatus.setBackgroundTintList(context.getResources().getColorStateList(R.color.CustomerGreen));
            default: holder.orderStatus.setBackgroundTintList(context.getResources().getColorStateList(R.color.CanceledGray));
        }
        //implement image setting

        CardView card = (CardView) holder.itemView.findViewById(R.id.cardpastOrders);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog orderInfoModal = new BottomSheetDialog(context);
                LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View modalView = li.inflate(R.layout.customer_order_info_modal, null);

                orderInfoModal.setContentView(modalView);

                double total = 0.0;
                for (CartItem item : order.getCart()) {
                    total += item.getPrice() * item.getQuantity();
                }
                double taxes = total * 0.13;

                ((TextView) modalView.findViewById(R.id.orderSubtotalPriceCustomerOrderInfoModal)).setText(String.format("$%.2f", total));
                ((TextView) modalView.findViewById(R.id.taxesPriceCustomerOrderInfoModal)).setText(String.format("$%.2f", taxes));
                ((TextView) modalView.findViewById(R.id.orderTotalPriceCustomerOrderInfoModal)).setText(String.format("$%.2f", total + taxes));

                // Handle recyclerview in modal
                RecyclerView modalRecyclerView = orderInfoModal.findViewById(R.id.recyclerViewCustomerOrderInfoModal);
                modalRecyclerView.setHasFixedSize(true);
                modalRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                ArrayList<CartItem> modalList = order.getCart();
                CustomerOrdersInfoListViewAdapter modalAdapter= new CustomerOrdersInfoListViewAdapter(context, modalList);
                modalRecyclerView.setAdapter(modalAdapter);
                modalAdapter.notifyDataSetChanged();

                orderInfoModal.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView storeName, orderSubtitle, orderStatus;
        ImageView productImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.storeName = itemView.findViewById(R.id.pastOrderCardStoreName);
            this.orderSubtitle = itemView.findViewById(R.id.pastOrderCardSubtitle);
            this.orderStatus = itemView.findViewById(R.id.textViewPastOrdersCustomerOrderStatus);
            this.productImg = itemView.findViewById(R.id.imageViewCustomerViewStorePhoto);
        }
    }
}
