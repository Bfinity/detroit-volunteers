package com.detroitlabs.detroitvolunteers.client;

import com.detroitlabs.detroitvolunteers.client.models.OpportunitiesResponse;

public interface SearchOpportunitiesCallBack {
    void onSuccess(OpportunitiesResponse response);
    void onError(int statusCode);
    void onFailure(String message);
}
