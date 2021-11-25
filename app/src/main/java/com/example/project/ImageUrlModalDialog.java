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

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ImageUrlModalDialog extends BottomSheetDialogFragment {

    private ModalListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_set_up_store, container,false);

        TextView done_modal = (TextView) v.findViewById(R.id.doneTextModal);
        EditText imgUrl = (EditText) v.findViewById(R.id.imageUrlTextBox);

        done_modal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onButtonClicked(String.valueOf(imgUrl.getText()));
                dismiss();
            }//end onClick
        });

        return v;
    }//end onCreateView

    public interface ModalListener{
        void onButtonClicked(String img_url);
    }//end ModalListener

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mListener = (ModalListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement ModalListener");
        }//end catch

    }//end onAttach
}//end ImageUrlModalDialog
