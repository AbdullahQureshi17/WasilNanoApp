package com.nano.liteloan.info.businesscorrespondant;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EligibiltyCriteria {

    @SerializedName("pages")
    public List<EligibilityCriteriaPages> pages;
}
