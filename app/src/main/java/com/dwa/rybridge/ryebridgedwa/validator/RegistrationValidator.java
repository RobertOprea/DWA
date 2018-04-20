package com.dwa.rybridge.ryebridgedwa.validator;

import com.dwa.rybridge.ryebridgedwa.data.RegisterData;

public class RegistrationValidator {

    public static String validateRegistrationData(RegisterData registerData) {
        String firstName = registerData.getFirstName();
        String lastName = registerData.getLastName();
        String emailAddress = registerData.getEmailAddress();
        String mobileNumber = registerData.getMobileNumber();
        String password = registerData.getPassword();
        String repeatPassword = registerData.getRepeatPassword();
        String accessCode = registerData.getAccessCode();

        if (firstName.isEmpty()) {
            return "First name field is empty!";
        } else if (lastName.isEmpty()) {
            return "Last name field is empty!";
        } else if (!EmailValidator.validate(emailAddress)) {
            return "Email field is invalid!";
        } else if (!PhoneNumberValidator.validate(mobileNumber)) {
            return "Phone number is invalid!";
        } else if (password.isEmpty()) {
            return "Password field is empty!";
        } else if (repeatPassword.isEmpty()) {
            return "Repeat password field is empty!";
        } else if (!PasswordValidator.validatePasswordAndConfirmPassword(password, repeatPassword)) {
            return "Passwords do not match!";
        } else if (accessCode.isEmpty()) {
            return "Access code field is empty!";
        }

        return "";
    }
}
