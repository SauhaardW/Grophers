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
        void setEmailEmptyError();
        void setPasswordEmptyError();
    }

    public interface Presenter {
        void backButtonClicked();
        void registerButtonClicked();
        void submitButtonClicked(String email, String password);
    }

    public interface ModelOwner {
        void isLoginSuccessful(LoginCallBackOwner loginCallBackOwner);
        void setEmail(String email);
        void setPassword(String password);
    }

    public interface ModelCustomer {
        void isLoginSuccessful(LoginCallBackCustomer loginCallBackCustomer);
        void setEmail(String email);
        void setPassword(String password);
    }
}
