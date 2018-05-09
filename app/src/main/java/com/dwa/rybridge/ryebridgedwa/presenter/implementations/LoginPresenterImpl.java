package com.dwa.rybridge.ryebridgedwa.presenter.implementations;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.dwa.rybridge.ryebridgedwa.data.User;
import com.dwa.rybridge.ryebridgedwa.presenter.LoginPresenter;
import com.dwa.rybridge.ryebridgedwa.ui.view.LoginView;
import com.dwa.rybridge.ryebridgedwa.validator.EmailValidator;

import android.support.annotation.NonNull;

import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.EMPTY_EMAIL;
import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.FAILED_LOGIN;
import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.INVALID_EMAIL;
import static com.dwa.rybridge.ryebridgedwa.constants.FirebaseConstants.USER_DATA;
import static com.dwa.rybridge.ryebridgedwa.constants.GeneralUseConstants.SUCCESSFUL_EMAIL_SEND;

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
                    view.displayToast(FAILED_LOGIN);
                }
            }
        });
    }

    @Override
    public void onForgotPasswordClicked(String email) {
        if (email.isEmpty()) {
            view.displayToast(EMPTY_EMAIL);
        } else if (!EmailValidator.validate(email)) {
            view.displayToast(INVALID_EMAIL);
        } else {
            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        view.displayToast(SUCCESSFUL_EMAIL_SEND);
                    }
                }
            });
        }
    }

    private void checkPoliciesAcceptance() {
        firebaseDatabase.getReference().child(USER_DATA).child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
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
