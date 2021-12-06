// Presenter OWNER
package com.example.project;

import android.content.Intent;

public class OwnerPresenter implements Contract.Presenter {
    private Contract.Model model;
    private Contract.View view;

    public OwnerPresenter(Contract.View view, Contract.Model model){
        this.model = model;
        this.view = view;
    }

    @Override
    public void backButtonClicked() {
        ((LoginOwnerActivity) view).finish();
    }

    @Override
    public void registerButtonClicked() {
        ((LoginOwnerActivity) view).startActivity(new Intent((LoginOwnerActivity) view, RegisterOwnerActivity.class));
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

        model.isLoginSuccessful(new LoginCallBackOwner() {
            @Override
            public void loginValidStoreCreation() {
                view.loginSuccessfulToast();
                view.hideProgressBar();
                ((LoginOwnerActivity) view).startActivity(new Intent(((LoginOwnerActivity) view),  SetUpStoreActivity.class));
            }

            @Override
            public void loginValid() {
                view.loginSuccessfulToast();
                view.hideProgressBar();
                ((LoginOwnerActivity) view).startActivity(new Intent(((LoginOwnerActivity) view), OwnerProductListActivity.class));
            }

            @Override
            public void loginInvalid() {
                view.loginFailedToast();
                view.hideProgressBar();
            }

            @Override
            public void loginDataFailed() {
                view.loginUserDataFailedToast();
                view.hideProgressBar();
            }
        });
    }
}
