package com.dwa.rybridge.ryebridgedwa.presenter;

import com.google.firebase.auth.FirebaseAuth;

import com.dwa.rybridge.ryebridgedwa.ui.view.MainView;

public class MainPresenterImpl implements MainPresenter {

    private FirebaseAuth firebaseAuth;
    private MainView view;

    public MainPresenterImpl(MainView view) {
        this.view = view;
    }

    @Override
    public void initialise() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onSingOutClicked() {
        firebaseAuth.signOut();
        view.navigateToLoginScreen();
    }
}
