package com.dwa.rybridge.ryebridgedwa.util;

import com.dwa.rybridge.ryebridgedwa.data.Report;

public class ReportCacheHolder {

    private static ReportCacheHolder instance;
    private Report report;

    public static ReportCacheHolder getInstance() {
        if (instance == null) {
            instance = new ReportCacheHolder();
        }

        return instance;
    }

    private ReportCacheHolder() {
        report = new Report();
    }

    public void onReporterDetailsCompleted(String reporterName, String reporterCompany, String reporterProject) {
        report.setName(reporterName);
        report.setCompany(reporterCompany);
        report.setProject(reporterProject);
    }
}
