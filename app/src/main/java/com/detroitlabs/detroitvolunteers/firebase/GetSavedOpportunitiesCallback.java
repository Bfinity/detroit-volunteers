package com.detroitlabs.detroitvolunteers.firebase;

import com.detroitlabs.detroitvolunteers.client.models.VolunteerOpportunity;

import java.util.ArrayList;

public interface GetSavedOpportunitiesCallback {
    void onSuccess(ArrayList<VolunteerOpportunity> listOfOpportunities);
    void onError(String errorMessage);
}
