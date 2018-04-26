package com.dwa.rybridge.ryebridgedwa.navigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dwa.rybridge.ryebridgedwa.ui.activities.ChangePasswordActivity;
import com.dwa.rybridge.ryebridgedwa.ui.activities.LoginActivity;
import com.dwa.rybridge.ryebridgedwa.ui.activities.MainActivity;
import com.dwa.rybridge.ryebridgedwa.ui.activities.PoliciesActivty;
import com.dwa.rybridge.ryebridgedwa.ui.activities.RegistrationActivity;

public class Navigator {

    private Activity sourceActivity;

    public static Navigator newInstance() {
        return new Navigator();
    }

    private Navigator() {

    }

    public void setSourceActivity(Activity sourceActivity) {
        this.sourceActivity = sourceActivity;
    }

    public void navigateToLoginActivity() {
        Intent intent = new Intent(sourceActivity, LoginActivity.class);
        sourceActivity.startActivity(intent);
    }

    public void navigateToRegistrationActivity() {
        Intent intent = new Intent(sourceActivity, RegistrationActivity.class);
        sourceActivity.startActivity(intent);
    }

    public void navigateToPoliciesActivity() {
        Intent intent = new Intent(sourceActivity, PoliciesActivty.class);
        sourceActivity.startActivity(intent);
    }

    public void navigateToSimplePoliciesActivity() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(PoliciesActivty.IS_SIMPLE_SCREEN_KEY, true);
        Intent intent = new Intent(sourceActivity, PoliciesActivty.class);
        intent.putExtras(bundle);
        sourceActivity.startActivity(intent);
    }

    public void navigateToMainActivity() {
        Intent intent = new Intent(sourceActivity, MainActivity.class);
        sourceActivity.startActivity(intent);
    }

    public void navigateToChangePasswordActivity() {
        Intent intent = new Intent(sourceActivity, ChangePasswordActivity.class);
        sourceActivity.startActivity(intent);
    }

}
