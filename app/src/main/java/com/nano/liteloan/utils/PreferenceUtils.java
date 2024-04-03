package com.nano.liteloan.utils;

import static com.nano.liteloan.utils.AppConstantsUtils.PreferenceUtils.APPLICATION_FEE;
import static com.nano.liteloan.utils.AppConstantsUtils.PreferenceUtils.BIOMETRIC_LOGIN;
import static com.nano.liteloan.utils.AppConstantsUtils.PreferenceUtils.BORROWER_TYPE;
import static com.nano.liteloan.utils.AppConstantsUtils.PreferenceUtils.CORR_ID;
import static com.nano.liteloan.utils.AppConstantsUtils.PreferenceUtils.DEVICE_ID;
import static com.nano.liteloan.utils.AppConstantsUtils.PreferenceUtils.LOGIN_CHECK;
import static com.nano.liteloan.utils.AppConstantsUtils.PreferenceUtils.PHONE_NUMBER;
import static com.nano.liteloan.utils.AppConstantsUtils.PreferenceUtils.PIN_CODE;
import static com.nano.liteloan.utils.AppConstantsUtils.PreferenceUtils.PREFERENCE_NAME;
import static com.nano.liteloan.utils.AppConstantsUtils.PreferenceUtils.REMAINING_BALANCE;
import static com.nano.liteloan.utils.AppConstantsUtils.PreferenceUtils.SESSION_INFO;
import static com.nano.liteloan.utils.AppConstantsUtils.PreferenceUtils.USER_ID;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nano.liteloan.application.MainApplication;
import com.nano.liteloan.info.BankInfo;
import com.nano.liteloan.info.BorrowerList;
import com.nano.liteloan.info.CityInfo;
import com.nano.liteloan.info.GetBorrowerList;
import com.nano.liteloan.info.ListItemInfo;
import com.nano.liteloan.info.LiteAccount;
import com.nano.liteloan.info.LoanApplyRequest;
import com.nano.liteloan.info.LoanCategoryInfo;
import com.nano.liteloan.info.LoanCategoryItems;
import com.nano.liteloan.info.ProductInfo;
import com.nano.liteloan.info.UserDate;
import com.nano.liteloan.info.UserProfile;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Muhammad Ahmad on 07/06/2020.
 */
public class PreferenceUtils {
    private static PreferenceUtils instance;
    private static SharedPreferences prefs;


    private PreferenceUtils() {
    }

    public static PreferenceUtils getInstance() {

        if (instance == null) {
            instance = new PreferenceUtils();
            prefs = MainApplication.getAppContext()
                    .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
        return instance;
    }

    public void setPhoneNumber(String number) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(PHONE_NUMBER, number);
        prefsEditor.apply();
    }

    public String getPhoneNumber() {

        return prefs.getString(PHONE_NUMBER, null);
    }

