package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Ahmad on 07/09/2020.
 */
public class ListItemInfo {

    @SerializedName("list_name")
    public String listName;

    @SerializedName("value")
    public String value;

    @SerializedName("label")
    public String label;
}
