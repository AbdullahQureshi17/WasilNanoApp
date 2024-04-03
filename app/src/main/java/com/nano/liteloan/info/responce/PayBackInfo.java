package com.nano.liteloan.info.responce;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Ahmad on 02/11/2021.
 */
public class PayBackInfo {

    @SerializedName("title")
    public String title;

    @SerializedName("due_date")
    public String dueDate;

    @SerializedName("days")
    public String days;

    @SerializedName("status")
    public Integer status;
}
