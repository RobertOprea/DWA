package com.dwa.rybridge.ryebridgedwa.presenter;

import com.dwa.rybridge.ryebridgedwa.ui.view.PoliciesView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PoliciesPresenterImpl implements PoliciesPresenter {

    private FirebaseDatabase firebaseDatabase;

    private PoliciesView view;

    public PoliciesPresenterImpl(PoliciesView view) {
        this.view = view;
    }

    @Override
    public void initialise() {
        firebaseDatabase = FirebaseDatabase.getInstance();

        getPolicyHtml();
    }

    private void getPolicyHtml() {
        firebaseDatabase.getReference().child("privacyPolicy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String htmlPage = dataSnapshot.getValue(String.class);

                view.loadHtmlPage(htmlPage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
