package com.dwa.rybridge.ryebridgedwa.presenter;

public interface HazardPhotoPresenter {

    void initialise();

    void onCameraClicked();

    void onGalleryClicked();

    void onPermissionGranted(int requestCode);
}
