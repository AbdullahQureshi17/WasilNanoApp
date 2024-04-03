package com.nano.liteloan.info.responce;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * Created by Muhammad Umer on 07/06/2020.
 * @param <T> This is the type parameter
 */
public class ResponceObject<T> implements Serializable {

    @SerializedName("meta")
    private MetaObject meta;

    @SerializedName("data")
    private T data = null;

    public MetaObject getMeta() {

        return meta;
    }

    public T getData() {

        return data;
    }


}
