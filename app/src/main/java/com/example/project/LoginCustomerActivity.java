// View CUSTOMER
package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class LoginCustomerActivity extends AppCompatActivity implements Contract.View {
    private Contract.Presenter presenter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_customer);

        progressBar = (ProgressBar) findViewById(R.id.progressBarCustomerLogin);

        presenter = new CustomerPresenter(this, new LoginModel());

        ImageView backButton = (ImageView) findViewById(R.id.backButtonTopBarCustomer);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.backButtonClicked();
            }
        });

        Button submitButton = (Button) findViewById(R.id.loginSubmitCustomer);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.submitButtonClicked(getEmail(), getPassword());
            }
        });

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.registerButtonClicked();
            }
        });
    }//end onCreate

    @Override
    public String getEmail(){
        EditText editTextEmail = (EditText) findViewById(R.id.editTextEmailCustomerLogin);
        return editTextEmail.getText().toString().trim();
    }//end getEmail

    @Override
    public String getPassword(){
        EditText editTextPassword = (EditText) findViewById(R.id.editTextPasswordCustomerLogin);
        return editTextPassword.getText().toString().trim();
    }//end getEmail

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }//end showProgressBar

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }//end hideProgressBar

    @Override
    public void loginSuccessfulToast() {
        Toast.makeText(LoginCustomerActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
    }//end loginSuccessfulToast

    @Override
    public void loginFailedToast() {
        Toast.makeText(LoginCustomerActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
    }//end loginFailedToast

    @Override
    public void loginUserDataFailedToast() {
        Toast.makeText(LoginCustomerActivity.this, "Failed to get User Data", Toast.LENGTH_LONG).show();
    }//end loginUserDataFailedToast
}