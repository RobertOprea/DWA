package com.dwa.rybridge.ryebridgedwa.navigator;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.dwa.rybridge.ryebridgedwa.ui.activities.CategoriesActivity;
import com.dwa.rybridge.ryebridgedwa.ui.activities.ChangePasswordActivity;
import com.dwa.rybridge.ryebridgedwa.ui.activities.HazardPhotoActivity;
import com.dwa.rybridge.ryebridgedwa.ui.activities.PhotoDescriptionActivity;
import com.dwa.rybridge.ryebridgedwa.ui.activities.ReporterDetailsActivity;
import com.dwa.rybridge.ryebridgedwa.ui.activities.LoginActivity;
import com.dwa.rybridge.ryebridgedwa.ui.activities.MainActivity;
import com.dwa.rybridge.ryebridgedwa.ui.activities.PoliciesActivty;
import com.dwa.rybridge.ryebridgedwa.ui.activities.RegistrationActivity;
import com.dwa.rybridge.ryebridgedwa.ui.activities.ReviewActivity;

public class Navigator {

    public static final int CAMERA_PIC_REQUEST = 10;
    public static final int GALLERY_REQUEST = 11;

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

    public void navigateToReporterDetailsActivity() {
        Intent intent = new Intent(sourceActivity, ReporterDetailsActivity.class);
        sourceActivity.startActivity(intent);
    }

    public void navigateToCategoriesActivity() {
        Intent intent = new Intent(sourceActivity, CategoriesActivity.class);
        sourceActivity.startActivity(intent);
    }

    public void navigateToHazardPhotoActivity() {
        Intent intent = new Intent(sourceActivity, HazardPhotoActivity.class);
        sourceActivity.startActivity(intent);
    }

    public void navigateToPhotoDescriptionActivity() {
        Intent intent = new Intent(sourceActivity, PhotoDescriptionActivity.class);
        sourceActivity.startActivity(intent);
    }

    public void navigateToReportReviewActivity() {
        Intent intent = new Intent(sourceActivity, ReviewActivity.class);
        sourceActivity.startActivity(intent);
    }

    public void openCamera(Uri photoUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        sourceActivity.startActivityForResult(intent, CAMERA_PIC_REQUEST);
    }

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        sourceActivity.startActivityForResult(intent, GALLERY_REQUEST);
    }
}
