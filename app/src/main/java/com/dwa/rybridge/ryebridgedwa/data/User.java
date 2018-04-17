package com.dwa.rybridge.ryebridgedwa.data;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public boolean acceptedTermsAndConditions;
    public String email;
    public String name;
    public boolean safetyInductionReceived;

    public User() {

    }

    public User(boolean acceptedTermsAndConditions, String email, String name, boolean safetyInductionReceived) {
        this.acceptedTermsAndConditions = acceptedTermsAndConditions;
        this.email = email;
        this.name = name;
        this.safetyInductionReceived = safetyInductionReceived;
    }

    public boolean isAcceptedTermsAndConditions() {
        return acceptedTermsAndConditions;
    }

    public void setAcceptedTermsAndConditions(boolean acceptedTermsAndConditions) {
        this.acceptedTermsAndConditions = acceptedTermsAndConditions;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSafetyInductionReceived() {
        return safetyInductionReceived;
    }

    public void setSafetyInductionReceived(boolean safetyInductionReceived) {
        this.safetyInductionReceived = safetyInductionReceived;
    }
}
