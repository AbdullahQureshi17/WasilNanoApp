package com.nano.liteloan.business;

import com.nano.liteloan.info.ApplyAgain;
import com.nano.liteloan.info.AuthenticationResponseInfo;
import com.nano.liteloan.info.BranchesDetail;
import com.nano.liteloan.info.BranchesList;
import com.nano.liteloan.info.ConfigurationRespondObject;
import com.nano.liteloan.info.GetApplicationResponseInfo;
import com.nano.liteloan.info.GetBorrowerList;
import com.nano.liteloan.info.GetBorrowerScheduleResponse;
import com.nano.liteloan.info.GetProductResponce;
import com.nano.liteloan.info.GetScheduleRequest;
import com.nano.liteloan.info.GetScheduleResponse;
import com.nano.liteloan.info.LoanApplyRequest;
import com.nano.liteloan.info.LoanApplyResponse;
import com.nano.liteloan.info.LoanInfo;
import com.nano.liteloan.info.LoanInfoRequest;
import com.nano.liteloan.info.LoanReceipt;
import com.nano.liteloan.info.LoanReceiptRequest;
import com.nano.liteloan.info.Location;
import com.nano.liteloan.info.LocationUpdate;
import com.nano.liteloan.info.PagesList;
import com.nano.liteloan.info.RecoveryBorrowerPayRequest;
import com.nano.liteloan.info.RecoveryPayBorrowerResponse;
import com.nano.liteloan.info.RecoveryPayRequest;
import com.nano.liteloan.info.SearchBorrower;
import com.nano.liteloan.info.SocailLoginRequestInfo;
import com.nano.liteloan.info.SurveyAnswerResponce;
import com.nano.liteloan.info.SurveyQuestions;
import com.nano.liteloan.info.SurveysRequest;
import com.nano.liteloan.info.SyncCallResponce;
import com.nano.liteloan.info.SyncLogs;
import com.nano.liteloan.info.businesscorrespondant.PotentialBorrower;
import com.nano.liteloan.info.businesscorrespondant.PotentialBorrowerRequest;
import com.nano.liteloan.info.responce.GETPaymentResponseInfo;
import com.nano.liteloan.info.responce.RecoveryPayResponse;
import com.nano.liteloan.info.responce.ResponceObject;
import com.nano.liteloan.utils.AppConstantsUtils;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public interface EasyLoanService {

    @POST(AppConstantsUtils.SOCIAL_LOGIN)
    Call<ResponceObject<AuthenticationResponseInfo>> socaillogin(@Body SocailLoginRequestInfo socialLoginRequestInfo);


    @GET(AppConstantsUtils.FETCH_CONFIGURATION)
    Call<ResponceObject<ConfigurationRespondObject>> getConfiguration();

    @GET(AppConstantsUtils.FETCH_LOAN_CATEGORIES)
    Call<ResponceObject<ConfigurationRespondObject>> getLoanCategories();

    @POST(AppConstantsUtils.LOAN_APPLY)
    Call<ResponceObject<LoanApplyResponse>> applyLoan(@Body LoanApplyRequest request);

    @POST(AppConstantsUtils.UPDATE_LOCATION)
    Call<ResponceObject<LocationUpdate>> updateLocation(@Body Location location);

    @POST(AppConstantsUtils.SYNC_LOGS)
    Call<ResponceObject<SyncCallResponce>> syncLogs(@Body SyncLogs log);

    @POST(AppConstantsUtils.APPLY_AGAIN)
    Call<ResponceObject<ApplyAgain>> applyagain();

    @GET(AppConstantsUtils.GET_APPLICATION)
    Call<ResponceObject<GetApplicationResponseInfo>> getApplication();

    @POST(AppConstantsUtils.GET_SCHEDULE)
    Call<ResponceObject<GetScheduleResponse>> getSchedule(@Body GetScheduleRequest request);

    @POST(AppConstantsUtils.RECOVERY_PAY)
    Call<ResponceObject<RecoveryPayResponse>> payRecovery(@Body RecoveryPayRequest request);

    @GET(AppConstantsUtils.GET_PAYMENT)
    Call<ResponceObject<GETPaymentResponseInfo>> getPayments();


    @GET(AppConstantsUtils.GET_PRODUCTS)
    Call<ResponceObject<GetProductResponce>> getProducts();

    @GET(AppConstantsUtils.GET_SURVEY)
    Call<ResponceObject<SurveyQuestions>> getSurvey();

    @POST(AppConstantsUtils.POST_SURVEY)
    Call<ResponceObject<SurveyAnswerResponce>> postSurvey(@Body SurveysRequest answer);

    @GET(AppConstantsUtils.ABOUT_US)
    Call<ResponceObject<PagesList>> aboutus();

    @GET(AppConstantsUtils.SEARCH)
    Call<ResponceObject<GetBorrowerList>> search(@Query("cnic")String cnic);

    @POST(AppConstantsUtils.LOAN_INFO)
    Call<ResponceObject<LoanInfo>> loanInfo(@Body LoanInfoRequest answer);


    @POST(AppConstantsUtils.LOAN_RECEIPT)
    Call<ResponceObject<LoanReceipt>> loanReceipt(@Body LoanReceiptRequest answer);


    @GET(AppConstantsUtils.GET_BORROWER)
    Call<ResponceObject<GetBorrowerList>> getBorrower(@Query("parameter") String parameter ,
                                                      @Query("search") String search ,
                                                      @Query("type") String type);

    @GET(AppConstantsUtils.GET_BORROWER_SCHEDULE)
    Call<ResponceObject<GetBorrowerScheduleResponse>> getBorrowerSchedule(@Query("application_id") int borrowerid);

    @POST(AppConstantsUtils.PAY_BORROWER_RECOVERY)
    Call<ResponceObject<RecoveryPayBorrowerResponse>> payBorrowerRecovery(@Body RecoveryBorrowerPayRequest request);

    @POST(AppConstantsUtils.BRANCH_DETAIL)
    Call<ResponceObject<BranchesList>> getBranchesDetail();


}
