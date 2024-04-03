package com.nano.liteloan.correspondant.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.BusinessCorrespondant;
import com.nano.liteloan.correspondant.correspondantadapter.MyBorrowersAdapter;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.BorrowerList;
import com.nano.liteloan.info.LoanApplyRequest;
import com.nano.liteloan.info.UserProfile;
import com.nano.liteloan.info.businesscorrespondant.GetDataResponce;
import com.nano.liteloan.info.businesscorrespondant.MyApplicants;
import com.nano.liteloan.info.responce.ApplicationDetailRespose;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.LoginAsItemClick;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.santalu.maskedittext.MaskEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PendingApplicationinFrag extends Fragment implements View.OnClickListener {

    private MainActivity context;
    private MaskEditText edCnic;
    private RecyclerView recyclerView;
    private ImageView ivNoBorrower;
    private ImageView btSearch;
    private MyBorrowersAdapter myLoanAdapter;
    List<MyApplicants> applicationlist = new ArrayList<>();
    private TextView tvPending, tvAdded, tvDisbursed, tvComplete, tvAll;


    public PendingApplicationinFrag(MainActivity mainActivity) {
        this.context = mainActivity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_applicationin_pending, container, false);

        tvAll = view.findViewById(R.id.all);
        tvAll.setOnClickListener(this);
        tvAdded = view.findViewById(R.id.added);
        tvAdded.setOnClickListener(this);
        tvPending = view.findViewById(R.id.pending);
        tvPending.setOnClickListener(this);
        tvDisbursed = view.findViewById(R.id.disbursed);
        tvDisbursed.setOnClickListener(this);
        tvComplete = view.findViewById(R.id.completed);
        tvComplete.setOnClickListener(this);

        edCnic = view.findViewById(R.id.ed_cnic);
        ivNoBorrower = view.findViewById(R.id.no_borrower);
        btSearch = view.findViewById(R.id.search);
        btSearch.setOnClickListener(view1 -> {

            if (myLoanAdapter != null) {
                myLoanAdapter.getFilter().filter(edCnic.getText().toString());
            }
        });
//        getBorrowerList();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        getData();

        setSearchView();
        return view;
    }

    private void setSearchView() {

        edCnic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (myLoanAdapter != null) {
                    myLoanAdapter.getFilter().filter(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void getData() {

        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }

        BusinessCorrespondant userBusiness = new BusinessCorrespondant();

        userBusiness.getData("my_applicants", new ResponseCallBack<GetDataResponce>() {
            @Override
            public void onSuccess(GetDataResponce body) {
                dialog.dismiss();
                getOnlyInProcess(body);


            }


            @Override
            public void onFailure(String message) {
                dialog.dismiss();


                AlertUtils.showAlert(context, message);
            }
        });

    }

    private void getOnlyInProcess(GetDataResponce body) {


        List<MyApplicants> tempList = new ArrayList<>();


        for (int i = 0; i < body.myApplicants.size(); i++) {
            if (Objects.equals(body.myApplicants.get(i).userstatus, "pending")) {
                tempList.add(body.myApplicants.get(i));
            }
        }
        body.myApplicants = tempList;

        applicationlist = tempList;
        setLoansRecyclerView(body.myApplicants);
    }

    private void setLoansRecyclerView(List<MyApplicants> applicationlist) {

        myLoanAdapter = new MyBorrowersAdapter(context, applicationlist, "all");
        recyclerView.setAdapter(myLoanAdapter);

        myLoanAdapter.setOnItemClickListener(new LoginAsItemClick() {
            @Override
            public void onItemClick(View view, int position, MyApplicants myApplicants) {
                //Toast.makeText(context, "dasasdasd", Toast.LENGTH_SHORT).show();

                getApplicationDetail(myApplicants.application_id);
                //context.setApplicationDetailFragment(myApplicants);
            }
        });


    }

    private void getApplicationDetail(String application_id) {

        final AlertDialog dialog = AlertUtils.showLoader(context);
        dialog.show();
        BusinessCorrespondant businessCorrespondant = new BusinessCorrespondant();
        businessCorrespondant.getApplicationDetails(application_id, new ResponseCallBack<ApplicationDetailRespose>() {
            @Override
            public void onSuccess(ApplicationDetailRespose body) {
                dialog.dismiss();

                LoanApplyRequest applyRequest = new LoanApplyRequest();

                BorrowerList borrowerList = new BorrowerList();
                borrowerList.status = body.application.status;
                borrowerList.name = body.borrower.name;
                borrowerList.cnic = body.borrower.cnic;
                borrowerList.userDetail = new UserProfile();
                borrowerList.userDetail.currentAddress = body.userProfile.userAddress;
                borrowerList.parentage = body.userProfile.parentage;
                borrowerList.bankDetail = body.userAcountDetail;
                borrowerList.userDetail.parentage = body.userProfile.parentage;
                borrowerList.userDetail.userId = body.application.userId;

                borrowerList.phoneno = body.borrower.phoneNo;
                applyRequest.assetPrice = body.application.assetPrice;
                applyRequest.assetName = body.application.assetName;
                applyRequest.asstMarketValue = body.application.assetMarketValue;
                applyRequest.onlineAsset = body.application.assetPic;
                applyRequest.onlineCNICBack = body.application.picCnicBack;
                applyRequest.onLineFront = body.application.picCnicFront;
                applyRequest.onlineIjarha = body.application.ijarahPic;
                applyRequest.onlineMusharka = body.application.musharkaPic;
                applyRequest.onlineProfile = body.application.borrowerPic;
                applyRequest.productId = body.application.productId;
                applyRequest.applicationId = application_id;
                applyRequest.dateTime = body.application.updatedAt;
                applyRequest.intervalTime = body.application.intervalTime;

                context.setApplicationDetailFragment(borrowerList, applyRequest, true);
            }

            @Override
            public void onFailure(String message) {
                dialog.dismiss();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.added:

                myLoanAdapter.filter(applicationlist, "added");

                tvAdded.setTextColor(Color.parseColor("#CCA348"));
                tvPending.setTextColor(Color.parseColor("#FFFFFF"));
                tvAll.setTextColor(Color.parseColor("#FFFFFF"));
                tvComplete.setTextColor(Color.parseColor("#FFFFFF"));
                tvDisbursed.setTextColor(Color.parseColor("#FFFFFF"));

                break;

            case R.id.pending:
                myLoanAdapter.filter(applicationlist, "pending");

                tvAdded.setTextColor(Color.parseColor("#FFFFFF"));
                tvPending.setTextColor(Color.parseColor("#CCA348"));
                tvAll.setTextColor(Color.parseColor("#FFFFFF"));
                tvComplete.setTextColor(Color.parseColor("#FFFFFF"));
                tvDisbursed.setTextColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.disbursed:
                myLoanAdapter.filter(applicationlist, "disburse");

                tvAdded.setTextColor(Color.parseColor("#FFFFFF"));
                tvPending.setTextColor(Color.parseColor("#FFFFFF"));
                tvAll.setTextColor(Color.parseColor("#FFFFFF"));
                tvComplete.setTextColor(Color.parseColor("#FFFFFF"));
                tvDisbursed.setTextColor(Color.parseColor("#CCA348"));
                break;

            case R.id.completed:
                myLoanAdapter.filter(applicationlist, "completed");

                tvAdded.setTextColor(Color.parseColor("#FFFFFF"));
                tvPending.setTextColor(Color.parseColor("#FFFFFF"));
                tvAll.setTextColor(Color.parseColor("#FFFFFF"));
                tvComplete.setTextColor(Color.parseColor("#CCA348"));
                tvDisbursed.setTextColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.all:
                myLoanAdapter.filter(applicationlist, "all");

                tvAdded.setTextColor(Color.parseColor("#FFFFFF"));
                tvPending.setTextColor(Color.parseColor("#FFFFFF"));
                tvAll.setTextColor(Color.parseColor("#CCA348"));
                tvComplete.setTextColor(Color.parseColor("#FFFFFF"));
                tvDisbursed.setTextColor(Color.parseColor("#FFFFFF"));
                break;
        }
    }
}