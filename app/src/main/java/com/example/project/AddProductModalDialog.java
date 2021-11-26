package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddProductModalDialog extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_product_modal, container, false);

        TextView productName = v.findViewById(R.id.productNameModalDisplay);
        TextView productPrice = v.findViewById(R.id.productPriceModalDisplay2);
        ImageView productImg = v.findViewById(R.id.productImageModalDisplay);

        Bundle bundle = getArguments();
        productName.setText(bundle.getString("product_brand", "") + " " + bundle.getString("product_name", ""));
        productPrice.setText(bundle.getString("product_price", ""));
        //implement image setting

        return v;
    }
}
