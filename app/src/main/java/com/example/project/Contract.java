package com.example.project;

public interface Contract {
    public interface View {
        String getEmail();
        String getPassword();
        void showProgressBar();
        void hideProgressBar();
        void loginSuccessfulToast();
        void loginFailedToast();
        void loginUserDataFailedToast();
    }

    public interface Presenter {
        void backButtonClicked();
        void registerButtonClicked();
        void submitButtonClicked(String email, String password);
    }

    public interface Model {
        void isLoginSuccessful(LoginCallBack loginCallBack, String email, String password);
    }
}
