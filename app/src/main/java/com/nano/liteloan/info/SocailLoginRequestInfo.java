package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class SocailLoginRequestInfo {

    @SerializedName("name")
    private String name;

    @SerializedName("account_id")
    private String accountid;

    @SerializedName("email")
    private String email;

    @SerializedName("login_by")
    private String loginby;

    @SerializedName("image")
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginby() {
        return loginby;
    }

    public void setLoginby(String loginby) {
        this.loginby = loginby;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
