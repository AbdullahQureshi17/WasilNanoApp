package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;
/**
 * Created by Muhammad Umer on 12/01/2021.
 */
public class Location {

    @SerializedName("longitude")
    public String longitude;

    @SerializedName("latitude")
    public String latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public Location() {
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
