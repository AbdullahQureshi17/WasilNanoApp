package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Muhammad Ahmad on 07/06/2020.
 */
public class LoanInfoResponse implements Serializable {
    @SerializedName("loan_amount")
    public String loanAmount;

    @SerializedName("loan_purpose")
    public String loanPurpose;


    @SerializedName("due_date")
    public String dueDate;

    @SerializedName("loan_id")
    public int loanid;


    @SerializedName("borrower_id")
    public int borrowerid;


    @SerializedName("status")
    public String status;

    public int getLoanid() {
        return loanid;
    }

    public void setLoanid(int loanid) {
        this.loanid = loanid;
    }

    public int getBorrowerid() {
        return borrowerid;
    }

    public void setBorrowerid(int borrowerid) {
        this.borrowerid = borrowerid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
