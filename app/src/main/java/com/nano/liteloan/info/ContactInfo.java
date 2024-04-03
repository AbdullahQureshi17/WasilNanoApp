package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Umer on 12/01/2021.
 */
public class ContactInfo {
    @SerializedName("phone_number")
    private String phonenumber;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ContactInfo(String phonenumber, String name, String type) {
        this.phonenumber = phonenumber;
        this.name = name;
        this.type = type;
    }
}
