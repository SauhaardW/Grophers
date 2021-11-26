package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;

public class OwnerOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_orders);

        TextView orderInfo = (TextView) findViewById(R.id.orderInfoButton);
        orderInfo.setOnClickListener(view -> {
            BottomSheetDialog orderInfoModal = new BottomSheetDialog(OwnerOrdersActivity.this);
            View modalView = getLayoutInflater().inflate(R.layout.order_info_modal, null);

            createSpinner(modalView);

            orderInfoModal.setContentView(modalView);
            orderInfoModal.show();
        });
    }//end onCreate

    public void createSpinner(View modalView){
        Spinner modalSpinner = (Spinner) modalView.findViewById(R.id.spinnerChoicesOrderInfo);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(OwnerOrdersActivity.this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.orderInfoModalList));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modalSpinner.setAdapter(adapter);
    }//end createSpinner
}