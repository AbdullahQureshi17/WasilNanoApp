package com.nano.liteloan.business;

import com.nano.liteloan.info.AuthRequestInfo;
import com.nano.liteloan.info.AuthenticationResponseInfo;
import com.nano.liteloan.info.ForgotPasswordRequestInfo;
import com.nano.liteloan.info.ForgotPasswordResponseInfo;
import com.nano.liteloan.info.LoginRequestInfo;
import com.nano.liteloan.info.OTPRequestInfo;
import com.nano.liteloan.info.OTPResponse;
import com.nano.liteloan.info.RegisterDeviceRequest;
import com.nano.liteloan.info.RegisterDeviceResponse;
import com.nano.liteloan.info.RegisterPushIdRequest;
import com.nano.liteloan.info.SocailLoginRequestInfo;
import com.nano.liteloan.info.SocialLoginResponse;
import com.nano.liteloan.info.responce.ResponceObject;
import com.nano.liteloan.utils.AppConstantsUtils;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static com.nano.liteloan.utils.AppConstantsUtils.SOCIAL_LOGIN;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public interface AuthService {

    @POST(AppConstantsUtils.REGISTER)
    Call<ResponceObject<AuthenticationResponseInfo>> register(@Body AuthRequestInfo authRequestInfo);

    @POST(AppConstantsUtils.LOGIN)
    Call<ResponceObject<AuthenticationResponseInfo>> login(@Body LoginRequestInfo loginRequestInfo);

    @POST(SOCIAL_LOGIN)
    Call<ResponceObject<SocialLoginResponse>> socialLoginResponse(@Body SocailLoginRequestInfo requestInfo);

    @POST(AppConstantsUtils.FORGOT_PASSWORD)
    Call<ResponceObject<ForgotPasswordResponseInfo>> forgotPassword(@Body ForgotPasswordRequestInfo requestInfo);

    @POST(AppConstantsUtils.OTP)
    Call<ResponceObject<OTPResponse>> getOTP(@Body OTPRequestInfo requestInfo);

    @POST(AppConstantsUtils.REGISTER_DEVICE)
    Call<ResponceObject<RegisterDeviceResponse>> registerDevice(@Body RegisterDeviceRequest request);


    @POST(AppConstantsUtils.REGISTER_PUSH_ID)
    Call<ResponceObject<RegisterDeviceResponse>> registerDevicePushId(@Body RegisterPushIdRequest request);
}
