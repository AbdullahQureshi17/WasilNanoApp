package com.nano.liteloan.info.businesscorrespondant;

import com.google.gson.annotations.SerializedName;

public class PendingList {


    @SerializedName("user_id")
    public int userid;

    @SerializedName("full_name")
    public String fullname;

    @SerializedName("latitude")
    public String latitude;

    @SerializedName("longitude")
    public String longitude;





    @SerializedName("cnic")
    public String cnic;


    @SerializedName("phone_no")
    public String phoneno;


    @SerializedName("correspondent_id")
    public int correspondentid;




    @SerializedName("requested amount")
    public int requestedAmount;




    @SerializedName("application_id")
    public int applicationid;

}
