package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Muhammad Ahmad on 4/17/2019.
 */
public class OTPResponse {

    @SerializedName("user")
    public UserDate userDate;

    @SerializedName("lite_account")
    public List<LiteAccount> liteaccount;
}
