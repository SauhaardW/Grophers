package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomerViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference db;
    StoreListViewAdapter adapter;
    ArrayList<Store> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view);

        recyclerView = findViewById(R.id.customerViewStoresList);
        db = FirebaseDatabase.getInstance().getReference("stores");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new StoreListViewAdapter(this, list);
        recyclerView.setAdapter(adapter);



        db.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                        Store store = dataSnapshot.getValue(Store.class);
                        list.add(store);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(CustomerViewActivity.this, "Error while getting store data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}