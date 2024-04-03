package com.nano.liteloan.correspondant.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.BusinessCorrespondant;
import com.nano.liteloan.business.impl.UserBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.ProfileCreateResponseInfo;
import com.nano.liteloan.info.UserDate;
import com.nano.liteloan.info.UserProfile;
import com.nano.liteloan.info.businesscorrespondant.CorrespondantDashboardObj;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by Muhammad Umer on 07/06/2020.
 * Modified by Muhammad Ahmad.
 */
public class CorrespondantDashboardScreen extends Fragment implements View.OnClickListener {

    private final MainActivity context;
    private TextView tvEmail, tvName;
    private RelativeLayout rlEmail, rlBranch;
    private ImageView profileImage;
    private TextView tvBranch;
    private CorrespondantDashboardObj correspondantDashboardObj = null;
    private TextView tvApplicants, tvAppinprocess, tvOverdueAmount;
    private TextView tvRadyToDisbursdCount;
    private TextView tvRadyToDisbursdAmount;
    private TextView tvBorrower, tvdisbursedamount, tvActiveloan, tvOutstanding, tvOverdue;
    private TextView tvPendingApproval, tvPendingApprovalAmount, tvPotentialClient;

    public CorrespondantDashboardScreen(MainActivity context) {
        this.context = context;
        context.setNavigationMenu(1);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_correspondant_dashboard_screen, container, false);

        initializeView(view);


