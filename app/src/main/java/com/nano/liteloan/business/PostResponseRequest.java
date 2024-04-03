package com.nano.liteloan.business;

import com.google.gson.annotations.SerializedName;

public class PostResponseRequest {

    @SerializedName("response")
    private String response;

    @SerializedName("borrower_id")
    private int user_d;

    @SerializedName("phone")
    private String phone;



    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getUser_d() {
        return user_d;
    }

    public void setUser_d(int user_d) {
        this.user_d = user_d;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
