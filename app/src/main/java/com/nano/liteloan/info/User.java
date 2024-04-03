package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;
/**
 * Created by Muhammad Umer on 07/06/2020.
 * Modified by Muhammad Ahmad
 */
public class User {


    @SerializedName("name")
    public String name;

    @SerializedName("category-name")
    public String categoryName;

    @SerializedName("amount")
    public String amount;

    @SerializedName("disbursement_amount")
    public String disbursementAmount;

    @SerializedName("disbursement_date")
    public String disbursementDate;

    @SerializedName("status")
    public String status;

    @SerializedName("image")
    public String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDisbursementAmount() {
        return disbursementAmount;
    }

    public void setDisbursementAmount(String disbursementAmount) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
