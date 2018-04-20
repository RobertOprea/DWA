package com.dwa.rybridge.ryebridgedwa.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidator {

    private static final Pattern PHONE_NUMBER_REGEX = Pattern.compile("^[+]?[0-9]{10,13}$");

    public static boolean validate(String phoneNumber) {
        Matcher matcher = PHONE_NUMBER_REGEX.matcher(phoneNumber);
        return matcher.find();
    }
}
