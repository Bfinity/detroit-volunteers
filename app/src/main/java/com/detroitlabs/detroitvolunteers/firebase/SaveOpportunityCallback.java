package com.detroitlabs.detroitvolunteers.firebase;

public interface SaveOpportunityCallback {
    void onSaveSuccess();
    void onError(String errorMessage);
}
