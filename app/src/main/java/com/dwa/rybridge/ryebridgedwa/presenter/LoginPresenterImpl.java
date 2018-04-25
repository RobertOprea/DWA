package com.dwa.rybridge.ryebridgedwa.presenter;

import android.support.annotation.NonNull;

import com.dwa.rybridge.ryebridgedwa.data.User;
import com.dwa.rybridge.ryebridgedwa.ui.view.LoginView;
import com.dwa.rybridge.ryebridgedwa.validator.EmailValidator;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView view;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;

    public LoginPresenterImpl(LoginView view) {
        this.view = view;
    }

    @Override
    public void initialise() {
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void onLoginClicked(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    checkPoliciesAcceptance();
                } else {
                    view.displayToast("Login failed!");
                }
            }
        });
    }

    @Override
    public void onForgotPasswordClicked(String email) {
        if (email.isEmpty()) {
            view.displayToast("Email field is empty!");
        } else if (!EmailValidator.validate(email)) {
            view.displayToast("Email is invalid!");
        } else {
            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        view.displayToast("E-mail sent successfully!");
                    }
                }
            });
        }
    }

    private void checkPoliciesAcceptance() {
        firebaseDatabase.getReference().child("userData").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User loggedInUser = dataSnapshot.getValue(User.class);
                if (loggedInUser.arePoliciesAccepted()) {
                    view.navigateToMainActivity();
                } else {
                    view.navigateToPoliciesActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
