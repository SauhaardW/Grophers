package com.example.project;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

public class OwnerProductListViewAdapter extends RecyclerView.Adapter<OwnerProductListViewAdapter.ViewHolder>{
    Context context;
    ArrayList<Product> list;
    String storeId;

    public OwnerProductListViewAdapter(Context context, ArrayList<Product> list, String storeId) {
        this.context = context;
        this.list = list;
        this.storeId = storeId;
    }

    @NonNull
    @Override
    public OwnerProductListViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_owner_product, parent, false);
        return new OwnerProductListViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerProductListViewAdapter.ViewHolder holder, int position) {
        Product product = list.get(position);
        holder.productName.setText(product.getBrand() + " " + product.getName());
        holder.productPrice.setText("$" + String.format("%.2f", product.getPrice()));
        //come back to test
        Glide.with(context).load(product.getImage()).into(holder.productImg);

        CardView card = (CardView) holder.itemView.findViewById(R.id.productCardOwner);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProductModalDialog editProductModalDialog = new EditProductModalDialog();
                Bundle bundle = new Bundle();
                bundle.putString("product_name", product.getName());
                bundle.putString("product_brand", product.getBrand());
                bundle.putString("product_price", "$" + String.format("%.2f", product.getPrice()));
                bundle.putDouble("product_price_double", product.getPrice());
                bundle.putString("product_img", product.getImage());
                bundle.putString("product_id", ((Integer)product.getId()).toString());
                bundle.putString("store_id", storeId);
                editProductModalDialog.setArguments(bundle);

//                //
//                View contentView = View.inflate(context, R.layout.edit_product_modal, null);
//
//                DisplayMetrics displayMetrics = editProductModalDialog.getActivity().getResources().getDisplayMetrics();
//
//                int width = displayMetrics.widthPixels;
//                int height = displayMetrics.heightPixels;
//
//                int maxHeight = (int) (height*0.88);
//
//                BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) contentView.getParent());
//                mBehavior.setPeekHeight(maxHeight);
//                //

                editProductModalDialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "addProductModal");
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

            this.productName = itemView.findViewById(R.id.productNameText2);
            this.productPrice = itemView.findViewById(R.id.textViewProductPrice2);
            this.productImg = itemView.findViewById(R.id.imageViewproductCardOwner);
        }
    }
}
