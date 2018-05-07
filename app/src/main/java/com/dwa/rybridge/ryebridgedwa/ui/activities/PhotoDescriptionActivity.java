package com.dwa.rybridge.ryebridgedwa.ui.activities;

import com.dwa.rybridge.ryebridgedwa.R;
import com.dwa.rybridge.ryebridgedwa.navigator.Navigator;
import com.dwa.rybridge.ryebridgedwa.presenter.PhotoDescriptionPresenter;
import com.dwa.rybridge.ryebridgedwa.presenter.PhotoDescriptionPresenterImpl;
import com.dwa.rybridge.ryebridgedwa.ui.view.PhotoDescriptionView;
import com.dwa.rybridge.ryebridgedwa.util.ViewUtil;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoDescriptionActivity extends AppCompatActivity implements PhotoDescriptionView {

    @BindView(R.id.photo_image_view) ImageView imageView;
    @BindView(R.id.photo_container) View photoContainer;
    @BindView(R.id.date_text_view) TextView dateTextView;
    @BindView(R.id.description_input_edittext) AppCompatEditText descriptionEditText;
    @BindView(R.id.actions_input_edittext) AppCompatEditText actionsEditText;

    private PhotoDescriptionPresenter presenter;
    private Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_description);

        ButterKnife.bind(this);

        navigator = Navigator.newInstance();
        navigator.setSourceActivity(this);

        presenter = new PhotoDescriptionPresenterImpl(this);
        presenter.initialise();
    }

    @Override
    public void onLoadImage(Uri imageUri) {
        ViewUtil.loadImage(imageView, imageUri);
    }

    @Override
    public void setDate(String date) {
        dateTextView.setText(date);
    }

    @Override
    public void hidePhotoContainer() {
        photoContainer.setVisibility(View.GONE);
    }

    @Override
    public void displayToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToReviewScreen() {
        //TODO: navigate to review screen
    }

    @Override
    public String getDescription() {
        return descriptionEditText.getText().toString();
    }

    @Override
    public String getActions() {
        return actionsEditText.getText().toString();
    }

    @OnClick(R.id.review_button)
    public void onReviewClicked() {
        presenter.onReviewHazardClicked();
    }
}
