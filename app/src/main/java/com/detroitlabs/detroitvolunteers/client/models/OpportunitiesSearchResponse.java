package com.detroitlabs.detroitvolunteers.client.models;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OpportunitiesSearchResponse {

    int currentPage;

    @Expose
    @SerializedName("opportunities")
    ArrayList<VolunteerOpportunity> volunteerOpportunities;

    @SerializedName("resultsSize")
    private long resultsSize;

    @SerializedName("sortCriteria")
    private String sortCriteria;


    public OpportunitiesSearchResponse(){
        volunteerOpportunities = new ArrayList<VolunteerOpportunity>();
    }

    public static OpportunitiesSearchResponse parseJSON(String response){
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Log.i("GSONResponse", response);
        OpportunitiesSearchResponse opportunitiesSearchResponse = gson.fromJson(response, OpportunitiesSearchResponse.class);
        return opportunitiesSearchResponse;
    }

    public ArrayList<VolunteerOpportunity> getList(){
        return volunteerOpportunities;
    }
}
