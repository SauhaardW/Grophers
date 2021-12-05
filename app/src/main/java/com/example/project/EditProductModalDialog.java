package com.example.project;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EditProductModalDialog extends BottomSheetDialogFragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_product_modal, container, false);

        ImageView productImg = v.findViewById(R.id.productImageModalDisplay3);

        Bundle bundle = getArguments();
        String storeId = bundle.getString("store_id");
        String productId = bundle.getString("product_id");
        String bundleName = bundle.getString("product_name");
        String bundleBrand = bundle.getString("product_brand");
        String bundlePrice = bundle.getString("product_price");
        Double bundlePriceDouble = bundle.getDouble("product_price_double");
        String bundleImg = bundle.getString("product_img");


        EditText productName = v.findViewById(R.id.productNameInput);
        EditText productBrand = v.findViewById(R.id.productBrandInput);
        EditText productPrice = v.findViewById(R.id.productPriceInput);

        productName.setText(bundleName);
        productBrand.setText(bundleBrand);
        productPrice.setText(String.format("%.2f", bundlePriceDouble));

        TextView delete_product = v.findViewById(R.id.deleteProductOwner);
        Button done = v.findViewById(R.id.doneEditingProduct);

        delete_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("stores").child(storeId).child("products").child(productId).removeValue();
                dismiss();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("stores").child(storeId).child("products").child(productId).child("name").setValue(productName.getText().toString().trim());
                FirebaseDatabase.getInstance().getReference("stores").child(storeId).child("products").child(productId).child("brand").setValue(productBrand.getText().toString().trim());
                FirebaseDatabase.getInstance().getReference("stores").child(storeId).child("products").child(productId).child("price").setValue(Double.parseDouble(productPrice.getText().toString().trim()));
                dismiss();
            }
        });
        return v;
    }
}
