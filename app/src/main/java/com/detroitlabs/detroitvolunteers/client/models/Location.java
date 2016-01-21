package com.detroitlabs.detroitvolunteers.client.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {

    String city;
    String region;

    public String getCity(){
        return city;
    }

    public String getRegion(){
        return region;
    }

    public Location() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.city);
        dest.writeString(this.region);
    }

    protected Location(Parcel in) {
        this.city = in.readString();
        this.region = in.readString();
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
