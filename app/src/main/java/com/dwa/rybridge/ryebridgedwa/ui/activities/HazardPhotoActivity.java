package com.dwa.rybridge.ryebridgedwa.ui.activities;

import com.dwa.rybridge.ryebridgedwa.R;
import com.dwa.rybridge.ryebridgedwa.navigator.Navigator;
import com.dwa.rybridge.ryebridgedwa.presenter.HazardPhotoPresenter;
import com.dwa.rybridge.ryebridgedwa.presenter.implementations.HazardPhotoPresenterImpl;
import com.dwa.rybridge.ryebridgedwa.ui.view.HazardPhotoView;
import com.dwa.rybridge.ryebridgedwa.util.ImageUtil;
import com.dwa.rybridge.ryebridgedwa.util.ReportCacheHolder;
import com.dwa.rybridge.ryebridgedwa.util.ViewUtil;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.NO_PHOTO_SELECTED;
import static com.dwa.rybridge.ryebridgedwa.constants.GeneralUseConstants.FILE_TIME_FORMAT;

public class HazardPhotoActivity extends AppCompatActivity implements HazardPhotoView {

    @BindView(R.id.photo_image_view) ImageView photoImageView;
    @BindView(R.id.loading_view) View loadingView;

    private HazardPhotoPresenter presenter;
    private Navigator navigator;
    private Uri photoUri;
    private String currentPhotoPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ButterKnife.bind(this);

        presenter = new HazardPhotoPresenterImpl(this, this);

        navigator = Navigator.newInstance();
        navigator.setSourceActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            presenter.onPermissionGranted(requestCode);
        }
    }

    @Override
    public void openCamera() {
        File photoFile = null;
        try {
            photoFile = createImageFile(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (photoFile != null) {
            photoUri = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
            navigator.openCamera(photoUri);
        }
    }

    @Override
    public void openGallery() {
        navigator.openGallery();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK ) {
            switch (requestCode) {
                case Navigator.CAMERA_PIC_REQUEST:
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            showLoadingView();
                            new ImageUtil(HazardPhotoActivity.this).orientPhoto(photoUri, currentPhotoPath);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    hideLoadingView();
                                    ViewUtil.loadImage(photoImageView, photoUri);
                                }
                            });
                        }
                    }.execute();
                    break;
                case Navigator.GALLERY_REQUEST:
                    photoUri = data.getData();
                    ViewUtil.loadImage(photoImageView, photoUri);
                    break;
            }
        }

    }


    @OnClick(R.id.take_photo_button)
    public void onTakePhotoClicked() {
        presenter.onCameraClicked();
    }

    @OnClick(R.id.gallery_button)
    public void onGalleryClicked() {
        presenter.onGalleryClicked();
    }

    @OnClick(R.id.next_button)
    public void onNextClicked() {
        if (photoUri != null) {
            ReportCacheHolder.getInstance().onPhotoSelected(photoUri);
            navigator.navigateToPhotoDescriptionActivity();
        } else {
            Toast.makeText(this, NO_PHOTO_SELECTED, Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.skip_photo_button)
    public void onSkipPhotoClicked() {
        ReportCacheHolder.getInstance().onPhotoSelected(null);
        navigator.navigateToPhotoDescriptionActivity();
    }

    private File createImageFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat(FILE_TIME_FORMAT).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image;
        image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void hideLoadingView() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingView() {
        loadingView.setVisibility(View.VISIBLE);
    }
}
