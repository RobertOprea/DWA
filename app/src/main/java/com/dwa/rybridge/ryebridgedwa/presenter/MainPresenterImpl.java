package com.dwa.rybridge.ryebridgedwa.presenter;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.dwa.rybridge.ryebridgedwa.data.Report;
import com.dwa.rybridge.ryebridgedwa.database.ReportDatabase;
import com.dwa.rybridge.ryebridgedwa.database.ReportRepository;
import com.dwa.rybridge.ryebridgedwa.database.RepositoryException;
import com.dwa.rybridge.ryebridgedwa.ui.view.MainView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainPresenterImpl implements MainPresenter {

    public static final int LOCATION_PERMISSION_CODE = 101;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private MainView view;
    private FusedLocationProviderClient locationProviderClient;
    private ReportRepository reportRepository;

    public MainPresenterImpl(MainView view, Context context) {
        this.view = view;
        reportRepository = new ReportDatabase(context);
    }

    @Override
    public void initialise(Activity activity) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        locationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE);
        } else {
            getLastKnownLocation();
        }

        checkCachedReports();
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

    @Override
    public void onUploadAllClicked() {
        uploadAllHazards();
    }

    private void uploadAllHazards() {
        try {
            uploadNextHazard(0);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    private void uploadNextHazard(final int index) throws RepositoryException {
        List<Report> reports = reportRepository.getAll();
        if (!reports.isEmpty() && index <= reports.size() - 1) {
            final Report report = reports.get(index);
            firebaseDatabase.getReference().child("reports").child(report.getName()).push().setValue(report).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        uploadImage(index, report);
                    }
                }
            });
        } else {
            reportRepository.clear();
            view.hideUploadAllView();
        }
    }

    private void uploadImage(final int index, Report report) {
        StorageReference storageReference = firebaseStorage.getReference();
        Uri uri = Uri.parse(report.getPhotoPath());
        StorageReference photoRef = storageReference.child("images/" + report.getName() + "/" + uri.getLastPathSegment());
        UploadTask uploadTask = photoRef.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TAG", "Failed to upload photo!");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                int newIndex = index + 1;
                try {
                    uploadNextHazard(newIndex);
                } catch (RepositoryException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLastKnownLocation() {
        locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

            }
        });
    }

    private void checkCachedReports() {
        List<Report> reports = new ArrayList<>();
        try {
            reports = reportRepository.getAll();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }

        if (!reports.isEmpty()) {
            view.showUploadAllView();
        } else {
            view.hideUploadAllView();
        }
    }
}
