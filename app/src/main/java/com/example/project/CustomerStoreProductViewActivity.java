package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        ImageView productBackgroundImg = (ImageView) findViewById(R.id.productsBackgroundCustomreStoreProductView);
        productBackgroundImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddProductModalDialog addProductModalDialog = new AddProductModalDialog();
                    addProductModalDialog.show(getSupportFragmentManager(), "addProductModal");
                }
            });

        Bundle extras = getIntent().getExtras();
        String store_id = extras.getString("store_id");
        String store_name = extras.getString("store_name");
        String store_hours = extras.getString("store_hours");

        TextView storeName = findViewById(R.id.storeNameDisplay);
        TextView storeHours = findViewById(R.id.storeHoursDisplay);
        EditText searchBar = findViewById(R.id.editTextStoreProductSearch);

        storeName.setText(store_name);
        storeHours.setText("Open: " + store_hours);
        searchBar.setHint("Search " + store_name);
        // implement image setting

        recyclerView = findViewById(R.id.recyclerViewProductCustomer);
        db = FirebaseDatabase.getInstance().getReference("stores");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new ProductListViewAdapter(this, list);
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
    }
}