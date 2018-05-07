package com.dwa.rybridge.ryebridgedwa.presenter;

import com.dwa.rybridge.ryebridgedwa.ui.view.PhotoDescriptionView;
import com.dwa.rybridge.ryebridgedwa.util.ReportCacheHolder;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.net.Uri;

public class PhotoDescriptionPresenterImpl implements PhotoDescriptionPresenter {

    private PhotoDescriptionView view;

    public PhotoDescriptionPresenterImpl(PhotoDescriptionView view) {
        this.view = view;
    }

    @Override
    public void initialise() {
        String photoPath = ReportCacheHolder.getInstance().getPhotoPath();
        if (photoPath != null) {
            Uri uri = Uri.parse(photoPath);
            view.onLoadImage(uri);
        } else {
            view.hidePhotoContainer();
        }

        view.setDate(getDateString());
    }

    @Override
    public void onReviewHazardClicked() {
        String description = view.getDescription();
        String actions = view.getActions();

        if (description.isEmpty()) {
            view.displayToast("Description is empty!");
        } else if (actions.isEmpty()) {
            view.displayToast("Actions taken is empty!");
        } else {
            ReportCacheHolder.getInstance().onDescriptionAdded(description, actions, getDateString());
            view.navigateToReviewScreen();
        }
    }

    private String getDateString() {
        DateTime dateTime = DateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("MMM dd, yyyy");
        return dateTimeFormatter.print(dateTime);
    }
}
