package com.nano.liteloan.info.businesscorrespondant;

import com.google.gson.annotations.SerializedName;

public class PotentialBorrowerRequest {

    @SerializedName("type")
    public String type;

    @SerializedName("parameter")
    public String parameter;

    @SerializedName("search")
    public String search;
}
