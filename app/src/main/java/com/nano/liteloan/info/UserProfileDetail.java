package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

public class UserProfileDetail {

    @SerializedName("permanent_address")
    public String userAddress;

    @SerializedName("user_id")
    public String userId;

    @SerializedName("parentage")
    public String parentage;

}
