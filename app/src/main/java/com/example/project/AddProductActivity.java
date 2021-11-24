package com.example.project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        TextView doneButton = (TextView) findViewById(R.id.buttonDoneAddProduct);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProduct();
            }
        });
    }

    private void createProduct() {
        EditText editTextName = (EditText)findViewById(R.id.editTextAddProductName);
        EditText editTextBrand = (EditText)findViewById(R.id.editTextAddProductBrand);
        EditText editTextPrice = (EditText)findViewById(R.id.editTextAddProductPrice);
        ImageView profileImg = (ImageView)findViewById(R.id.imageViewProductImage);

        String name = editTextName.getText().toString().trim();
        String brand = editTextBrand.getText().toString().trim();
        double price = Double.parseDouble(editTextPrice.getText().toString().trim());
        String img = profileImg.getContentDescription().toString().trim();

        if (name.isEmpty()) {
            editTextName.setError("The product name cannot be empty");
            editTextName.requestFocus();
            return;
        } else if (brand.isEmpty()) {
            editTextBrand.setError("The brand cannot be empty");
            editTextBrand.requestFocus();
            return;
        } else if (price == 0.0) {
            editTextBrand.setError("The price cannot be 0");
            editTextBrand.requestFocus();
            return;
        }

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users").child("owners").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Owner owner = task.getResult().getValue(Owner.class);
                    Integer storeId = owner.getStoreId();

                    FirebaseDatabase.getInstance().getReference("stores").child(storeId.toString()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                Store store = task.getResult().getValue(Store.class);
                                ArrayList<Product> products = store.getProducts();
                                if (products == null) {
                                    products = new ArrayList<Product>();
                                }

                                Product product = new Product(name, brand, price);
                                products.add(product);
                                store.setProducts(products);
                                FirebaseDatabase.getInstance().getReference("stores").child(storeId.toString()).setValue(store);
                            } else {
                                Log.println(Log.ERROR, "AddProductActivity", "Error while accessing store data.");
                            }
                        }
                    });

                    finish();
                } else {
                    Log.println(Log.ERROR, "AddProductActivity", "Error while accessing user data.");
                }
            }
        });
    }

}