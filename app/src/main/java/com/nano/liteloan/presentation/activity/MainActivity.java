package com.nano.liteloan.presentation.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.jaredrummler.android.device.DeviceName;
import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.AuthBusiness;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.correspondant.correspondantdialoguefragment.CorrespondantInfoDialogue;
import com.nano.liteloan.correspondant.fragment.ActiveLoanFrag;
import com.nano.liteloan.correspondant.fragment.AddNewBorrower;
import com.nano.liteloan.correspondant.fragment.AgreementIjarahFragment;
import com.nano.liteloan.correspondant.fragment.AgreementMusharkaFragment;
import com.nano.liteloan.correspondant.fragment.ApplicationDetailFragment;
import com.nano.liteloan.correspondant.fragment.ApplicationinProcessFrag;
import com.nano.liteloan.correspondant.fragment.ApplicationinReadyToDisbursdFrag;
import com.nano.liteloan.correspondant.fragment.BankAccountFragment;
import com.nano.liteloan.correspondant.fragment.BorrowerLoginAs;
import com.nano.liteloan.correspondant.fragment.BorrowerRecoveryFrag;
import com.nano.liteloan.correspondant.fragment.CorrespondantDashboardScreen;
import com.nano.liteloan.correspondant.fragment.ImagePreviewFRagment;
import com.nano.liteloan.correspondant.fragment.LoanDetailFragment;
import com.nano.liteloan.correspondant.fragment.LoanProductListFragment;
import com.nano.liteloan.correspondant.fragment.MapFragment;
import com.nano.liteloan.correspondant.fragment.MyBorrowersFragment;
import com.nano.liteloan.correspondant.fragment.MyInterestedApplicants;
import com.nano.liteloan.correspondant.fragment.OfflineFragment;
import com.nano.liteloan.correspondant.fragment.OtpScreenFrag;
import com.nano.liteloan.correspondant.fragment.OverDueFragment;
import com.nano.liteloan.correspondant.fragment.PendingApplicationinFrag;
import com.nano.liteloan.correspondant.fragment.PotentialBorrowers;
import com.nano.liteloan.correspondant.fragment.ReadytoDisburseFragment;
import com.nano.liteloan.correspondant.fragment.VerificationFrag;
import com.nano.liteloan.correspondant.fragment.VerificationPendingFrag;
import com.nano.liteloan.correspondant.fragment.offilne.OfflineMobileWallet;
import com.nano.liteloan.correspondant.fragment.offilne.OfflineProfileMainFrag;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.BorrowerList;
import com.nano.liteloan.info.BorrowerReceiptObj;
import com.nano.liteloan.info.ConfigurationRespondObject;
import com.nano.liteloan.info.GetApplicationInfo;
import com.nano.liteloan.info.GetBorrowerList;
import com.nano.liteloan.info.LoanApplyRequest;
import com.nano.liteloan.info.LoanCategoryInfo;
import com.nano.liteloan.info.LoanInfoRequest;
import com.nano.liteloan.info.PagesList;
import com.nano.liteloan.info.RegisterDeviceRequest;
import com.nano.liteloan.info.RegisterDeviceResponse;
import com.nano.liteloan.info.RegisterPushIdRequest;
import com.nano.liteloan.info.SearchBorrower;
import com.nano.liteloan.info.UserProfile;
import com.nano.liteloan.info.businesscorrespondant.ApplicationOverDueList;
import com.nano.liteloan.info.businesscorrespondant.MyApplicants;
import com.nano.liteloan.info.businesscorrespondant.PendingList;
import com.nano.liteloan.info.businesscorrespondant.PendingVerifications;
import com.nano.liteloan.info.businesscorrespondant.PotentialBorrowerRequest;
import com.nano.liteloan.presentation.fragment.AboutUsFragment;
import com.nano.liteloan.presentation.fragment.FAQSFragment;
import com.nano.liteloan.presentation.fragment.GeneralLoanFragment;
import com.nano.liteloan.presentation.fragment.HelpCenterFragment;
import com.nano.liteloan.presentation.fragment.HomeFragment;
import com.nano.liteloan.presentation.fragment.MobileFinancing;
import com.nano.liteloan.presentation.fragment.MyLoansFragment;
import com.nano.liteloan.presentation.fragment.PaymentPaidFragment;
import com.nano.liteloan.presentation.fragment.PaymentTransactionFragment;
import com.nano.liteloan.presentation.fragment.PrivacyPolicyFragment;
import com.nano.liteloan.presentation.fragment.SettingFragment;
import com.nano.liteloan.presentation.fragment.SurveyScreen;
import com.nano.liteloan.presentation.fragment.TermsAndConditionsFragment;
import com.nano.liteloan.presentation.fragment.TotalpaidFragment;
import com.nano.liteloan.presentation.fragment.businesscorrespondance.BorrowerRecoveryFragment;
import com.nano.liteloan.presentation.fragment.businesscorrespondance.BusinessCorrespondance;
import com.nano.liteloan.presentation.fragment.businesscorrespondance.ChequeReceiptFragment;
import com.nano.liteloan.presentation.fragment.businesscorrespondance.UserDetailFragment;
import com.nano.liteloan.presentation.fragment.businesscorrespondance.UserInfoFragment;
import com.nano.liteloan.presentation.fragment.businesscorrespondance.WalletDetailFragment;
import com.nano.liteloan.presentation.fragment.dialoguefragment.InfoDialogFragment;
import com.nano.liteloan.presentation.fragment.profile.EvaluationFragment;
import com.nano.liteloan.presentation.fragment.profile.FeePyamntFragment;
import com.nano.liteloan.presentation.fragment.profile.MainProfileFragment;
import com.nano.liteloan.presentation.fragment.profile.MobileWalletFragment;
import com.nano.liteloan.presentation.fragment.profile.RecoveryFragment;
import com.nano.liteloan.presentation.fragment.profile.UserProfileFrag;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;

