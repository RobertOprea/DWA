package com.dwa.rybridge.ryebridgedwa.presenter.implementations;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.dwa.rybridge.ryebridgedwa.presenter.ChangePasswordPresenter;
import com.dwa.rybridge.ryebridgedwa.ui.view.ChangePasswordView;
import com.dwa.rybridge.ryebridgedwa.validator.PasswordValidator;

import android.support.annotation.NonNull;

import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.INVALID_NEW_PASSWORD;
import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.INVALID_OLD_PASSWORD;
import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.INVALID_REPEAT_PASSWORD;
import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.MISMATCHED_PASSWORDS;
import static com.dwa.rybridge.ryebridgedwa.constants.GeneralUseConstants.SUCCESSFUL_PASSWORD_CHANGE;

public class ChangePasswordPresenterImpl implements ChangePasswordPresenter {

    private ChangePasswordView view;
    private FirebaseUser firebaseUser;

    public ChangePasswordPresenterImpl(ChangePasswordView view) {
        this.view = view;
    }

    @Override
    public void initialise() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void onChangePasswordClicked() {
        String oldPassword = view.getOldPassword();

        if (!PasswordValidator.validatePassword(oldPassword)) {
            view.displayToast(INVALID_OLD_PASSWORD);
            return;
        }

        final String newPassword = view.getNewPassword();

        if (!PasswordValidator.validatePassword(newPassword)) {
            view.displayToast(INVALID_NEW_PASSWORD);
            return;
        }

        String repeatPassword = view.getConfirmationString();

        if (!PasswordValidator.validatePassword(repeatPassword)) {
            view.displayToast(INVALID_REPEAT_PASSWORD);
            return;
        }

        if (!PasswordValidator.validatePasswordAndConfirmPassword(newPassword, repeatPassword)) {
            view.displayToast(MISMATCHED_PASSWORDS);
        } else {
            AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), oldPassword);
            firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        changePassword(newPassword);
                    }
                }
            });
        }
    }

    private void changePassword(String newPassword) {
        firebaseUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    view.displayToast(SUCCESSFUL_PASSWORD_CHANGE);
                    view.finishView();
                }
            }
        });
    }
}
