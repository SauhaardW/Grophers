package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class OwnerOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_orders);

        TextView orderInfo = (TextView) findViewById(R.id.orderInfoButton);
        orderInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderInfoModalDialog modalDialog = new OrderInfoModalDialog();
                modalDialog.show(getSupportFragmentManager(), "ownerOrderInfoModal");
            }//end onClick
        });
    }//end onCreate
}