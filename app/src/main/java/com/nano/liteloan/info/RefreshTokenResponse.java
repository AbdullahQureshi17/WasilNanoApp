package com.nano.liteloan.info;

import com.nano.liteloan.info.responce.MetaObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Ahmad on 07/08/2020.
 */
public class RefreshTokenResponse {

    @SerializedName("meta")
    public MetaObject metaObject;


    @SerializedName("data")
    public RefreshDate refreshDate;
}
