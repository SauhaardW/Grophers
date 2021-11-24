package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class SetUpStoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_store);

        TextView doneButton = (TextView) findViewById(R.id.doneSetUpStore);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerStore();
            }
        });
    }

    private void registerStore() {
        EditText editTextName = (EditText)findViewById(R.id.storeNameTextBox);
        EditText editTextHours = (EditText)findViewById(R.id.openHoursTextBox);
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

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users").child("owners").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Owner owner = task.getResult().getValue(Owner.class);
                    Store newStore = new Store(name, hours, imgURL, owner);
                    owner.setStoreId(newStore.getId());

                    FirebaseDatabase.getInstance().getReference("stores").child(((Integer)newStore.getId()).toString()).setValue(newStore);
                    FirebaseDatabase.getInstance().getReference("users").child("owners").child(uid).setValue(owner);

                    startActivity(new Intent(SetUpStoreActivity.this, OwnerHomeActivity.class));
                } else {
                    Log.println(Log.ERROR, "SetUpStoreActivity", "Error while accessing user data.");
                }
            }
        });

    }
}