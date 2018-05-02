package com.dwa.rybridge.ryebridgedwa.presenter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.dwa.rybridge.ryebridgedwa.ui.view.CategoriesView;
import com.dwa.rybridge.ryebridgedwa.util.ReportCacheHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriesPresenterImpl implements CategoriesPresenter {

    private FirebaseDatabase firebaseDatabase;
    private ReportCacheHolder reportCacheHolder;
    private Map<String, List<String>> categoriesMap = new HashMap<>();

    private CategoriesView view;

    public CategoriesPresenterImpl(CategoriesView view) {
        this.view = view;
    }

    @Override
    public void initialise() {
        reportCacheHolder = ReportCacheHolder.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseDatabase.getReference().child("categories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String categoryHeader = snapshot.getKey();
                    List<String> categoryChildren = new ArrayList<>();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        String childValue = child.getValue(String.class);
                        categoryChildren.add(childValue);
                    }
                    categoriesMap.put(categoryHeader, categoryChildren);
                }

                view.setupCategories(categoriesMap);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onChildClicked(String groupName, String childName) {
        reportCacheHolder.onCategorySelected(groupName, childName);
        view.goToPhotoScreen();
    }
}
