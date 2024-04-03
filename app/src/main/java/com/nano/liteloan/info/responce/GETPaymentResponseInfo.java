package com.nano.liteloan.info.responce;

import com.nano.liteloan.info.PaymentInfo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Muhammad Ahmad on 12/12/2020.
 */
public class GETPaymentResponseInfo {

    @SerializedName("Payments")
    public List<PaymentInfo> paymentInfoList;
}
