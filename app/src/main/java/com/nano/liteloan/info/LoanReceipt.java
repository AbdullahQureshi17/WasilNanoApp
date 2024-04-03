package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Umer on 09/07/2021.
 */
public class LoanReceipt {

    @SerializedName("application")
    public BorrowerApplicationResponse application;
}
