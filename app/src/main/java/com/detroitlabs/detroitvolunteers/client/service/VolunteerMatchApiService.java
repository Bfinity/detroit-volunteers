package com.detroitlabs.detroitvolunteers.client.service;

import com.detroitlabs.detroitvolunteers.client.models.OpportunitiesSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VolunteerMatchApiService {

    @GET("call?action=searchOpportunities&query=%7B%22location%22%3A%22detroit%2C+us%2C+mi%22%7D")
    Call<OpportunitiesSearchResponse> getAllVolunteerOpportunities();

}
