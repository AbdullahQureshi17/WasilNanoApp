package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Umer on 09/07/2021.
 */
public class LoanInfoObject {

    @SerializedName("loan_amount")
    public int loanamount;

    @SerializedName("loan_porpose")
    public String loanporpose;

    @SerializedName("due_date")
    public String duedate;

    @SerializedName("acount_type")
    public String acounttype;

    public int getLoanamount() {
        return loanamount;
    }

    public void setLoanamount(int loanamount) {
        this.loanamount = loanamount;
    }

    public String getLoanporpose() {
        return loanporpose;
    }

    public void setLoanporpose(String loanporpose) {
        this.loanporpose = loanporpose;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getAcounttype() {
        return acounttype;
    }

    public void setAcounttype(String acounttype) {
        this.acounttype = acounttype;
    }
}
