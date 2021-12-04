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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        holder.productQuantity.setText("x"+((Integer)product.getQuantity()).toString());
        holder.productTotal.setText("$" + String.format("%.2f", product.getPrice()*product.getQuantity()));
        Glide.with(context).load(product.getImage()).into(holder.productImg);

        //implement image setting

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase.getInstance().getReference("users").child("customers").child(uid).child("cart").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                                CartItem temp = dataSnapshot.getValue(CartItem.class);
                                Integer key = Integer.parseInt(dataSnapshot.getKey());
                                if (temp.getId() == product.getId()) {
                                    FirebaseDatabase.getInstance().getReference("users").child("customers").child(uid).child("cart").child(key.toString()).removeValue();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Error while getting cart data to update", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productPrice, productQuantity, productTotal, removeButton;
        ImageView productImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.productName = itemView.findViewById(R.id.textViewCartCardName);
            this.productPrice = itemView.findViewById(R.id.textViewCartCardPrice);
            this.productImg = itemView.findViewById(R.id.imageViewCartCardPhoto);
            this.productQuantity = itemView.findViewById(R.id.quantity_of_item);
            this.productTotal = itemView.findViewById(R.id.multiplied_product_price1);
            this.removeButton = itemView.findViewById(R.id.remove_text1);
        }
    }
}
