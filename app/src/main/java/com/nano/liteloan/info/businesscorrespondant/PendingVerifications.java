package com.nano.liteloan.info.businesscorrespondant;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PendingVerifications {
    @SerializedName("pending_list")
    public List<PendingList> pendinglist;

}
