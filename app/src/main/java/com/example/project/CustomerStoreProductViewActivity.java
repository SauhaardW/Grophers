package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerStoreProductViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference db;
    ProductListViewAdapter adapter;
    ArrayList<Product> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_store_product_view);

        Bundle extras = getIntent().getExtras();
        String store_id = extras.getString("store_id");
        String store_name = extras.getString("store_name");
        String store_hours = extras.getString("store_hours");
        String store_img = extras.getString("store_img");

        TextView storeName = findViewById(R.id.storeNameDisplay);
        TextView storeHours = findViewById(R.id.storeHoursDisplay);
        EditText searchBar = findViewById(R.id.editTextStoreProductSearch);
        ImageView storeProfilePic = findViewById(R.id.imageViewStoreCustomerProductsView);

        storeName.setText(store_name);
        storeHours.setText("Open: " + store_hours);
        searchBar.setHint("Search " + store_name);

        Glide.with(CustomerStoreProductViewActivity.this).load(store_img).into(storeProfilePic);

        recyclerView = findViewById(R.id.recyclerViewProductCustomer);
        db = FirebaseDatabase.getInstance().getReference("stores");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new ProductListViewAdapter(this, list, store_id);
        recyclerView.setAdapter(adapter);

        db.child(store_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot dataSnapshot : task.getResult().child("products").getChildren()) {
                        Product product = dataSnapshot.getValue(Product.class);
                        list.add(product);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(CustomerStoreProductViewActivity.this, "Error while getting store data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView numProductsInCart = findViewById(R.id.numProductsInCart);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users").child("customers").child(uid).child("cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Integer count = 0;
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        count += snap.getValue(CartItem.class).quantity;
                    }
                    numProductsInCart.setText(count.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                numProductsInCart.setText("N/A");
            }
        });

        Button cartButton = findViewById(R.id.shoppingCartInvisibleButton);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerStoreProductViewActivity.this, CustomerCartActivity.class);
                intent.putExtra("store_id", store_id);
                intent.putExtra("store_name", store_name);
                startActivity(intent);
            }
        });
    }
}