package com.dwa.rybridge.ryebridgedwa.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String email) {
        Matcher matcher = EMAIL_REGEX.matcher(email);
        return matcher.find();
    }
}
