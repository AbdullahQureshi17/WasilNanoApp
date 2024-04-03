package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class UserProfile {



    public UserProfile() {

    }

    @SerializedName("account_title")
    private String accounttitle;


    @SerializedName("bank_id")
    private int bankid;

    public String getAccounttitle() {
        return accounttitle;
    }

    public void setAccounttitle(String accounttitle) {
        this.accounttitle = accounttitle;
    }

    public int getBankid() {
        return bankid;
    }

    public void setBankid(int bankid) {
        this.bankid = bankid;
    }

    @SerializedName("name")
    public String name;

    @SerializedName("branchid")
    public String branchId;

    @SerializedName("branch_name")
    public String branchname;



    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getSurveyFlag() {
        return surveyFlag;
    }

    public void setSurveyFlag(String surveyFlag) {
        this.surveyFlag = surveyFlag;
    }

    @SerializedName("survey_flag")
    public String surveyFlag;

    public String getOutbalance() {
        return outbalance;
    }

    public void setOutbalance(String outbalance) {
        this.outbalance = outbalance;
    }

    @SerializedName("outstanding_balance")
    public String outbalance;


    @SerializedName("fee_category")
    public String loanid;

    @SerializedName("user_type")
    private int usertype;

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    @SerializedName("user_id")
    public String userId;

    @SerializedName("is_enable")
    public int isenable;

    @SerializedName("is_editable")
    public int iseditable;

    public int isIseditable() {
        return iseditable;
    }

    public void setIseditable(int iseditable) {
        this.iseditable = iseditable;
    }

    @SerializedName("email")
    public String email;

    public int getSynclog() {
        return synclog;
    }

    public void setSynclog(int synclog) {
        this.synclog = synclog;
    }

    @SerializedName("sync_logs")
    public int synclog;

    @SerializedName("phone")
    public String phone;

    @SerializedName("login_type")
    public String loginType;

    @SerializedName("cnic")
    public String cnic;

    @SerializedName("cnic_issue_date")
    public String issueDate;

    @SerializedName("cnic_expiry_date")
    public String expiryDate;

    @SerializedName("image")
    public String image;

    @SerializedName("parentage")
    public String parentage;

    @SerializedName("dob")
    public String dob;

    @SerializedName("gender")
    public String gender;

    @SerializedName("profession")
    public String profession;

    @SerializedName("monthly_income")
    public String monthlyIncome;

    @SerializedName("dependents")
    public String dependents;

    @SerializedName("duration_of_stay")
    public String durationOfStay;

    @SerializedName("permanent_address")
    public String parmentAddress;

    @SerializedName("current_address")
    public String currentAddress;

    @SerializedName("city")
    public String city;


    @SerializedName("account_number")
    public String accountNumber;

    @SerializedName("cnic_front")
    public String cnicFront;

    @SerializedName("cnic_back")
    public String cnicBack;

    @SerializedName("utility_bill")
    public String utilityBill;

    @SerializedName("other")
    public String other;

    @SerializedName("pin")
    public String pin;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @SerializedName("latitude")
    public String latitude;

    @SerializedName("longitude")
    public String longitude;

    @SerializedName("score")
    private String score;

    @SerializedName("active_loan")
    public String activeLoan;

    @SerializedName("previous_loan")
    public String previousLoan;

    @SerializedName("marital_status")
    public String maritalStatus;

    @SerializedName("source_of_earning")
    public String sourceOfEarning;

    @SerializedName("visit_abroad")
    public String visitAbroad;

    @SerializedName("visit_purpose")
    public String visitPurpose;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getParentage() {
        return parentage;
    }

    public void setParentage(String parentage) {
        this.parentage = parentage;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(String monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getDependents() {
        return dependents;
    }

    public void setDependents(String dependents) {
        this.dependents = dependents;
    }

    public String getDurationOfStay() {
        return durationOfStay;
    }

    public void setDurationOfStay(String durationOfStay) {
        this.durationOfStay = durationOfStay;
    }

    public String getParmentAddress() {
        return parmentAddress;
    }

    public void setParmentAddress(String parmentAddress) {
        this.parmentAddress = parmentAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCnicFront() {
        return cnicFront;
    }

    public void setCnicFront(String cnicFront) {
        this.cnicFront = cnicFront;
    }

    public String getCnicBack() {
        return cnicBack;
    }

    public void setCnicBack(String cnicBack) {
        this.cnicBack = cnicBack;
    }

    public String getUtilityBill() {
        return utilityBill;
    }

    public void setUtilityBill(String utilityBill) {
        this.utilityBill = utilityBill;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
