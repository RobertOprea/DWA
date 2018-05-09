package com.dwa.rybridge.ryebridgedwa.validator;

import com.dwa.rybridge.ryebridgedwa.data.RegisterData;

import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.EMPTY_ACCESS_CODE;
import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.EMPTY_FIRST_NAME;
import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.EMPTY_LAST_NAME;
import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.EMPTY_PASSWORD;
import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.EMPTY_REPEAT_PASSWORD;
import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.INVALID_EMAIL;
import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.INVALID_PHONE_NUMBER;
import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.MISMATCHED_PASSWORDS;

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
            return EMPTY_FIRST_NAME;
        } else if (lastName.isEmpty()) {
            return EMPTY_LAST_NAME;
        } else if (!EmailValidator.validate(emailAddress)) {
            return INVALID_EMAIL;
        } else if (!PhoneNumberValidator.validate(mobileNumber)) {
            return INVALID_PHONE_NUMBER;
        } else if (password.isEmpty()) {
            return EMPTY_PASSWORD;
        } else if (repeatPassword.isEmpty()) {
            return EMPTY_REPEAT_PASSWORD;
        } else if (!PasswordValidator.validatePasswordAndConfirmPassword(password, repeatPassword)) {
            return MISMATCHED_PASSWORDS;
        } else if (accessCode.isEmpty()) {
            return EMPTY_ACCESS_CODE;
        }

        return "";
    }
}
