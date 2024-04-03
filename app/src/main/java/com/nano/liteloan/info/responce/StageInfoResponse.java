package com.nano.liteloan.info.responce;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Ahmad on 07/04/2020.
 */
public class StageInfoResponse {

    @SerializedName("meta")
    public MetaObject metaObject;

    @SerializedName("data")
    public StageInfo stageInfoList;
}
