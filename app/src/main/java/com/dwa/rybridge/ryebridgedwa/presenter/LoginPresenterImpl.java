package com.dwa.rybridge.ryebridgedwa.presenter;

import android.support.annotation.NonNull;

import com.dwa.rybridge.ryebridgedwa.ui.view.LoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView view;
    private FirebaseAuth auth;

    public LoginPresenterImpl(LoginView view) {
        this.view = view;
    }

    @Override
    public void initialise() {
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onLoginClicked(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    view.displayToast("Login successful!");
                } else {
                    view.displayToast("Login failed!");
                }
            }
        });
    }
}
