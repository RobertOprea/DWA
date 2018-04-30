package com.dwa.rybridge.ryebridgedwa.ui.activities;

import com.dwa.rybridge.ryebridgedwa.R;
import com.dwa.rybridge.ryebridgedwa.navigator.Navigator;
import com.dwa.rybridge.ryebridgedwa.presenter.ReporterDetailsPresenter;
import com.dwa.rybridge.ryebridgedwa.presenter.ReporterDetailsPresenterImpl;
import com.dwa.rybridge.ryebridgedwa.ui.view.ReporterDetailsView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReporterDetailsActivity extends AppCompatActivity implements ReporterDetailsView {

    @BindView(R.id.reporter_name_input_edittext) AppCompatEditText reporterNameEditText;
    @BindView(R.id.reporter_company_input_edittext) AppCompatEditText reporterCompanyEditText;
    @BindView(R.id.reporter_project_input_edittext) AppCompatEditText repoterProjectEditText;

    private ReporterDetailsPresenter presenter;
    private Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hazard_report);
        ButterKnife.bind(this);

        presenter = new ReporterDetailsPresenterImpl(this);
        presenter.initialise();
        initNavigator();
    }

    @Override
    public String getReporterName() {
        return reporterNameEditText.getText().toString();
    }

    @Override
    public String getReporterCompany() {
        return reporterCompanyEditText.getText().toString();
    }

    @Override
    public String getRepoterProject() {
        return repoterProjectEditText.getText().toString();
    }

    @Override
    public void goToNextScreen() {
        navigator.navigateToCategoriesActivity();
    }

    @Override
    public void displayToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.next_button)
    public void onNextButtonClicked() {
        presenter.onNextButtonClicked();
    }

    private void initNavigator() {
        navigator = Navigator.newInstance();
        navigator.setSourceActivity(this);
    }

}
