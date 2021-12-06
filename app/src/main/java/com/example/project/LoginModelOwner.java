// Model (Customer *AND* Owner)
package com.example.project;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class LoginModelOwner implements Contract.ModelOwner {
    String email;
    String password;

    protected LoginModelOwner(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public void isLoginSuccessful(LoginCallBackOwner loginCallBackOwner) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseDatabase.getInstance().getReference("users").child("owners").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    // User is an owner
                                    if (task.getResult().getValue(Owner.class).getStoreId() == 0) {
                                        loginCallBackOwner.loginValidStoreCreation();
                                    } else {
                                        loginCallBackOwner.loginValid();
                                    }
                                } else {
                                    // User is a customer
                                    loginCallBackOwner.loginInvalid();
                                }

                            } else {
                                loginCallBackOwner.loginDataFailed();
                            }
                        }
                    });
                } else {
                    loginCallBackOwner.loginInvalid();
                }

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
