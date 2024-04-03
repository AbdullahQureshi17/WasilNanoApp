package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;
/**
 * Created by Muhammad Umer on 12/01/2021.
 */
public class SmsLog {
    @SerializedName("phone_number")
    private String phonenumber;

    @SerializedName("sms_body")
    private String smsbody;

    public SmsLog(String phonenumber, String smsbody, String smstime) {
        this.phonenumber = phonenumber;
        this.smsbody = smsbody;
        this.smstime = smstime;
    }

    public SmsLog() {
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSmsbody() {
        return smsbody;
    }

    public void setSmsbody(String smsbody) {
        this.smsbody = smsbody;
    }

    public String getSmstime() {
        return smstime;
    }

    public void setSmstime(String smstime) {
        this.smstime = smstime;
    }

    @SerializedName("sms_time")
    private String smstime;
}
