package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Muhammad Umer on 04/028/2020.
 */
public class GetProductResponce {
    @SerializedName("categories")
    public List<LoanCategoryInfo> loanCategories;
}
