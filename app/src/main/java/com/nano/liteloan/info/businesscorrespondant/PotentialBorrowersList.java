package com.nano.liteloan.info.businesscorrespondant;

import com.google.gson.annotations.SerializedName;

public class PotentialBorrowersList {

    @SerializedName("user_id")
    public String userId;

    @SerializedName("full_name")
    public String fullname;

    @SerializedName("cnic")
    public String cnic;

    @SerializedName("phone_no")
    public String phoneno;
}
