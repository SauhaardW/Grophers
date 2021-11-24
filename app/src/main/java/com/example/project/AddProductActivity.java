package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        EditText editTextName = (EditText)findViewById(R.id.textEditAddProductName);
        EditText editTextBrand = (EditText)findViewById(R.id.textEditAddProductBrand);
        ImageView profileImg = (ImageView)findViewById(R.id.profileImg);

        String name = editTextName.getText().toString().trim();
        String hours = editTextHours.getText().toString().trim();
        String imgURL = profileImg.getContentDescription().toString();

        if (name.isEmpty()) {
            editTextName.setError("The store name cannot be empty");
            editTextName.requestFocus();
            return;
        } else if (hours.isEmpty()) {
            editTextHours.setError("The open hours cannot be empty");
            editTextHours.requestFocus();
            return;
        }
    }

}