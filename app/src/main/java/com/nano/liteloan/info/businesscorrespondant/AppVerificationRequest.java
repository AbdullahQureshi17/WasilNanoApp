package com.nano.liteloan.info.businesscorrespondant;

import com.google.gson.annotations.SerializedName;

public class AppVerificationRequest {
    @SerializedName("status")
    public String status;

    @SerializedName("latitude")
    public String latitude;

    @SerializedName("longitude")
    public String longitude;

    @SerializedName("user_id")
    public int userid;

    @SerializedName("application_id")
    public int applicationid;

    @SerializedName("reject_reason")
    public String rejectreason;

    @SerializedName("recommend_amount")
    public String recommendamount;

    @SerializedName("correspondent_id")
    public int correspondentid;


}
