// Model (Customer *AND* Owner)
package com.example.project;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class LoginModelCustomer implements Contract.ModelCustomer {
    String email;
    String password;

    protected LoginModelCustomer(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public void isLoginSuccessful(LoginCallBackCustomer loginCallBackCustomer) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase.getInstance().getReference("users").child("customers").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                loginCallBackCustomer.loginValid();
                            } else {
                                loginCallBackCustomer.loginInvalid();
                            }

                        } else {
                            loginCallBackCustomer.loginDataFailed();
                        }
                    }
                });
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
