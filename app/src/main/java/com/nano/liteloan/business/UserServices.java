package com.nano.liteloan.business;

import com.nano.liteloan.info.ApplicationfeeResponse;
import com.nano.liteloan.info.ForgetPinRequest;
import com.nano.liteloan.info.PinSetResponse;
import com.nano.liteloan.info.ProfileCreateRequestInfo;
import com.nano.liteloan.info.ProfileCreateResponseInfo;
import com.nano.liteloan.info.SetApplicationFeeRequest;
import com.nano.liteloan.info.responce.ResponceObject;
import com.nano.liteloan.info.responce.StageInfoResponse;
import com.nano.liteloan.utils.AppConstantsUtils;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Muhammad Ahmad on 07/06/2020.
 */
public interface UserServices {


    @GET("api/getProfile")
    Call<ResponceObject<ProfileCreateResponseInfo>> getUserProfile();

    @POST(AppConstantsUtils.PROFILE_CREATE)
    Call<ResponceObject<ProfileCreateResponseInfo>> profilecreate(@Body ProfileCreateRequestInfo profileCreateRequestInfo);

    @POST(AppConstantsUtils.SET_PROFILE_SET)
    Call<ResponceObject<ProfileCreateResponseInfo>> setProfile(@Body ProfileCreateRequestInfo profileCreateRequestInfo);

    @GET(AppConstantsUtils.GET_STAGE_INFO)
    Call<StageInfoResponse> getStageInfo();

    @POST(AppConstantsUtils.SET_PIN_CODE)
    Call<ResponceObject<PinSetResponse>> setPinCode(@Body RequestBody requestBody);

    @POST(AppConstantsUtils.FORGET_PIN_CODE)
    Call<ResponceObject<PinSetResponse>> forgetPinCode(@Body ForgetPinRequest request);

    @POST(AppConstantsUtils.SET_APPLICATION_FEE)
    Call<ResponceObject<ApplicationfeeResponse>> setApplicationfee(@Body SetApplicationFeeRequest request);
}
