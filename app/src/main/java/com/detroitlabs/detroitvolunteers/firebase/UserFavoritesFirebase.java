package com.detroitlabs.detroitvolunteers.firebase;

import com.detroitlabs.detroitvolunteers.client.models.VolunteerOpportunity;
import com.detroitlabs.detroitvolunteers.models.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class UserFavoritesFirebase {

    VolunteerMatchFirebase firebase;
    User user;

    public UserFavoritesFirebase(User user, VolunteerMatchFirebase firebase){
        this.user = user;
        this.firebase = firebase;
    }

    public void saveOpportunityToFavorites(VolunteerOpportunity opportunity, final SaveOpportunityCallback callback){
        firebase.getFirebaseFavoriteRef().child(user.getUserUid()).push().setValue(opportunity, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if(firebaseError != null){
                    callback.onError(firebaseError.getMessage());
                }
                else{
                    callback.onSaveSuccess();
                }
            }
        });
    }

    public void getSavedOpportunities(User user, final GetSavedOpportunitiesCallback callback){
        firebase.getFirebaseFavoriteRef().child(user.getUserUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<VolunteerOpportunity> list = new ArrayList<VolunteerOpportunity>();
                for(DataSnapshot shot: dataSnapshot.getChildren()) {
                    VolunteerOpportunity op = shot.getValue(VolunteerOpportunity.class);
                    list.add(op);
                }
                callback.onSuccess(list);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onError(firebaseError.getMessage());
            }
        });
    }
}
