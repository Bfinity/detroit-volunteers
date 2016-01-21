package com.detroitlabs.detroitvolunteers.firebase;

import android.support.annotation.NonNull;

import com.detroitlabs.detroitvolunteers.models.User;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UserAuthentication {

    private VolunteerMatchFirebase firebase;

    public UserAuthentication(VolunteerMatchFirebase volunteerMatchFirebase){
        this.firebase = volunteerMatchFirebase;
    }

    public void createNewUser(@NonNull final User user, final String password, final UserAuthCallBack callBack) {
        firebase.getFirebaseBaseRef().createUser(user.getUserEmail(), password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                if (stringObjectMap.containsKey("uid")) {
                    final User updatedUser = updateUserUid(user, stringObjectMap);
                    storeUserDataToFirebase(updatedUser, new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            if (firebaseError != null) {
                                callBack.onError(firebaseError.getMessage());
                            } else {
                                loginUser(updatedUser, password, callBack);
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                callBack.onError(firebaseError.getMessage());
            }
        });
    }

    public void authenticateUser(@NonNull final User user, final String password, final UserAuthCallBack callback){
        Query query = firebase.getFirebaseUserRef().orderByChild("userEmail").equalTo(user.getUserEmail());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    User returningUser = dataSnapshot.getChildren().iterator().next().getValue(User.class);
                    loginUser(returningUser, password, callback);
                } else {
                    callback.onError("Unable to authenticate with this email. Please try again");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onError(firebaseError.getMessage());
            }
        });
    }

    private void loginUser(@NonNull final User user, String password, final UserAuthCallBack callback){
        firebase.getFirebaseBaseRef().authWithPassword(user.getUserEmail(), password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                User userAuth = updateUserUid(user, authData.getUid());
                callback.onSuccess(userAuth);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                callback.onError(firebaseError.getMessage());
            }
        });
    }

    private User updateUserUid(@NonNull User user, Map<String, Object> map){
        String uid = (String) map.get("uid");
        return updateUserUid(user, uid);
    }

    private User updateUserUid(@NonNull User user, String uid){
        return new User.Builder().fromUser(user).withUserUid(uid).build();
    }

    private void storeUserDataToFirebase(@NonNull User updatedUser, Firebase.CompletionListener completionListener){
        Map<String, String> map = new HashMap<String, String>();
        map.put("userName", updatedUser.getUserName());
        map.put("userEmail", updatedUser.getUserEmail());
        firebase.getFirebaseUserRef().child(updatedUser.getUserUid()).setValue(map, completionListener);
    }
}