    public void setBaseUrl(String number) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("base_url", number);
        prefsEditor.apply();
    }

    public String getBaseUrl() {

        return prefs.getString("base_url", null);
    }

    public void setIPAddress(String number) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("ip_address", number);
        prefsEditor.apply();
    }

    public String getIPAddress() {

        return prefs.getString("ip_address", null);
    }

    public void setUserId(String number) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(USER_ID, number);
        prefsEditor.apply();
    }

    public String getUserId() {

        return prefs.getString(USER_ID, null);
    }

    public void setcorrespondantId(String number) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(CORR_ID, number);
        prefsEditor.apply();
    }

    public String getcorrespondantId() {

        return prefs.getString(CORR_ID, null);
    }

    public void setPinCode(String number) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(PIN_CODE, number);
        prefsEditor.apply();
    }

    public String getPinCode() {

        return prefs.getString(PIN_CODE, null);
    }

    public boolean isSessionActive() {
        return prefs.getBoolean(SESSION_INFO, false);
    }

    public void setSessionActive(boolean value) {

        try {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(SESSION_INFO, value);
            editor.apply();
        } catch (Exception e) {
            Log.e("ahmad", "" + e.getMessage());
        }
    }

    public void setDeviceId(String deviceId) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(DEVICE_ID, deviceId);
        prefsEditor.apply();
    }

    public String getDeviceId() {

        return prefs.getString(DEVICE_ID, null);
    }

    public void setLoggedin(boolean logggedin) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putBoolean(LOGIN_CHECK, logggedin);
        prefsEditor.apply();
    }

    public boolean loggedin() {
        return prefs.getBoolean(LOGIN_CHECK, false);
    }

    public void setFeePaid(int logggedin) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(APPLICATION_FEE, logggedin);
        prefsEditor.apply();
    }

    public int isFeePaid() {
        return prefs.getInt(APPLICATION_FEE, 0);
    }


    public void setInBioMetric(boolean isBio) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putBoolean(BIOMETRIC_LOGIN, isBio);
        prefsEditor.apply();
    }

    public boolean isBiometric() {
        return prefs.getBoolean(BIOMETRIC_LOGIN, false);
    }

    public void addUserDetail(UserDate userDetailInfo) {

        String userInfo = new Gson().toJson(userDetailInfo);

        try {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(AppConstantsUtils.ProfileUtils.USER_DETAILS, userInfo);
            editor.apply();
        } catch (Exception e) {
            Log.e("ahmad", e.getMessage());
        }
    }


    public UserDate getUserDetail() {

        Gson gson = new Gson();
        Type type = new TypeToken<UserDate>() {
        }.getType();

        String json = prefs.getString(AppConstantsUtils.ProfileUtils.USER_DETAILS, "");

        if (json.equals("")) {
            return null;
        }
        return gson.fromJson(json, type);
    }

    public void setLoginAsActive(boolean isActive) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(AppConstantsUtils.ProfileUtils.LOGIN_AS_USER_ACTIVE, isActive);
        editor.apply();
    }

    public boolean isLoginAsActive() {
        return prefs.getBoolean(AppConstantsUtils.ProfileUtils.LOGIN_AS_USER_ACTIVE, false);
    }

    public void addCorrespondentUserDetail(UserDate userDetailInfo) {

        String userInfo = new Gson().toJson(userDetailInfo);

        try {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(AppConstantsUtils.ProfileUtils.LOGIN_AS_USER_DETAILS, userInfo);
            editor.apply();
        } catch (Exception e) {
            Log.e("ahmad", e.getMessage());
        }
    }

    public UserDate getCorrespondentUserDetail() {

        Gson gson = new Gson();
        Type type = new TypeToken<UserDate>() {
        }.getType();

        String json = prefs.getString(AppConstantsUtils.ProfileUtils.LOGIN_AS_USER_DETAILS, "");

        if (json.equals("")) {
            return null;
        }
        return gson.fromJson(json, type);
    }

    public void addPotentialBorrower(GetBorrowerList borrowerList) {
        String data = new Gson().toJson(borrowerList);

        try {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("potential_borrower", data);
            editor.apply();
        } catch (Exception e) {
            Log.e("ahmad", e.getMessage());
        }
    }

    public GetBorrowerList getPotentialBorrower() {
        Gson gson = new Gson();
        Type type = new TypeToken<GetBorrowerList>() {
        }.getType();

        String json = prefs.getString("potential_borrower", "");

        if (json.equals("")) {
            return null;
        }
        return gson.fromJson(json, type);
    }

    public void addLoanCategoriesDetail(List<LoanCategoryItems> items) {

        String loanDetail = new Gson().toJson(items);

        try {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(AppConstantsUtils.ProfileUtils.LOAN_CATEGORIES_ITEMS, loanDetail);
            editor.apply();
        } catch (Exception e) {
            Log.e("ahmad", e.getMessage());
        }
    }


    public List<LoanCategoryItems> getLaonCategoryDetail() {

        Gson gson = new Gson();
        Type type = new TypeToken<List<LoanCategoryItems>>() {
        }.getType();

        String json = prefs.getString(AppConstantsUtils.ProfileUtils.LOAN_CATEGORIES_ITEMS, "");

        if (json.equals("")) {
            return null;
        }
        return gson.fromJson(json, type);
    }


    public void productPurpose(List<ProductInfo> items) {

        String loanDetail = new Gson().toJson(items);

        try {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(AppConstantsUtils.ProfileUtils.PRODUCT_PURPOSE, loanDetail);
            editor.apply();
        } catch (Exception e) {
            Log.e("ahmad", e.getMessage());
        }
    }

    public List<ProductInfo> getproductPurposeDetail() {

        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductInfo>>() {
        }.getType();

        String json = prefs.getString(AppConstantsUtils.ProfileUtils.PRODUCT_PURPOSE, "");

        if (json.equals("")) {
            return null;
        }
        return gson.fromJson(json, type);
    }


    public void addAccountDetail(List<LiteAccount> liteAccount) {

        String accountDetail = new Gson().toJson(liteAccount);

        try {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(AppConstantsUtils.ProfileUtils.ACCOUNT_DETAIL, accountDetail);
            editor.apply();
        } catch (Exception e) {
            Log.e("ahmad", e.getMessage());
        }
    }


    public List<LiteAccount> getAccountDetail() {

        Gson gson = new Gson();
        Type type = new TypeToken<List<LiteAccount>>() {
        }.getType();

        String json = prefs.getString(AppConstantsUtils.ProfileUtils.ACCOUNT_DETAIL, "");

        if (json.equals("")) {
            return null;
        }
        return gson.fromJson(json, type);
    }


    public void addLoanCategories(List<LoanCategoryInfo> loanCategoryInfos) {

        String userInfo = new Gson().toJson(loanCategoryInfos);

        try {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(AppConstantsUtils.ProfileUtils.LOAN_CATEGORIES, userInfo);
            editor.apply();
        } catch (Exception e) {
            Log.e("ahmad", e.getMessage());
        }
    }

    public List<LoanCategoryInfo> getLoanCategories() {

        Gson gson = new Gson();
        Type type = new TypeToken<List<LoanCategoryInfo>>() {
        }.getType();

        String json = prefs.getString(AppConstantsUtils.ProfileUtils.LOAN_CATEGORIES, "");

        if (json.equals("")) {
            return null;
        }
        return gson.fromJson(json, type);
    }

    public void addCityInfo(List<CityInfo> cityInfos) {

        String userInfo = new Gson().toJson(cityInfos);

        try {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(AppConstantsUtils.ProfileUtils.CITY_LIST, userInfo);
            editor.apply();
        } catch (Exception e) {
            Log.e("ahmad", e.getMessage());
        }
    }

    public List<CityInfo> getCityCategory() {

        Gson gson = new Gson();
        Type type = new TypeToken<List<CityInfo>>() {
        }.getType();

        String json = prefs.getString(AppConstantsUtils.ProfileUtils.CITY_LIST, "");

        if (json.equals("")) {
            return null;
        }
        return gson.fromJson(json, type);
    }

    public void addBankLint(List<BankInfo> bankInfos) {

        String userInfo = new Gson().toJson(bankInfos);

        try {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(AppConstantsUtils.ProfileUtils.BANK_LIST, userInfo);
            editor.apply();
        } catch (Exception e) {
            Log.e("ahmad", e.getMessage());
        }
    }

    public List<BankInfo> getBankList() {

        Gson gson = new Gson();
        Type type = new TypeToken<List<BankInfo>>() {
        }.getType();

        String json = prefs.getString(AppConstantsUtils.ProfileUtils.BANK_LIST, "");

        if (json.equals("")) {
            return null;
        }
        return gson.fromJson(json, type);
    }

    public void addProfessions(List<ListItemInfo> professionInfos) {

        String userInfo = new Gson().toJson(professionInfos);

        try {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(AppConstantsUtils.ProfileUtils.PROFESSION_LIST, userInfo);
            editor.apply();
        } catch (Exception e) {
            Log.e("ahmad", e.getMessage());
        }
    }

    public List<ListItemInfo> getProfessionList() {

        Gson gson = new Gson();
        Type type = new TypeToken<List<ListItemInfo>>() {
        }.getType();

        String json = prefs.getString(AppConstantsUtils.ProfileUtils.PROFESSION_LIST, "");

        if (json.equals("")) {
            return null;
        }
        return gson.fromJson(json, type);
    }

    public void clearPreferences() {
        String phone = null;
        String pin = null;
        boolean isBio = false;

        if (isBiometric()) {

            phone = getPhoneNumber();
            pin = getPinCode();
            isBio = isBiometric();
        }

        prefs.edit().clear().apply();


        setPhoneNumber(phone);
        setPinCode(pin);
        setInBioMetric(isBio);
    }

    public void setBorrowerType(String borrowertype) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(BORROWER_TYPE, borrowertype);
        prefsEditor.apply();
    }

    public String getBorrowertype() {

        return prefs.getString(BORROWER_TYPE, null);
    }

    public void setFee(String fee) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("fee", fee);
        prefsEditor.apply();
    }

    public String getFee() {

        return prefs.getString("fee", null);
    }

    public void setRemainingBalance(String remainingBalance) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(REMAINING_BALANCE, remainingBalance);
        prefsEditor.apply();
    }

    public String getRemainingBalance() {

        return prefs.getString(REMAINING_BALANCE, null);
    }


    public void addBorrowerList(HashMap<String, BorrowerList> borrowerList) {

        String userInfo = new Gson().toJson(borrowerList);

        try {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(AppConstantsUtils.ProfileUtils.GET_BORROWER_LIST, userInfo);
            editor.apply();
        } catch (Exception e) {
            Log.e("ahmad", e.getMessage());
        }
    }

    public HashMap<String, BorrowerList> getBorrowerList() {

        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, BorrowerList>>() {
        }.getType();

        String json = prefs.getString(AppConstantsUtils.ProfileUtils.GET_BORROWER_LIST, "");

        if (json.equals("")) {
            return null;
        }
        return gson.fromJson(json, type);
    }

    public void addUserProfileList(HashMap<String, UserProfile> borrowerList) {

        String userInfo = new Gson().toJson(borrowerList);

        try {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(AppConstantsUtils.ProfileUtils.GET_USERPROFILE_LIST, userInfo);
            editor.apply();
        } catch (Exception e) {
            Log.e("ahmad", e.getMessage());
        }
    }

    public HashMap<String, UserProfile> getUserProfileList() {

        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, UserProfile>>() {
        }.getType();

        String json = prefs.getString(AppConstantsUtils.ProfileUtils.GET_USERPROFILE_LIST, "");

        if (json.equals("")) {
            return null;
        }
        return gson.fromJson(json, type);
    }


    public void addLoanRequest(LoanApplyRequest loanApplyRequest, String key) {

        String userInfo = new Gson().toJson(loanApplyRequest);

        try {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(key, userInfo);
            editor.apply();
        } catch (Exception e) {
            Log.e("ahmad", e.getMessage());
        }
    }

    public LoanApplyRequest getLoanRequest(String key) {

        Gson gson = new Gson();
        Type type = new TypeToken<LoanApplyRequest>() {
        }.getType();

        String json = prefs.getString(key, "");

        if (json.equals("")) {
            return null;
        }
        return gson.fromJson(json, type);
    }

    public void saveImageBase64(String image, String key) {

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, image);
        editor.apply();
    }

    public String getBase64Image(String key) {

        return prefs.getString(key, null);
    }

}

