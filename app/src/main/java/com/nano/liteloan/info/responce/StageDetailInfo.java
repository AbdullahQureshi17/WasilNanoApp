package com.nano.liteloan.info.responce;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Ahmad on 11/20/2020.
 */
public class StageDetailInfo {

    @SerializedName("id")
    public Integer id;

    @SerializedName("title")
    public String profile;

    @SerializedName("stage_id")
    public Integer stageNumber;

    @SerializedName("status")
    public Integer status;

    @SerializedName("score")
    public Integer score;
}
