package com.dwa.rybridge.ryebridgedwa.presenter;

import android.telephony.PhoneNumberUtils;

import com.dwa.rybridge.ryebridgedwa.ui.view.RegistrationView;
import com.dwa.rybridge.ryebridgedwa.validator.EmailValidator;
import com.dwa.rybridge.ryebridgedwa.validator.PasswordValidator;
import com.dwa.rybridge.ryebridgedwa.validator.PhoneNumberValidator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationPresenterImpl implements RegistrationPresenter {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    private RegistrationView registrationView;

    public RegistrationPresenterImpl(RegistrationView registrationView) {
        this.registrationView = registrationView;
    }

    @Override
    public void initialise() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void onRegisterClicked() {
        String firstName = registrationView.getFirstName();

        if (firstName.isEmpty()) {
            registrationView.displayToastMessage("First name field is empty!");
            return;
        }

        String lastName = registrationView.getLastName();

        if (lastName.isEmpty()) {
            registrationView.displayToastMessage("Last name field is empty!");
            return;
        }

        String emailAddress = registrationView.getEmailAddress();

        if (!EmailValidator.validate(emailAddress)) {
            registrationView.displayToastMessage("Email field is invalid!");
            return;
        }

        String mobileNumber = registrationView.getMobileNumber();

        if (!PhoneNumberValidator.validate(mobileNumber)) {
            registrationView.displayToastMessage("Phone number is invalid!");
            return;
        }

        String password = registrationView.getPassword();
        String repeatPassword = registrationView.getRepeatPassword();

        if (password.isEmpty()) {
            registrationView.displayToastMessage("Password field is empty!");
            return;
        } else if (repeatPassword.isEmpty()) {
            registrationView.displayToastMessage("Repeat password field is empty!");
            return;
        } else if (!PasswordValidator.validatePasswordAndConfirmPassword(password, repeatPassword)) {
            registrationView.displayToastMessage("Passwords do not match!");
            return;
        }

        String accessCode = registrationView.getAccessCode();

        if (accessCode.isEmpty()) {
            registrationView.displayToastMessage("Access code field is empty!");
            return;
        }

        startRegister();
    }

    private void startRegister() {

    }
}
