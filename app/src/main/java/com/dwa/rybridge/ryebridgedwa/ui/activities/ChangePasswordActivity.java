package com.dwa.rybridge.ryebridgedwa.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.dwa.rybridge.ryebridgedwa.R;
import com.dwa.rybridge.ryebridgedwa.presenter.ChangePasswordPresenter;
import com.dwa.rybridge.ryebridgedwa.presenter.implementations.ChangePasswordPresenterImpl;
import com.dwa.rybridge.ryebridgedwa.ui.view.ChangePasswordView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordView{

    @BindView(R.id.old_password_input_edittext)
    EditText oldPasswordEditText;
    @BindView(R.id.new_password_input_edittext)
    EditText newPasswordEditText;
    @BindView(R.id.repeat_new_password_input_edittext)
    EditText confimationEditText;

    ChangePasswordPresenter passwordPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        ButterKnife.bind(this);

        initPresenter();
    }

    @Override
    public String getOldPassword() {
        return oldPasswordEditText.getText().toString();
    }

    @Override
    public String getNewPassword() {
        return newPasswordEditText.getText().toString();
    }

    @Override
    public String getConfirmationString() {
        return confimationEditText.getText().toString();
    }

    @Override
    public void finishView() {
        finish();
    }

    @Override
    public void displayToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.change_password_link_textview)
    public void onChangePasswordClicked() {
        passwordPresenter.onChangePasswordClicked();
    }

    private void initPresenter() {
        passwordPresenter = new ChangePasswordPresenterImpl(this);
        passwordPresenter.initialise();
    }
}
