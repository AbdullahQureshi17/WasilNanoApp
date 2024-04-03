package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class RecievedApplicationRequestInfo {

    @SerializedName("category_id")
    private String categoryid;

    @SerializedName("product_id")
    private String productid;

    @SerializedName("amount")
    private String amount;
}
