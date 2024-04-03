package com.nano.liteloan.correspondant.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nano.liteloan.R;
import com.nano.liteloan.business.PostResponseRequest;
import com.nano.liteloan.business.impl.BusinessCorrespondant;
import com.nano.liteloan.correspondant.correspondantdialoguefragment.BorrowerInterestedFragment;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.BorrowerList;
import com.nano.liteloan.info.businesscorrespondant.AddBorrower;
import com.nano.liteloan.info.businesscorrespondant.AddBorrowerResponce;
import com.nano.liteloan.info.businesscorrespondant.MyApplicants;
import com.nano.liteloan.info.responce.RecoveryPayResponse;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.alerts.AlertUtils;


public class BorrowerLoginAs extends Fragment {

    private MainActivity context;

    private EditText edPhoneno;
    private TextView tvName , tvCnic , tvSanctionno;
    private BorrowerList borrowerList;
    private Button btLogin;

    public BorrowerLoginAs(MainActivity mainActivity, BorrowerList borrowerList) {
        this.context = mainActivity;
        this.borrowerList = borrowerList;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_borrower_login_as, container, false);

        edPhoneno = view.findViewById(R.id.phoneno);

        tvName = view.findViewById(R.id.name);
        tvCnic = view.findViewById(R.id.cnic);
        tvSanctionno = view.findViewById(R.id.sanctionno);

        btLogin = view.findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edPhoneno.getText().toString().equalsIgnoreCase("")){
                    SubmitData();
                } else {

                }
            }
        });

        ImageView image = view.findViewById(R.id.back);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.getSupportFragmentManager().popBackStackImmediate();
            }
        });

        setContent();

        return view;
    }

    private void setContent() {
        if(borrowerList != null){
            if(borrowerList.phoneno != null
                && !borrowerList.phoneno.equalsIgnoreCase("")){
                edPhoneno.setText(borrowerList.phoneno);
            }
            if(borrowerList.cnic != null
                    && !borrowerList.cnic.equalsIgnoreCase("")){
                tvCnic.setText(borrowerList.cnic);
            }
            if(borrowerList.name != null
                    && !borrowerList.name.equalsIgnoreCase("")){
                tvName.setText(borrowerList.name);
            }
            if(borrowerList.sanctionno != null
                    && !borrowerList.sanctionno.equalsIgnoreCase("")){

                tvSanctionno.setText(borrowerList.sanctionno);
            }

        }
    }

    private void SubmitData() {

        AddBorrower addBorrower = new AddBorrower();
        if(edPhoneno.getText().toString().substring(0,2).equalsIgnoreCase("03")){
            addBorrower.phoneno = "92" + edPhoneno.getText().toString().substring(1);

        } else {
            addBorrower.phoneno = edPhoneno.getText().toString();
        }

        addBorrower.cnic = borrowerList.cnic;
        addBorrower.name = borrowerList.name;

        final androidx.appcompat.app.AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }


        BusinessCorrespondant userBusiness = new BusinessCorrespondant();
        userBusiness.addBorrower(addBorrower ,new ResponseCallBack<AddBorrowerResponce>() {
            @Override
            public void onSuccess(AddBorrowerResponce body) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                MyApplicants myApplicants = new MyApplicants();
                myApplicants.userId = body.responseObj.userId;
                myApplicants.cnic = body.responseObj.cnic;
                myApplicants.name = body.responseObj.name;
                myApplicants.phoneno = body.responseObj.phoneno;
                myApplicants.otpflag = body.responseObj.otpflag;
                context.borrowerOTPFragment(myApplicants);
            }

            @Override
            public void onFailure(String message) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                AlertUtils.showAlert(context, message);
            }
        });

    }
}