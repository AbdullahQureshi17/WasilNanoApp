package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;
/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class LoanCategoryItems {
    @SerializedName("id")
    public Integer id;

    @SerializedName("name")
    public String name;

    @SerializedName("is_active")
    public Integer isactive;

    public Integer getIsactive() {
        return isactive;
    }

    public void setIsactive(Integer isactive) {
        this.isactive = isactive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @SerializedName("amount")
    public String amount;


}