import java.util.List;

/**
 * Created by Muhammad Umer on 07/06/2020.
 * Modified by Muhammad Ahmad.
 */
public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public static boolean isInfoShowing = true;
    private int locationRequestCode = 1000;
    public ImageView profileImage;

    public TextView tvHeaderName, tvMyProfile;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
    String aboutUs = "";
    String termandCondition = "";
    String helpCenter = "";
    String faqs = "";
    String privacy = "";
    NavigationView navigationView;
    String userType = "local";


    private PendingVerifications verificationPending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        tvLogout = findViewById(R.id.logout);
//        tvLogout.setOnClickListener(this);
        //setBottomNavigation();


//        checLocationPermission();


        getPagesData();
//        checklogsPermissions();

        //mainProfileFrag(false);


        getConfigurations();
        requestParametersForRegisterDevice();


        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        navigationContent(navigationView);

        setNavigationMenu(0);

        if (PreferenceUtils.getInstance().isLoginAsActive()) {
            homeFrag();
        } else {
            dashBoardFragment();
        }

       // getBorrowerList("", "");

    }

    public void setNavigationMenu(int value) {

        if (PreferenceUtils.getInstance().isLoginAsActive()) {
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);

            Menu navMenu = navigationView.getMenu();
            if (PreferenceUtils.getInstance().getCorrespondentUserDetail() != null
                    && PreferenceUtils.getInstance().getCorrespondentUserDetail().getName() != null) {
                navMenu.findItem(R.id.nav_exit).setVisible(true);
                navMenu.findItem(R.id.nav_exit).setTitle("Exit From "
                        + PreferenceUtils.getInstance().getUserDetail().getName());
            } else {

                navMenu.findItem(R.id.nav_exit).setVisible(false);
            }

        } else {
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.borrower_menu);
        }

    }

    public void hideItem(int value) {
        if (value == 0) {
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu navMenu = navigationView.getMenu();
            navMenu.findItem(R.id.my_borrowers).setVisible(false);
        } else {
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu navMenu = navigationView.getMenu();
            navMenu.findItem(R.id.my_borrowers).setVisible(true);
        }
    }

