package com.example.project;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OwnerOrderProductListViewAdapter extends RecyclerView.Adapter<OwnerOrderProductListViewAdapter.ViewHolder> {

    Context context;
    ArrayList<CartItem> list;

    public OwnerOrderProductListViewAdapter(Context context, ArrayList<CartItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.owner_order_info_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = list.get(position);
        holder.productName.setText(item.getBrand() + " " + item.getName());
        holder.productQuantity.setText(String.format("x%d", item.getQuantity()));
        holder.productPrice.setText("$" + String.format("%.2f", item.getPrice()));
        Glide.with(context).load(item.getImage()).into(holder.productImg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productPrice, productQuantity;
        ImageView productImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.productName = itemView.findViewById(R.id.productNameText);
            this.productPrice = itemView.findViewById(R.id.textViewProductPrice);
            this.productQuantity = itemView.findViewById(R.id.numProductText);
            this.productImg = itemView.findViewById(R.id.imageViewProductCard);
        }
    }
}
