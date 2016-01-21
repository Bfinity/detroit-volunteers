package com.detroitlabs.detroitvolunteers.firebase;

import com.detroitlabs.detroitvolunteers.models.User;

public interface UserAuthCallBack {
    void onSuccess(User user);
    void onError(String errorMessage);
}
