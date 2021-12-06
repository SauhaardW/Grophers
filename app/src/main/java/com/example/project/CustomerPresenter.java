// Presenter CUSTOMER
package com.example.project;

import android.content.Intent;
import android.util.Log;

public class CustomerPresenter implements Contract.Presenter {
    protected Contract.Model model;
    protected Contract.View view;

    public CustomerPresenter(Contract.View view, Contract.Model model){
        this.model = model;
        this.view = view;
    }

    @Override
    public void backButtonClicked() {
        ((LoginCustomerActivity) view).finish();
    }

    @Override
    public void registerButtonClicked() {
        ((LoginCustomerActivity) view).startActivity(new Intent((LoginCustomerActivity) view, RegisterCustomerActivity.class));
    }

    @Override
    public void submitButtonClicked(String email, String password) {
        view.showProgressBar();

        if (email.isEmpty()) {
            view.setEmailEmptyError();
            view.hideProgressBar();
            return;
        } else if (password.isEmpty()) {
            view.setPasswordEmptyError();
            view.hideProgressBar();
            return;
        }

        model.setEmail(email);
        model.setPassword(password);

        model.isLoginSuccessful(new LoginCallBack() {
            @Override
            public void loginValidStoreCreation() {
                // Do nothing, we don't reach this case as customers
                return;
            }

            @Override
            public void loginValid() {
                view.loginSuccessfulToast();
                view.hideProgressBar();
                ((LoginCustomerActivity) view).startActivity(new Intent(((LoginCustomerActivity) view), CustomerViewActivity.class));
            }

            @Override
            public void loginInvalid() {
                view.loginFailedToast();
                view.hideProgressBar();
            }

            @Override
            public void loginValidationFailed() {
                view.loginUserDataFailedToast();
                view.hideProgressBar();
            }
        });
    }
}
