package com.dwa.rybridge.ryebridgedwa.ui.view;

import android.net.Uri;

public interface PhotoDescriptionView {

    void onLoadImage(Uri imageUri);

    void setDate(String date);

    void hidePhotoContainer();

    void displayToast(String message);

    void navigateToReviewScreen();

    String getDescription();

    String getActions();

}
