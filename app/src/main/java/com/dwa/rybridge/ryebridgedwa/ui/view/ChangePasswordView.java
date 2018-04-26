package com.dwa.rybridge.ryebridgedwa.ui.view;

public interface ChangePasswordView {

    String getOldPassword();

    String getNewPassword();

    String getConfirmationString();

    void finishView();

    void displayToast(String message);

}
