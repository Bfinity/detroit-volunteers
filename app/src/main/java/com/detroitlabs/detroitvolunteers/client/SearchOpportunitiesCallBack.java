package com.detroitlabs.detroitvolunteers.client;

import com.detroitlabs.detroitvolunteers.client.models.OpportunitiesSearchResponse;

public interface SearchOpportunitiesCallBack {
    void onSuccess(OpportunitiesSearchResponse response);
    void onError(int statusCode);
    void onFailure(String message);
}
