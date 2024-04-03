package com.nano.liteloan.business.impl;

import com.google.gson.Gson;
import com.nano.liteloan.business.AuthService;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.AuthRequestInfo;
import com.nano.liteloan.info.AuthenticationResponseInfo;
import com.nano.liteloan.info.ForgotPasswordRequestInfo;
import com.nano.liteloan.info.ForgotPasswordResponseInfo;
import com.nano.liteloan.info.OTPRequestInfo;
import com.nano.liteloan.info.OTPResponse;
import com.nano.liteloan.info.RegisterDeviceRequest;
import com.nano.liteloan.info.RegisterDeviceResponse;
import com.nano.liteloan.info.RegisterPushIdRequest;
import com.nano.liteloan.info.SocailLoginRequestInfo;
import com.nano.liteloan.info.SocialLoginResponse;
import com.nano.liteloan.info.responce.ResponceObject;
import com.nano.liteloan.network.ApiClient;
import com.nano.liteloan.utils.AppConstantsUtils;
import com.nano.liteloan.utils.PreferenceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Muhammad Umer on 07/06/2020.
 * Mod by Muhammad Ahmad
 */
public class AuthBusiness {

    private AuthService authService;

    public AuthBusiness() {

        authService = ApiClient.getApiClient(PreferenceUtils.getInstance().getIPAddress()
                + AppConstantsUtils.BASE_URL).create(AuthService.class);
    }

