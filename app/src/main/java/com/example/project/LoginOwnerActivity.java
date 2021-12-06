// View OWNER
package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class LoginOwnerActivity extends AppCompatActivity implements Contract.View {

    private Contract.Presenter presenter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_owner);

        progressBar = (ProgressBar) findViewById(R.id.progressBarOwnerLogin);

        presenter = new OwnerPresenter(this, new LoginModel("", ""));

        ImageView backButton = (ImageView) findViewById(R.id.backButtonTopBarOwner);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.backButtonClicked();
            }
        });

        Button submitButton = (Button) findViewById(R.id.loginSubmitOwner);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.submitButtonClicked(getEmail(), getPassword());
            }
        });

        Button registerButton = (Button) findViewById(R.id.registerButton2);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.registerButtonClicked();
            }
        });
    }//end onCreate

    @Override
    public String getEmail() {
        EditText editTextEmail = (EditText) findViewById(R.id.editTextEmailOwnerLogin);
        return editTextEmail.getText().toString().trim();
    }//end getEmail

    @Override
    public String getPassword() {
        EditText editTextPassword = (EditText) findViewById(R.id.editTextPasswordOwnerLogin);
        return editTextPassword.getText().toString().trim();
    }//end getPassword

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
        Toast.makeText(LoginOwnerActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
    }//end loginSuccessfulToast

    @Override
    public void loginFailedToast() {
        Toast.makeText(LoginOwnerActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
    }//end loginFailedToast

    @Override
    public void loginUserDataFailedToast() {
        Toast.makeText(LoginOwnerActivity.this, "Failed to get User Data", Toast.LENGTH_LONG).show();
    }//end loginUserDataFailedToast

    @Override
    public void setEmailEmptyError() {
        EditText editTextEmail = (EditText) findViewById(R.id.editTextEmailOwnerLogin);
        editTextEmail.setError("The email field is empty");
        editTextEmail.requestFocus();
    }

    @Override
    public void setPasswordEmptyError() {
        EditText editTextEmail = (EditText) findViewById(R.id.editTextPasswordOwnerLogin);
        editTextEmail.setError("The password field is empty");
        editTextEmail.requestFocus();
    }
}