//    private void checklogsPermissions() {
//        if (checkPermission() == true) {
//
//        } else if (checkPermission() == false) {
//            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
//
//            PermissionScreen perFragment = new PermissionScreen(MainActivity.this);
//            getSupportFragmentManager().beginTransaction()
//                    .setCustomAnimations(R.anim.slide_in_right,
//                            R.anim.slide_out_left,
//                            R.anim.slide_in_left,
//                            R.anim.slide_out_right)
//                    .replace(R.id.main_container, perFragment)
//                    .commit();
//        }
//
//    }

    protected boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALL_LOG)
                + ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.READ_SMS)
                + ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            return false;
        } else {
            return true;
            // Do something, when permissions are already granted
        }
    }

    private void getConfigurations() {

        EasyLoanBusiness business = new EasyLoanBusiness();
        business.getConfiguration(new ResponseCallBack<ConfigurationRespondObject>() {
            @Override
            public void onSuccess(ConfigurationRespondObject body) {

                PreferenceUtils.getInstance().addLoanCategories(body.loanCategories);
                PreferenceUtils.getInstance().addCityInfo(body.cityInfoList);
                PreferenceUtils.getInstance().addBankLint(body.bankInfoList);
                PreferenceUtils.getInstance().addProfessions(body.configList.profession);
            }

            @Override
            public void onFailure(String message) {

                AlertUtils.showAlert(MainActivity.this, message);
            }
        });
    }

    private void navigationContent(NavigationView navigationView) {

        navigationView.getMenu().getItem(0).setChecked(true);
        tvHeaderName = navigationView.getHeaderView(0).findViewById(R.id.tv_hed_name);
        // TextView tvType = navigationView.getHeaderView(0).findViewById(R.id.tv_hed_type);
        profileImage = navigationView.getHeaderView(0).findViewById(R.id.imageView);
        tvMyProfile = navigationView.getHeaderView(0).findViewById(R.id.tv_hed_type);

        tvMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (PreferenceUtils.getInstance().getBorrowertype() != null)
//                    myProfileFrag();
            }
        });

        if (PreferenceUtils.getInstance().getUserDetail() != null) {

            tvHeaderName.setText(PreferenceUtils.getInstance().getUserDetail().getName());

        }

        Button btnEdit = navigationView.getHeaderView(0).findViewById(R.id.btn_edit_profile);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainProfileFrag(0);

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

    }

    public void showInfoFragment() {

        InfoDialogFragment infoDialogFragment = new InfoDialogFragment();
        infoDialogFragment.show(getSupportFragmentManager(),
                InfoDialogFragment.class.getName());
        isInfoShowing = true;
    }

    public void showCorrespondantInfoFragment() {

        CorrespondantInfoDialogue infoDialogFragment = new CorrespondantInfoDialogue();
        infoDialogFragment.show(getSupportFragmentManager(),
                CorrespondantInfoDialogue.class.getName());
        isInfoShowing = true;
    }


    private void requestParametersForRegisterDevice() {

        registerDevice();
    }

    @SuppressLint("HardwareIds")
    private void registerDevice() {

        RegisterDeviceRequest registerRequestBody = new RegisterDeviceRequest();

        registerRequestBody.uuid = Settings.Secure.getString(this
                .getContentResolver(), Settings.Secure.ANDROID_ID);

        registerRequestBody.deviceModel = DeviceName.getDeviceName();
        registerRequestBody.osVersion = String.valueOf(Build.VERSION.SDK_INT);

        if (PreferenceUtils.getInstance().getDeviceId() == null) {

            registerDevice(registerRequestBody);
        } else {

            registerPushId(PreferenceUtils.getInstance().getDeviceId());
        }


    }

    private void registerDevice(RegisterDeviceRequest registerRequestBody) {

        AuthBusiness business = new AuthBusiness();
        business.registerDevice(registerRequestBody, new ResponseCallBack<RegisterDeviceResponse>() {
            @Override
            public void onSuccess(RegisterDeviceResponse body) {

                if (body != null && body.deviceDetail != null) {

                    PreferenceUtils.getInstance().setDeviceId(body.deviceDetail.deviceId);

                    registerPushId(body.deviceDetail.deviceId);
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private void registerPushId(final String deviceId) {

        final RegisterPushIdRequest request = new RegisterPushIdRequest();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnSuccessListener(MainActivity.this, new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {

                        request.pushId = instanceIdResult.getToken();
                        request.deviceId = deviceId;

                        registerPushId(request);

                    }
                });

        request.deviceId = deviceId;


    }

    private void registerPushId(RegisterPushIdRequest request) {

        AuthBusiness business = new AuthBusiness();
        business.registerPushId(request, new ResponseCallBack<RegisterDeviceResponse>() {
            @Override
            public void onSuccess(RegisterDeviceResponse body) {


            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    public void openDrawer() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                homeFrag();
                break;
            case R.id.nav_myloans:
                myLoanFrag(null);

                break;
            case R.id.nav_payments:
                paymentDoneFragment();
                break;

//            case R.id.offline:
//                if (isNetworkAvailable()) {
//                    offlineFragment();
////                    AlertUtils.showAlert(MainActivity.this , "You have Interent connection please try");
//                } else {
//                    offlineFragment();
//                }
//
//                break;

//            case R.id.nav_evaluation:
//                evaluationFragment();
//                break;

            case R.id.nav_wallet:
                mobileWalletFragment();
                break;

            case R.id.nav_aboutus:
                aboutUsFragment();
                break;
            case R.id.nav_terms:
                tremsAndConditionsFragment();
                break;
            case R.id.nav_help:
                helpCenterFragment();
                break;
            case R.id.nav_privacy:
                privacyPolicyFragment();
                break;
            case R.id.my_pot_borrowers:
                potentialborrowerFragment();
                break;

            case R.id.correspondant_home:
                dashBoardFragment();
                break;

            case R.id.my_borrowers:
                myApplicacntsFragment();
                break;

//            case R.id.my_verification:
//                pendingVerificationFragment();
//                break;
            case R.id.nav_logout:
                logoutSession();
                break;

            case R.id.nav_exit:
                PreferenceUtils.getInstance().setLoginAsActive(false);
                resetMainActivity();
                break;

            case R.id.faq:
                faqsFragment();
                break;
            case R.id.language:
//                languageFragment();
                break;


        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void homeFrag() {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .replace(R.id.main_container, homeFragment)
                .commit();


    }

    public void dashBoardFragment() {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        CorrespondantDashboardScreen homeFragment = new CorrespondantDashboardScreen(MainActivity.this);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .replace(R.id.main_container, homeFragment)
                .commit();


    }

    public void offlineFragment() {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        OfflineFragment homeFragment = new OfflineFragment(MainActivity.this);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(SettingFragment.class.getName())
                .replace(R.id.main_container, homeFragment)
                .commit();


    }


    public void languageFragment() {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof SettingFragment)) {


            SettingFragment mainFragment = new SettingFragment(MainActivity.this);

            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(SettingFragment.class.getName())
                    .replace(R.id.main_container, mainFragment)
                    .commit();


        }

    }

    public void myLoanFrag(Bundle bundle) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof MyLoansFragment)) {

            MyLoansFragment myloansFragment = new MyLoansFragment();
            myloansFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, myloansFragment)
                    .commit();
        }
    }

    public void myProfileFrag() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof UserProfileFrag)) {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            UserProfileFrag myloansFragment = new UserProfileFrag(MainActivity.this);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, myloansFragment)
                    .commit();
        }
    }


    public void mainProfileFrag(int pageNumber) {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof MainProfileFragment)) {

            Bundle bundle = new Bundle();
            bundle.putInt(MainActivity.this.getString(R.string.page_number), pageNumber);

            MainProfileFragment mainFragment = new MainProfileFragment();
            mainFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, mainFragment)
                    .addToBackStack(MainProfileFragment.class.getName())
                    .commit();


        }

    }

    public void offlineProfileMainFrag(UserProfile borrowerList, String cnic) {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof OfflineProfileMainFrag)) {


            OfflineProfileMainFrag mainFragment = new OfflineProfileMainFrag(borrowerList, cnic);


            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, mainFragment)
                    .addToBackStack(MainProfileFragment.class.getName())
                    .commit();


        }

    }

    public void OfflineMobileFrag(UserProfile userProfile, UserProfile borrowerList) {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof OfflineMobileWallet)) {


            OfflineMobileWallet mainFragment = new OfflineMobileWallet(userProfile, borrowerList);


            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, mainFragment)
                    .addToBackStack(OfflineMobileWallet.class.getName())
                    .commit();


        }

    }

    public void evaluationFragment() {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof EvaluationFragment)) {


            EvaluationFragment mainFragment = new EvaluationFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, mainFragment)
                    .addToBackStack(EvaluationFragment.class.getName())
                    .commit();


        }

    }

    public void userDetailFragment(String cnic, SearchBorrower searchBorrower) {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof UserDetailFragment)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("searchBorrower", searchBorrower);

            UserDetailFragment mainFragment = new UserDetailFragment(cnic, MainActivity.this);


            mainFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, mainFragment)
                    .addToBackStack(UserDetailFragment.class.getName())
                    .commit();


        }

    }

    public void userInfoFragment(LoanInfoRequest loanInfoRequest) {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof UserInfoFragment)) {

            UserInfoFragment mainFragment = new UserInfoFragment(MainActivity.this, loanInfoRequest);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, mainFragment)
                    .addToBackStack(UserDetailFragment.class.getName())
                    .commit();
        }
    }

    public void setLoanProductFragment(BorrowerList borrower) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (!(fragment instanceof LoanProductListFragment)) {
            LoanProductListFragment loanFragment = new LoanProductListFragment();

            Bundle bundle = new Bundle();
            bundle.putSerializable("borrower_details", borrower);
            loanFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, loanFragment)
                    .addToBackStack(UserDetailFragment.class.getName())
                    .commit();
        }
    }

    public void removeFragment() {

        getSupportFragmentManager().popBackStack();
    }

    public void setBankDetailFragment(BorrowerList borrower) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (!(fragment instanceof BankAccountFragment)) {

            BankAccountFragment bankAccountFragment = new BankAccountFragment(borrower);

            Bundle bundle = new Bundle();
            bundle.putSerializable("borrower_details", borrower);
            bankAccountFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, bankAccountFragment)
                    .addToBackStack(UserDetailFragment.class.getName())
                    .commit();
        }
    }

    public void setApplicationDetailFragment(BorrowerList myApplicants, LoanApplyRequest loanApplyRequest, boolean edit) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (!(fragment instanceof ApplicationDetailFragment)) {

            ApplicationDetailFragment applicationDetailFragment = new ApplicationDetailFragment(myApplicants,
                    loanApplyRequest, edit);

            Bundle bundle = new Bundle();
            applicationDetailFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, applicationDetailFragment)
                    .addToBackStack(UserDetailFragment.class.getName())
                    .commit();
        }
    }

    public void userWalletFragment(BorrowerReceiptObj body, LoanInfoRequest loanInfoRequest) {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof WalletDetailFragment)) {

            Bundle bundle = new Bundle();
            bundle.putSerializable("LoanInfoRequest", loanInfoRequest);

            WalletDetailFragment mainFragment = new WalletDetailFragment(MainActivity.this, body);

            mainFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, mainFragment)
                    .addToBackStack(WalletDetailFragment.class.getName())
                    .commit();


        }

    }

    public void userChequeFragment(BorrowerReceiptObj body, LoanInfoRequest loanInfoRequest) {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof ChequeReceiptFragment)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("LoanInfoRequest", loanInfoRequest);

            ChequeReceiptFragment mainFragment = new ChequeReceiptFragment(MainActivity.this, body);

            mainFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, mainFragment)
                    .addToBackStack(ChequeReceiptFragment.class.getName())
                    .commit();


        }

    }


    public void aboutUsFragment() {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof AboutUsFragment)) {


            AboutUsFragment mainFragment = new AboutUsFragment(MainActivity.this, aboutUs);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, mainFragment)
                    .addToBackStack(AboutUsFragment.class.getName())
                    .commit();


        }

    }

    public void surveyFragment(LoanApplyRequest loanApplyRequest) {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof SurveyScreen)) {


            SurveyScreen mainFragment = new SurveyScreen(MainActivity.this, loanApplyRequest);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, mainFragment)
                    .addToBackStack(SurveyScreen.class.getName())
                    .commit();


        }

    }

    public void tremsAndConditionsFragment() {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof TermsAndConditionsFragment)) {


            TermsAndConditionsFragment mainFragment = new TermsAndConditionsFragment(MainActivity.this, termandCondition);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, mainFragment)
                    .addToBackStack(TermsAndConditionsFragment.class.getName())
                    .commit();


        }

    }

    public void helpCenterFragment() {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof HelpCenterFragment)) {


            HelpCenterFragment mainFragment = new HelpCenterFragment(MainActivity.this, helpCenter);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, mainFragment)
                    .addToBackStack(HelpCenterFragment.class.getName())
                    .commit();


        }

    }

    public void privacyPolicyFragment() {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof PrivacyPolicyFragment)) {


            PrivacyPolicyFragment mainFragment = new PrivacyPolicyFragment(MainActivity.this, privacy);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, mainFragment)
                    .addToBackStack(PrivacyPolicyFragment.class.getName())
                    .commit();


        }

    }

    public void faqsFragment() {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof FAQSFragment)) {


            FAQSFragment mainFragment = new FAQSFragment(MainActivity.this, faqs);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, mainFragment)
                    .addToBackStack(FAQSFragment.class.getName())
                    .commit();


        }

    }


    public void mobileWalletFragment() {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof MobileWalletFragment)) {


            MobileWalletFragment mainFragment = new MobileWalletFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, mainFragment)
                    .addToBackStack(MobileWalletFragment.class.getName())
                    .commit();


        }

    }

    public void payFeeFragment() {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof FeePyamntFragment)) {


            FeePyamntFragment mainFragment = new FeePyamntFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, mainFragment)
                    .addToBackStack(FeePyamntFragment.class.getName())
                    .commit();


        }

    }


    private void totalPaidPayment() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof TotalpaidFragment)) {

            TotalpaidFragment totalpiadFragment = new TotalpaidFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, totalpiadFragment)
                    .commit();

        }

    }

