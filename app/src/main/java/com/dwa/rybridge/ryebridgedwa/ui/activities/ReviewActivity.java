package com.dwa.rybridge.ryebridgedwa.ui.activities;

import com.dwa.rybridge.ryebridgedwa.R;
import com.dwa.rybridge.ryebridgedwa.data.Report;
import com.dwa.rybridge.ryebridgedwa.presenter.HazardReviewPresenter;
import com.dwa.rybridge.ryebridgedwa.presenter.HazardReviewPresenterImpl;
import com.dwa.rybridge.ryebridgedwa.ui.view.HazardReviewView;
import com.dwa.rybridge.ryebridgedwa.util.ViewUtil;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewActivity extends AppCompatActivity implements HazardReviewView {

    @BindView(R.id.report_details_text_view) TextView detailsTextView;
    @BindView(R.id.project_text_view) TextView projectTextView;
    @BindView(R.id.category_text_view) TextView categoryTextView;
    @BindView(R.id.date_text_view) TextView dateTextView;
    @BindView(R.id.description_text_view) TextView descriptionTextView;
    @BindView(R.id.actions_text_view) TextView actionsTextView;
    @BindView(R.id.photo_image_view) ImageView photoImageView;

    private HazardReviewPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ButterKnife.bind(this);

        presenter = new HazardReviewPresenterImpl(this);
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
        ViewUtil.loadImage(photoImageView, Uri.parse(report.getPhotoPath()));
    }
}
