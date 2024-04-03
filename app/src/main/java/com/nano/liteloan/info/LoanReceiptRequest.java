package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Umer on 09/07/2021.
 */
public class LoanReceiptRequest {

//    @SerializedName("borrower_id")
//    public int borrowerid;
//
//    @SerializedName("loan_id")
//    public int loanid;


    @SerializedName("type")
    public String type;


    @SerializedName("disbursement_image")
    public String disbursementImage;

    @SerializedName("disbursement_amount")
    public String disbursementAmount;

    @SerializedName("loan_purpose")
    public String loanpurpose;




    public String getDisbursementAmount() {
        return disbursementAmount;
    }

    public void setDisbursementAmount(String disbursementAmount) {
        this.disbursementAmount = disbursementAmount;
    }

//    public int getBorrowerid() {
//        return borrowerid;
//    }
//
//    public void setBorrowerid(int borrowerid) {
//        this.borrowerid = borrowerid;
//    }
//
//    public int getLoanid() {
//        return loanid;
//    }
//
//    public void setLoanid(int loanid) {
//        this.loanid = loanid;
//    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDisbursementImage() {
        return disbursementImage;
    }

    public void setDisbursementImage(String disbursementImage) {
        this.disbursementImage = disbursementImage;
    }

    @SerializedName("branch_id")
    public int branchid;

    public int getBranchid() {
        return branchid;
    }

    public void setBranchid(int branchid) {
        this.branchid = branchid;
    }

    @SerializedName("name")
    public String name;

    @SerializedName("correnpondent_id")
    public String correnpondentid;

    @SerializedName("parentage")
    public String parentage;

    @SerializedName("cnic")
    public String cnic;

    @SerializedName("have_cell_phone")
    public int havecellphone;

//    @SerializedName("loan_info")
//    public LoanInfoObject loaninfo;


    @SerializedName("fee")
    public int fee;

    @SerializedName("affidavit")
    public String affidavit;

    @SerializedName("loan_amount")
    public int loanamount;

    @SerializedName("product_id")
    public String productId;

    @SerializedName("category_id")
    public int categoryId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }



    @SerializedName("due_date")
    public String duedate;

    @SerializedName("account_type")
    public String acounttype;

    @SerializedName("phone_no")
    public String phoneno;



    public String getLoanpurpose() {
        return loanpurpose;
    }

    public void setLoanpurpose(String loanpurpose) {
        this.loanpurpose = loanpurpose;
    }

    public String getAffidavit() {
        return affidavit;
    }

    public void setAffidavit(String affidavit) {
        this.affidavit = affidavit;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public int getLoanamount() {
        return loanamount;
    }

    public void setLoanamount(int loanamount) {
        this.loanamount = loanamount;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCorrenpondentid() {
        return correnpondentid;
    }

    public void setCorrenpondentid(String correnpondentid) {
        this.correnpondentid = correnpondentid;
    }

    public String getParentage() {
        return parentage;
    }

    public void setParentage(String parentage) {
        this.parentage = parentage;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public int getHavecellphone() {
        return havecellphone;
    }

    public void setHavecellphone(int havecellphone) {
        this.havecellphone = havecellphone;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getAffidavitImage() {
        return affidavit;
    }

    public void setAffidavitImage(String affidavit) {
        this.affidavit = affidavit;
    }
}