        getProfile();
        getDashboard();
        return view;
    }

    private void initializeView(View view) {

        tvBranch = view.findViewById(R.id.tv_branch);
        rlBranch = view.findViewById(R.id.rlbranch);
        tvBorrower = view.findViewById(R.id.myborrowers);
        tvdisbursedamount = view.findViewById(R.id.amountdisburse);
        tvActiveloan = view.findViewById(R.id.activeloan);
        tvOutstanding = view.findViewById(R.id.outstandingamount);
        tvPotentialClient = view.findViewById(R.id.potential_applicants);
        tvOverdue = view.findViewById(R.id.overdueloans);
        CardView cvPendingApproval = view.findViewById(R.id.cvPending_approval);
        cvPendingApproval.setOnClickListener(this);
        tvPendingApproval = view.findViewById(R.id.tv_pending_approval);
        tvPendingApprovalAmount = view.findViewById(R.id.tv_pending_approval_amount);
        CardView cvReadytodisbursed = view.findViewById(R.id.cvReadytodisbursed);
        cvReadytodisbursed.setOnClickListener(this);
        tvRadyToDisbursdCount = view.findViewById(R.id.readytodisbursd);
        tvRadyToDisbursdAmount = view.findViewById(R.id.amountreadytodisburse);

        tvOverdueAmount = view.findViewById(R.id.overdueammount);

        tvApplicants = view.findViewById(R.id.applicants);
        tvAppinprocess = view.findViewById(R.id.appinprocess);

        CardView cvmyborrower = view.findViewById(R.id.cvMyBOrrowers);
        cvmyborrower.setOnClickListener(this);

        LinearLayout cvAppinProcess = view.findViewById(R.id.cvappinprocess);
        cvAppinProcess.setOnClickListener(this);

        CardView cvActiveLoan = view.findViewById(R.id.cvActiveLoan);
        cvActiveLoan.setOnClickListener(this);
        LinearLayout cvPotentialBorrwer = view.findViewById(R.id.cvPotentialBorrwer);
        cvPotentialBorrwer.setOnClickListener(this);
        CardView cvOverDue = view.findViewById(R.id.cvOverdue);
        cvOverDue.setOnClickListener(this);
        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(context::dashBoardFragment);
        ImageView ivDrawer = view.findViewById(R.id.iv_drawer);
        ivDrawer.setOnClickListener(view1 -> context.openDrawer());
        LinearLayout cvPotentialClient = view.findViewById(R.id.cvPotentialClient);
        cvPotentialClient.setOnClickListener(this);


        tvEmail = view.findViewById(R.id.tv_email);
        rlEmail = view.findViewById(R.id.rlemail);

        tvName = view.findViewById(R.id.tv_name);

        profileImage = view.findViewById(R.id.imageView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cvPotentialBorrwer:
                if (correspondantDashboardObj != null) {
                    context.myApplicacntsFragment();
                }

                return;

            case R.id.cvActiveLoan:
                if (correspondantDashboardObj != null) {
                    context.activeLoanFragment();
                }

                return;

            case R.id.cvOverdue:
                if (correspondantDashboardObj != null) {
                    context.overDueFragment();
                }
                return;

            case R.id.cvMyBOrrowers:
                if (correspondantDashboardObj != null) {
                    context.myBorrowerFragment();
                }
                return;
            case R.id.cvappinprocess:
                if (correspondantDashboardObj != null) {
                    context.applicationInProcessFrag();
                }
                return;
            case R.id.cvReadytodisbursed:
                if (correspondantDashboardObj != null) {
                    context.applicationInReadyToDisbursedProcessFrag();
                }
                return;

            case R.id.cvPending_approval:
                if (correspondantDashboardObj != null) {
                    context.applicationPendingApprovalFrag();
                }
                return;
            case R.id.cvPotentialClient:
                if (correspondantDashboardObj != null) {
                    context.potentialborrowerFragment();
                }
                return;

        }
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

                PreferenceUtils.getInstance().setRemainingBalance(String.valueOf(body.userDate.outbalance));
                PreferenceUtils.getInstance().setBorrowerType(String.valueOf(body.userDate.getUsertype()));

                if (dialog != null) {
                    dialog.dismiss();
                }

                PreferenceUtils.getInstance()
                        .setRemainingBalance(String.valueOf(body.userDate.outbalance));
                PreferenceUtils.getInstance()
                        .setBorrowerType(String.valueOf(body.userDate.getUsertype()));

                context.hideItem(Integer.valueOf(PreferenceUtils.getInstance().getBorrowertype()));
                PreferenceUtils.getInstance().setcorrespondantId(body.userDate.userId);

                if (body.userDate.branchname != null && !body.userDate.branchname.equalsIgnoreCase("")) {
                    tvBranch.setText(body.userDate.branchname);
                    rlBranch.setVisibility(View.VISIBLE);
                } else {
                    rlBranch.setVisibility(View.GONE);
                }

                if (body.userDate.email != null && !body.userDate.email.equalsIgnoreCase("")) {
                    rlEmail.setVisibility(View.VISIBLE);
                    tvEmail.setText(body.userDate.getEmail());
                } else {
                    rlEmail.setVisibility(View.GONE);
                }


                setContent(body.userDate);


                dialog.dismiss();


            }

            @Override
            public void onFailure(String message) {

                dialog.dismiss();


                AlertUtils.showAlert(context, message);
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void setContent(UserProfile userDate) {

        UserDate date = PreferenceUtils.getInstance().getCorrespondentUserDetail();

        if (userDate != null) {

            if (userDate.pin != null && !userDate.pin.isEmpty()) {

                PreferenceUtils.getInstance().setPinCode(userDate.pin);
            }

            if (userDate.name != null && !userDate.name.isEmpty()) {

                tvName.setText(userDate.name);
                context.tvHeaderName.setText(userDate.name);

                date.setName(userDate.name);
            } else {


            }


            if (userDate.image != null && !userDate.image.isEmpty()) {

                Picasso.get()
                        .load(userDate.image)
                        .into(context.profileImage);

                Picasso.get()
                        .load(userDate.image)
                        .into(profileImage);

                date.setImageUrl(userDate.image);

            } else {
                context.showCorrespondantInfoFragment();
            }


            PreferenceUtils.getInstance().addCorrespondentUserDetail(date);


        }
    }


    private void getDashboard() {

        final androidx.appcompat.app.AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }

        BusinessCorrespondant userBusiness = new BusinessCorrespondant();
        userBusiness.getDashboard(new ResponseCallBack<CorrespondantDashboardObj>() {
            @Override
            public void onSuccess(CorrespondantDashboardObj body) {


                correspondantDashboardObj = body;
                tvBorrower.setText(String.valueOf(body.potentialborrower));
                tvdisbursedamount.setText(AppUtils
                        .getFormatAmount(body.amountdisbursed) + " Pkr");
                tvActiveloan.setText(String.valueOf(body.activeloans));
                tvOutstanding.setText((AppUtils
                        .getFormatAmount(body.outstandingamount)) + " Pkr");
                tvOverdue.setText(String.valueOf(body.overdueloans));
                tvRadyToDisbursdCount.setText(String.valueOf(body.readytodisburse));
                tvRadyToDisbursdAmount.setText(AppUtils.getFormatAmount(body.readyToDisbursedAmount) + " Pkr");
                tvPendingApproval.setText(String.valueOf(body.pendingApplicationCount));
                tvPendingApprovalAmount.setText(AppUtils.getFormatAmount(body.pendingApplicationAmount) + " Pkr");
                tvApplicants.setText(String.valueOf(body.interestedApplicants));
                tvPotentialClient.setText(String.valueOf(body.potentialCount));
                tvOverdueAmount.setText((AppUtils
                        .getFormatAmount(body.ourdueamount)) + " Pkr");

                tvAppinprocess.setText(String.valueOf(body.myapplicants));


                dialog.dismiss();

            }

            @Override
            public void onFailure(String message) {

                dialog.dismiss();


                AlertUtils.showAlert(context, message);
            }
        });

    }
}