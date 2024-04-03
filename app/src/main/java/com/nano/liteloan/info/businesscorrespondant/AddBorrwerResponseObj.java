package com.nano.liteloan.info.businesscorrespondant;

import com.google.gson.annotations.SerializedName;

public class AddBorrwerResponseObj {
    @SerializedName("user_id")
    public int userId;

    @SerializedName("name")
    public String name;

    @SerializedName("cnic")
    public String cnic;

    @SerializedName("parentage")
    public String parentage;

    @SerializedName("dob")
    public String dob;

    @SerializedName("gender")
    public String gender;

    @SerializedName("phone_no")
    public String phoneno;

    @SerializedName("otp_flag")
    public String otpflag;

//    @SerializedName("loan_amount")
//    public String loanamount;
//
//
//
//    @SerializedName("fee")
//    public String fee;
//
//    @SerializedName("due_date")
//    public String duedate;
}
