package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;
/**
 * Created by Muhammad Umer on 22/02/2020.
 */
public class LiteAccount {
    @SerializedName("account_no")
    public String accountNo;

    @SerializedName("name")
    public String accountTitle;

    @SerializedName("title")
    public String title;
}
