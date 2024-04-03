package com.nano.liteloan.info.businesscorrespondant;

import com.google.gson.annotations.SerializedName;
import com.nano.liteloan.info.BorrowerList;

import java.util.List;

public class GetDataResponce {

    @SerializedName("my_borrower")
    public OverdueLoans potentialborrower;

    @SerializedName("active_loans")
    public ActiveLoans activeloans;

    @SerializedName("overdue_loans")
    public OverdueLoans overdueloans;

    @SerializedName("list")
    public List<MyApplicants> myApplicants;


    @SerializedName("pending_verifications")
    public PendingVerifications pendingverifications;

    @SerializedName("ready_to_disburse")
    public VerifiedList verifiedlist;

}
