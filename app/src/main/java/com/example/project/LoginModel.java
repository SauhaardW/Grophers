// Model (Customer *AND* Owner)
package com.example.project;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginModel implements Contract.Model {
    String email;
    String password;

    protected LoginModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public void isLoginSuccessful(LoginCallBack loginCallBack) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loginCallBack.loginSuccess(task.isSuccessful());
            }
        });
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
