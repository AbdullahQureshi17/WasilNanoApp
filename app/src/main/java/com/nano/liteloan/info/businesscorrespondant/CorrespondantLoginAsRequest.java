package com.nano.liteloan.info.businesscorrespondant;

import com.google.gson.annotations.SerializedName;

public class CorrespondantLoginAsRequest {

    @SerializedName("id")
    public String userId;


    @SerializedName("password")
    public String otp;


    @SerializedName("phone")
    public String phone;
}
