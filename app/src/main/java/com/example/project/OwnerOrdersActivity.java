package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OwnerOrdersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference db;
    OwnerOrdersListViewAdapter adapter;
    ArrayList<Order> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_orders);


        recyclerView = findViewById(R.id.ownerOrders_recyclerView);
        db = FirebaseDatabase.getInstance().getReference("stores");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new OwnerOrdersListViewAdapter(OwnerOrdersActivity.this, list);
        recyclerView.setAdapter(adapter);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users").child("owners").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Integer store_id = task.getResult().child("storeId").getValue(int.class);
                    db.child(store_id.toString()).child("orders").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                                    Order order = dataSnapshot.getValue(Order.class);
                                    list.add(order);
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(OwnerOrdersActivity.this, "Error while getting store data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(OwnerOrdersActivity.this, "Error while getting user data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }//end onCreate
}