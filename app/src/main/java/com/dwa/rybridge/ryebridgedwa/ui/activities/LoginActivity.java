package com.dwa.rybridge.ryebridgedwa.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.dwa.rybridge.ryebridgedwa.R;
import com.dwa.rybridge.ryebridgedwa.navigator.Navigator;
import com.dwa.rybridge.ryebridgedwa.presenter.LoginPresenter;
import com.dwa.rybridge.ryebridgedwa.presenter.implementations.LoginPresenterImpl;
import com.dwa.rybridge.ryebridgedwa.ui.view.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.email_input_edittext) EditText emailEditText;
    @BindView(R.id.password_input_edittext) EditText passwordEditText;
    @BindView(R.id.loading_view) View loadingView;

    private LoginPresenter loginPresenter;
    private Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        initNavigator();
        initPresenter();
    }

    @Override
    public void displayToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToMainActivity() {
        navigator.navigateToMainActivity();
        finish();
    }

    @Override
    public void navigateToPoliciesActivity() {
        navigator.navigateToPoliciesActivity();
        finish();
    }

    @OnClick(R.id.sign_in_link_textview)
    public void onLoginClicked() {
        closeKeyboard();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        loginPresenter.onLoginClicked(email, password);
    }

    @OnClick(R.id.forgot_password_link_textview)
    public void onForgotPasswordClicked() {
        closeKeyboard();
        loginPresenter.onForgotPasswordClicked(emailEditText.getText().toString());
    }

    @OnClick(R.id.register_user_link_textview)
    public void onRegisterNewUserClicked() {
        navigator.navigateToRegistrationActivity();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void initNavigator() {
        navigator = Navigator.newInstance();
        navigator.setSourceActivity(this);
    }

    private void initPresenter() {
        loginPresenter = new LoginPresenterImpl(this);
        loginPresenter.initialise();
    }

    @Override
    public void hideLoadingView() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingView() {
        loadingView.setVisibility(View.VISIBLE);
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(emailEditText.getWindowToken(), 0);
    }
}
