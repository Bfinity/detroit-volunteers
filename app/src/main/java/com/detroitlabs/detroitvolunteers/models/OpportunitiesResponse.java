package com.detroitlabs.detroitvolunteers.models;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OpportunitiesResponse {

    int currentPage;

    @Expose
    @SerializedName("opportunities")
    ArrayList<VolunteerOpportunity> volunteerOpportunities;

    @SerializedName("resultsSize")
    private long resultsSize;

    @SerializedName("sortCriteria")
    private String sortCriteria;


    public OpportunitiesResponse(){
        volunteerOpportunities = new ArrayList<VolunteerOpportunity>();
    }

    public static OpportunitiesResponse parseJSON(String response){
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Log.i("GSONResponse", response);
        OpportunitiesResponse opportunitiesResponse = gson.fromJson(response, OpportunitiesResponse.class);
        return opportunitiesResponse;
    }

    public ArrayList<VolunteerOpportunity> getList(){
        return volunteerOpportunities;
    }
}
