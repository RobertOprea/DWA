package com.dwa.rybridge.ryebridgedwa.ui.view;

public interface RegistrationView extends LoadingView{

    String getFirstName();

    String getLastName();

    String getEmailAddress();

    String getMobileNumber();

    String getPassword();

    String getRepeatPassword();

    String getAccessCode();

    void displayToastMessage(String message);

    void navigateToPolictiesActivity();
}
