package com.dwa.rybridge.ryebridgedwa.ui.view;

public interface PoliciesView {

    void loadHtmlPage(String htmlContent);

    boolean isPoliciesSwitchChecked();

    boolean isInductionSwitchChecked();

    void setNextButtonVisibility(int visibility);

    void navigateToMainActivity();
}
