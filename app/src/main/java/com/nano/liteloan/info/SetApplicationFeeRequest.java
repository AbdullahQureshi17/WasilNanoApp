package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;
/**
 * Created by Muhammad Umer on 11/6/2020.
 */
public class SetApplicationFeeRequest {
    @SerializedName("application_fee")
    private String imageURL;

    @SerializedName("fee")
    private String fee;

    @SerializedName("receive_date")
    private String receivedate;

    public String getReceivedate() {
        return receivedate;
    }

    public void setReceivedate(String receivedate) {
        this.receivedate = receivedate;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    @SerializedName("txn_id")
    private String txnid;

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    @SerializedName("stage")
    private int stage;

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

//    public String getSubmitteddate() {
//        return submitteddate;
//    }
//
//    public void setSubmitteddate(String submitteddate) {
//        this.submitteddate = submitteddate;
//    }
//
//    @SerializedName("submitted_date")
//    private String submitteddate;

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @SerializedName("category_id")
    private String categoryId;

    @SerializedName("wallet_no")
    private String walletno;

    public String getWalletno() {
        return walletno;
    }

    public void setWalletno(String walletno) {
        this.walletno = walletno;
    }
}
