package com.dwa.rybridge.ryebridgedwa.presenter;

import android.view.View;

import com.dwa.rybridge.ryebridgedwa.ui.view.PoliciesView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PoliciesPresenterImpl implements PoliciesPresenter {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    private PoliciesView view;

    public PoliciesPresenterImpl(PoliciesView view) {
        this.view = view;
    }

    @Override
    public void initialise() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        onSwitchesCheckChanged();
        getPolicyHtml();
    }

    @Override
    public void onSwitchesCheckChanged() {
        boolean areSwitchesChecked = view.isPoliciesSwitchChecked() && view.isInductionSwitchChecked();
        view.setNextButtonVisibility(areSwitchesChecked ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onNextButtonClicked() {
        String userId = firebaseAuth.getUid();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("userData").child(userId);
        databaseReference.child("acceptedTermsAndConditions").setValue(true);
        databaseReference.child("safetyInductionReceived").setValue(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                view.navigateToMainActivity();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
