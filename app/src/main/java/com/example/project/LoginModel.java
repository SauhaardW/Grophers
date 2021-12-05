// Model (Customer *AND* Owner)
package com.example.project;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginModel implements Contract.Model {
    @Override
    public void isLoginSuccessful(LoginCallBack loginCallBack, String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loginCallBack.loginSuccess(task.isSuccessful());
            }
        });
    }
}
