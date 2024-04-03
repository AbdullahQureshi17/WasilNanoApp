package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BranchesList {

    @SerializedName("branches")
    public List<BranchesDetail> borrowerList;
}
