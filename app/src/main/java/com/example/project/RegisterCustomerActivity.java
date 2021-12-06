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
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterCustomerActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

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

        // Create listener for registration button
        Button registerButton = (Button) findViewById(R.id.registerSubmit);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call function that handles registration
                registerCustomer();
            }
        });
    }

    private boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("^(.+)@(\\S+)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void registerCustomer() {
        // Declare views by ID
        EditText editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        EditText editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);

        // Grab text from views
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String passwordConfirmation = editTextConfirmPassword.getText().toString().trim();

        // Validate password
        if (!password.equals(passwordConfirmation)) {
            editTextConfirmPassword.setError("The passwords do not match.");
            editTextConfirmPassword.requestFocus();
            return;
        } else if (password.length() < 6) {
            editTextPassword.setError("The password must be at least 6 characters long.");
            editTextPassword.requestFocus();
            return;
        // Validate username
        } else if (username.isEmpty()) {
            editTextUsername.setError("The username field is empty.");
            editTextUsername.requestFocus();
            return;
        // Validate email
        } else if (email.isEmpty()) {
            editTextEmail.setError("The email field is empty.");
            editTextEmail.requestFocus();
            return;
        } else if (!validateEmail(email)) {
            editTextEmail.setError("The email field is invalid.");
            editTextEmail.requestFocus();
            return;
        }

        // Set progress bar to visible, we're processing stuff now
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);

        // Use Firebase mAuth to create new user
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Create User Object
                    User user = new User(username, email);

                    FirebaseDatabase.getInstance().getReference("users").child("customers").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterCustomerActivity.this, "Your account has been registered", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterCustomerActivity.this, LoginCustomerActivity.class));
                            } else {
                                Toast.makeText(RegisterCustomerActivity.this, "Your account failed to be registered", Toast.LENGTH_LONG).show();
                            }
                            pb.setVisibility(View.INVISIBLE);
                        }
                    });
                } else {
                    Toast.makeText(RegisterCustomerActivity.this, "Your account failed to be registered", Toast.LENGTH_LONG).show();
                }
                // Processing is done, set progress bar back to invisible
                pb.setVisibility(View.INVISIBLE);
            }
        });

    }
}