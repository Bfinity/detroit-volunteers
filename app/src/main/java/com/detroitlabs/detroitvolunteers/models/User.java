package com.detroitlabs.detroitvolunteers.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = User.Builder.class)
public class User implements Parcelable {


    private String userName;

    private String userEmail;

    private String userUid;

    private User(Builder builder){
        this.userName = builder.userName;
        this.userEmail = builder.userEmail;
        this.userUid = builder.userUid;
    }

    public String getUserName(){
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserUid() {
        return userUid;
    }

    @JsonPOJOBuilder()
    public static class Builder {
        private String userName;
        private String userEmail;
        private String userUid;

        public Builder(){

        }

        public Builder(@JsonProperty("userEmail") String userEmail){
            this.userEmail = userEmail;
        }

        public Builder withUserName(String userName){
            this.userName = userName;
            return this;
        }

        public Builder withUserUid(String userUid){
            this.userUid = userUid;
            return this;
        }

        public User build(){
            return new User(this);
        }

        public Builder fromUser(User user){
            this.userName = user.getUserName();
            this.userEmail = user.getUserEmail();
            this.userUid = user.getUserUid();
            return this;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.userEmail);
        dest.writeString(this.userUid);
    }

    protected User(Parcel in) {
        this.userName = in.readString();
        this.userEmail = in.readString();
        this.userUid = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
