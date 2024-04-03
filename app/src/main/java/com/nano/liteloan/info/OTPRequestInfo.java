package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Ahmad on 4/17/2019.
 */
public class OTPRequestInfo {

    @SerializedName("phone")
    public String phone;

    @SerializedName("password")
    public String password;

    @SerializedName("hash_key")
    public String hashKey;

    @SerializedName("pin")
    public String pinCode;
}
