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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OwnerProductListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference db;
    OwnerProductListViewAdapter adapter;
    ArrayList<Product> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_product_list);




        recyclerView = findViewById(R.id.recyclerViewProductOwner);
        db = FirebaseDatabase.getInstance().getReference("stores");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users").child("owners").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Integer store_id = task.getResult().child("storeId").getValue(int.class);
                    String store_name = task.getResult().child("name").getValue(String.class);

                    adapter = new OwnerProductListViewAdapter(OwnerProductListActivity.this, list, store_id.toString());
                    recyclerView.setAdapter(adapter);

                    Button createProductButton = findViewById(R.id.createProductButtonInvisible);
                    createProductButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(OwnerProductListActivity.this, AddProductModalDialog.class);
                            intent.putExtra("store_id", store_id);
                            intent.putExtra("store_name", store_name);
                            startActivity(intent);
                        }
                    });
                    db.child(store_id.toString()).child("products").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                                    Product product = dataSnapshot.getValue(Product.class);
                                    list.add(product);
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(OwnerProductListActivity.this, "Error while getting store data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(OwnerProductListActivity.this, "Error while getting user data", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}