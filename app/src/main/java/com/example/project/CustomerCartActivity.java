package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class CustomerCartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference db;
    CartListViewAdapter adapter;
    ArrayList<CartItem> list;
    Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_cart);

        TextView textViewName = findViewById(R.id.store_cart_text);
        TextView textViewSubtotal = findViewById(R.id.order_subtotal_price);
        TextView textViewTotal = findViewById(R.id.order_total_price);
        TextView textViewFees = findViewById(R.id.taxes_price);

        recyclerView = findViewById(R.id.recyclerViewCart);
        db = FirebaseDatabase.getInstance().getReference("users");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.child("customers").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    String store_id = task.getResult().child("curCartStoreId").getValue(String.class);
                    String customerName = task.getResult().child("username").getValue(String.class);

                    if (store_id == null) {
                        return;
                    }

                    adapter = new CartListViewAdapter(CustomerCartActivity.this, list, store_id);
                    recyclerView.setAdapter(adapter);

                    FirebaseDatabase.getInstance().getReference("stores").child(store_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                store = task.getResult().getValue(Store.class);
                                textViewName.setText(store.getName());
                            } else {
                                Toast.makeText(CustomerCartActivity.this, "Error while getting store data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                    Double subtotal = 0.0;
                    Integer cartCount = 0;
                    for (DataSnapshot dataSnapshot : task.getResult().child("cart").getChildren()) {
                        CartItem cartItem = dataSnapshot.getValue(CartItem.class);
                        subtotal += cartItem.getPrice()*cartItem.getQuantity();
                        cartCount += cartItem.getQuantity();
                        list.add(cartItem);
                    }
                    adapter.notifyDataSetChanged();

                    TextView cartCountTextView = findViewById(R.id.numProductsInCart3);
                    cartCountTextView.setText(cartCount.toString());

                    Double fees = subtotal*0.13;
                    textViewSubtotal.setText("$" + String.format("%.2f", subtotal));
                    textViewFees.setText("$" + String.format("%.2f", fees));
                    textViewTotal.setText("$" + String.format("%.2f", subtotal+fees));

                    ImageView checkoutButton = findViewById(R.id.checkout_button);
                    checkoutButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            long timestamp = new Date().getTime();
                            String timestampString = ((Long)timestamp).toString();
                            Order order =  new Order(uid, store_id, list, timestamp, "Processing", store.getName(), customerName);

                            db.child("customers").child(uid).child("orders").child(timestampString).setValue(order);
                            db.child("customers").child(uid).child("cart").setValue(null);
                            db.child("customers").child(uid).child("curCartStoreId").setValue(null);

                            FirebaseDatabase.getInstance().getReference("stores").child(store_id).child("orders").child(timestampString).setValue(order);

                            startActivity(new Intent(CustomerCartActivity.this, SuccessActivity.class));
                        }
                    });
                } else {
                    Toast.makeText(CustomerCartActivity.this, "Error while getting cart data", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}