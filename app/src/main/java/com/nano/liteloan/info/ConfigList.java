package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Muhammad Ahmad on 07/09/2020.
 */
public class ConfigList {


    @SerializedName("verification_type")
    public List<ListItemInfo> verificationType = null;

    @SerializedName("profession")
    public List<ListItemInfo> profession = null;

    @SerializedName("gender")
    public List<ListItemInfo> gender = null;
}
