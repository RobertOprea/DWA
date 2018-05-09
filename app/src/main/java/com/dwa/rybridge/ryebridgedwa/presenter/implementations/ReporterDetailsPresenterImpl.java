package com.dwa.rybridge.ryebridgedwa.presenter.implementations;

import com.google.firebase.auth.FirebaseAuth;

import com.dwa.rybridge.ryebridgedwa.presenter.ReporterDetailsPresenter;
import com.dwa.rybridge.ryebridgedwa.ui.view.ReporterDetailsView;
import com.dwa.rybridge.ryebridgedwa.util.ReportCacheHolder;

import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.INVALID_COMPANY;
import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.INVALID_PROJECT;
import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.INVALID_REPORTER_NAME;

public class ReporterDetailsPresenterImpl implements ReporterDetailsPresenter {

    private ReportCacheHolder reportCacheHolder;
    private FirebaseAuth firebaseAuth;
    private ReporterDetailsView view;

    public ReporterDetailsPresenterImpl(ReporterDetailsView view) {
        this.view = view;
    }

    @Override
    public void initialise() {
        reportCacheHolder = ReportCacheHolder.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onNextButtonClicked() {
        String reporterName = view.getReporterName();
        String reporterCompany = view.getReporterCompany();
        String reporterProject = view.getRepoterProject();
        String email = firebaseAuth.getCurrentUser().getEmail();

        if (reporterName.isEmpty()) {
            view.displayToast(INVALID_REPORTER_NAME);
        } else if (reporterCompany.isEmpty()) {
            view.displayToast(INVALID_COMPANY);
        } else if (reporterProject.isEmpty()) {
            view.displayToast(INVALID_PROJECT);
        } else {
            reportCacheHolder.onReporterDetailsCompleted(reporterName, reporterCompany, reporterProject, email);
            view.goToNextScreen();
        }
    }
}
