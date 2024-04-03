package com.nano.liteloan.business.impl;

import com.google.gson.Gson;
import com.nano.liteloan.business.UserServices;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.ApplicationfeeResponse;
import com.nano.liteloan.info.ForgetPinRequest;
import com.nano.liteloan.info.PinSetResponse;
import com.nano.liteloan.info.ProfileCreateRequestInfo;
import com.nano.liteloan.info.ProfileCreateResponseInfo;
import com.nano.liteloan.info.SetApplicationFeeRequest;
import com.nano.liteloan.info.responce.ErrorObject;
import com.nano.liteloan.info.responce.ResponceObject;
import com.nano.liteloan.info.responce.StageInfoResponse;
import com.nano.liteloan.network.ApiClient;
import com.nano.liteloan.utils.AppConstantsUtils;
import com.nano.liteloan.utils.PreferenceUtils;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Muhammad Ahmad on 07/06/2020.
 */
public class UserBusiness {

    private UserServices services;

    public UserBusiness() {

        services = ApiClient.getApiClient(PreferenceUtils.getInstance().getIPAddress()
                + AppConstantsUtils.BASE_URL).create(UserServices.class);
    }

    public void getUserProfile(final ResponseCallBack<ProfileCreateResponseInfo> responseCallBack) {

        services.getUserProfile()
                .enqueue(new Callback<ResponceObject<ProfileCreateResponseInfo>>() {
                    @Override
                    public void onResponse(Call<ResponceObject<ProfileCreateResponseInfo>> call,
                                           Response<ResponceObject<ProfileCreateResponseInfo>> response) {

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
                    public void onFailure(Call<ResponceObject<ProfileCreateResponseInfo>> call, Throwable t) {
                        responseCallBack.onFailure(t.getMessage());
                    }
                });
    }


    public void getStageInfo(final ResponseCallBack<StageInfoResponse> responseCallBack) {

        services.getStageInfo().enqueue(new Callback<StageInfoResponse>() {
            @Override
            public void onResponse(Call<StageInfoResponse> call, Response<StageInfoResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().metaObject != null
                            && response.body().metaObject.getCode() == 200) {
                        responseCallBack.onSuccess(response.body());
                    } else if (response.body() != null
                            && response.body().metaObject != null) {

                        responseCallBack.onFailure(response.body().metaObject.getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<StageInfoResponse> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }


    public void createProfile(final ProfileCreateRequestInfo requestInfo,
                              final ResponseCallBack<ProfileCreateResponseInfo> responseCallBack) {

        String json = new Gson().toJson(requestInfo);

        Call<ResponceObject<ProfileCreateResponseInfo>> call = services.profilecreate(requestInfo);

        call.enqueue(new Callback<ResponceObject<ProfileCreateResponseInfo>>() {
            @Override
            public void onResponse(Call<ResponceObject<ProfileCreateResponseInfo>> call,
                                   Response<ResponceObject<ProfileCreateResponseInfo>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getMeta() != null
                            && response.body().getMeta().getCode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMeta() != null) {

                        if (response.body().getMeta().getErrorObject() != null) {
                            ErrorObject errorObject = response.body().getMeta().getErrorObject();
                            String error = "";
                            if (errorObject.name != null
                                    && errorObject.name.size() > 0) {
                                error = error + errorObject.name.get(0) + "\n";
                            }

                            if (errorObject.parentage != null
                                    && errorObject.parentage.size() > 0) {
                                error = error + errorObject.parentage.get(0) + "\n";
                            }

                            if (errorObject.dob != null
                                    && errorObject.dob.size() > 0) {
                                error = error + errorObject.dob.get(0) + "\n";
                            }

                            if (errorObject.gender != null
                                    && errorObject.gender.size() > 0) {
                                error = error + errorObject.gender.get(0) + "\n";
                            }

                            if (errorObject.cnic != null
                                    && errorObject.cnic.size() > 0) {
                                error = error + errorObject.cnic.get(0) + "\n";
                            }

                            if (errorObject.profession != null
                                    && errorObject.profession.size() > 0) {
                                error = error + errorObject.profession.get(0) + "\n";
                            }

                            if (errorObject.incomeFrom != null
                                    && errorObject.incomeFrom.size() > 0) {
                                error = error + errorObject.incomeFrom.get(0) + "\n";
                            }

                            if (errorObject.incomeTo != null
                                    && errorObject.incomeTo.size() > 0) {
                                error = error + errorObject.incomeTo.get(0) + "\n";
                            }

                            if (errorObject.dependent != null
                                    && errorObject.dependent.size() > 0) {
                                error = error + errorObject.dependent.get(0) + "\n";
                            }

                            if (errorObject.durationOfStay != null
                                    && errorObject.durationOfStay.size() > 0) {
                                error = error + errorObject.durationOfStay.get(0) + "\n";
                            }

                            if (errorObject.address != null
                                    && errorObject.address.size() > 0) {
                                error = error + errorObject.address.get(0) + "\n";
                            }

                            if (errorObject.city != null
                                    && errorObject.city.size() > 0) {
                                error = error + errorObject.city.get(0) + "\n";
                            }

                            if (errorObject.bankId != null
                                    && errorObject.bankId.size() > 0) {
                                error = error + errorObject.bankId.get(0) + "\n";
                            }

                            if (errorObject.accountNo != null
                                    && errorObject.accountNo.size() > 0) {
                                error = error + errorObject.accountNo.get(0) + "\n";
                            }

                            if (errorObject.latitude != null
                                    && errorObject.latitude.size() > 0) {
                                error = error + errorObject.latitude.get(0) + "\n";
                            }

                            if (errorObject.longitude != null
                                    && errorObject.longitude.size() > 0) {
                                error = error + errorObject.longitude.get(0) + "\n";
                            }

                            responseCallBack.onFailure(error);
                        } else {
                            responseCallBack.onFailure(response.body().getMeta().getMessage());
                        }

                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponceObject<ProfileCreateResponseInfo>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }


        });
    }

    public void setProfile(final ProfileCreateRequestInfo requestInfo,
                           final ResponseCallBack<ProfileCreateResponseInfo> responseCallBack) {

        String json = new Gson().toJson(requestInfo);

        Call<ResponceObject<ProfileCreateResponseInfo>> call = services.setProfile(requestInfo);

        call.enqueue(new Callback<ResponceObject<ProfileCreateResponseInfo>>() {
            @Override
            public void onResponse(Call<ResponceObject<ProfileCreateResponseInfo>> call,
                                   Response<ResponceObject<ProfileCreateResponseInfo>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getMeta() != null
                            && response.body().getMeta().getCode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMeta() != null) {

                        if (response.body().getMeta().getErrorObject() != null) {
                            ErrorObject errorObject = response.body().getMeta().getErrorObject();
                            String error = "";
                            if (errorObject.name != null
                                    && errorObject.name.size() > 0) {
                                error = error + errorObject.name.get(0) + "\n";
                            }

                            if (errorObject.parentage != null
                                    && errorObject.parentage.size() > 0) {
                                error = error + errorObject.parentage.get(0) + "\n";
                            }

                            if (errorObject.dob != null
                                    && errorObject.dob.size() > 0) {
                                error = error + errorObject.dob.get(0) + "\n";
                            }

                            if (errorObject.gender != null
                                    && errorObject.gender.size() > 0) {
                                error = error + errorObject.gender.get(0) + "\n";
                            }

                            if (errorObject.cnic != null
                                    && errorObject.cnic.size() > 0) {
                                error = error + errorObject.cnic.get(0) + "\n";
                            }

                            if (errorObject.profession != null
                                    && errorObject.profession.size() > 0) {
                                error = error + errorObject.profession.get(0) + "\n";
                            }

                            if (errorObject.incomeFrom != null
                                    && errorObject.incomeFrom.size() > 0) {
                                error = error + errorObject.incomeFrom.get(0) + "\n";
                            }

                            if (errorObject.incomeTo != null
                                    && errorObject.incomeTo.size() > 0) {
                                error = error + errorObject.incomeTo.get(0) + "\n";
                            }

                            if (errorObject.dependent != null
                                    && errorObject.dependent.size() > 0) {
                                error = error + errorObject.dependent.get(0) + "\n";
                            }

                            if (errorObject.durationOfStay != null
                                    && errorObject.durationOfStay.size() > 0) {
                                error = error + errorObject.durationOfStay.get(0) + "\n";
                            }

                            if (errorObject.address != null
                                    && errorObject.address.size() > 0) {
                                error = error + errorObject.address.get(0) + "\n";
                            }

                            if (errorObject.city != null
                                    && errorObject.city.size() > 0) {
                                error = error + errorObject.city.get(0) + "\n";
                            }

                            if (errorObject.bankId != null
                                    && errorObject.bankId.size() > 0) {
                                error = error + errorObject.bankId.get(0) + "\n";
                            }

                            if (errorObject.accountNo != null
                                    && errorObject.accountNo.size() > 0) {
                                error = error + errorObject.accountNo.get(0) + "\n";
                            }

                            if (errorObject.latitude != null
                                    && errorObject.latitude.size() > 0) {
                                error = error + errorObject.latitude.get(0) + "\n";
                            }

                            if (errorObject.longitude != null
                                    && errorObject.longitude.size() > 0) {
                                error = error + errorObject.longitude.get(0) + "\n";
                            }
                            if (errorObject.email != null
                                    && errorObject.email.size() > 0) {
                                error = error + errorObject.email.get(0) + "\n";
                            }

                            if (errorObject.issueDate != null
                                    && errorObject.issueDate.size() > 0) {
                                error = error + errorObject.issueDate.get(0) + "\n";
                            }

                            if (errorObject.expiryDate != null
                                    && errorObject.expiryDate.size() > 0) {
                                error = error + errorObject.expiryDate.get(0) + "\n";
                            }

                            if (errorObject.incomeError != null
                                    && errorObject.incomeError.size() > 0) {
                                error = error + errorObject.incomeError.get(0) + "\n";
                            }

                            if (errorObject.utilityBill != null
                                    && errorObject.utilityBill.size() > 0) {
                                error = error + errorObject.utilityBill.get(0) + "\n";
                            }

                            if (errorObject.pinCodeError != null
                                    && errorObject.pinCodeError.size() > 0) {
                                error = error + errorObject.pinCodeError.get(0) + "\n";
                            }

                            if (errorObject.maritalStatus != null
                                    && errorObject.maritalStatus.size() > 0) {
                                error = error + errorObject.maritalStatus.get(0) + "\n";
                            }

                            responseCallBack.onFailure(error);
                        } else {
                            responseCallBack.onFailure(response.body().getMeta().getMessage());
                        }

                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponceObject<ProfileCreateResponseInfo>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }


        });
    }

    public void setPinCode(String code, final ResponseCallBack<PinSetResponse> responseCallBack) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pin", code);
        String json = new Gson().toJson(hashMap);
        RequestBody requestBody = RequestBody
                .create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                        json);

        services.setPinCode(requestBody).enqueue(new Callback<ResponceObject<PinSetResponse>>() {
            @Override
            public void onResponse(Call<ResponceObject<PinSetResponse>> call,
                                   Response<ResponceObject<PinSetResponse>> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getMeta() != null
                            && response.body().getMeta().getCode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMeta() != null) {

                        responseCallBack.onFailure(response.body()
                                .getMeta().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponceObject<PinSetResponse>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }

    public void forgetPinCode(ForgetPinRequest request, final ResponseCallBack<PinSetResponse> responseCallBack) {

        String json = new Gson().toJson(request);

        services.forgetPinCode(request).enqueue(new Callback<ResponceObject<PinSetResponse>>() {
            @Override
            public void onResponse(Call<ResponceObject<PinSetResponse>> call,
                                   Response<ResponceObject<PinSetResponse>> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getMeta() != null
                            && response.body().getMeta().getCode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMeta() != null) {

                        responseCallBack.onFailure(response.body()
                                .getMeta().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponceObject<PinSetResponse>> call, Throwable t) {
                responseCallBack.onFailure(t.getMessage());
            }
        });
    }


    public void setApplicationFee(SetApplicationFeeRequest request, final ResponseCallBack<ApplicationfeeResponse> responseCallBack) {

        String json = new Gson().toJson(request);

        services.setApplicationfee(request).enqueue(new Callback<ResponceObject<ApplicationfeeResponse>>() {
            @Override
            public void onResponse(Call<ResponceObject<ApplicationfeeResponse>> call,
                                   Response<ResponceObject<ApplicationfeeResponse>> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null
                            && response.body().getMeta() != null
                            && response.body().getMeta().getCode() == 200) {
                        responseCallBack.onSuccess(response.body().getData());
                    } else if (response.body() != null
                            && response.body().getMeta() != null) {

                        responseCallBack.onFailure(response.body()
                                .getMeta().getMessage());
                    }
                } else {
                    responseCallBack.onFailure(response.body()
                            .getMeta().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponceObject<ApplicationfeeResponse>> call, Throwable t) {
                responseCallBack.onFailure("The application fee field is required.");
            }
        });

    }


}
