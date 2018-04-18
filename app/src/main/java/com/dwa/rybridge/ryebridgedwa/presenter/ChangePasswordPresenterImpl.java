package com.dwa.rybridge.ryebridgedwa.presenter;

import com.dwa.rybridge.ryebridgedwa.ui.view.ChangePasswordView;
import com.dwa.rybridge.ryebridgedwa.validator.PasswordValidator;

public class ChangePasswordPresenterImpl implements ChangePasswordPresenter {

    private ChangePasswordView view;

    public ChangePasswordPresenterImpl(ChangePasswordView view) {
        this.view = view;
    }

    @Override
    public void onChangePasswordClicked() {
        String oldPassword = view.getOldPassword();

        if (!PasswordValidator.validatePassword(oldPassword)) {
            view.displayToast("Old password is invalid!");
            return;
        }

        String newPassword = view.getNewPassword();

        if (!PasswordValidator.validatePassword(newPassword)) {
            view.displayToast("New password is invalid!");
            return;
        }

        String repeatPassword = view.getConfirmationString();

        if (!PasswordValidator.validatePassword(repeatPassword)) {
            view.displayToast("Repeat password is invalid!");
            return;
        }

        if (!PasswordValidator.validatePasswordAndConfirmPassword(newPassword, repeatPassword)) {
            view.displayToast("Passwords do not match!");
        } else {
            //TODO: happy flow
        }
    }
}
