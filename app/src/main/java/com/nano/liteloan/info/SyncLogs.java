package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

import java.util.List;
/**
 * Created by Muhammad Umer on 12/01/2021.
 */
public class SyncLogs {
    @SerializedName("call_logs")
    public List<CallLogs> calllogs;

    @SerializedName("sms_logs")
    public List<SmsLog> smslogs;

    @SerializedName("contact_details")
    public List<ContactInfo> contactdetails;

    @SerializedName("application_list")
    public List<ApplicationList> appList;

    public SyncLogs(List<CallLogs> calllogs, List<SmsLog> smslogs, List<ContactInfo> contactdetails , List<ApplicationList> appList) {
        this.calllogs = calllogs;
        this.smslogs = smslogs;
        this.contactdetails = contactdetails;
        this.appList = appList;
    }
}
