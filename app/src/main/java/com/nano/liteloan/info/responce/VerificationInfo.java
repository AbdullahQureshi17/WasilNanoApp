package com.nano.liteloan.info.responce;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Muhammad Ahmad on 11/20/2020.
 */
public class VerificationInfo {

    @SerializedName("title")
    public String title;

    @SerializedName("reason")
    public List<String> reasonList;

    @SerializedName("status")
    public Integer status;
}
