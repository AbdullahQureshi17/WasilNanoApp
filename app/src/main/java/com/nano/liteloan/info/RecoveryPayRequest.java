package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Ahmad on 11/11/2020.
 */
public class RecoveryPayRequest {


    @SerializedName("application_id")
    public String id;

//    @SerializedName("installment_no")
//    public String installmentno;




    @SerializedName("submitted_date")
    private String date;

    @SerializedName("transfer_id")
    private String transferId;

    @SerializedName("receipt_image")
    private String image;

    @SerializedName("amount")
    private String amount;

    @SerializedName("phoneno")
    private String walletno;

    public String getWalletno() {
        return walletno;
    }

    public void setWalletno(String walletno) {
        this.walletno = walletno;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
