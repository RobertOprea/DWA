package com.dwa.rybridge.ryebridgedwa.ui.activities;

import com.dwa.rybridge.ryebridgedwa.R;
import com.dwa.rybridge.ryebridgedwa.presenter.CategoriesPresenter;
import com.dwa.rybridge.ryebridgedwa.presenter.CategoriesPresenterImpl;
import com.dwa.rybridge.ryebridgedwa.ui.adapters.CategoriesAdapter;
import com.dwa.rybridge.ryebridgedwa.ui.view.CategoriesView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesActivity extends AppCompatActivity implements CategoriesView{

    @BindView(R.id.categories_list) ExpandableListView categoriesListView;

    private CategoriesPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        ButterKnife.bind(this);

        presenter = new CategoriesPresenterImpl(this);
        presenter.initialise();
    }

    @Override
    public void setupCategories(Map<String, List<String>> categoriesMap) {
        CategoriesAdapter adapter = new CategoriesAdapter(this, categoriesMap);
        categoriesListView.setAdapter(adapter);
    }
}
