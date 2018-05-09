package com.dwa.rybridge.ryebridgedwa.presenter;

import android.app.Activity;

public interface ReporterDetailsPresenter {

    void initialise(Activity actrivity);

    void onNextButtonClicked();

    void onPermissionGranted();
}
