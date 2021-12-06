// Presenter CUSTOMER
package com.example.project;

import android.content.Intent;

public class CustomerPresenter implements Contract.Presenter {
    private Contract.Model model;
    private Contract.View view;

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

        model.setEmail(email);
        model.setPassword(password);

        model.isLoginSuccessful(new LoginCallBack() {
            @Override
            public void loginSuccess(Boolean success) {
                if (success) {
                    view.loginSuccessfulToast();
                    ((LoginCustomerActivity) view).startActivity(new Intent(((LoginCustomerActivity) view), CustomerViewActivity.class));
                } else {
                    view.loginFailedToast();
                }
                view.hideProgressBar();
            }
        });
    }
}
