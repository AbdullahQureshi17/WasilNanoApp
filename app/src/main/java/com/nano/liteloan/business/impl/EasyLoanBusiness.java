package com.nano.liteloan.business.impl;

import com.google.gson.Gson;
import com.nano.liteloan.business.EasyLoanService;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.ApplyAgain;
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
import com.nano.liteloan.info.SearchBorrowerRequest;
import com.nano.liteloan.info.SurveyAnswerResponce;
import com.nano.liteloan.info.SurveyQuestions;
import com.nano.liteloan.info.SurveysRequest;
import com.nano.liteloan.info.SyncCallResponce;
import com.nano.liteloan.info.SyncLogs;
import com.nano.liteloan.info.businesscorrespondant.PotentialBorrowerRequest;
import com.nano.liteloan.info.responce.GETPaymentResponseInfo;
import com.nano.liteloan.info.responce.RecoveryPayResponse;
import com.nano.liteloan.info.responce.ResponceObject;
import com.nano.liteloan.network.ApiClient;
import com.nano.liteloan.utils.AppConstantsUtils;
import com.nano.liteloan.utils.PreferenceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class EasyLoanBusiness {

    private EasyLoanService loanService;

    public EasyLoanBusiness() {

        loanService = ApiClient.getApiClient(PreferenceUtils.getInstance().getIPAddress()
                + AppConstantsUtils.BASE_URL).create(EasyLoanService.class);
    }

    public void updateLocation(Location loc, final ResponseCallBack<LocationUpdate> responseCallBack) {

        String json = new Gson().toJson(loc);


        Call<ResponceObject<LocationUpdate>> call = loanService.updateLocation(loc);

        call.enqueue(new Callback<ResponceObject<LocationUpdate>>() {
            @Override
            public void onResponse(Call<ResponceObject<LocationUpdate>> call,
                                   Response<ResponceObject<LocationUpdate>> response) {

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
            public void onFailure(Call<ResponceObject<LocationUpdate>> call, Throwable t) {

                responseCallBack.onFailure(t.getMessage());
            }
        });

    }


    public void syncLogs(SyncLogs log, final ResponseCallBack<SyncCallResponce> responseCallBack) {

        String json = new Gson().toJson(log);

        Call<ResponceObject<SyncCallResponce>> call = loanService.syncLogs(log);

        call.enqueue(new Callback<ResponceObject<SyncCallResponce>>() {
            @Override
            public void onResponse(Call<ResponceObject<SyncCallResponce>> call,
                                   Response<ResponceObject<SyncCallResponce>> response) {

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
            public void onFailure(Call<ResponceObject<SyncCallResponce>> call, Throwable t) {

                responseCallBack.onFailure(t.getMessage());
            }
        });

    }


    public void getConfiguration(final ResponseCallBack<ConfigurationRespondObject> responseCallBack) {


        Call<ResponceObject<ConfigurationRespondObject>> call = loanService.getConfiguration();

        call.enqueue(new Callback<ResponceObject<ConfigurationRespondObject>>() {
            @Override
            public void onResponse(Call<ResponceObject<ConfigurationRespondObject>> call,
                                   Response<ResponceObject<ConfigurationRespondObject>> response) {

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
            public void onFailure(Call<ResponceObject<ConfigurationRespondObject>> call, Throwable t) {

                responseCallBack.onFailure(t.getMessage());
            }
        });

    }

    public void getLoanCategories(final ResponseCallBack<ConfigurationRespondObject> responseCallBack) {


        Call<ResponceObject<ConfigurationRespondObject>> call = loanService.getLoanCategories();

        call.enqueue(new Callback<ResponceObject<ConfigurationRespondObject>>() {
            @Override
            public void onResponse(Call<ResponceObject<ConfigurationRespondObject>> call,
                                   Response<ResponceObject<ConfigurationRespondObject>> response) {

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
            public void onFailure(Call<ResponceObject<ConfigurationRespondObject>> call, Throwable t) {

                responseCallBack.onFailure(t.getMessage());
            }
        });

    }

    public void applyForLoan(LoanApplyRequest request,
                             final ResponseCallBack<LoanApplyResponse> responseCallBack) {
        String json = new Gson().toJson(request);

        String jsona = "ASdad";
        loanService.applyLoan(request).enqueue(new Callback<ResponceObject<LoanApplyResponse>>() {
            @Override
            public void onResponse(Call<ResponceObject<LoanApplyResponse>> call,
                                   Response<ResponceObject<LoanApplyResponse>> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getMeta() != null
                            && response.body().getMeta().getCode() == 200) {

                        LoanApplyResponse loanApplyResponse = response.body().getData();
                        loanApplyResponse.message = response.body().getMeta().getMessage();
                        responseCallBack.onSuccess(loanApplyResponse);

                    } else if (response.body() != null
                            && response.body().getMeta() != null) {

                        if (response.body().getMeta().getMessage().equalsIgnoreCase("error occured Application is in process")) {

                            responseCallBack.onFailure(response.body().getMeta().getMessage());

                        } else {
                            responseCallBack.onFailure(response.body().getMeta().getMessage());
                        }

                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponceObject<LoanApplyResponse>> call, Throwable t) {

                responseCallBack.onFailure(t.getMessage());
            }
        });
    }

    public void payRecovery(RecoveryPayRequest request,
                            final ResponseCallBack<RecoveryPayResponse> responseCallBack) {

        String json = new Gson().toJson(request);

        loanService.payRecovery(request).enqueue(new Callback<ResponceObject<RecoveryPayResponse>>() {
            @Override
            public void onResponse(Call<ResponceObject<RecoveryPayResponse>> call,
                                   Response<ResponceObject<RecoveryPayResponse>> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getMeta() != null
                            && response.body().getMeta().getCode() == 200) {

                        RecoveryPayResponse loanApplyResponse = new RecoveryPayResponse();
                        //  loanApplyResponse.message = response.body().getMeta().getMessage();
                        responseCallBack.onSuccess(loanApplyResponse);

                    } else if (response.body() != null
                            && response.body().getMeta() != null) {

                        responseCallBack.onFailure(response.body().getMeta().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponceObject<RecoveryPayResponse>> call, Throwable t) {

                responseCallBack.onFailure(t.getMessage());
            }
        });
    }


    public void getApplication(final ResponseCallBack<GetApplicationResponseInfo> responseCallBack) {


        Call<ResponceObject<GetApplicationResponseInfo>> call = loanService.getApplication();

        call.enqueue(new Callback<ResponceObject<GetApplicationResponseInfo>>() {
            @Override
            public void onResponse(Call<ResponceObject<GetApplicationResponseInfo>> call,
                                   Response<ResponceObject<GetApplicationResponseInfo>> response) {
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
            public void onFailure(Call<ResponceObject<GetApplicationResponseInfo>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }


        });
    }

    public void getPayments(final ResponseCallBack<GETPaymentResponseInfo> responseCallBack) {


        Call<ResponceObject<GETPaymentResponseInfo>> call = loanService.getPayments();

        call.enqueue(new Callback<ResponceObject<GETPaymentResponseInfo>>() {
            @Override
            public void onResponse(Call<ResponceObject<GETPaymentResponseInfo>> call,
                                   Response<ResponceObject<GETPaymentResponseInfo>> response) {
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
            public void onFailure(Call<ResponceObject<GETPaymentResponseInfo>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }


        });
    }


    public void getSchedule(GetScheduleRequest request,
                            final ResponseCallBack<GetScheduleResponse> responseCallBack) {
        String json = new Gson().toJson(request);

        Call<ResponceObject<GetScheduleResponse>> call = loanService.getSchedule(request);

        call.enqueue(new Callback<ResponceObject<GetScheduleResponse>>() {
            @Override
            public void onResponse(Call<ResponceObject<GetScheduleResponse>> call,
                                   Response<ResponceObject<GetScheduleResponse>> response) {
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
            public void onFailure(Call<ResponceObject<GetScheduleResponse>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }


        });
    }


    public void applyAgain(final ResponseCallBack<ApplyAgain> responseCallBack) {


        Call<ResponceObject<ApplyAgain>> call = loanService.applyagain();

        call.enqueue(new Callback<ResponceObject<ApplyAgain>>() {
            @Override
            public void onResponse(Call<ResponceObject<ApplyAgain>> call,
                                   Response<ResponceObject<ApplyAgain>> response) {

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
            public void onFailure(Call<ResponceObject<ApplyAgain>> call, Throwable t) {

                responseCallBack.onFailure(t.getMessage());
            }
        });

    }


    public void getProducts(final ResponseCallBack<GetProductResponce> responseCallBack) {


        Call<ResponceObject<GetProductResponce>> call = loanService.getProducts();

        call.enqueue(new Callback<ResponceObject<GetProductResponce>>() {
            @Override
            public void onResponse(Call<ResponceObject<GetProductResponce>> call,
                                   Response<ResponceObject<GetProductResponce>> response) {
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
            public void onFailure(Call<ResponceObject<GetProductResponce>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }


        });
    }


    public void getSurvey(final ResponseCallBack<SurveyQuestions> responseCallBack) {


        Call<ResponceObject<SurveyQuestions>> call = loanService.getSurvey();

        call.enqueue(new Callback<ResponceObject<SurveyQuestions>>() {
            @Override
            public void onResponse(Call<ResponceObject<SurveyQuestions>> call,
                                   Response<ResponceObject<SurveyQuestions>> response) {
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
            public void onFailure(Call<ResponceObject<SurveyQuestions>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }


        });
    }


    public void postSurvey(SurveysRequest answer, final ResponseCallBack<SurveyAnswerResponce> responseCallBack) {

        String json = new Gson().toJson(answer);
        Call<ResponceObject<SurveyAnswerResponce>> call = loanService.postSurvey(answer);

        call.enqueue(new Callback<ResponceObject<SurveyAnswerResponce>>() {
            @Override
            public void onResponse(Call<ResponceObject<SurveyAnswerResponce>> call,
                                   Response<ResponceObject<SurveyAnswerResponce>> response) {
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
            public void onFailure(Call<ResponceObject<SurveyAnswerResponce>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }


        });
    }

    public void aboutUs(final ResponseCallBack<PagesList> responseCallBack) {


        Call<ResponceObject<PagesList>> call = loanService.aboutus();

        call.enqueue(new Callback<ResponceObject<PagesList>>() {
            @Override
            public void onResponse(Call<ResponceObject<PagesList>> call,
                                   Response<ResponceObject<PagesList>> response) {
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
            public void onFailure(Call<ResponceObject<PagesList>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }


        });
    }


    public void searchBorrower(SearchBorrowerRequest request, final ResponseCallBack<GetBorrowerList> responseCallBack) {

        String json = new Gson().toJson(request);
        Call<ResponceObject<GetBorrowerList>> call = loanService.search(request.getCnic());

        call.enqueue(new Callback<ResponceObject<GetBorrowerList>>() {
            @Override
            public void onResponse(Call<ResponceObject<GetBorrowerList>> call,
                                   Response<ResponceObject<GetBorrowerList>> response) {
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
            public void onFailure(Call<ResponceObject<GetBorrowerList>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }


        });
    }


    public void loanInfo(LoanInfoRequest request, final ResponseCallBack<LoanInfo> responseCallBack) {

        String json = new Gson().toJson(request);
        Call<ResponceObject<LoanInfo>> call = loanService.loanInfo(request);

        call.enqueue(new Callback<ResponceObject<LoanInfo>>() {
            @Override
            public void onResponse(Call<ResponceObject<LoanInfo>> call,
                                   Response<ResponceObject<LoanInfo>> response) {
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
            public void onFailure(Call<ResponceObject<LoanInfo>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }


        });
    }

    public void loanReceipt(LoanReceiptRequest request, final ResponseCallBack<LoanReceipt> responseCallBack) {

        String json = new Gson().toJson(request);
        Call<ResponceObject<LoanReceipt>> call = loanService.loanReceipt(request);

        call.enqueue(new Callback<ResponceObject<LoanReceipt>>() {
            @Override
            public void onResponse(Call<ResponceObject<LoanReceipt>> call,
                                   Response<ResponceObject<LoanReceipt>> response) {
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
            public void onFailure(Call<ResponceObject<LoanReceipt>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }


        });
    }


    public void getBorrowerList(PotentialBorrowerRequest request, final ResponseCallBack<GetBorrowerList> responseCallBack) {

        String json = new Gson().toJson(request);
        Call<ResponceObject<GetBorrowerList>> call = loanService.getBorrower(request.parameter, request.search, request.type);

        call.enqueue(new Callback<ResponceObject<GetBorrowerList>>() {
            @Override
            public void onResponse(Call<ResponceObject<GetBorrowerList>> call,
                                   Response<ResponceObject<GetBorrowerList>> response) {
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
            public void onFailure(Call<ResponceObject<GetBorrowerList>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }


        });
    }


    public void getBorrowerSchedule(int request,
                                    final ResponseCallBack<GetBorrowerScheduleResponse> responseCallBack) {
        String json = new Gson().toJson(request);

        Call<ResponceObject<GetBorrowerScheduleResponse>> call = loanService.getBorrowerSchedule(request);

        call.enqueue(new Callback<ResponceObject<GetBorrowerScheduleResponse>>() {
            @Override
            public void onResponse(Call<ResponceObject<GetBorrowerScheduleResponse>> call,
                                   Response<ResponceObject<GetBorrowerScheduleResponse>> response) {
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
            public void onFailure(Call<ResponceObject<GetBorrowerScheduleResponse>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }


        });
    }


    public void payBorrowerRecovery(RecoveryBorrowerPayRequest request,
                                    final ResponseCallBack<RecoveryPayResponse> responseCallBack) {

        String json = new Gson().toJson(request);

        loanService.payBorrowerRecovery(request).enqueue(new Callback<ResponceObject<RecoveryPayBorrowerResponse>>() {
            @Override
            public void onResponse(Call<ResponceObject<RecoveryPayBorrowerResponse>> call,
                                   Response<ResponceObject<RecoveryPayBorrowerResponse>> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getMeta() != null
                            && response.body().getMeta().getCode() == 200) {

                        RecoveryPayResponse loanApplyResponse = new RecoveryPayResponse();
                        //  loanApplyResponse.message = response.body().getMeta().getMessage();
                        responseCallBack.onSuccess(loanApplyResponse);

                    } else if (response.body() != null
                            && response.body().getMeta() != null) {

                        responseCallBack.onFailure(response.body().getMeta().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponceObject<RecoveryPayBorrowerResponse>> call, Throwable t) {

                responseCallBack.onFailure(t.getMessage());
            }
        });
    }

    public void branchesDetail(final ResponseCallBack<BranchesList> responseCallBack) {


        loanService.getBranchesDetail().enqueue(new Callback<ResponceObject<BranchesList>>() {
            @Override
            public void onResponse(Call<ResponceObject<BranchesList>> call,
                                   Response<ResponceObject<BranchesList>> response) {

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
            public void onFailure(Call<ResponceObject<BranchesList>> call, Throwable t) {

                responseCallBack.onFailure(t.getMessage());
            }
        });
    }


}
