package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;
/**
 * Created by Muhammad Umer on 5/17/2020.
 */
public class ApplicationList {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("app_name")
    private String name;
}
