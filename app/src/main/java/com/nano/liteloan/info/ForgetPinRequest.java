package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;
/**
 * Created by Muhammad Ahmad on 08/07/2020.
 */
public class ForgetPinRequest {

    @SerializedName("user_id")
    public String userId;

    @SerializedName("pin")
    public String pin;

    @SerializedName("password")
    public String code;
}
