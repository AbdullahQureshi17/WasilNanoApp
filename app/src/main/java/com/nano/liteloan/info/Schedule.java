package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Muhammad Umer on 07/06/2020.
 * Modified by Muhammad Ahmad
 */
public class Schedule implements Serializable {


    @SerializedName("recovery amount")
    public String recoveryAmount;

    @SerializedName("status")
    public String status;

    @SerializedName("paid-date")
    public String paidDate;

    public String getRecoveryAmount() {
        return recoveryAmount;
    }

    public void setRecoveryAmount(String recoveryAmount) {
        this.recoveryAmount = recoveryAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }
}
