package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Muhammad Umer on 09/07/2021.
 */
public class LoanInfoRequest implements Serializable {
    @SerializedName("name")
    public String name;

    @SerializedName("correnpondent_id")
    public String correnpondentid;

    @SerializedName("parentage")
    public String parentage;

    @SerializedName("branch_id")
    public int branchid;

    @SerializedName("cnic")
    public String cnic;

    @SerializedName("have_cell_phone")
    public int havecellphone;

//    @SerializedName("loan_info")
//    public LoanInfoObject loaninfo;


    @SerializedName("fee")
    public int fee;

    @SerializedName("affidavit")
    public String disbursementImage;

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

    @SerializedName("loan_purpose")
    public String loanporpose;

    @SerializedName("due_date")
    public String duedate;

    @SerializedName("account_type")
    public String acounttype;

    @SerializedName("phone_no")
    public String phoneno;

    public int getBranchid() {
        return branchid;
    }

    public void setBranchid(int branchid) {
        this.branchid = branchid;
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

    public String getDisbursementImage() {
        return disbursementImage;
    }

    public void setDisbursementImage(String disbursementImage) {
        this.disbursementImage = disbursementImage;
    }
}
