package com.dwa.rybridge.ryebridgedwa.ui.activities;

import com.dwa.rybridge.ryebridgedwa.R;
import com.dwa.rybridge.ryebridgedwa.navigator.Navigator;
import com.dwa.rybridge.ryebridgedwa.presenter.MainPresenter;
import com.dwa.rybridge.ryebridgedwa.presenter.implementations.MainPresenterImpl;
import com.dwa.rybridge.ryebridgedwa.ui.view.MainView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.upload_all_container) View uploadAllContainer;
    @BindView(R.id.loading_view) View loadingView;

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

    @Override
    public void showUploadAllView() {
        uploadAllContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideUploadAllView() {
        uploadAllContainer.setVisibility(View.GONE);
    }

    @OnClick(R.id.sign_out_button)
    public void onSignOutClicked() {
        presenter.onSignOutClicked();
    }

    @OnClick(R.id.view_policies_link_textview)
    public void onViewPoliciesClicked() {
        navigator.navigateToSimplePoliciesActivity();
    }

    @OnClick(R.id.change_password_link_textview)
    public void onChangePasswordClicked() {
        navigator.navigateToChangePasswordActivity();
    }

    @OnClick(R.id.report_hazard_button)
    public void onReportHazardClicked() {
        navigator.navigateToReporterDetailsActivity();
    }

    @OnClick(R.id.upload_all_button)
    public void onUploadAllClicked() {
        presenter.onUploadAllClicked();
    }

    private void initPresenter() {
        presenter = new MainPresenterImpl(this, this);
        presenter.initialise();
    }

    private void initNavigator() {
        navigator = Navigator.newInstance();
        navigator.setSourceActivity(this);
    }

    @Override
    public void hideLoadingView() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingView() {
        loadingView.setVisibility(View.VISIBLE);
    }
}
