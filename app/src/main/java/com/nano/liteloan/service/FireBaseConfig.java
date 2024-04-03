package com.nano.liteloan.service;

import android.app.Activity;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.nano.liteloan.BuildConfig;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.User;
import com.nano.liteloan.utils.PreferenceUtils;

public class FireBaseConfig {

//    private static FireBaseConfig instance;
//
//    private FireBaseConfig(){
//
//    }
//
//    public static FireBaseConfig getInstance(){
//        if (instance == null) {
//            instance = new  FireBaseConfig();
//        }
//        return instance;
//
//
//    }

    public static void getApplicationBaseURL(Activity activity,
                                             ResponseCallBack<User> responseCallBack) {

        try {
            FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
            FirebaseRemoteConfigSettings settings = new FirebaseRemoteConfigSettings
                    .Builder()
                    .setMinimumFetchIntervalInSeconds(0)
                    .build();

            remoteConfig.setConfigSettingsAsync(settings);

            remoteConfig.fetchAndActivate()
                    .addOnCompleteListener(activity, task -> {

                        saveBaseUrl(remoteConfig, responseCallBack);

                    });
        } catch (Exception e) {

            responseCallBack.onFailure(e.getMessage());
        }


    }

    private static void saveBaseUrl(FirebaseRemoteConfig remoteConfig,
                                    ResponseCallBack<User> responseCallBack) {

        try {
            String updateUrl = remoteConfig.getString("nano_base_url");
            boolean isUpdate = remoteConfig.getBoolean("is_update_required");
            PreferenceUtils.getInstance().setBaseUrl(updateUrl);

            responseCallBack.onSuccess(new User());
        } catch (Exception e){

            responseCallBack.onFailure(e.getMessage());
        }


    }
}
