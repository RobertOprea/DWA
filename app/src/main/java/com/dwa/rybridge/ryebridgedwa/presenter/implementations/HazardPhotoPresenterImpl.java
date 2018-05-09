package com.dwa.rybridge.ryebridgedwa.presenter.implementations;

import com.dwa.rybridge.ryebridgedwa.presenter.HazardPhotoPresenter;
import com.dwa.rybridge.ryebridgedwa.ui.view.HazardPhotoView;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class HazardPhotoPresenterImpl implements HazardPhotoPresenter {

    private static final int CAMERA_PERMISSION_CODE = 102;
    private static final int STORAGE_PERMISSION_CODE = 103;

    private Activity activity;
    private HazardPhotoView view;

    public HazardPhotoPresenterImpl(Activity activity, HazardPhotoView view) {
        this.activity = activity;
        this.view = view;
    }

    @Override
    public void initialise() {

    }

    @Override
    public void onCameraClicked() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CODE);
        } else {
            view.openCamera();
        }
    }

    @Override
    public void onGalleryClicked() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        } else {
            view.openGallery();
        }
    }

    @Override
    public void onPermissionGranted(int requestCode) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            view.openCamera();
        } else {
            view.openGallery();
        }
    }
}
