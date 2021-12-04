package com.example.project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddProductModalDialog extends BottomSheetDialogFragment {

    DatabaseReference db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_product_modal, container, false);

        TextView productName = v.findViewById(R.id.productNameModalDisplay);
        TextView productPrice = v.findViewById(R.id.productPriceModalDisplay2);
        ImageView productImg = v.findViewById(R.id.productImageModalDisplay);

        Bundle bundle = getArguments();
        productName.setText(bundle.getString("product_brand", "") + " " + bundle.getString("product_name", ""));
        productPrice.setText(bundle.getString("product_price", ""));
        String storeId = bundle.getString("store_id");
        String productId = bundle.getString("product_id");
        String productImageUrl = bundle.getString("product_img");

        Glide.with(this).load(productImageUrl).into(productImg);

        TextView count = v.findViewById(R.id.numberProductsAddedToCartModal);
        Button minus = v.findViewById(R.id.minusButtonSubtractProductFromNumModal);
        Button plus = v.findViewById(R.id.plusButtonAddAnotherProductToNumModal);

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer currentCount = Integer.parseInt(count.getText().toString());
                if (currentCount > 1) {
                    currentCount--;
                    count.setText(currentCount.toString());
                }
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer currentCount = Integer.parseInt(count.getText().toString());
                currentCount++;
                count.setText((currentCount).toString());
            }
        });

        Button addToCart = v.findViewById(R.id.addToCartButtonAddProductModal);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase.getInstance().getReference("users").child("customers").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            // Customer customer = task.getResult().getValue(Customer.class);
                            String curCartStoreId = task.getResult().child("curCartStoreId").getValue(String.class);
                            ArrayList<CartItem> customerCart = new ArrayList<CartItem>();
                            for (DataSnapshot dataSnapshot : task.getResult().child("cart").getChildren()) {
                                customerCart.add(dataSnapshot.getValue(CartItem.class));
                            }

                            FirebaseDatabase.getInstance().getReference("stores").child(storeId).child("products").child(productId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        CartItem cartItem = new CartItem(task.getResult().getValue(Product.class), Integer.parseInt(count.getText().toString()));

                                        ArrayList<CartItem> cart;
                                        if (curCartStoreId == null || !curCartStoreId.equals(storeId)) {
                                            //Toast.makeText(CustomerStoreProductViewActivity.this.get, "You had a cart active from another store, we've cleared it for you.", Toast.LENGTH_SHORT).show();
                                            Log.println(Log.ERROR, "Dialog", "You had a cart active from another store, we've cleared it for you.");
                                            cart = new ArrayList<CartItem>();
                                            FirebaseDatabase.getInstance().getReference("users").child("customers").child(uid).child("curCartStoreId").setValue(storeId);
                                            cart.add(cartItem);
                                        } else {
                                            cart = customerCart;
                                            boolean found = false;
                                            for (int i = 0; i < cart.size(); i++) {
                                                CartItem temp = cart.get(i);
                                                if (temp.getId() == cartItem.getId()) {
                                                    temp.setQuantity(temp.getQuantity()+(Integer.parseInt(count.getText().toString())));
                                                    cart.set(i, temp);
                                                    found = true;
                                                    break;
                                                }
                                            }
                                            if (!found) {
                                                cart.add(cartItem);
                                            }
                                        }

                                        FirebaseDatabase.getInstance().getReference("users").child("customers").child(uid).child("cart").setValue(cart);
                                    } else {
                                        Toast.makeText(getContext(), "Error while retrieving product info", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "Error while retrieving cart", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dismiss();
            }
        });

        return v;
    }
}
