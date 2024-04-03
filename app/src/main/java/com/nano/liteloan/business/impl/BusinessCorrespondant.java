package com.nano.liteloan.business.impl;

import com.google.gson.Gson;
import com.nano.liteloan.business.BusinessCorrespondantService;
import com.nano.liteloan.business.PostResponseRequest;
import com.nano.liteloan.delegate.ResponseCallBack;
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
import com.nano.liteloan.network.ApiClient;
import com.nano.liteloan.utils.AppConstantsUtils;
import com.nano.liteloan.utils.PreferenceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessCorrespondant {
    private BusinessCorrespondantService loanService;

    public BusinessCorrespondant() {

        loanService = ApiClient.getApiClient(PreferenceUtils.getInstance().getIPAddress()
                + AppConstantsUtils.BASE_URL).create(BusinessCorrespondantService.class);
    }

    public void getEligibility(final ResponseCallBack<EligibiltyCriteria> responseCallBack) {

//        String json = new Gson().toJson(loc);


        Call<ResponceObject<EligibiltyCriteria>> call = loanService.getEligibility();

        call.enqueue(new Callback<ResponceObject<EligibiltyCriteria>>() {
            @Override
            public void onResponse(Call<ResponceObject<EligibiltyCriteria>> call,
                                   Response<ResponceObject<EligibiltyCriteria>> response) {

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
            public void onFailure(Call<ResponceObject<EligibiltyCriteria>> call, Throwable t) {

                responseCallBack.onFailure(t.getMessage());
            }
        });

    }


    public void getDashboard(final ResponseCallBack<CorrespondantDashboardObj> responseCallBack) {

//        String json = new Gson().toJson(loc);


        Call<ResponceObject<CorrespondantDashboardObj>> call = loanService.getDashboard();

        call.enqueue(new Callback<ResponceObject<CorrespondantDashboardObj>>() {
            @Override
            public void onResponse(Call<ResponceObject<CorrespondantDashboardObj>> call,
                                   Response<ResponceObject<CorrespondantDashboardObj>> response) {

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
            public void onFailure(Call<ResponceObject<CorrespondantDashboardObj>> call, Throwable t) {

                responseCallBack.onFailure(t.getMessage());
            }
        });

    }


    public void addBorrower(final AddBorrower addBorrower, final ResponseCallBack<AddBorrowerResponce> responseCallBack) {

        String json = new Gson().toJson(addBorrower);


        Call<ResponceObject<AddBorrowerResponce>> call = loanService.addBorrower(addBorrower);

        call.enqueue(new Callback<ResponceObject<AddBorrowerResponce>>() {
            @Override
            public void onResponse(Call<ResponceObject<AddBorrowerResponce>> call,
                                   Response<ResponceObject<AddBorrowerResponce>> response) {

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
            public void onFailure(Call<ResponceObject<AddBorrowerResponce>> call, Throwable t) {

                responseCallBack.onFailure(t.getMessage());
            }
        });

    }


    public void applicationVerification(final AppVerificationRequest addBorrower,
                                        final ResponseCallBack<ApplicationVerificationResponce> responseCallBack) {

        String json = new Gson().toJson(addBorrower);


        Call<ResponceObject<ApplicationVerificationResponce>> call = loanService.applicationVerification(addBorrower);

        call.enqueue(new Callback<ResponceObject<ApplicationVerificationResponce>>() {
            @Override
            public void onResponse(Call<ResponceObject<ApplicationVerificationResponce>> call,
                                   Response<ResponceObject<ApplicationVerificationResponce>> response) {

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
            public void onFailure(Call<ResponceObject<ApplicationVerificationResponce>> call, Throwable t) {

                responseCallBack.onFailure(t.getMessage());
            }
        });

    }

    public void getData(final String myBorrower, final ResponseCallBack<GetDataResponce> responseCallBack) {

        String json = new Gson().toJson(myBorrower);


        Call<ResponceObject<GetDataResponce>> call = loanService.getData(myBorrower);

        call.enqueue(new Callback<ResponceObject<GetDataResponce>>() {
            @Override
            public void onResponse(Call<ResponceObject<GetDataResponce>> call,
                                   Response<ResponceObject<GetDataResponce>> response) {

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
            public void onFailure(Call<ResponceObject<GetDataResponce>> call, Throwable t) {

                responseCallBack.onFailure(t.getMessage());
            }
        });

    }


    public void getApplicationDetails(final String id,
                                      final ResponseCallBack<ApplicationDetailRespose> responseCallBack) {

        String json = new Gson().toJson(id);


        Call<ResponceObject<ApplicationDetailRespose>> call = loanService.getApplicationDetail(id);

        call.enqueue(new Callback<ResponceObject<ApplicationDetailRespose>>() {
            @Override
            public void onResponse(Call<ResponceObject<ApplicationDetailRespose>> call,
                                   Response<ResponceObject<ApplicationDetailRespose>> response) {

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
            public void onFailure(Call<ResponceObject<ApplicationDetailRespose>> call, Throwable t) {

                responseCallBack.onFailure(t.getMessage());
            }
        });

    }

    public void loginAsSomeOne(final CorrespondantLoginAsRequest requestInfo,
                               final ResponseCallBack<OTPResponse> responseCallBack) {
        String json = new Gson().toJson(requestInfo);
        Call<ResponceObject<OTPResponse>> call = loanService.loginAsUser(requestInfo);


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


    public void postResponse(PostResponseRequest request,
                             final ResponseCallBack<RecoveryPayResponse> responseCallBack) {

        String json = new Gson().toJson(request);

        loanService.postResponse(request).enqueue(new Callback<ResponceObject<RecoveryPayResponse>>() {
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


    public void offlineBorroweradd(final OfflineData offlineData, final ResponseCallBack<OfflineResponce> responseCallBack) {

        String json = new Gson().toJson(offlineData);


        Call<ResponceObject<OfflineResponce>> call = loanService.offlineBorrower(offlineData);

        call.enqueue(new Callback<ResponceObject<OfflineResponce>>() {
            @Override
            public void onResponse(Call<ResponceObject<OfflineResponce>> call,
                                   Response<ResponceObject<OfflineResponce>> response) {

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
            public void onFailure(Call<ResponceObject<OfflineResponce>> call, Throwable t) {

                responseCallBack.onFailure(t.getMessage());
            }
        });

    }







}





