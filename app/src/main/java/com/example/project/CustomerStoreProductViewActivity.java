package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CustomerStoreProductViewActivity extends AppCompatActivity {

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
    }
}