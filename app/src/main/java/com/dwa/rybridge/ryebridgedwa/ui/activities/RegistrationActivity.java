package com.dwa.rybridge.ryebridgedwa.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.dwa.rybridge.ryebridgedwa.R;
import com.dwa.rybridge.ryebridgedwa.presenter.RegistrationPresenter;
import com.dwa.rybridge.ryebridgedwa.presenter.RegistrationPresenterImpl;
import com.dwa.rybridge.ryebridgedwa.ui.view.RegistrationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity implements RegistrationView {

    @BindView(R.id.first_name_input_edittext)
    EditText firstNameEditText;
    @BindView(R.id.last_name_input_edittext)
    EditText lastNameEditText;
    @BindView(R.id.email_address_input_edittext)
    EditText emailAddressEditText;
    @BindView(R.id.mobile_number_input_edittext)
    EditText mobileNumberEditText;
    @BindView(R.id.password_input_edittext)
    EditText passwordEditText;
    @BindView(R.id.repeat_password_input_edittext)
    EditText repeatPasswordEditText;
    @BindView(R.id.access_code_input_edittext)
    EditText accessCodeEditText;

    private RegistrationPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ButterKnife.bind(this);

        initPresenter();
    }

    private void initPresenter() {
        presenter = new RegistrationPresenterImpl(this);
        presenter.initialise();

    }

    @Override
    public String getFirstName() {
        return firstNameEditText.getText().toString();
    }

    @Override
    public String getLastName() {
        return lastNameEditText.getText().toString();
    }

    @Override
    public String getEmailAddress() {
        return emailAddressEditText.getText().toString();
    }

    @Override
    public String getMobileNumber() {
        return mobileNumberEditText.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordEditText.getText().toString();
    }

    @Override
    public String getRepeatPassword() {
        return repeatPasswordEditText.getText().toString();
    }

    @Override
    public String getAccessCode() {
        return accessCodeEditText.getText().toString();
    }

    @Override
    public void displayToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.register_button)
    public void onRegisterClicked() {
        presenter.onRegisterClicked();
    }
}
