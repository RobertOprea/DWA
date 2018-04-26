package com.dwa.rybridge.ryebridgedwa.presenter;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import com.dwa.rybridge.ryebridgedwa.ui.view.MainView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

public class MainPresenterImpl implements MainPresenter {

    public static final int LOCATION_PERMISSION_CODE = 101;

    private FirebaseAuth firebaseAuth;
    private MainView view;
    private FusedLocationProviderClient locationProviderClient;

    public MainPresenterImpl(MainView view) {
        this.view = view;
    }

    @Override
    public void initialise(Activity activity) {
        firebaseAuth = FirebaseAuth.getInstance();
        locationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE);
        } else {
            getLastKnownLocation();
        }
    }

    @Override
    public void onSingOutClicked() {
        firebaseAuth.signOut();
        view.navigateToLoginScreen();
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

            }
        });
    }
}
