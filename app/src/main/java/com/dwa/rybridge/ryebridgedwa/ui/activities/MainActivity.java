package com.dwa.rybridge.ryebridgedwa.ui.activities;

import com.dwa.rybridge.ryebridgedwa.R;
import com.dwa.rybridge.ryebridgedwa.navigator.Navigator;
import com.dwa.rybridge.ryebridgedwa.presenter.MainPresenter;
import com.dwa.rybridge.ryebridgedwa.presenter.MainPresenterImpl;
import com.dwa.rybridge.ryebridgedwa.ui.view.MainView;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.upload_all_container) View uploadAllContainer;

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
        presenter.onSingOutClicked();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            presenter.onPermissionGranted();
        }
    }

    private void initPresenter() {
        presenter = new MainPresenterImpl(this, this);
        presenter.initialise(this);
    }

    private void initNavigator() {
        navigator = Navigator.newInstance();
        navigator.setSourceActivity(this);
    }
}
