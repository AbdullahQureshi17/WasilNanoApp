package com.nano.liteloan.info.businesscorrespondant;

import com.google.gson.annotations.SerializedName;

public class CorrespondantDashboardObj {
    @SerializedName("my_borrower")
    public int potentialborrower;

    @SerializedName("amount_disbursed")
    public String amountdisbursed;

    @SerializedName("active_loans")
    public int activeloans;

    @SerializedName("ready_to_disburse")
    public int readytodisburse;

    @SerializedName("ready_to_disburse_amount")
    public String readyToDisbursedAmount;


    @SerializedName("outstanding_amount")
    public int outstandingamount;

    @SerializedName("overdue_loans")
    public int overdueloans;

    @SerializedName("pending_verifications")
    public int pendingverifications;

    @SerializedName("pending")
    public int pendingApplicationCount;

    @SerializedName("pending_amount")
    public int pendingApplicationAmount;

    @SerializedName("in_process")
    public int myapplicants;

    @SerializedName("potential_borrower")
    public int potentialCount;


    @SerializedName("ourdue_amount")
    public int ourdueamount;

    @SerializedName("interested_applicants")
    public int interestedApplicants;


}

