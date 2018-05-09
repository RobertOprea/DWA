package com.dwa.rybridge.ryebridgedwa.ui.view;

import com.dwa.rybridge.ryebridgedwa.data.Report;

public interface HazardReviewView extends LoadingView{

    void onDisplayReport(Report report);

    void goToMainScreen();
}
