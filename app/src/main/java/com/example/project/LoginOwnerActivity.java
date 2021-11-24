package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class LoginOwnerActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_owner);

        // Create Firebase Auth instance
        mAuth = FirebaseAuth.getInstance();

        Button submitButton = (Button) findViewById(R.id.loginSubmitOwner);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call function that handles log-ins
                logInOwner();
            }
        });

        Button registerButton = (Button) findViewById(R.id.registerButton2);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginOwnerActivity.this, RegisterOwnerActivity.class));
            }
        });
    }

    private void logInOwner() {
        // Declare Views
        EditText editTextEmail = (EditText) findViewById(R.id.editTextEmailOwnerLogin);
        EditText editTextPassword = (EditText) findViewById(R.id.editTextPasswordOwnerLogin);

        // Get text from views
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Set progress bar to visible, we are processing
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBarOwnerLogin);
        pb.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginOwnerActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                    FirebaseDatabase.getInstance().getReference("users").child("owners").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().getValue(Owner.class).getStoreId() == 0) {
                                    startActivity(new Intent(LoginOwnerActivity.this, SetUpStoreActivity.class));
                                } else {
                                    startActivity(new Intent(LoginOwnerActivity.this, OwnerHomeActivity.class));
                                }
                            } else {
                                Toast.makeText(LoginOwnerActivity.this, "Failed to get user data", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(LoginOwnerActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
                pb.setVisibility(View.INVISIBLE);
            }
        });
    }
}