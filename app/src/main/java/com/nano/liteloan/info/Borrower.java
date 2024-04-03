package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
/**
 * Created by Muhammad Ahmad on 07/06/2020.
 */
public class Borrower implements Serializable {


    @SerializedName("name")
    public String name;
    @SerializedName("parentage")
    public String parentage;

    @SerializedName("cnic")
    public String cnic;

    @SerializedName("gender")
    public String gender;

    @SerializedName("phone_no")
    public String phoneno;

    @SerializedName("dob")
    public String dob;

    @SerializedName("account")
    public String account;

    @SerializedName("account_type")
    public String accounttype;

    @SerializedName("fee")
    public int fee;

    @SerializedName("loan_duration")
    public List<String> loanduration;

}
