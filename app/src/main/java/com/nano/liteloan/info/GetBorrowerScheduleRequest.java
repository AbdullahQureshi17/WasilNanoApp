package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;
/**
 * Created by Muhammad Umer on 09/10/2021.
 */
public class GetBorrowerScheduleRequest {
    public Integer getApplicationid() {
        return applicationid;
    }

    public void setApplicationid(Integer applicationid) {
        this.applicationid = applicationid;
    }

    @SerializedName("application_id")
    public Integer applicationid;

    @SerializedName("borrower_id")
    public Integer borrowerid;

    public Integer getLoanid() {
        return borrowerid;
    }

    public void setLoanid(Integer loanid) {
        this.borrowerid = loanid;
    }
}
