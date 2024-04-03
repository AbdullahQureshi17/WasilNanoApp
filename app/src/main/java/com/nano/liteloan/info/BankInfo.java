package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Ahmad on 07/06/2020.
 */
public class BankInfo {

    @SerializedName("id")
    public Integer bankId;

    @SerializedName("name")
    public String bankName;

    @SerializedName("short_name")
    public String bankSortName;

    @SerializedName("logo")
    public String logo;

    @SerializedName("mask")
    public String mask;

    @SerializedName("description")
    public String desc;
}
