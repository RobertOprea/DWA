package com.dwa.rybridge.ryebridgedwa.presenter.implementations;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import com.dwa.rybridge.ryebridgedwa.presenter.ReporterDetailsPresenter;
import com.dwa.rybridge.ryebridgedwa.ui.view.ReporterDetailsView;
import com.dwa.rybridge.ryebridgedwa.util.ReportCacheHolder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.INVALID_COMPANY;
import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.INVALID_PROJECT;
import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.INVALID_REPORTER_NAME;

public class ReporterDetailsPresenterImpl implements ReporterDetailsPresenter {

    public static final int LOCATION_PERMISSION_CODE = 101;

    private ReportCacheHolder reportCacheHolder;
    private FirebaseAuth firebaseAuth;
    private ReporterDetailsView view;
    private FusedLocationProviderClient locationProviderClient;

    public ReporterDetailsPresenterImpl(ReporterDetailsView view) {
        this.view = view;
    }

    @Override
    public void initialise(Activity activity) {
        reportCacheHolder = ReportCacheHolder.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        locationProviderClient = LocationServices.getFusedLocationProviderClient(activity);

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE);
        } else {
            getLastKnownLocation();
        }
    }

    @Override
    public void onPermissionGranted() {
        getLastKnownLocation();
    }

    @SuppressLint("MissingPermission")
    private void getLastKnownLocation() {
        locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    reportCacheHolder.setCoordinates(location.getLatitude(), location.getLongitude());
                }
            }
        });
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
