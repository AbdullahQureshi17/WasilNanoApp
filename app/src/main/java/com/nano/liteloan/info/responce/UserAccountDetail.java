package com.nano.liteloan.info.responce;

import com.google.gson.annotations.SerializedName;

public class UserAccountDetail {

    @SerializedName("account_title")
    public String accountTitle;

    @SerializedName("account_no")
    public String accountNumber;

    @SerializedName("verified_at")
    public String verifyAt;

    @SerializedName("bankId")
    public String bankId;
}
