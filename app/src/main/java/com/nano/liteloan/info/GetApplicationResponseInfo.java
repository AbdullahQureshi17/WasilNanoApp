package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class GetApplicationResponseInfo {


    @SerializedName("Application")
    public List<GetApplicationInfo> getApplication;

}

