package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Muhammad Umer on 09/07/2021.
 */
public class SearchBorrower implements Serializable {

    @SerializedName("user")
    public Borrower user;

    @SerializedName("loan_info")
    public List<LoanInfoResponse> loaninfo;

}
