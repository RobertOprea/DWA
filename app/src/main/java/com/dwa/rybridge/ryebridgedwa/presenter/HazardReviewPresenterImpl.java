package com.dwa.rybridge.ryebridgedwa.presenter;

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
import com.dwa.rybridge.ryebridgedwa.ui.view.HazardReviewView;
import com.dwa.rybridge.ryebridgedwa.util.ReportCacheHolder;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

public class HazardReviewPresenterImpl implements HazardReviewPresenter {

    private HazardReviewView view;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private ReportRepository reportRepository;
    private Report report;

    public HazardReviewPresenterImpl(HazardReviewView view, Context context) {
        this.view = view;
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        reportRepository = new ReportDatabase(context);
    }

    @Override
    public void initialise() {
        report = ReportCacheHolder.getInstance().getReport();
        view.onDisplayReport(report);
    }

    @Override
    public void onUploadNowClicked() {
        firebaseDatabase.getReference().child("reports").child(report.getName()).push().setValue(report).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    uploadImage();
                }
            }
        });
    }

    @Override
    public void onUploadLaterClicked() {
        try {
            reportRepository.insert(report);
            view.goToMainScreen();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCancelReportClicked() {
        ReportCacheHolder.getInstance().clear();
        view.goToMainScreen();
    }

    private void uploadImage() {
        if (report.getPhotoPath() != null) {
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
                    view.goToMainScreen();
                }
            });
        } else {
            view.goToMainScreen();
        }
    }
}
