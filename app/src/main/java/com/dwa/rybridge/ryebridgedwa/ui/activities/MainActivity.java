package com.dwa.rybridge.ryebridgedwa.ui.activities;

import com.dwa.rybridge.ryebridgedwa.R;
import com.dwa.rybridge.ryebridgedwa.navigator.Navigator;
import com.dwa.rybridge.ryebridgedwa.presenter.MainPresenter;
import com.dwa.rybridge.ryebridgedwa.presenter.MainPresenterImpl;
import com.dwa.rybridge.ryebridgedwa.ui.view.MainView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    private MainPresenter presenter;
    private Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initPresenter();
        initNavigator();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void navigateToLoginScreen() {
        navigator.navigateToLoginActivity();
        finish();
    }

    @OnClick(R.id.sign_out_button)
    public void onSignOutClicked() {
        presenter.onSingOutClicked();
    }

    @OnClick(R.id.view_policies_link_textview)
    public void onViewPoliciesClicked() {
        navigator.navigateToSimplePoliciesActivity();
    }

    private void initPresenter() {
        presenter = new MainPresenterImpl(this);
        presenter.initialise();
    }

    private void initNavigator() {
        navigator = Navigator.newInstance();
        navigator.setSourceActivity(this);
    }
}
