package com.nano.liteloan.info.businesscorrespondant;

import com.google.gson.annotations.SerializedName;

public class ApplicationOverDueList {
    @SerializedName("id")
    public int id;

    @SerializedName("user_id")
    public int userid;

    public String getFullname() {
        return fullname;
    }

    @SerializedName("full_name")
    public String fullname;


    @SerializedName("cnic")
    public String cnic;


    @SerializedName("phone_no")
    public String phoneno;

    @SerializedName("status")
    public String status;

    @SerializedName("due_date")
    public String duedate;

    @SerializedName("psid")
    public String psid;

    @SerializedName("requested amount")
    public int requestedAmount;

    public long getRecoverydays() {
        return recoverydays;
    }

    public long recoverydays;


}
