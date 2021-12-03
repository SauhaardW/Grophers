package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
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

        // hamburger menu code
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutCustomerOrders);
        ImageButton hamburgerButton = (ImageButton) findViewById(R.id.imageButtonHamburgerCustomerOrders);
        hamburgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isOpen()) {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });

        NavigationView navView = findViewById(R.id.navViewCustomerOrders);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Item1:
                        startActivity(new Intent(PastOrdersCustomer.this, CustomerViewActivity.class));
                        break;
                    case R.id.Item2:
                        startActivity(new Intent(PastOrdersCustomer.this, PastOrdersCustomer.class));
                        break;
                    case R.id.Item3:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

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