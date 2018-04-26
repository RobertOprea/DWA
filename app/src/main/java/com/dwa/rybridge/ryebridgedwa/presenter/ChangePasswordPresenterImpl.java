package com.dwa.rybridge.ryebridgedwa.presenter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.dwa.rybridge.ryebridgedwa.ui.view.ChangePasswordView;
import com.dwa.rybridge.ryebridgedwa.validator.PasswordValidator;

import android.support.annotation.NonNull;
import android.util.Log;

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
            view.displayToast("Old password is invalid!");
            return;
        }

        final String newPassword = view.getNewPassword();

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
                    view.displayToast("Password was changed successfully!");
                    view.finishView();
                }
            }
        });
    }
}