//    public void setLoanDetailFragment(GetApplicationInfo getApplication) {
//
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
//
//
//        if (!(fragment instanceof MakepaymentFragment)) {
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("loan_detail", getApplication);
//
//            MakepaymentFragment makepaymentFragment = new MakepaymentFragment();
//            makepaymentFragment.setArguments(bundle);
//
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.main_container, makepaymentFragment)
//                    .addToBackStack(MakepaymentFragment.class.getName())
//                    .commit();
//        }
//
//    }


    public void paymentPaidFragment(GetApplicationInfo getApplication) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof PaymentPaidFragment)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("loan_detail", getApplication);

            PaymentPaidFragment paymentpiadFragment = new PaymentPaidFragment();
            paymentpiadFragment.setArguments(bundle);


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, paymentpiadFragment)
                    .addToBackStack("something")
                    .commit();

        }

    }

    public void payRecoveryUser(String outstandingbalance) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof RecoveryFragment)) {

            RecoveryFragment paymentpiadFragment = new RecoveryFragment(MainActivity.this, outstandingbalance);


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, paymentpiadFragment)
                    .addToBackStack("something")
                    .commit();

        }

    }

    public void borrowerRecoveryFragment(BorrowerReceiptObj borrowerReceiptObj) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof BorrowerRecoveryFragment)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("borrower_detail", borrowerReceiptObj);

            BorrowerRecoveryFragment paymentpiadFragment = new BorrowerRecoveryFragment();
            paymentpiadFragment.setArguments(bundle);


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, paymentpiadFragment)
                    .addToBackStack("something")
                    .commit();

        }

    }


    public void borrowerRecoveryFragment(GetApplicationInfo getApplication) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof PaymentPaidFragment)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("loan_detail", getApplication);

            PaymentPaidFragment paymentpiadFragment = new PaymentPaidFragment();
            paymentpiadFragment.setArguments(bundle);


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, paymentpiadFragment)
                    .addToBackStack("something")
                    .commit();

        }

    }


    //    private void setBottomNavigation() {
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//
//
//                switch (menuItem.getItemId()) {
//
//                    case R.id.navigation_home:
//                        return true;
//
//                }
//                return false;
//            }
//        });
//
//    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (fragment instanceof MyLoansFragment
                || fragment instanceof TotalpaidFragment ||
                fragment instanceof UserProfileFrag ||
                fragment instanceof BusinessCorrespondance) {

            homeFrag();

        } else if (fragment instanceof TermsAndConditionsFragment || fragment instanceof PrivacyPolicyFragment
                || fragment instanceof HelpCenterFragment || fragment instanceof AboutUsFragment) {
            if (PreferenceUtils.getInstance().getBorrowertype().equalsIgnoreCase("1")) {
                dashBoardFragment();
                clearBackStack();
            } else {
                homeFrag();
                clearBackStack();
            }

        } else {

            super.onBackPressed();
        }

    }

    private void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void callGeneralLoanFragment(LoanCategoryInfo loanCategoryInfo) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof GeneralLoanFragment)) {

            Bundle bundle = new Bundle();
            bundle.putSerializable("loan_detail", loanCategoryInfo);

            GeneralLoanFragment generalloanFragment = new GeneralLoanFragment();
            generalloanFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, generalloanFragment)
                    .addToBackStack("something")
                    .commit();

        }

    }

    public void LoanDetailFragment(LoanCategoryInfo loanCategoryInfo) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (!(fragment instanceof LoanDetailFragment)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("loan_detail", loanCategoryInfo);

            LoanDetailFragment loanDetailFragment = new LoanDetailFragment();
            loanDetailFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, loanDetailFragment)
                    .addToBackStack("something")
                    .commit();

        }
    }

    public void mobileFinancingLoanFragment(LoanCategoryInfo loanCategoryInfo) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof MobileFinancing)) {

            Bundle bundle = new Bundle();
            bundle.putSerializable("loan_detail", loanCategoryInfo);

            MobileFinancing mobileFinancing = new MobileFinancing();
            mobileFinancing.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, mobileFinancing)
                    .addToBackStack("something")
                    .commit();

        }

    }

