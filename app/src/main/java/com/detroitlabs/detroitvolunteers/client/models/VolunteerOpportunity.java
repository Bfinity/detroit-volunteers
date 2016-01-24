package com.detroitlabs.detroitvolunteers.client.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class VolunteerOpportunity implements Parcelable {

    Availability availability;

    int[] categoryIds;

    int id;

    @SerializedName("plaintextDescription")
    String opportunityDescription;

    @SerializedName("location")
    Location location;

    int minimumAge;

    ParentOrg parentOrg;

    @SerializedName("title")
    String opportunityTitle;

    @SerializedName("vmUrl")
    String opportunityUrl;

    public Availability getAvailability(){
        return availability;
    }

    public int[] getCategoryIds(){
        return categoryIds;
    }

    public int getId(){
        return id;
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


    public VolunteerOpportunity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.availability, 0);
        dest.writeIntArray(this.categoryIds);
        dest.writeInt(this.id);
        dest.writeString(this.opportunityDescription);
        dest.writeParcelable(this.location, 0);
        dest.writeInt(this.minimumAge);
        dest.writeParcelable(this.parentOrg, 0);
        dest.writeString(this.opportunityTitle);
        dest.writeString(this.opportunityUrl);
    }

    protected VolunteerOpportunity(Parcel in) {
        this.availability = in.readParcelable(Availability.class.getClassLoader());
        this.categoryIds = in.createIntArray();
        this.id = in.readInt();
        this.opportunityDescription = in.readString();
        this.location = in.readParcelable(Location.class.getClassLoader());
        this.minimumAge = in.readInt();
        this.parentOrg = in.readParcelable(ParentOrg.class.getClassLoader());
        this.opportunityTitle = in.readString();
        this.opportunityUrl = in.readString();
    }

    public static final Creator<VolunteerOpportunity> CREATOR = new Creator<VolunteerOpportunity>() {
        public VolunteerOpportunity createFromParcel(Parcel source) {
            return new VolunteerOpportunity(source);
        }

        public VolunteerOpportunity[] newArray(int size) {
            return new VolunteerOpportunity[size];
        }
    };
}
