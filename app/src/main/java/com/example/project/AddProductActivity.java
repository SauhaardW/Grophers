package com.example.project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AddProductActivity extends AppCompatActivity{

    String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        TextView addProductPhoto = (TextView) findViewById(R.id.textViewAddProductPhoto);
        addProductPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageUrlModalDialog modal = new ImageUrlModalDialog();
                modal.show(getSupportFragmentManager(), "addProductImgModal");

                //come back to test from getting url from modal
                getSupportFragmentManager().setFragmentResultListener("imgUrl", AddProductActivity.this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        imgUrl = result.getString("imgUrl");
                        ImageView productPic = findViewById(R.id.imageViewProductImage);
                        Glide.with(AddProductActivity.this).load(imgUrl).into(productPic);
                    }//end onFragmentResult
                });
            }//end onClick for addProductPhoto
        });

        Button doneButton = (Button)findViewById(R.id.buttonDoneAddProduct);
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

        if (editTextName.getText().toString().isEmpty()) {
            editTextName.setError("The product name cannot be empty");
            editTextName.requestFocus();
            return;
        } else if (editTextBrand.getText().toString().isEmpty()) {
            editTextBrand.setError("The brand cannot be empty");
            editTextBrand.requestFocus();
            return;
        } else if (editTextPrice.getText().toString().isEmpty()) {
            editTextPrice.setError("The price cannot be empty");
            editTextPrice.requestFocus();
            return;
        }

        String name = editTextName.getText().toString().trim();
        String brand = editTextBrand.getText().toString().trim();
        try {
            double price = Double.parseDouble(editTextPrice.getText().toString().trim());
        } catch (NumberFormatException ex) {
            editTextPrice.setError("The price has to be a number");
            editTextPrice.requestFocus();
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

                                Product product = new Product(name, brand, price, products.size());
                                getSupportFragmentManager().setFragmentResultListener("imgUrl", AddProductActivity.this, new FragmentResultListener() {
                                    @Override
                                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                                        imgUrl = result.getString("imgUrl");
                                    }
                                });
                                product.setImage(imgUrl);
                                products.add(product);
                                store.setProducts(products);
                                FirebaseDatabase.getInstance().getReference("stores").child(storeId.toString()).child("products").setValue(products);
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