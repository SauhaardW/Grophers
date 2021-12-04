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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomerOrdersInfoListViewAdapter extends RecyclerView.Adapter<CustomerOrdersInfoListViewAdapter.ViewHolder> {

    Context context;
    ArrayList<CartItem> list;

    public CustomerOrdersInfoListViewAdapter(Context context, ArrayList<CartItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.customer_info_product_card, parent, false);
        return new CustomerOrdersInfoListViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerOrdersInfoListViewAdapter.ViewHolder holder, int position) {
        CartItem product = list.get(position);

        holder.productName.setText(product.getBrand() + " " + product.getName());
        holder.productPrice.setText("$" + String.format("%.2f", product.getPrice()));
        holder.productQuantity.setText("x"+((Integer)product.getQuantity()).toString());
        holder.productTotal.setText("$" + String.format("%.2f", product.getPrice()*product.getQuantity()));
        //ONLY ONE NOT WORKING :/// FIX
        Glide.with(context).load(product.image).into(holder.productImg);
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
