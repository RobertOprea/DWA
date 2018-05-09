package com.dwa.rybridge.ryebridgedwa.ui.activities;

import com.dwa.rybridge.ryebridgedwa.R;
import com.dwa.rybridge.ryebridgedwa.navigator.Navigator;
import com.dwa.rybridge.ryebridgedwa.presenter.SplashPresenter;
import com.dwa.rybridge.ryebridgedwa.presenter.implementations.SplashPresenterImpl;
import com.dwa.rybridge.ryebridgedwa.ui.view.SplashView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity implements SplashView {

    private Navigator navigator;
    private SplashPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setupNavigator();
        setupPresenter();
    }

    private void setupNavigator() {
        navigator = Navigator.newInstance();
        navigator.setSourceActivity(this);
    }

    private void setupPresenter() {
        presenter = new SplashPresenterImpl(this);
        presenter.initialise();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onViewCreated();
    }

    @Override
    public void navigateToLoginScreen() {
        navigator.navigateToLoginActivity();
    }

    @Override
    public void navigateToPoliciesScreen() {
        navigator.navigateToPoliciesActivity();
    }

    @Override
    public void navigateToMainScreen() {
        navigator.navigateToMainActivity();
    }
}
