package com.dwa.rybridge.ryebridgedwa.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.dwa.rybridge.ryebridgedwa.data.User;
import com.dwa.rybridge.ryebridgedwa.ui.view.SplashView;

import android.os.Handler;

public class SplashPresenterImpl implements SplashPresenter {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private SplashView view;

    public SplashPresenterImpl(SplashView view) {
        this.view = view;
    }

    @Override
    public void initialise() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void onViewCreated() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.navigateToLoginScreen();
                }
            }, 2000);
        } else {
            checkIfPoliciesAreAccepted();
        }
    }

    private void checkIfPoliciesAreAccepted() {
        firebaseDatabase.getReference().child("userData").child(firebaseAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user.arePoliciesAccepted()) {
                    view.navigateToMainScreen();
                } else {
                    view.navigateToPoliciesScreen();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
