package com.nano.liteloan.info.businesscorrespondant;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActiveLoans {
    @SerializedName("application_list")
    public List<ApplicationOverDueList> applicationlist;


}
