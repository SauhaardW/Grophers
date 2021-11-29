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

public class PastOrdersCustomer extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference db;
    CustomerOrdersListViewAdapter adapter;
    ArrayList<Order> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_orders_customer);

        recyclerView = findViewById(R.id.recyclerViewPastOrder);
        db = FirebaseDatabase.getInstance().getReference("users");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new CustomerOrdersListViewAdapter(PastOrdersCustomer.this, list);
        recyclerView.setAdapter(adapter);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.child("customers").child(uid).child("orders").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                        list.add(dataSnapshot.getValue(Order.class));
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(PastOrdersCustomer.this, "Error while getting user data", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}