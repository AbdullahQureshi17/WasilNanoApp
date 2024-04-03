package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

/**
* Created by Muhammad Ahmad.
*/
public class RegisterDeviceRequest {

    @SerializedName("uu_id")
    public String uuid;

    @SerializedName("iemei_no")
    public String iemeiNumber;

    @SerializedName("os_version")
    public String osVersion;

    @SerializedName("device_model")
    public String deviceModel;
}
