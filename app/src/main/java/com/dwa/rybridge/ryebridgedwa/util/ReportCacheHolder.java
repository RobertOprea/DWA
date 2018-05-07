package com.dwa.rybridge.ryebridgedwa.util;

import com.dwa.rybridge.ryebridgedwa.data.Report;

import android.net.Uri;

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

    public void onCategorySelected(String groupName, String childName) {
        String fullCategoryName = groupName + ", " + childName;
        report.setCategory(fullCategoryName);
    }

    public void onPhotoSelected(Uri uri) {
        String photoPath = null;

        if (uri != null) {
            photoPath = uri.toString();
        }

        report.setPhotoPath(photoPath);
    }

    public void onDescriptionAdded(String description, String actions, String date) {
        report.setDescription(description);
        report.setActionTaken(actions);
        report.setDateTaken(date);
    }

    public String getPhotoPath() {
        return report.getPhotoPath();
    }
}
