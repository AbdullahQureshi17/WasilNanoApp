package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;
/**
 * Created by Muhammad Umer on 07/06/2020.
 * Modified by Muhammad Ahmad
 */
public class GetScheduleRequest {

    public Integer getApplicationid() {
        return applicationid;
    }

    public void setApplicationid(Integer applicationid) {
        this.applicationid = applicationid;
    }

    @SerializedName("application_id")
    public Integer applicationid;

}
