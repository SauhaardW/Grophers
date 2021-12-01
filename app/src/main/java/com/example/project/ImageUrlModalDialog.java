package com.example.project;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ImageUrlModalDialog extends BottomSheetDialogFragment {

//    private ModalListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_get_img_url, container,false);

        TextView done_modal = (TextView) v.findViewById(R.id.doneTextModal);
        EditText imgUrl = (EditText) v.findViewById(R.id.imageUrlTextBox);

        done_modal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("imgUrl", imgUrl.getText().toString());
                getParentFragmentManager().setFragmentResult("imgUrl", bundle);
                dismiss();
            }
        });

        return v;
    }
