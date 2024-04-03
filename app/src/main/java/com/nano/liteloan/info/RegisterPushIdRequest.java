package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Ahmad.
 */
public class RegisterPushIdRequest {

    @SerializedName("push_id")
    public String pushId;

    @SerializedName("device_id")
    public String deviceId;
}
