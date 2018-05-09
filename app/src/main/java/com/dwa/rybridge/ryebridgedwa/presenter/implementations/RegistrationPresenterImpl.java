package com.dwa.rybridge.ryebridgedwa.presenter.implementations;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.dwa.rybridge.ryebridgedwa.data.RegisterData;
import com.dwa.rybridge.ryebridgedwa.presenter.RegistrationPresenter;
import com.dwa.rybridge.ryebridgedwa.ui.view.RegistrationView;
import com.dwa.rybridge.ryebridgedwa.validator.RegistrationValidator;

import android.support.annotation.NonNull;

import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.FAILED_REGISTRATION;
import static com.dwa.rybridge.ryebridgedwa.constants.ErrorMessageConstants.INVALID_ACCESS_CODE;
import static com.dwa.rybridge.ryebridgedwa.constants.FirebaseConstants.ACCESS_CODE;
import static com.dwa.rybridge.ryebridgedwa.constants.FirebaseConstants.EMAIL;
import static com.dwa.rybridge.ryebridgedwa.constants.FirebaseConstants.NAME;
import static com.dwa.rybridge.ryebridgedwa.constants.FirebaseConstants.PHONE;
import static com.dwa.rybridge.ryebridgedwa.constants.FirebaseConstants.USER_DATA;

public class RegistrationPresenterImpl implements RegistrationPresenter {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private RegisterData registerData;

    private RegistrationView registrationView;
    private DatabaseReference userDBReference;
    private DataUploadValueListener dataUploadValueListener;

    public RegistrationPresenterImpl(RegistrationView registrationView) {
        this.registrationView = registrationView;
    }

    @Override
    public void initialise() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        dataUploadValueListener = new DataUploadValueListener();
    }

    @Override
    public void onRegisterClicked() {
        registerData = new RegisterData();
        registerData.setFirstName(registrationView.getFirstName());
        registerData.setLastName(registrationView.getLastName());
        registerData.setEmailAddress(registrationView.getEmailAddress());
        registerData.setMobileNumber(registrationView.getMobileNumber());
        registerData.setPassword(registrationView.getPassword());
        registerData.setRepeatPassword(registrationView.getRepeatPassword());
        registerData.setAccessCode(registrationView.getAccessCode());

        String message = RegistrationValidator.validateRegistrationData(registerData);

        if (!message.isEmpty()) {
            registrationView.displayToastMessage(message);
        } else {
            startRegister();
        }
    }

    private void startRegister() {
        firebaseDatabase.getReference().child(ACCESS_CODE).addListenerForSingleValueEvent(new AccessCodeValueListener());
    }

    //TODO Should consider extracting this into a usecase
    private void registerUser() {
        registrationView.showLoadingView();
        firebaseAuth.createUserWithEmailAndPassword(registerData.getEmailAddress(), registerData.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    uploadData();
                } else{
                    registrationView.hideLoadingView();
                    registrationView.displayToastMessage(FAILED_REGISTRATION);
                }
            }
        });
    }

    //TODO Should consider extracting this into a usecase
    private void uploadData() {
        String userId = firebaseAuth.getUid();
        userDBReference = firebaseDatabase.getReference().child(USER_DATA).child(userId);
        userDBReference.child(NAME).setValue(registerData.getFirstName() + " " + registerData.getLastName());
        userDBReference.child(EMAIL).setValue(registerData.getEmailAddress());
        userDBReference.child(PHONE).setValue(registerData.getMobileNumber());
        userDBReference.addListenerForSingleValueEvent(dataUploadValueListener);
    }

    //TODO Should consider moving it as a separate class
    private class AccessCodeValueListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String firebaseAccessCode = dataSnapshot.getValue(String.class);

            if (!registerData.getAccessCode().equals(firebaseAccessCode)) {
                registrationView.displayToastMessage(INVALID_ACCESS_CODE);
            } else {
                registerUser();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

    //TODO Should consider moving it as a separate class
    private class DataUploadValueListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            registrationView.hideLoadingView();
            registrationView.navigateToPolictiesActivity();
            userDBReference.removeEventListener(this);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
