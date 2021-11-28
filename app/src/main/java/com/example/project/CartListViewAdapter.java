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

import java.util.ArrayList;

public class CartListViewAdapter extends RecyclerView.Adapter<CartListViewAdapter.ViewHolder> {

    Context context;
    ArrayList<CartItem> list;
    String storeId;

    public CartListViewAdapter(Context context, ArrayList<CartItem> list, String storeId) {
        this.context = context;
        this.list = list;
        this.storeId = storeId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cart_item_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem product = list.get(position);
        holder.productName.setText(product.getBrand() + " " + product.getName());
        holder.productPrice.setText("$" + String.format("%.2f", product.getPrice()));
        holder.productQuantity.setText(((Integer)product.getQuantity()).toString());
        holder.productTotal.setText("$" + String.format("%.2f", product.getPrice()*product.getQuantity()));
        //implement image setting


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productPrice, productQuantity, productTotal;
        ImageView productImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.productName = itemView.findViewById(R.id.textViewCartCardName);
            this.productPrice = itemView.findViewById(R.id.textViewCartCardPrice);
            this.productImg = itemView.findViewById(R.id.imageViewCartCardPhoto);
            this.productQuantity = itemView.findViewById(R.id.quantity_of_item);
            this.productTotal = itemView.findViewById(R.id.multiplied_product_price1);
        }
    }
}
