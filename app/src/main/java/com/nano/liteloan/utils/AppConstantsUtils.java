package com.nano.liteloan.utils;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class AppConstantsUtils {


    public static final String LIVE_IP = "https://internal.akhuwat.org.pk";
   // public static final String LIVE_IP = "http://116.58.62.235";

    public static final String NEW_IP = "http://203.170.68.123";

    public static final String PTCL_IP = "http://116.71.135.116";
    public static final String LOCAL_IP = "http://10.84.12.98";
    public static final String BASE_URL = "/easy_akhuwat_v2/api/public/";


    public static final String REGISTER = "authorize/register";
    public static final String LOGIN = "authorize/login";
    public static final String GET_PRODUCTS = "authorize/fetchCategoryOrproduct";
    public static final String SOCIAL_LOGIN = "authorize/socialLogin";
    public static final String PROFILE_CREATE = "api/createProfile";
    public static final String FETCH_CONFIGURATION = "api/fetchConfig";

    public static final String FORGOT_PASSWORD = "authorize/forgetPassword";
    public static final String FETCH_LOAN_CATEGORIES = "api/fetchConfigCategoryOrproduct";
    public static final String LOAN_APPLY = "api/receiveApplication";
    public static final String UPDATE_LOCATION = "api/updateLocation";
    public static final String SYNC_LOGS = "api/syncLogs";
    public static final String APPLY_AGAIN = "api/applyAgain";

    public static final String GET_APPLICATION = "api/getApplication";
    public static final String GET_PAYMENT = "api/getPayments";
    public static final String GET_SCHEDULE = "api/getSchedule";
    public static final String OTP = "authorize/otpLogin";
    public static final String REGISTER_DEVICE = "api/register";
    public static final String REGISTER_PUSH_ID = "api/registerPush";
    public static final String SET_PROFILE_SET = "api/setProfile";
    public static final String GET_STAGE_INFO = "api/getStages";
    public static final String SET_PIN_CODE = "api/setPinCode";
    public static final String FORGET_PIN_CODE = "authorize/fogetPin";
    public static final String SET_APPLICATION_FEE = "api/setApplicationfee";
    public static final String RECOVERY_PAY = "api/pay-recovery";

    public static final String GET_SURVEY = "api/getSurvey";
    public static final String POST_SURVEY = "api/postSurvey";

    public static final String ABOUT_US = "api/pages";

    public static final String SEARCH = "api/searchBorrower";

    public static final String LOAN_INFO = "api/borrowerApplication";
    public static final String LOAN_RECEIPT = "api/borrowerDisbursement";
    public static final String GET_BORROWER = "api/getPotentialBorrowers";
    public static final String GET_BORROWER_SCHEDULE = "api/getBorrowerSchedule";
    public static final String PAY_BORROWER_RECOVERY = "api/payBorrowerRecovery";
    public static final String BRANCH_DETAIL = "api/getBranches";

    public static final String FETCH_ELIGIBILITY = "authorize/fetchEligibilityCriteria";
    public static final String GET_DASHBOARD = "api/getDashboard";
    public static final String ADD_BORROWER = "api/addBorrower";
    public static final String APPLICATION_VERIFICATION = "api/applicationVerification";
    public static final String GETDATA = "api/getDashboardDetail";
    public static final String LOGIN_AS = "/authorize/loginBorrower";
    public static final String GET_RECOVERY = "/api/getRecovery";
    public static final String POST_RESPONSE = "api/postResponse";

    public static final String OFFLINE_BORROWER = "api/setprofileoffline";
    /**
     * Created by Muhammad Ahmad on 07/07/2020.
     */
    public class ProfileUtils {

        static final String USER_DETAILS = "user_data";
        static final String LOGIN_AS_USER_DETAILS = "login_as_user_data";
        static final String LOGIN_AS_USER_ACTIVE = "login_as_user_active";
        static final String ACCOUNT_DETAIL = "account_detail";
        static final String LOAN_CATEGORIES = "loan_categories";
        static final String LOAN_CATEGORIES_ITEMS = "loan_categories_items";
        static final String PRODUCT_PURPOSE = "product_purpose";
        static final String CITY_LIST = "city_list";
        static final String BANK_LIST = "bank_list";
        static final String PROFESSION_LIST = "profession_list";

        public static final String EASY_PAISA = "Easy Paisa";
        public static final String JAZZ_CASH = "Jazz Cash";

        static final String GET_BORROWER_LIST = "get_borrower_list";
        static final String GET_USERPROFILE_LIST = "get_userprofile_list";

    }

    /**
     * Created by Muhammad Ahmad.
     */
    public class PreferenceUtils {

        static final String PREFERENCE_NAME = "Akhuwat_easyLoan";
        static final String ACCESS_TOKEN = "access_token";
        static final String PHONE_NUMBER = "phone_number";
        static final String USER_ID = "user_id";
        static final String CORR_ID = "corr_id";
        static final String PIN_CODE = "pin_code";
        static final String SESSION_INFO = "session_active";
        static final String DEVICE_ID = "device_id";
        static final String BORROWER_TYPE = "borrowertype";
        static final String LOGIN_CHECK = "loggedInmode";
        static final String APPLICATION_FEE = "is_fee_paid";
        static final String BIOMETRIC_LOGIN = "biometric_login";
        static final String REMAINING_BALANCE = "remaining_balance";

    }

    /**
     * Created by Muhammad Ahmad on 08/06/2020.
     */
    public class StageUtils {

        public static final String BASIC = "basic";
        public static final String BASIC_DOC = "basic_doc";
        public static final String EVALUATION = "evaluation";
        public static final String WALLET = "wallet";

        public static final String SURVEY = "survey";

        public static final String FEE = "fee";
        public static final String IN_PROCESS = "inProcess";
        public static final String FEE_CONFIRMATION = "fee_confirmation";
        public static final String FEE_CONFIRMED = "fee_confirmed";
        public static final String STATUS_EVALUATION = "status_evaluation";
        public static final String STATUS_NOT_EVALUATION = "status_not_evaluation";
        public static final String STATUS_LOANAPPLICATION = "status_loanapplication";
        public static final String STATUS_LOANAPPLICATIONR = "status_loanapplicationr";
        public static final String STATUS_LOANAPPLICATIONREJECTED = "status_loanapplicationrejected";

        public static final String STATUS_BANK = "status_bank";
        public static final String STATUS_REPAY = "status_repay";
        public static final String STATUS_NOT_LOANAPPLICATION = "status_not_loanapplication";

    }

    /**
     * Created by Muhammad Ahmad on 07/07/2020.
     */
    public class CameraCodeConstantUtils {


        public static final int UPDATE_PROFILE_PIC_GALLERY = 5000;
        public static final int UPDATE_PROFILE_PIC = 6000;
        public static final int CNIC_BACK = 10;
        public static final int CNIC_FRONT = 11;
        public static final int SALARY_SLIP = 12;
        public static final int UTILITY_BILL = 13;
        public static final int OTHER_DOC = 14;
    }
}