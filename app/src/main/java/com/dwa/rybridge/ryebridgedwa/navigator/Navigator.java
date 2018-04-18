package com.dwa.rybridge.ryebridgedwa.navigator;

import android.app.Activity;
import android.content.Intent;

import com.dwa.rybridge.ryebridgedwa.ui.activities.ChangePasswordActivity;

public class Navigator {

    private static Navigator instance;
    private Activity sourceActivity;

    public static Navigator getInstance() {
        if (instance == null) {
            instance = new Navigator();
        }

        return instance;
    }

    private Navigator() {

    }

    public void setSourceActivity(Activity sourceActivity) {
        this.sourceActivity = sourceActivity;
    }

    public void navigateToChangePasswordScreen() {
        Intent intent = new Intent(sourceActivity, ChangePasswordActivity.class);
        sourceActivity.startActivity(intent);
    }

}
