package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoreListViewAdapter extends RecyclerView.Adapter<StoreListViewAdapter.ViewHolder> {

    Context context;
    ArrayList<Store> list;

    public StoreListViewAdapter(Context context, ArrayList<Store> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.store_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Store store = list.get(position);
        holder.storeName.setText(store.getName());
        holder.storeHours.setText(store.getHours());
        //implement image setting

        CardView card = (CardView) holder.itemView.findViewById(R.id.cardStore);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CustomerStoreProductViewActivity.class);
                intent.putExtra("store_id", ((Integer)store.getId()).toString());
                intent.putExtra("store_name", store.getName());
                intent.putExtra("store_hours", store.getHours());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView storeName, storeHours;
        ImageView storeImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.storeName = itemView.findViewById(R.id.textViewCustomerViewStoreName);
            this.storeHours = itemView.findViewById(R.id.textViewCustomerViewStoreTimings);
            this.storeImg = itemView.findViewById(R.id.imageViewCustomerViewStorePhoto);
        }
    }
}
