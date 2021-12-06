// View CUSTOMER
package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class OldLoginCustomerActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_customer);

        // Back Button handling
        ImageView backButton = (ImageView) findViewById(R.id.backButtonTopBarCustomer);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Create Firebase Auth instance
        mAuth = FirebaseAuth.getInstance();

        Button submitButton = (Button) findViewById(R.id.loginSubmitCustomer);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call function that handles log-ins
                logInCustomer();
            }
        });

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OldLoginCustomerActivity.this, RegisterCustomerActivity.class));
            }
        });
    }

    private void logInCustomer() {
        // Declare Views
        EditText editTextEmail = (EditText) findViewById(R.id.editTextEmailCustomerLogin);
        EditText editTextPassword = (EditText) findViewById(R.id.editTextPasswordCustomerLogin);

        // Get text from views
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Set progress bar to visible, we are processing
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBarCustomerLogin);
        pb.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(OldLoginCustomerActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(OldLoginCustomerActivity.this, CustomerViewActivity.class));
                } else {
                    Toast.makeText(OldLoginCustomerActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
                pb.setVisibility(View.INVISIBLE);
            }
        });
    }
}