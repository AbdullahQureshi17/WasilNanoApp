package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class GetApplicationInfo implements Serializable {


    @SerializedName("id")
    public Integer id;

    @SerializedName("user_id")
    public  Integer userId;

    @SerializedName("application-date")
    public  String applicationDate;

    @SerializedName("category_id")
    public  Integer categoryId;

    @SerializedName("category-name")
    public String categoryName;

    @SerializedName("product-id")
    private Integer productId;

    @SerializedName("product-name")
    public  String productName;

    @SerializedName("requested amount")
    public  String requestedAmount;

    @SerializedName("disbursement_amount")
    public  Integer disbursementAmount;

    @SerializedName("disbursement_date")
    public  String disbursementDate;

    @SerializedName("status")
    public  String status;

    @SerializedName("loan_status")
    public  String loanstatus;

    @SerializedName("deleted")
    public  Integer deleted;

    @SerializedName("outstanding")
    public  Integer outstanding;

    public Integer getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(Integer outstanding) {
        this.outstanding = outstanding;
    }

    public String getLoanstatus() {
        return loanstatus;
    }

    public void setLoanstatus(String loanstatus) {
        this.loanstatus = loanstatus;
    }

    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(String requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Integer getDisbursementAmount() {
        return disbursementAmount;
    }

    public void setDisbursementAmount(Integer disbursementAmount) {
        this.disbursementAmount = disbursementAmount;
    }

    public String getDisbursementDate() {
        return disbursementDate;
    }

    public void setDisbursementDate(String disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

}
