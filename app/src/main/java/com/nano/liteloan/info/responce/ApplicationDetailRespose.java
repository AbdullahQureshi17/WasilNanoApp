package com.nano.liteloan.info.responce;

import com.google.gson.annotations.SerializedName;
import com.nano.liteloan.info.UserDetail;
import com.nano.liteloan.info.UserProfileDetail;

public class ApplicationDetailRespose {


    @SerializedName("application")
    public ApplicationDetial application;
    @SerializedName("user")
    public UserDetail user;
    @SerializedName("user_profile")
    public UserProfileDetail userProfile;
    @SerializedName("borrower")
    public BorrowerDetail borrower;
    @SerializedName("user_account")
    public UserAccountDetail userAcountDetail;
}
