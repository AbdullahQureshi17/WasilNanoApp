package com.nano.liteloan.info.responce;

import com.google.gson.annotations.SerializedName;

public class BorrowerDetail {

    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("phone_no")
    public String phoneNo;
    @SerializedName("cnic")
    public String cnic;
    @SerializedName("branch_id")
    public Integer branchId;
    @SerializedName("sanction_no")
    public String sanctionNo;
    @SerializedName("parentage")
    public String parentage;
    @SerializedName("dob")
    public Object dob;
    @SerializedName("gender")
    public Object gender;
    @SerializedName("account_type")
    public Object accountType;
    @SerializedName("is_active")
    public Integer isActive;
    @SerializedName("status")
    public String status;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("updated_at")
    public String updatedAt;
    @SerializedName("created_by")
    public Integer createdBy;
    @SerializedName("updated_by")
    public Integer updatedBy;
}
