package com.dwa.rybridge.ryebridgedwa.data;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class User {

    public boolean acceptedTermsAndConditions;
    public boolean safetyInductionReceived;

    public User() {

    }

    public User(boolean acceptedTermsAndConditions, boolean safetyInductionReceived) {
        this.acceptedTermsAndConditions = acceptedTermsAndConditions;
        this.safetyInductionReceived = safetyInductionReceived;
    }

    public boolean isAcceptedTermsAndConditions() {
        return acceptedTermsAndConditions;
    }

    public void setAcceptedTermsAndConditions(boolean acceptedTermsAndConditions) {
        this.acceptedTermsAndConditions = acceptedTermsAndConditions;
    }

    public boolean isSafetyInductionReceived() {
        return safetyInductionReceived;
    }

    public void setSafetyInductionReceived(boolean safetyInductionReceived) {
        this.safetyInductionReceived = safetyInductionReceived;
    }

    public boolean arePoliciesAccepted() {
        return acceptedTermsAndConditions && safetyInductionReceived;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("acceptedTermsAndConditions", acceptedTermsAndConditions);
        result.put("safetyInductionReceived", safetyInductionReceived);
        return result;
    }
}
