package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Umer on 12/01/2021.
 */
public class CallLogs {
    @SerializedName("phone_number")
    private String phonenumber;

    @SerializedName("call_type")
    private String calltype;

    @SerializedName("call_time")
    private String calltime;

    public CallLogs(String phonenumber, String calltype, String calltime, int callduration) {
        this.phonenumber = phonenumber;
        this.calltype = calltype;
        this.calltime = calltime;
        this.callduration = callduration;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCalltype() {
        return calltype;
    }

    public void setCalltype(String calltype) {
        this.calltype = calltype;
    }

    public String getCalltime() {
        return calltime;
    }

    public void setCalltime(String calltime) {
        this.calltime = calltime;
    }

    public int getCallduration() {
        return callduration;
    }

    public void setCallduration(int callduration) {
        this.callduration = callduration;
    }

    @SerializedName("call_duration")
    private int callduration;
}

