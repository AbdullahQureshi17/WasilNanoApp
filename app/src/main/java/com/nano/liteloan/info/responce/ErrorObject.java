package com.nano.liteloan.info.responce;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Muhammad Ahmad on 07/16/2020.
 */
public class ErrorObject {

    @SerializedName("name")
    public List<String> name = null;

    @SerializedName("parentage")
    public List<String> parentage = null;

    @SerializedName("dob")
    public List<String> dob = null;

    @SerializedName("gender")
    public List<String> gender = null;

    @SerializedName("cnic")
    public List<String> cnic = null;

    @SerializedName("profession")
    public List<String> profession = null;

    @SerializedName("income_from")
    public List<String> incomeFrom = null;

    @SerializedName("income_to")
    public List<String> incomeTo = null;

    @SerializedName("dependent")
    public List<String> dependent = null;

    @SerializedName("duration_of_stay")
    public List<String> durationOfStay = null;

    @SerializedName("address")
    public List<String> address = null;

    @SerializedName("city")
    public List<String> city = null;

    @SerializedName("bank_id")
    public List<String> bankId = null;

    @SerializedName("account_no")
    public List<String> accountNo = null;

    @SerializedName("latitude")
    public List<String> latitude = null;

    @SerializedName("longitude")
    public List<String> longitude = null;

    @SerializedName("email")
    public List<String> email = null;

    @SerializedName("cnic_issue_date")
    public List<String> issueDate = null;

    @SerializedName("cnic_expiry_date")
    public List<String> expiryDate = null;

    @SerializedName("income")
    public List<String> incomeError = null;

    @SerializedName("utility_bill")
    public List<String> utilityBill = null;

    @SerializedName("pin")
    public List<String> pinCodeError = null;

    @SerializedName("active_loan")
    public List<String> activeLoan = null;

    @SerializedName("previouse_loan")
    public List<String> previouseLoan = null;

    @SerializedName("marital_status")
    public List<String> maritalStatus = null;
}
