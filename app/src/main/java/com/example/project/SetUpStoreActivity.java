package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class SetUpStoreActivity extends AppCompatActivity {

    public String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_store);

        TextView addProfilePicButton = (TextView) findViewById(R.id.addProfilePhoto);
        addProfilePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageUrlModalDialog modal = new ImageUrlModalDialog();
                modal.show(getSupportFragmentManager(), "setUpStoreModal");

                getSupportFragmentManager().setFragmentResultListener("imgUrl", SetUpStoreActivity.this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        imgUrl = result.getString("imgUrl");
                        ImageView profilePic = findViewById(R.id.profileImg);
                        Glide.with(SetUpStoreActivity.this).load(imgUrl).into(profilePic);
                    }//end onFragmentResult
                });
            }//end onClick
        });

        TextView doneButton = (TextView) findViewById(R.id.doneSetUpStore);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerStore();
            }
        });
    }//end onCreate

    private void registerStore() {
        EditText editTextName = (EditText)findViewById(R.id.storeNameTextBox);
        EditText editTextHours = (EditText)findViewById(R.id.openHoursTextBox);
        ImageView profileImg = (ImageView)findViewById(R.id.profileImg);

        String name = editTextName.getText().toString().trim();
        String hours = editTextHours.getText().toString().trim();

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

                    FirebaseDatabase.getInstance().getReference("stores").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                long count = task.getResult().getChildrenCount();
                                Store newStore = new Store(name, hours, imgUrl, owner, (int)count);

                                owner.setStoreId((int)count);

                                FirebaseDatabase.getInstance().getReference("stores").child(((Integer)newStore.getId()).toString()).setValue(newStore);
                                FirebaseDatabase.getInstance().getReference("users").child("owners").child(uid).setValue(owner);

                                startActivity(new Intent(SetUpStoreActivity.this, OwnerProductListActivity.class));
                            } else {
                                Log.println(Log.ERROR, "SetUpStoreActivity", "Error while generating store id.");
                            }
                        }
                    });
                } else {
                    Log.println(Log.ERROR, "SetUpStoreActivity", "Error while accessing user data.");
                }
            }
        });

    }
}