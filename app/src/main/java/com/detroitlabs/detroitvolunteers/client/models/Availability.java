package com.detroitlabs.detroitvolunteers.client.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Availability implements Parcelable {
    boolean ongoing;

    boolean singleDayOpportunity;

    String startDate;

    String endDate;

    public boolean isOngoing(){
        return ongoing;
    }

    public boolean isSingleDayOpportunity(){
        return singleDayOpportunity;
    }

    public String getStartDate(){
        return startDate;
    }

    public String getEndDate(){
        return endDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(ongoing ? (byte) 1 : (byte) 0);
        dest.writeByte(singleDayOpportunity ? (byte) 1 : (byte) 0);
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
    }

    public Availability() {
    }

    protected Availability(Parcel in) {
        this.ongoing = in.readByte() != 0;
        this.singleDayOpportunity = in.readByte() != 0;
        this.startDate = in.readString();
        this.endDate = in.readString();
    }

    public static final Parcelable.Creator<Availability> CREATOR = new Parcelable.Creator<Availability>() {
        public Availability createFromParcel(Parcel source) {
            return new Availability(source);
        }

        public Availability[] newArray(int size) {
            return new Availability[size];
        }
    };
}
