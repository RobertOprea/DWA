package com.dwa.rybridge.ryebridgedwa.ui.view;

public interface LoginView extends LoadingView{

    void displayToast(String message);

    void navigateToMainActivity();

    void navigateToPoliciesActivity();
}
