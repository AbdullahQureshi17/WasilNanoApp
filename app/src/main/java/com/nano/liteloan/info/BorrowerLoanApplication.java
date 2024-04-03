package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;
/**
 * Created by Muhammad Umer on 09/07/2021.
 */
public class BorrowerLoanApplication {


    @SerializedName("loan_id")
    public int loanid;

    @SerializedName("borrower_id")
    public int borrowerid;

    @SerializedName("category_id")
    public int categoryid;

    @SerializedName("product_id")
    public int productid;

    @SerializedName("loan_purpose")
    public String loanpurpose;

    @SerializedName("account_type")
    public String accounttype;

    @SerializedName("loan_amount")
    public int loanamount;

    @SerializedName("fee")
    public int fee;

    @SerializedName("due_date")
    public String duedate;

    @SerializedName("affidavit")
    public String affidavit;
}
