package com.dwa.rybridge.ryebridgedwa.ui.view;

public interface ReporterDetailsView {

    String getReporterName();

    String getReporterCompany();

    String getRepoterProject();

    void goToNextScreen();

    void displayToast(String message);
}
