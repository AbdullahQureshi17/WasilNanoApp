package com.nano.liteloan.info.businesscorrespondant;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VerifiedList {
    @SerializedName("verified_list")
    public List<VerifiedBorrower> verifiedBorrowers;
}
