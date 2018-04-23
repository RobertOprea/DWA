package com.dwa.rybridge.ryebridgedwa.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.dwa.rybridge.ryebridgedwa.R;
import com.dwa.rybridge.ryebridgedwa.presenter.PoliciesPresenter;
import com.dwa.rybridge.ryebridgedwa.presenter.PoliciesPresenterImpl;
import com.dwa.rybridge.ryebridgedwa.ui.view.PoliciesView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PoliciesActivty extends AppCompatActivity implements PoliciesView{

    @BindView(R.id.policy_webview)
    WebView webView;

    private PoliciesPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policies);

        ButterKnife.bind(this);

        initPresenter();
    }

    @Override
    public void loadHtmlPage(String htmlContent) {
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadData(htmlContent, "text/html; charset=UTF-8",null);
    }

    private void initPresenter() {
        presenter = new PoliciesPresenterImpl(this);
        presenter.initialise();
    }
}
