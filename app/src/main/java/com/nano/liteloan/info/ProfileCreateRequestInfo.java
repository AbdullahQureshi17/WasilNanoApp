package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class ProfileCreateRequestInfo {

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getActiveLoan() {
        return activeLoan;
    }

    public void setActiveLoan(String activeLoan) {
        this.activeLoan = activeLoan;
    }

    public String getPreviouseLoan() {
        return previouseLoan;
    }

    public void setPreviouseLoan(String previouseLoan) {
        this.previouseLoan = previouseLoan;
    }

    public String getEarnigSourse() {
        return earnigSourse;
    }

    public void setEarnigSourse(String earnigSourse) {
        this.earnigSourse = earnigSourse;
    }

    public String getMartialStatus() {
        return martialStatus;
    }

    public void setMartialStatus(String martialStatus) {
        this.martialStatus = martialStatus;
    }

    public String getVisitAbroad() {
        return visitAbroad;
    }

    public void setVisitAbroad(String visitAbroad) {
        this.visitAbroad = visitAbroad;
    }

    public String getVisitPurpose() {
        return visitPurpose;
    }

    public void setVisitPurpose(String visitPurpose) {
        this.visitPurpose = visitPurpose;
    }

    @SerializedName("stage")
    public String stage;

    @SerializedName("phone")
    public String phoneNumber;

    @SerializedName("name")
    private String name;

    @SerializedName("cnic")
    private String cnic;

    @SerializedName("cnic_issue_date")
    public String issueDate;

    @SerializedName("cnic_expiry_date")
    public String expiryDate;

    @SerializedName("image")
    private String image;

    @SerializedName("parentage")
    private String parentage;

    public String getAccounttitle() {
        return accounttitle;
    }

    public void setAccounttitle(String accounttitle) {
        this.accounttitle = accounttitle;
    }

    @SerializedName("account_title")
    private String accounttitle;


    @SerializedName("dob")
    private String dob;

    @SerializedName("verified_at")
    public String verifyAt;

    @SerializedName("gender")
    private String gender;

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    @SerializedName("organization_name")
    private String organizationName;


    @SerializedName("profession")
    private String profession;

//    @SerializedName("income")
//    private String incomefrom;


    @SerializedName("income_to")
    private String incometo;


    @SerializedName("dependent")
    private String dependents;

    @SerializedName("duration_of_stay")
    private String durationofstay;


    public String getParmentaddress() {
        return parmentaddress;
    }

    public void setParmentaddress(String parmentaddress) {
        this.parmentaddress = parmentaddress;
    }

    @SerializedName("permanent_address")
    private String parmentaddress;

    @SerializedName("current_address")
    public String currentAddress;

    @SerializedName("city")
    private int city;

    @SerializedName("latitude")
    private String latitude;


    @SerializedName("longitude")
    private String longitude;


    @SerializedName("bank_id")
    private int bankid;


    @SerializedName("account_no")
    private String accountnumber;

    @SerializedName("cnic_front")
    private String cnicfront;


    @SerializedName("cnic_back")
    private String cnicback;

    @SerializedName("utility_bill")
    private String utilitybill;


    public String getfIncome() {
        return fIncome;
    }

    public void setfIncome(String fIncome) {
        this.fIncome = fIncome;
    }

    @SerializedName("income")
    private String salarySlip;

    @SerializedName("future_expected_income")
    public String fIncome;

    @SerializedName("other")
    private String other;

    @SerializedName("is_update")
    private int isUpdate;

    @SerializedName("email")
    public String email;

    @SerializedName("pin")
    public String pinCode;

    @SerializedName("user_id")
    public String borrowerId;

    @SerializedName("active_loan")
    public String activeLoan;

    public int getLoanamount() {
        return loanamount;
    }

    public void setLoanamount(int loanamount) {
        this.loanamount = loanamount;
    }

    public String getLoanbank() {
        return loanbank;
    }

    public void setLoanbank(String loanbank) {
        this.loanbank = loanbank;
    }

    @SerializedName("loan_amount")
    public int loanamount;

    @SerializedName("loan_source")
    public String loanbank;

    @SerializedName("previouse_loan")
    public String previouseLoan;

    @SerializedName("source_of_earning")
    public String earnigSourse;

    @SerializedName("marital_status")
    public String martialStatus;

    @SerializedName("visit_abroad")
    public String visitAbroad;

    @SerializedName("visit_purpose")
    public String visitPurpose;

    @SerializedName("branch_id")
    public int branchId;

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalarySlip() {
        return salarySlip;
    }

    public void setSalarySlip(String salarySlip) {
        this.salarySlip = salarySlip;
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


    public String getDependents() {
        return dependents;
    }

    public void setDependents(String dependents) {
        this.dependents = dependents;
    }

    public String getDurationofstay() {
        return durationofstay;
    }

    public void setDurationofstay(String durationofstay) {
        this.durationofstay = durationofstay;
    }


    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public String getCnicfront() {
        return cnicfront;
    }

    public void setCnicfront(String cnicfront) {
        this.cnicfront = cnicfront;
    }

    public String getCnicback() {
        return cnicback;
    }

    public void setCnicback(String cnicback) {
        this.cnicback = cnicback;
    }

    public String getUtilitybill() {
        return utilitybill;
    }

    public void setUtilitybill(String utilitybill) {
        this.utilitybill = utilitybill;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

//    public String getIncomefrom() {
//        return incomefrom;
//    }
//
//    public void setIncomefrom(String incomefrom) {
//        this.incomefrom = incomefrom;
//    }

    public String getIncometo() {
        return incometo;
    }

    public void setIncometo(String incometo) {
        this.incometo = incometo;
    }

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

    public int getBankid() {
        return bankid;
    }

    public void setBankid(int bankid) {
        this.bankid = bankid;
    }
}
