package com.dwa.rybridge.ryebridgedwa.ui.activities;

import com.dwa.rybridge.ryebridgedwa.R;
import com.dwa.rybridge.ryebridgedwa.data.Report;
import com.dwa.rybridge.ryebridgedwa.navigator.Navigator;
import com.dwa.rybridge.ryebridgedwa.presenter.HazardReviewPresenter;
import com.dwa.rybridge.ryebridgedwa.presenter.implementations.HazardReviewPresenterImpl;
import com.dwa.rybridge.ryebridgedwa.ui.view.HazardReviewView;
import com.dwa.rybridge.ryebridgedwa.util.ViewUtil;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReviewActivity extends AppCompatActivity implements HazardReviewView {

    @BindView(R.id.report_details_text_view) TextView detailsTextView;
    @BindView(R.id.project_text_view) TextView projectTextView;
    @BindView(R.id.category_text_view) TextView categoryTextView;
    @BindView(R.id.date_text_view) TextView dateTextView;
    @BindView(R.id.description_text_view) TextView descriptionTextView;
    @BindView(R.id.actions_text_view) TextView actionsTextView;
    @BindView(R.id.photo_image_view) ImageView photoImageView;

    private HazardReviewPresenter presenter;
    private Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ButterKnife.bind(this);

        navigator = Navigator.newInstance();
        navigator.setSourceActivity(this);

        presenter = new HazardReviewPresenterImpl(this, this);
        presenter.initialise();
    }

    @Override
    public void onDisplayReport(Report report) {
        detailsTextView.setText(report.getUserDetails());
        projectTextView.setText(report.getProject());
        categoryTextView.setText(report.getCategory());
        dateTextView.setText(report.getDateTaken());
        descriptionTextView.setText(report.getDescription());
        actionsTextView.setText(report.getActionTaken());
        if (report.getPhotoPath() != null && !report.getPhotoPath().isEmpty()) {
            ViewUtil.loadImage(photoImageView, Uri.parse(report.getPhotoPath()));
        } else {
            photoImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void goToMainScreen() {
        navigator.navigateToMainActivity();
    }

    @OnClick(R.id.upload_now_button)
    public void onUploadNowClicked() {
        presenter.onUploadNowClicked();
    }

    @OnClick(R.id.upload_later_button)
    public void onUploadLaterClicked() {
        presenter.onUploadLaterClicked();
    }

    @OnClick(R.id.cancel_button)
    public void onCancelClicked() {
        presenter.onCancelReportClicked();
    }
}
