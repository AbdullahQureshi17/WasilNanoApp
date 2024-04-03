package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Umer on 09/10/2021.
 */
public class RecoveryBorrowerPayRequest {
    @SerializedName("application_id")
    public String id;

//    @SerializedName("installment_no")
//    public String installmentno;


    @SerializedName("submitted_date")
    private String date;

    @SerializedName("another_loan")
    private String anotherLoan;


    @SerializedName("loan_use_for")
    private String loanUseFor;

    @SerializedName("household_phone")
    private String householdPhone;

    @SerializedName("transfer_id")
    private String transferId;

    public String getAnotherLoan() {
        return anotherLoan;
    }

    public void setAnotherLoan(String anotherLoan) {
        this.anotherLoan = anotherLoan;
    }

    public String getLoanUseFor() {
        return loanUseFor;
    }

    public void setLoanUseFor(String loanUseFor) {
        this.loanUseFor = loanUseFor;
    }

    public String getHouseholdPhone() {
        return householdPhone;
    }

    public void setHouseholdPhone(String householdPhone) {
        this.householdPhone = householdPhone;
    }

    @SerializedName("receipt_image")
    private String image;

    @SerializedName("amount")
    private String amount;

    @SerializedName("payment_type")
    private String paymentType;



    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public int getBorrowerid() {
        return borrowerid;
    }

    public void setBorrowerid(int borrowerid) {
        this.borrowerid = borrowerid;
    }

    @SerializedName("borrower_id")
    private int borrowerid;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
