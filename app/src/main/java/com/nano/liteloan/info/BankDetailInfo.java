package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

public class BankDetailInfo {

    @SerializedName("user_id")
    public String userId;

    @SerializedName("bank_id")
    public String bankId;

    @SerializedName("account_title")
    public String account_title;

    @SerializedName("account_no")
    public String account_no;

}
