package com.example.project;

public interface Contract {
    public interface View {
        void backButtonClicked();
        void registerButtonClicked();
        void submitButtonClicked();
        void getEmail();
        void getPassword();
        void loginSuccessfulToast();
        void loginFailedToast();
        void loginUserDataFailedToast();
    }

    public interface Presenter {
        void backButtonFunctionality();
        void registerButtonFunctionality();
        void submitButtonFunctionality();
    }

    public interface Model {
        void isLoginSuccessful();
    }
}
