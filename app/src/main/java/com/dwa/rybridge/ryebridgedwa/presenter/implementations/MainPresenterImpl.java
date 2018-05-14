package com.dwa.rybridge.ryebridgedwa.presenter.implementations;

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
import com.dwa.rybridge.ryebridgedwa.presenter.MainPresenter;
import com.dwa.rybridge.ryebridgedwa.ui.view.MainView;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.dwa.rybridge.ryebridgedwa.constants.FirebaseConstants.IMAGES_FOLDER;
import static com.dwa.rybridge.ryebridgedwa.constants.FirebaseConstants.REPORTS;

public class MainPresenterImpl implements MainPresenter {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private MainView view;
    private ReportRepository reportRepository;

    public MainPresenterImpl(MainView view, Context context) {
        this.view = view;
        reportRepository = new ReportDatabase(context);
    }

    @Override
    public void initialise() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        checkCachedReports();
    }

    @Override
    public void onSignOutClicked() {
        try {
            firebaseAuth.signOut();
            reportRepository.clear();
            view.navigateToLoginScreen();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUploadAllClicked() {
        view.showLoadingView();
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
            firebaseDatabase.getReference().child(REPORTS).child(report.getName()).push().setValue(report).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        uploadImage(index, report);
                    }
                }
            });
        } else {
            view.hideLoadingView();
            reportRepository.clear();
            view.hideUploadAllView();
        }
    }

    private void uploadImage(final int index, Report report) {
        if (report.getPhotoPath() != null) {
            StorageReference storageReference = firebaseStorage.getReference();
            Uri uri = Uri.parse(report.getPhotoPath());
            StorageReference photoRef = storageReference.child(IMAGES_FOLDER + report.getName() + "/" + uri.getLastPathSegment());
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
        } else {
            int newIndex = index + 1;
            try {
                uploadNextHazard(newIndex);
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        }

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
