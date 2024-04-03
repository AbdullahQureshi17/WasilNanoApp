package com.nano.liteloan.delegate;


/**
 * Created by Muhammad Ahmad on 5/4/2019.
 * @param <T>
 */
public interface ResponseCallBack<T> {

    public void onSuccess(T body);
    void onFailure(String message);
}
