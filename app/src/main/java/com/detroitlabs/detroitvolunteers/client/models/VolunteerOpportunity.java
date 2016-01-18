package com.detroitlabs.detroitvolunteers.client.models;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class VolunteerOpportunity implements Parcelable {

    int[] categoryIds;

    @SerializedName("plaintextDescription")
    String opportunityDescription;

    @SerializedName("location")
    Location location;

    int minimumAge;

    ParentOrg parentOrg;

    @SerializedName("title")
    String opportunityTitle;

    @SerializedName("vmURL")
    String opportunityUrl;

    public int[] getCategoryIds(){
        return categoryIds;
    }

    public String getOpportunityDescription(){
        return opportunityDescription;
    }

    public Location getLocation(){
        return location;
    }

    public int getMinimumAge(){
        return minimumAge;
    }

    public ParentOrg getParentOrg(){
        return parentOrg;
    }

    public String getOpportunityTitle(){
        return opportunityTitle;
    }

    public String getOpportunityUrl(){
        return opportunityUrl;
    }

    public static class ParentOrg implements Parcelable {
        int id;
        String name;

        public int getId(){
            return id;
        }

        public String getName(){
            return name;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
        }

        public ParentOrg() {
        }

        protected ParentOrg(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
        }

        public static final Creator<ParentOrg> CREATOR = new Creator<ParentOrg>() {
            public ParentOrg createFromParcel(Parcel source) {
                return new ParentOrg(source);
            }

            public ParentOrg[] newArray(int size) {
                return new ParentOrg[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(this.categoryIds);
        dest.writeString(this.opportunityDescription);
        dest.writeParcelable(this.location, 0);
        dest.writeInt(this.minimumAge);
        dest.writeParcelable(this.parentOrg, flags);
        dest.writeString(this.opportunityTitle);
        dest.writeString(this.opportunityUrl);
    }

    public VolunteerOpportunity() {
    }

    protected VolunteerOpportunity(Parcel in) {
        this.categoryIds = in.createIntArray();
        this.opportunityDescription = in.readString();
        this.location = in.readParcelable(Location.class.getClassLoader());
        this.minimumAge = in.readInt();
        this.parentOrg = in.readParcelable(ParentOrg.class.getClassLoader());
        this.opportunityTitle = in.readString();
        this.opportunityUrl = in.readString();
    }

    public static final Parcelable.Creator<VolunteerOpportunity> CREATOR = new Parcelable.Creator<VolunteerOpportunity>() {
        public VolunteerOpportunity createFromParcel(Parcel source) {
            return new VolunteerOpportunity(source);
        }

        public VolunteerOpportunity[] newArray(int size) {
            return new VolunteerOpportunity[size];
        }
    };
}
