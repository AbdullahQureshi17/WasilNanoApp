package com.nano.liteloan.info.businesscorrespondant;

import com.google.gson.annotations.SerializedName;

public class MyApplicants {

    @SerializedName("application_id")
    public String application_id;

    @SerializedName("user_id")
    public int userId;

    @SerializedName("name")
    public String name;


    public String offlinestatus;

    @SerializedName("cnic")
    public String cnic;


    @SerializedName("phone_no")
    public String phoneno;

    @SerializedName("parentage")
    public String parentage;

    @SerializedName("psid")
    public String psid;

    @SerializedName("otp_flag")
    public String otpflag;

    @SerializedName("gender")
    public String gender;

    @SerializedName("dob")
    public String dob;

    @SerializedName("user_status")
    public String userstatus;

}
