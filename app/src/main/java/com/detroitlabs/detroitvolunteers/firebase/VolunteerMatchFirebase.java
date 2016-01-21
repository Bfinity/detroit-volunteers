package com.detroitlabs.detroitvolunteers.firebase;

import com.firebase.client.Firebase;

public class VolunteerMatchFirebase {

    private final String BASE_REF = "https://detroit-volunteers.firebaseio.com";
    private final String USER_ENDPOINT = "users";
    private final String FAVORITES_ENDPOINT = "savedOpportunities";

    private final Firebase firebaseBaseRef = new Firebase(BASE_REF);
    private final Firebase firebaseUserRef = firebaseBaseRef.child(USER_ENDPOINT);
    private final Firebase firebaseFavoriteRef = firebaseBaseRef.child(FAVORITES_ENDPOINT);

    public Firebase getFirebaseBaseRef(){
        return firebaseBaseRef;
    }

    public Firebase getFirebaseUserRef(){
        return firebaseUserRef;
    }

    public Firebase getFirebaseFavoriteRef(){
        return firebaseFavoriteRef;
    }

}
