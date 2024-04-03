package com.nano.liteloan.info.businesscorrespondant;

import com.google.gson.annotations.SerializedName;

public class VerifiedBorrower {
    @SerializedName("user_id")
    public String userid;

    @SerializedName("application_id")
    public String applicationid;

    @SerializedName("correspondent_id")
    public String correspondentid;

    @SerializedName("full_name")
    public String fullname;

    @SerializedName("cnic")
    public String cnic;

    @SerializedName("phone_no")
    public String phoneno;

    @SerializedName("requested amount")
    public String requestedamount;

    @SerializedName("recommended_amount")
    public String recommendedamount;

}
