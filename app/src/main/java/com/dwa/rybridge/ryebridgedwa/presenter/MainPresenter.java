package com.dwa.rybridge.ryebridgedwa.presenter;

import android.app.Activity;

public interface MainPresenter {

    void initialise(Activity activity);

    void onSingOutClicked();

    void onPermissionGranted();
}