    public void socialLogin(final SocailLoginRequestInfo requestInfo,
                            final ResponseCallBack<SocialLoginResponse> responseCallBack) {

        String json = new Gson().toJson(requestInfo);

        Call<ResponceObject<SocialLoginResponse>> call = authService.socialLoginResponse(requestInfo);

        call.enqueue(new Callback<ResponceObject<SocialLoginResponse>>() {
            @Override
            public void onResponse(Call<ResponceObject<SocialLoginResponse>> call,
                                   Response<ResponceObject<SocialLoginResponse>> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getMeta() != null
                            && response.body().getMeta().getCode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMeta() != null) {

                        responseCallBack.onFailure(response.body().getMeta().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponceObject<SocialLoginResponse>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }


    public void register(final AuthRequestInfo requestInfo,
                         final ResponseCallBack<AuthenticationResponseInfo> responseCallBack) {

        String json = new Gson().toJson(requestInfo);

        Call<ResponceObject<AuthenticationResponseInfo>> call = authService.register(requestInfo);

        call.enqueue(new Callback<ResponceObject<AuthenticationResponseInfo>>() {
            @Override
            public void onResponse(Call<ResponceObject<AuthenticationResponseInfo>> call,
                                   Response<ResponceObject<AuthenticationResponseInfo>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getMeta() != null
                            && response.body().getMeta().getCode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMeta() != null) {

                        responseCallBack.onFailure(response.body().getMeta().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponceObject<AuthenticationResponseInfo>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }

        });
    }

//    public void login(final LoginRequestInfo requestInfo,
//                         final ResponseCallBack<AuthenticationResponseInfo> responseCallBack) {
//        String json = new Gson().toJson(requestInfo);
//        Call<ResponceObject<AuthenticationResponseInfo>> call = authService.login(requestInfo);
//
//        call.enqueue(new Callback<ResponceObject<AuthenticationResponseInfo>>() {
//            @Override
//            public void onResponse(Call<ResponceObject<AuthenticationResponseInfo>> call,
//                                   Response<ResponceObject<AuthenticationResponseInfo>> response) {
//
//                if (response.isSuccessful()){
//                    if (response.body() != null
//                    && response.body().getMeta() != null
//                    && response.body().getMeta().getCode() == 200){
//                        responseCallBack.onSuccess(response.body().getData());
//                    } else if (response.body() != null
//                    && response.body().getMeta() != null){
//                        responseCallBack.onFailure(response.body().getMeta().getMessage());
//                    }
//
//                } else {
//                    responseCallBack.onFailure(response.message());
//
//
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponceObject<AuthenticationResponseInfo>> call, Throwable t) {
//                responseCallBack.onFailure(t.getMessage());
//            }
//        });
//
//
//    }

    public void otpAuth(final OTPRequestInfo requestInfo,
                        final ResponseCallBack<OTPResponse> responseCallBack) {
        String json = new Gson().toJson(requestInfo);
        Call<ResponceObject<OTPResponse>> call = authService.getOTP(requestInfo);

        call.enqueue(new Callback<ResponceObject<OTPResponse>>() {
            @Override
            public void onResponse(Call<ResponceObject<OTPResponse>> call,
                                   Response<ResponceObject<OTPResponse>> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getMeta() != null
                            && response.body().getMeta().getCode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMeta() != null) {
                        responseCallBack.onFailure(response.body().getMeta().getMessage());
                    }

                } else {
                    responseCallBack.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponceObject<OTPResponse>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });


    }

    public void forgotPassword(final ForgotPasswordRequestInfo requestInfo,
                      final ResponseCallBack<ForgotPasswordResponseInfo> responseCallBack) {
        String json = new Gson().toJson(requestInfo);
        Call<ResponceObject<ForgotPasswordResponseInfo>> call = authService.forgotPassword(requestInfo);

        call.enqueue(new Callback<ResponceObject<ForgotPasswordResponseInfo>>() {
            @Override
            public void onResponse(Call<ResponceObject<ForgotPasswordResponseInfo>> call,
                                   Response<ResponceObject<ForgotPasswordResponseInfo>> response) {

                if (response.isSuccessful()){
                    if (response.body() != null
                            && response.body().getMeta() != null
                            && response.body().getMeta().getCode() == 200){
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMeta() != null){
                        responseCallBack.onFailure(response.body().getMeta().getMessage());
                    }

                } else {
                    responseCallBack.onFailure(response.message());



                }

            }

            @Override
            public void onFailure(Call<ResponceObject<ForgotPasswordResponseInfo>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }


        });


    }

    public void registerDevice(RegisterDeviceRequest registerDeviceRequest,
                               final ResponseCallBack<RegisterDeviceResponse> responseCallBack){

        String json = new Gson().toJson(registerDeviceRequest);

        authService.registerDevice(registerDeviceRequest)
                .enqueue(new Callback<ResponceObject<RegisterDeviceResponse>>() {
            @Override
            public void onResponse(Call<ResponceObject<RegisterDeviceResponse>> call,
                                   Response<ResponceObject<RegisterDeviceResponse>> response) {
                if (response.isSuccessful()){
                    if (response.body() != null
                            && response.body().getMeta() != null
                            && response.body().getMeta().getCode() == 200){
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMeta() != null){
                        responseCallBack.onFailure(response.body().getMeta().getMessage());
                    }

                } else {
                    responseCallBack.onFailure(response.message());



                }
            }

            @Override
            public void onFailure(Call<ResponceObject<RegisterDeviceResponse>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }

    public void registerPushId(RegisterPushIdRequest registerDeviceRequest,
                               final ResponseCallBack<RegisterDeviceResponse> responseCallBack){

        String json = new Gson().toJson(registerDeviceRequest);

        authService.registerDevicePushId(registerDeviceRequest)
                .enqueue(new Callback<ResponceObject<RegisterDeviceResponse>>() {
                    @Override
                    public void onResponse(Call<ResponceObject<RegisterDeviceResponse>> call,
                                           Response<ResponceObject<RegisterDeviceResponse>> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null
                                    && response.body().getMeta() != null
                                    && response.body().getMeta().getCode() == 200){
                                responseCallBack.onSuccess(response.body().getData());
                            } else if (response.body() != null
                                    && response.body().getMeta() != null){
                                responseCallBack.onFailure(response.body().getMeta().getMessage());
                            }

                        } else {
                            responseCallBack.onFailure(response.message());



                        }
                    }

                    @Override
                    public void onFailure(Call<ResponceObject<RegisterDeviceResponse>> call, Throwable t) {
                        responseCallBack.onFailure(t.getMessage());
                    }
                });
    }

}


