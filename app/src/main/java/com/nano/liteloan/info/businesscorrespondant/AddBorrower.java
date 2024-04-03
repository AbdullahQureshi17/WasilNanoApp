package com.nano.liteloan.info.businesscorrespondant;

import com.google.gson.annotations.SerializedName;

public class AddBorrower {

    @SerializedName("name")
    public String name;

    @SerializedName("phone_no")
    public String phoneno;

    @SerializedName("cnic")
    public String cnic;
}
