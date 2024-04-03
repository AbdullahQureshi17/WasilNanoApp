package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class ConfigurationRespondObject {

    @SerializedName("categories")
    public List<LoanCategoryInfo> loanCategories;

    @SerializedName("products")
    public List<ProductInfo> productInfoList;

    @SerializedName("banks")
    public List<BankInfo> bankInfoList;

    @SerializedName("cities")
    public List<CityInfo> cityInfoList;

    @SerializedName("list")
    public ConfigList configList;

}
