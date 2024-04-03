package com.nano.liteloan.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Muhammad Ahmad on 07/08/2020.
 */
public class MainApplication extends Application {

    private static MainApplication instance;


    public static MainApplication getAppContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }
}
