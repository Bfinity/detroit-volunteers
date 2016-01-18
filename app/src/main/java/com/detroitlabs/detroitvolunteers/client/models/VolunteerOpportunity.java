package com.detroitlabs.detroitvolunteers.client.models;

import android.location.Location;

import com.google.gson.annotations.SerializedName;

public class VolunteerOpportunity {

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

    public static class ParentOrg{
        int id;
        String name;

        public int getId(){
            return id;
        }

        public String getName(){
            return name;
        }
    }

}
