package com.dwa.rybridge.ryebridgedwa.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.dwa.rybridge.ryebridgedwa.R;
import com.dwa.rybridge.ryebridgedwa.navigator.Navigator;
import com.dwa.rybridge.ryebridgedwa.presenter.PoliciesPresenter;
import com.dwa.rybridge.ryebridgedwa.presenter.PoliciesPresenterImpl;
import com.dwa.rybridge.ryebridgedwa.ui.view.PoliciesView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class PoliciesActivty extends AppCompatActivity implements PoliciesView{

    public static final String IS_SIMPLE_SCREEN_KEY = "is_simple_screen";

    @BindView(R.id.policy_webview)
    WebView webView;
    @BindView(R.id.policies_switch)
    SwitchCompat policiesSwitch;
    @BindView(R.id.induction_switch)
    SwitchCompat inductionSwitch;
    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.policies_acceptance_container)
    View policiesAcceptanceContainer;

    private Navigator navigator;
    private PoliciesPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policies);

        ButterKnife.bind(this);

        initNavigator();
        initPresenter();

        boolean isSimple = getIntent().getBooleanExtra(IS_SIMPLE_SCREEN_KEY, false);

        if (isSimple) {
            policiesAcceptanceContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadHtmlPage(String htmlContent) {
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadData(htmlContent, "text/html; charset=UTF-8",null);
    }

    @Override
    public boolean isPoliciesSwitchChecked() {
        return policiesSwitch.isChecked();
    }

    @Override
    public boolean isInductionSwitchChecked() {
        return inductionSwitch.isChecked();
    }

    @Override
    public void setNextButtonVisibility(int visibility) {
        nextButton.setVisibility(visibility);
    }

    @Override
    public void navigateToMainActivity() {
        navigator.navigateToMainActivity();
    }

    @OnCheckedChanged({R.id.policies_switch, R.id.induction_switch})
    public void onSwitchesCheckChanged() {
        presenter.onSwitchesCheckChanged();
    }

    @OnClick(R.id.next_button)
    public void onNextButtonClicked() {
        presenter.onNextButtonClicked();
    }

    private void initNavigator() {
        navigator = Navigator.newInstance();
        navigator.setSourceActivity(this);
    }

    private void initPresenter() {
        presenter = new PoliciesPresenterImpl(this);
        presenter.initialise();
    }
}
