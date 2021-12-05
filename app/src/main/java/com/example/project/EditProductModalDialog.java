package com.example.project;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import androidx.fragment.app.FragmentResultListener;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EditProductModalDialog extends BottomSheetDialogFragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_product_modal, container, false);

        Bundle bundle = getArguments();
        String storeId = bundle.getString("store_id");
        String productId = bundle.getString("product_id");
        String bundleName = bundle.getString("product_name");
        String bundleBrand = bundle.getString("product_brand");
        String bundlePrice = bundle.getString("product_price");
        Double bundlePriceDouble = bundle.getDouble("product_price_double");
        String bundleImg = bundle.getString("product_img");

        ImageView productPic = v.findViewById(R.id.productImageModalDisplay3);
        Glide.with(getContext()).load(bundleImg).into(productPic);


        EditText productName = v.findViewById(R.id.productNameInput);
        EditText productBrand = v.findViewById(R.id.productBrandInput);
        EditText productPrice = v.findViewById(R.id.productPriceInput);

        productName.setText(bundleName);
        productBrand.setText(bundleBrand);
        productPrice.setText(String.format("%.2f", bundlePriceDouble));

        TextView delete_product = v.findViewById(R.id.deleteProductOwner);
        Button done = v.findViewById(R.id.doneEditingProduct);
        TextView editImg = v.findViewById(R.id.editImageOwner);

        editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageUrlModalDialog modal = new ImageUrlModalDialog();
                modal.show(getFragmentManager(), "editProductModalDialog");

                getFragmentManager().setFragmentResultListener("imgUrl", EditProductModalDialog.this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        String newImgUrl = result.getString("imgUrl");
                        ImageView profilePic = v.findViewById(R.id.productImageModalDisplay3);
                        Glide.with(EditProductModalDialog.this).load(newImgUrl).into(profilePic);
                        FirebaseDatabase.getInstance().getReference("stores").child(storeId).child("products").child(productId).child("image").setValue(newImgUrl);
                    }//end onFragmentResult
                });
            }//end onClick
        });


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
