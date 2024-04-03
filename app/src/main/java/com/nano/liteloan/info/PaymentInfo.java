package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Ahmad on 12/12/2020.
 */
public class PaymentInfo {

    @SerializedName("amount")
    public String amount;

    @SerializedName("transaction id")
    public String transactionId;

    @SerializedName("receive-date")
    public String date;

    @SerializedName("type")
    public String paymentType;
}
