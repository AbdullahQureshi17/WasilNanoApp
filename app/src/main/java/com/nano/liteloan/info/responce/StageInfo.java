package com.nano.liteloan.info.responce;

import com.google.gson.annotations.SerializedName;
import com.nano.liteloan.info.businesscorrespondant.ApplicationFee;

import java.util.List;

/**
 * Created by Muhammad Ahmad on 07/04/2020.
 */
public class StageInfo {


    @SerializedName("application_fee")
    public VerificationInfo feeVerifyInfo;

    @SerializedName("application_status")
    public VerificationInfo applicationVerifyInfo;

    @SerializedName("bank_account")
    public VerificationInfo bankVerifyInfo;

    @SerializedName("recovery")
    public VerificationInfo recoveryVerifyInfo;

    @SerializedName("stages")
    public List<StageDetailInfo> stageInfoList;

    @SerializedName("approved_amount")
    public String approvedAmount;

    @SerializedName("requested_amount")
    public String requestedAmount;

    @SerializedName("pay_back")
    public PayBackInfo payBackInfo;

    @SerializedName("application")
    public ApplicationFee fee;






}
