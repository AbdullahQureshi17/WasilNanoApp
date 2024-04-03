package com.nano.liteloan.info.responce;

import com.google.gson.annotations.SerializedName;
/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class MetaObject {

    @SerializedName("status")
    private String status;

    @SerializedName("code")
    private Integer code;

    @SerializedName("message")
    private String message;

    public ErrorObject getErrorObject() {
        return errorObject;
    }

    public void setErrorObject(ErrorObject errorObject) {
        this.errorObject = errorObject;
    }

    @SerializedName("error")
    private ErrorObject errorObject;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