//    private void checLocationPermission() {
//
//        if (ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//                requestPermissions(new String[]{
//                        Manifest.permission.READ_PHONE_STATE,
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE
//
//                }, locationRequestCode);
//            } else {
//
//                Log.i("ahmad", "ahmad Permission");
//            }
//        }
//    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == locationRequestCode) {
            // for each permission check if the user granted/denied them
            // you may want to group the rationale in a single dialog,
            // this is just an example
            for (int i = 0, len = permissions.length; i < len; i++) {
                String permission = permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    // user ejected the permission
                    boolean showRationale =
                            shouldShowRequestPermissionRationale(permission);
                    if (!showRationale) {
                        // user also CHECKED "never ask again"
                        // you can either enable some fall back,
                        // disable features of your app
                        // or open another dialog explaining
                        // again the permission and directing to
                        // the app setting
                        Log.i("ahmad", "ahmad");


                    } else if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permission)) {
                        //showRationale(permission, R.string.permission_denied_contacts);
                        // user did NOT check "never ask again"
                        // this is a good place to explain the user
                        // why you need the permission and ask if he wants
                        // to accept it (the rationale)
                        Log.i("ahmad", "ahmad");
                    }
                }
            }

            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.logout) {
//            logoutSession();
//        }
    }

    public void logoutSession() {

        PreferenceUtils.getInstance().clearPreferences();

        startActivity(new Intent(getApplicationContext(), NetworkCheckActivity.class));

        finishAffinity();
    }

    public void paymentDoneFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof PaymentTransactionFragment)) {

            PaymentTransactionFragment paymentDoneFragment = new PaymentTransactionFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, paymentDoneFragment)
                    .addToBackStack(PaymentTransactionFragment.class.getName())
                    .commit();
        }
    }

    public void businessCorrespondanceFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof BusinessCorrespondance)) {

            BusinessCorrespondance paymentDoneFragment = new BusinessCorrespondance(MainActivity.this);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, paymentDoneFragment)
                    .addToBackStack(BusinessCorrespondance.class.getName())
                    .commit();
        }
    }

    public void potentialborrowerFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof PotentialBorrowers)) {

            PotentialBorrowers paymentDoneFragment = new PotentialBorrowers(MainActivity.this);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, paymentDoneFragment)
                    .addToBackStack(PotentialBorrowers.class.getName())
                    .commit();
        }
    }

    public void myApplicacntsFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof MyInterestedApplicants)) {

            MyInterestedApplicants paymentDoneFragment = new MyInterestedApplicants(MainActivity.this);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, paymentDoneFragment)
                    .addToBackStack(MyInterestedApplicants.class.getName())
                    .commit();
        }
    }

    public void myBorrowerFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof MyBorrowersFragment)) {

            MyBorrowersFragment paymentDoneFragment = new MyBorrowersFragment(MainActivity.this);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, paymentDoneFragment)
                    .addToBackStack(MyBorrowersFragment.class.getName())
                    .commit();
        }
    }

    public void applicationInProcessFrag() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof ApplicationinProcessFrag)) {

            ApplicationinProcessFrag paymentDoneFragment = new ApplicationinProcessFrag(MainActivity.this);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, paymentDoneFragment)
                    .addToBackStack(ApplicationinProcessFrag.class.getName())
                    .commit();
        }
    }

    public void applicationInReadyToDisbursedProcessFrag() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof ApplicationinReadyToDisbursdFrag)) {

            ApplicationinReadyToDisbursdFrag paymentDoneFragment =
                    new ApplicationinReadyToDisbursdFrag(MainActivity.this);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, paymentDoneFragment)
                    .addToBackStack(ApplicationinProcessFrag.class.getName())
                    .commit();
        }
    }

    public void applicationPendingApprovalFrag() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof PendingApplicationinFrag)) {

            PendingApplicationinFrag paymentDoneFragment =
                    new PendingApplicationinFrag(MainActivity.this);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, paymentDoneFragment)
                    .addToBackStack(ApplicationinProcessFrag.class.getName())
                    .commit();
        }
    }

    public void activeLoanFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof ActiveLoanFrag)) {
            ActiveLoanFrag paymentDoneFragment = new ActiveLoanFrag(MainActivity.this);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, paymentDoneFragment)
                    .addToBackStack(ActiveLoanFrag.class.getName())
                    .commit();
        }
    }

    public void borrowerRevcoveryFragment(ApplicationOverDueList applicationOverDueList) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof BorrowerRecoveryFrag)) {
            BorrowerRecoveryFrag paymentDoneFragment = new BorrowerRecoveryFrag(applicationOverDueList);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, paymentDoneFragment)
                    .addToBackStack(BorrowerRecoveryFrag.class.getName())
                    .commit();
        }
    }

    public void overDueFragment() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof OverDueFragment)) {
            OverDueFragment paymentDoneFragment = new OverDueFragment(MainActivity.this);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, paymentDoneFragment)
                    .addToBackStack(OverDueFragment.class.getName())
                    .commit();
        }
    }

    public void pendingVerificationFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof VerificationPendingFrag)) {
            VerificationPendingFrag paymentDoneFragment = new VerificationPendingFrag(MainActivity.this);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, paymentDoneFragment)
                    .addToBackStack(VerificationPendingFrag.class.getName())
                    .commit();
        }
    }

    public void addNewborrowerFragment(BorrowerList borrowerList, String value, List<String> responselist) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof AddNewBorrower)) {

            AddNewBorrower paymentDoneFragment = new AddNewBorrower(MainActivity.this, borrowerList, responselist);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, paymentDoneFragment)
                    .addToBackStack(AddNewBorrower.class.getName())
                    .commit();
        }
    }

    public void readyToDisburse() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof ReadytoDisburseFragment)) {

            ReadytoDisburseFragment paymentDoneFragment = new ReadytoDisburseFragment(MainActivity.this);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, paymentDoneFragment)
                    .addToBackStack(ReadytoDisburseFragment.class.getName())
                    .commit();
        }
    }

    public void borrowerLoginAsFragment(BorrowerList borrowerList) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof BorrowerLoginAs)) {

            BorrowerLoginAs paymentDoneFragment = new BorrowerLoginAs(MainActivity.this, borrowerList);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, paymentDoneFragment)
                    .addToBackStack(BorrowerLoginAs.class.getName())
                    .commit();
        }
    }

    public void borrowerOTPFragment(MyApplicants borrowerList) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof OtpScreenFrag)) {

            OtpScreenFrag paymentDoneFragment = new OtpScreenFrag(MainActivity.this, borrowerList);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, paymentDoneFragment)
                    .addToBackStack(BorrowerLoginAs.class.getName())
                    .commit();
        }
    }

    public void verificationFragment(PendingList pendingList) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof VerificationFrag)) {

            VerificationFrag paymentDoneFragment = new VerificationFrag(MainActivity.this, pendingList);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, paymentDoneFragment)
                    .addToBackStack(VerificationFrag.class.getName())
                    .commit();
        }
    }

    public void verificationMapFragment(String lon, String lat, String name, String phoneno) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof MapFragment)) {

            MapFragment paymentDoneFragment = new MapFragment(lon, lat, name, phoneno);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, paymentDoneFragment)
                    .addToBackStack(MapFragment.class.getName())
                    .commit();
        }
    }

    public void getPagesData() {
        EasyLoanBusiness business = new EasyLoanBusiness();
        business.aboutUs(new ResponseCallBack<PagesList>() {
            @Override
            public void onSuccess(PagesList body) {

                for (int i = 0; i < body.getPageList().size(); i++) {
                    if (body.getPageList().get(i).name.equalsIgnoreCase("about")) {

                        aboutUs = body.getPageList().get(i).content;

                    } else if (body.getPageList().get(i).name.equalsIgnoreCase("faqs")) {

                        faqs = body.getPageList().get(i).content;

                    } else if (body.getPageList().get(i).name.equalsIgnoreCase("privacy_policy")) {

                        privacy = body.getPageList().get(i).content;

                    } else if (body.getPageList().get(i).name.equalsIgnoreCase("help_center")) {

                        helpCenter = body.getPageList().get(i).content;

                    } else if (body.getPageList().get(i).name.equalsIgnoreCase("terms_and_condition")) {

                        termandCondition = body.getPageList().get(i).content;
                    }
                }
            }

            @Override
            public void onFailure(String message) {

                AlertUtils.showAlert(MainActivity.this, message);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("ahmad", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        PreferenceUtils.getInstance().setSessionActive(false);

        Log.e("ahmad", "onStop");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("eQard", "enetr activity crop");
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (fragment instanceof MainProfileFragment) {
            MainProfileFragment mainProfileFragment = (MainProfileFragment) fragment;
            mainProfileFragment.setDataIntent(requestCode, resultCode, data);
        } else if (fragment instanceof OfflineProfileMainFrag) {
            OfflineProfileMainFrag offlineProfileMainFrag = (OfflineProfileMainFrag) fragment;
            offlineProfileMainFrag.setDataIntent(requestCode, resultCode, data);
        } else if (fragment instanceof BankAccountFragment) {
            BankAccountFragment bankAccountFragment = (BankAccountFragment) fragment;
            bankAccountFragment.setDataIntent(requestCode, resultCode, data);
        } else if (fragment instanceof ApplicationDetailFragment) {
            ApplicationDetailFragment applicationDetailFragment = (ApplicationDetailFragment) fragment;
            applicationDetailFragment.setDataIntent(requestCode, resultCode, data);
        }


//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                String pathe = result.getUriFilePath(context, false);
//                Bitmap bitmap = AppUtils.checkPhotoOrientationAndGetImage(pathe);
//                Log.d("eQard" , " Activity result crop is ok");
//                handleCrop(resultCode, bitmap);
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Toast.makeText(context, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
//            }
//        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ahmad", "onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void resetMainActivity() {

        startActivity(new Intent(MainActivity.this, SplashActivity.class));
        finish();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void setAgreementFragment(int i, Bundle bundle) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (i == 1) {
            if (!(fragment instanceof AgreementMusharkaFragment)) {

                AgreementMusharkaFragment agreementFragment = new AgreementMusharkaFragment();
                agreementFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_container, agreementFragment)
                        .addToBackStack("something")
                        .commit();

            }
        } else if (i == 2) {
            if (!(fragment instanceof AgreementIjarahFragment)) {

                AgreementIjarahFragment agreementFragment = new AgreementIjarahFragment();
                agreementFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_container, agreementFragment)
                        .addToBackStack("something")
                        .commit();

            }
        }

    }

    public void setImagePreview(Bitmap bitmap) {

        ImagePreviewFRagment imagePreviewFRagment = new ImagePreviewFRagment(bitmap);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, imagePreviewFRagment)
                .addToBackStack("something")
                .commit();
    }

    void getBorrowerList(String value, String search) {

        PotentialBorrowerRequest potentialBorrowerRequest = new PotentialBorrowerRequest();
        potentialBorrowerRequest.parameter = value;

        potentialBorrowerRequest.search = search;
        potentialBorrowerRequest.type = "";

        EasyLoanBusiness business = new EasyLoanBusiness();
        business.getBorrowerList(potentialBorrowerRequest, new ResponseCallBack<GetBorrowerList>() {
            @Override
            public void onSuccess(GetBorrowerList body) {

                PreferenceUtils.getInstance().addPotentialBorrower(body);

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}
