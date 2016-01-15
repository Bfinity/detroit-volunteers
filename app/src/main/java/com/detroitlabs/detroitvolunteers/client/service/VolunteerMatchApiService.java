package com.detroitlabs.detroitvolunteers.client.service;

import com.detroitlabs.detroitvolunteers.client.models.OpportunitiesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VolunteerMatchApiService {
    // http://www.volunteermatch.org/api/call?action=searchOpportunities&query="{\"name\":\"john\"}" 

    @GET("call?action=searchOpportunities&query=%7B%22location%22%3A%22detroit%2C+us%2C+mi%22%7D")
    Call<OpportunitiesResponse> getResponse();
}
