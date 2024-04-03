package com.nano.liteloan.correspondant.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.BusinessCorrespondant;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.correspondant.correspondantadapter.MyApplicantListAdapter;
import com.nano.liteloan.correspondant.correspondantadapter.MyBorrowersAdapter;
import com.nano.liteloan.correspondant.correspondantadapter.PotentialBorrowerListAdapter;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.GetBorrowerList;
import com.nano.liteloan.info.OTPResponse;
import com.nano.liteloan.info.businesscorrespondant.ApplicationOverDueList;
import com.nano.liteloan.info.businesscorrespondant.CorrespondantLoginAsRequest;
import com.nano.liteloan.info.businesscorrespondant.GetDataResponce;
import com.nano.liteloan.info.businesscorrespondant.OverdueLoans;
import com.nano.liteloan.info.businesscorrespondant.PotentialBorrowersList;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.OnItemClickListener;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.santalu.maskedittext.MaskEditText;

import java.util.List;

public class MyBorrowersFragment extends Fragment implements View.OnClickListener {

    private MainActivity context;
    private MaskEditText edCnic;
    private RecyclerView recyclerView;
    private ImageView ivNoBorrower;
    private ImageView btSearch;
    private MyApplicantListAdapter myLoanAdapter;
    private TextView tvDisbursed, tvComplete, tvAll;
    private List<ApplicationOverDueList> borrowerlist;


    public MyBorrowersFragment(MainActivity context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_borrowers, container, false);

        tvAll = view.findViewById(R.id.all);
        tvAll.setOnClickListener(this);
        tvDisbursed = view.findViewById(R.id.disbursed);
        tvDisbursed.setOnClickListener(this);
        tvComplete = view.findViewById(R.id.completed);
        tvComplete.setOnClickListener(this);

        edCnic = view.findViewById(R.id.ed_cnic);
        ivNoBorrower = view.findViewById(R.id.no_borrower);
        btSearch = view.findViewById(R.id.search);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchUser();
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

    private void searchUser() {

        if (myLoanAdapter != null) {
            myLoanAdapter.getFilter().filter(edCnic.getText().toString());
        }
//
//        if (edCnic.getText().toString().length() < 15) {
//            edCnic.setError("Enter Valid Cnic");
//            return;
//        } else {
////            serachBorrower(edCnic.getText().toString());
//        }
    }
//    void serachBorrower(String cnic) {
//        final AlertDialog dialog = AlertUtils.showLoader(context);
//
//        if (dialog != null) {
//            dialog.show();
//        }
//
//        SearchBorrowerRequest searchBorrowerRequest = new SearchBorrowerRequest();
//        searchBorrowerRequest.setCnic(cnic);
//
//        EasyLoanBusiness business = new EasyLoanBusiness();
//        business.searchBorrower(searchBorrowerRequest, new ResponseCallBack<SearchBorrower>() {
//            @Override
//            public void onSuccess(SearchBorrower body) {
//
//                dialog.dismiss();
////                setLoansRecyclerView(body);
//
//
//            }
//
//            @Override
//            public void onFailure(String message) {
//
//                dialog.dismiss();
//                AlertUtils.showAlert(getActivity(), message);
//            }
//        });
//    }

    private void getData() {

        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }

        BusinessCorrespondant userBusiness = new BusinessCorrespondant();

        userBusiness.getData("my_borrower", new ResponseCallBack<GetDataResponce>() {
            @Override
            public void onSuccess(GetDataResponce body) {
                dialog.dismiss();

                setLoansRecyclerView(body.potentialborrower.applicationlist);
                borrowerlist = body.potentialborrower.applicationlist;
            }

            @Override
            public void onFailure(String message) {
                dialog.dismiss();


                AlertUtils.showAlert(context, message);
            }
        });

    }

    private void setLoansRecyclerView(List<ApplicationOverDueList> borrowerlist) {

        myLoanAdapter = new MyApplicantListAdapter(context, borrowerlist , "all");
        recyclerView.setAdapter(myLoanAdapter);

        myLoanAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CorrespondantLoginAsRequest request = new CorrespondantLoginAsRequest();
                request.userId = String.valueOf(borrowerlist.get(position).userid);

                request.phone = borrowerlist.get(position).phoneno;
                setOtpLoginAs(request);

            }
        });


    }

    private void setOtpLoginAs(CorrespondantLoginAsRequest request) {

        BusinessCorrespondant userBusiness = new BusinessCorrespondant();
        userBusiness.loginAsSomeOne(request, new ResponseCallBack<OTPResponse>() {
            @Override
            public void onSuccess(OTPResponse body) {

                if (body != null && body.userDate != null) {

                    PreferenceUtils.getInstance().setLoginAsActive(true);
                    PreferenceUtils.getInstance().addUserDetail(body.userDate);
                    PreferenceUtils.getInstance().addAccountDetail(body.liteaccount);

                    context.resetMainActivity();
                }
            }

            @Override
            public void onFailure(String message) {

                AlertUtils.showAlert(context, message);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.disbursed:
                myLoanAdapter.filter(borrowerlist, "disburse");


                tvAll.setTextColor(Color.parseColor("#FFFFFF"));
                tvComplete.setTextColor(Color.parseColor("#FFFFFF"));
                tvDisbursed.setTextColor(Color.parseColor("#CCA348"));

                break;
            case R.id.added:
                myLoanAdapter.filter(borrowerlist, "added");


                tvAll.setTextColor(Color.parseColor("#FFFFFF"));
                tvComplete.setTextColor(Color.parseColor("#FFFFFF"));
                tvDisbursed.setTextColor(Color.parseColor("#FFFFFF"));

                break;

            case R.id.completed:
                myLoanAdapter.filter(borrowerlist, "completed");


                tvAll.setTextColor(Color.parseColor("#FFFFFF"));
                tvComplete.setTextColor(Color.parseColor("#CCA348"));
                tvDisbursed.setTextColor(Color.parseColor("#FFFFFF"));
                break;

            case R.id.all:
                myLoanAdapter.filter(borrowerlist, "all");


                tvAll.setTextColor(Color.parseColor("#CCA348"));
                tvComplete.setTextColor(Color.parseColor("#FFFFFF"));
                tvDisbursed.setTextColor(Color.parseColor("#FFFFFF"));
                break;
        }
    }
}