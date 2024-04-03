package com.nano.liteloan.correspondant.fragment;

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

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.BusinessCorrespondant;
import com.nano.liteloan.correspondant.correspondantadapter.ActiveLoanListAdapter;
import com.nano.liteloan.correspondant.correspondantadapter.ReadyToDisburseAdapter;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.OTPResponse;
import com.nano.liteloan.info.businesscorrespondant.ApplicationOverDueList;
import com.nano.liteloan.info.businesscorrespondant.CorrespondantLoginAsRequest;
import com.nano.liteloan.info.businesscorrespondant.GetDataResponce;
import com.nano.liteloan.info.businesscorrespondant.VerifiedBorrower;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.OnItemClickListener;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.santalu.maskedittext.MaskEditText;

import java.util.List;

public class ReadytoDisburseFragment extends Fragment {
    private MainActivity context;
    private MaskEditText edCnic;
    private RecyclerView recyclerView;
    private ImageView ivNoBorrower;
    private ImageView btSearch;
    private ReadyToDisburseAdapter myLoanAdapter;

    public ReadytoDisburseFragment(MainActivity context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_readyto_disburse, container, false);
        edCnic = view.findViewById(R.id.ed_cnic);
        ivNoBorrower = view.findViewById(R.id.no_borrower);
        btSearch = view.findViewById(R.id.search);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myLoanAdapter != null){
                    myLoanAdapter.getFilter().filter(edCnic.getText().toString());
                }
            }
        });
//        getBorrowerList();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        getData();
        setSearchView();

        return view ;
    }
    private void setSearchView() {

        edCnic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (myLoanAdapter != null){
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
        userBusiness.getData("ready_to_disburse",new ResponseCallBack<GetDataResponce>() {
            @Override
            public void onSuccess(GetDataResponce body) {

                setLoansRecyclerView(body.verifiedlist.verifiedBorrowers);


                dialog.dismiss();

            }

            @Override
            public void onFailure(String message) {

                dialog.dismiss();


                AlertUtils.showAlert(context, message);
            }
        });

    }

    private void setLoansRecyclerView(List<VerifiedBorrower> applicationlist) {

        myLoanAdapter = new ReadyToDisburseAdapter(context , applicationlist);
        recyclerView.setAdapter(myLoanAdapter);

        myLoanAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CorrespondantLoginAsRequest request = new CorrespondantLoginAsRequest();
                request.userId = String.valueOf(applicationlist.get(position).userid);

                request.phone = applicationlist.get(position).phoneno;
                setOtpLoginAs(request);
//                context.borrowerRevcoveryFragment(applicationlist.get(position));

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


}