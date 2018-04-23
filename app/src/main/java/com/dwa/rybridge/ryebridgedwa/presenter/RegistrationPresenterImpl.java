package com.dwa.rybridge.ryebridgedwa.presenter;

import android.support.annotation.NonNull;

import com.dwa.rybridge.ryebridgedwa.data.RegisterData;
import com.dwa.rybridge.ryebridgedwa.ui.view.RegistrationView;
import com.dwa.rybridge.ryebridgedwa.validator.RegistrationValidator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationPresenterImpl implements RegistrationPresenter {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private RegisterData registerData;

    private RegistrationView registrationView;
    private String userId;

    public RegistrationPresenterImpl(RegistrationView registrationView) {
        this.registrationView = registrationView;
    }

    @Override
    public void initialise() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
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
        firebaseDatabase.getReference().child("accessCode").addValueEventListener(new AccessCodeValueListener());
    }

    //TODO Should consider extracting this into a usecase
    private void registerUser() {
        firebaseAuth.createUserWithEmailAndPassword(registerData.getEmailAddress(), registerData.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    uploadData();
                } else{
                    registrationView.displayToastMessage("Registration failed!");
                }
            }
        });
    }

    //TODO Should consider extracting this into a usecase
    private void uploadData() {
        String userId = firebaseAuth.getUid();
        DatabaseReference userDBReference = firebaseDatabase.getReference().child("userData").child(userId);
        userDBReference.child("name").setValue(registerData.getFirstName() + " " + registerData.getLastName());
        userDBReference.child("email").setValue(registerData.getEmailAddress());
        userDBReference.child("phone").setValue(registerData.getMobileNumber());
        userDBReference.addValueEventListener(new DataUploadValueListener());
    }

    //TODO Should consider moving it as a separate class
    private class AccessCodeValueListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String firebaseAccessCode = dataSnapshot.getValue(String.class);

            if (!registerData.getAccessCode().equals(firebaseAccessCode)) {
                registrationView.displayToastMessage("Invalid access code!");
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
            registrationView.navigateToPolictiesActivity();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
