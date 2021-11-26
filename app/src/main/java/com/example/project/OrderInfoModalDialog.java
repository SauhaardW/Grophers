package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class OrderInfoModalDialog extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.order_info_modal, container, false);

        String[] arraySpinner = new String[]{"Processing order", "Order completed", "Order canceled"};
        Spinner modalSpinner = (Spinner) v.findViewById(R.id.spinnerChoicesOrderInfo);
        ArrayList<String> spinnerListChoices = new ArrayList<String>();
        spinnerListChoices.addAll(Arrays.asList(arraySpinner));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(OrderInfoModalDialog.this, android.R.layout.simple_spinner_item,spinnerListChoices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modalSpinner.setAdapter(adapter);

        return v;
    }//end onCreateView
}//end OrderInfoModalDialog
