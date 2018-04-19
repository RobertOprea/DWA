package com.dwa.rybridge.ryebridgedwa.presenter;

public interface LoginPresenter {

    void initialise();

    void onLoginClicked(String email, String password);

    void onForgotPasswordClicked(String email);
}
