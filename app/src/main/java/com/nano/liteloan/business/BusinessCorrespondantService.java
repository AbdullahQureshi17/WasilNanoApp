package com.nano.liteloan.business;

import com.nano.liteloan.info.OTPResponse;
import com.nano.liteloan.info.OfflineData;
import com.nano.liteloan.info.OfflineResponce;
import com.nano.liteloan.info.RecoveryPayRequest;
import com.nano.liteloan.info.businesscorrespondant.AddBorrower;
import com.nano.liteloan.info.businesscorrespondant.AddBorrowerResponce;
import com.nano.liteloan.info.businesscorrespondant.AppVerificationRequest;
import com.nano.liteloan.info.businesscorrespondant.ApplicationVerificationResponce;
import com.nano.liteloan.info.businesscorrespondant.CorrespondantDashboardObj;
import com.nano.liteloan.info.businesscorrespondant.CorrespondantLoginAsRequest;
import com.nano.liteloan.info.businesscorrespondant.EligibiltyCriteria;
import com.nano.liteloan.info.businesscorrespondant.GetDataResponce;
import com.nano.liteloan.info.responce.ApplicationDetailRespose;
import com.nano.liteloan.info.responce.RecoveryPayResponse;
import com.nano.liteloan.info.responce.ResponceObject;
import com.nano.liteloan.utils.AppConstantsUtils;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BusinessCorrespondantService {


    @GET(AppConstantsUtils.FETCH_ELIGIBILITY)
    Call<ResponceObject<EligibiltyCriteria>> getEligibility();

    @GET(AppConstantsUtils.GET_DASHBOARD)
    Call<ResponceObject<CorrespondantDashboardObj>> getDashboard();

    @POST(AppConstantsUtils.ADD_BORROWER)
    Call<ResponceObject<AddBorrowerResponce>> addBorrower(@Body AddBorrower addBorrower);

    @POST(AppConstantsUtils.APPLICATION_VERIFICATION)
    Call<ResponceObject<ApplicationVerificationResponce>> applicationVerification(@Body AppVerificationRequest addBorrower);

    @GET(AppConstantsUtils.GETDATA)
    Call<ResponceObject<GetDataResponce>> getData(@Query("list_name") String value);

    @GET("api/getApplicationById/{id}")
    Call<ResponceObject<ApplicationDetailRespose>> getApplicationDetail(@Path("id") String id);

    @POST(AppConstantsUtils.LOGIN_AS)
    Call<ResponceObject<OTPResponse>> loginAsUser(@Body CorrespondantLoginAsRequest request);

    @POST(AppConstantsUtils.GET_RECOVERY)
    Call<ResponceObject<RecoveryPayResponse>> payRecovery(@Body RecoveryPayRequest request);

    @POST(AppConstantsUtils.POST_RESPONSE)
    Call<ResponceObject<RecoveryPayResponse>> postResponse(@Body PostResponseRequest request);

    @POST(AppConstantsUtils.OFFLINE_BORROWER)
    Call<ResponceObject<OfflineResponce>> offlineBorrower(@Body OfflineData offlineData);

}
