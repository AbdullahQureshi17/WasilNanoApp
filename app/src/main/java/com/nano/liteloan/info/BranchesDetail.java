package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Umer on 07/06/2020.
 * Modified by Muhammad Ahmad.
 */
public class BranchesDetail {

    @SerializedName("name")
    String Branchname ;

    @SerializedName("id")
    int Branchid;

    public String getBranchname() {
        return Branchname;
    }

    public void setBranchname(String branchname) {
        Branchname = branchname;
    }

    public int getBranchid() {
        return Branchid;
    }

    public void setBranchid(int branchid) {
        Branchid = branchid;
    }
}
