package com.dwa.rybridge.ryebridgedwa.presenter;

import com.dwa.rybridge.ryebridgedwa.data.Report;
import com.dwa.rybridge.ryebridgedwa.ui.view.HazardReviewView;
import com.dwa.rybridge.ryebridgedwa.util.ReportCacheHolder;

public class HazardReviewPresenterImpl implements HazardReviewPresenter {

    private HazardReviewView view;

    public HazardReviewPresenterImpl(HazardReviewView view) {
        this.view = view;
    }

    @Override
    public void initialise() {
        Report report = ReportCacheHolder.getInstance().getReport();
        view.onDisplayReport(report);
    }

    @Override
    public void onUploadNowClicked() {

    }

    @Override
    public void onUploadLaterClicked() {

    }

    @Override
    public void onCancelReportClicked() {

    }
}
