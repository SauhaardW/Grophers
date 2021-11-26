package com.example.project;

import android.content.Context;
import android.content.Intent;
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

public class ProductListViewAdapter extends RecyclerView.Adapter<ProductListViewAdapter.ViewHolder> {

    Context context;
    ArrayList<Product> list;

    public ProductListViewAdapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.product_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = list.get(position);
        holder.productName.setText(product.getBrand() + " " + product.getName());
        holder.productPrice.setText("$" + String.format("%.2f", product.getPrice()));
        //implement image setting

        CardView card = (CardView) holder.itemView.findViewById(R.id.productCard);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProductModalDialog addProductModalDialog = new AddProductModalDialog();
                Bundle bundle = new Bundle();
                bundle.putString("product_name", product.getName());
                bundle.putString("product_brand", product.getBrand());
                bundle.putString("product_price", "$" + String.format("%.2f", product.getPrice()));
                bundle.putString("product_img", product.getImage());
                addProductModalDialog.setArguments(bundle);
                addProductModalDialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "addProductModal");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productPrice;
        ImageView productImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.productName = itemView.findViewById(R.id.productNameText);
            this.productPrice = itemView.findViewById(R.id.textViewProductPrice);
            this.productImg = itemView.findViewById(R.id.imageViewProductCard);
        }
    }
}
