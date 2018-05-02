package com.dwa.rybridge.ryebridgedwa.presenter;

import com.dwa.rybridge.ryebridgedwa.ui.view.ReporterDetailsView;
import com.dwa.rybridge.ryebridgedwa.util.ReportCacheHolder;

public class ReporterDetailsPresenterImpl implements ReporterDetailsPresenter {

    private ReportCacheHolder reportCacheHolder;
    private ReporterDetailsView view;

    public ReporterDetailsPresenterImpl(ReporterDetailsView view) {
        this.view = view;
    }

    @Override
    public void initialise() {
        reportCacheHolder = ReportCacheHolder.getInstance();
    }

    @Override
    public void onNextButtonClicked() {
        String reporterName = view.getReporterName();
        String reporterCompany = view.getReporterCompany();
        String reporterProject = view.getRepoterProject();

        if (reporterName.isEmpty()) {
            view.displayToast("Reporter name is invalid!");
        } else if (reporterCompany.isEmpty()) {
            view.displayToast("Reporter company is invalid!");
        } else if (reporterProject.isEmpty()) {
            view.displayToast("Reporter project is invalid!");
        } else {
            reportCacheHolder.onReporterDetailsCompleted(reporterName, reporterCompany, reporterProject);
            view.goToNextScreen();
        }
    }
}