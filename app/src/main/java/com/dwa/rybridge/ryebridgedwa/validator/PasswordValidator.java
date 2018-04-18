package com.dwa.rybridge.ryebridgedwa.validator;

public class PasswordValidator {

    public static boolean validatePassword(String password) {
        //More validation rules can be added if needed
        return password != null && password.length() >= 6;
    }

    public static boolean validatePasswordAndConfirmPassword(String password, String confirmation) {
        if (!validatePassword(password) || (!validatePassword(confirmation))) {
            return false;
        }

        return password.equals(confirmation);
    }

}
