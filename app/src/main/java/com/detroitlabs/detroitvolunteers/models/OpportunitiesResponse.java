package com.detroitlabs.detroitvolunteers.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class OpportunitiesResponse {

    List<VolunteerOpportunity> volunteerOpportunities;

    public OpportunitiesResponse(){
        volunteerOpportunities = new ArrayList<VolunteerOpportunity>();
    }

    public static OpportunitiesResponse parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, OpportunitiesResponse.class);
    }
}
