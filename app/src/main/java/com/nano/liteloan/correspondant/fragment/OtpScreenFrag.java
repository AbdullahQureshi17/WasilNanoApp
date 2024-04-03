package com.nano.liteloan.correspondant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.BusinessCorrespondant;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.BorrowerList;
import com.nano.liteloan.info.OTPResponse;
import com.nano.liteloan.info.businesscorrespondant.CorrespondantLoginAsRequest;
import com.nano.liteloan.info.businesscorrespondant.MyApplicants;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;

public class OtpScreenFrag extends Fragment {

    private LinearLayout llOtp, llLogin;
    private EditText edOtp, edName, edPhoneno ;
    private RelativeLayout rlOtp;
    private TextView tvSendOtp;
    private Button btLogin;
    private MainActivity context;
    private MyApplicants borrowerList;


    public OtpScreenFrag(MainActivity context, MyApplicants borrowerList) {
        this.context = context;
        this.borrowerList = borrowerList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_otp_screen, container, false);

        tvSendOtp = view.findViewById(R.id.sendotp);



        edOtp = view.findViewById(R.id.otp);
        llOtp = view.findViewById(R.id.llotp);
        rlOtp = view.findViewById(R.id.rlOpt);
        rlOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edPhoneno.getText().toString() != null) {
                    CorrespondantLoginAsRequest request = new CorrespondantLoginAsRequest();
                    request.userId = String.valueOf(borrowerList.userId);

                    request.phone = edPhoneno.getText().toString();
                    setLoginAs(request);
                } else {
                    edPhoneno.setError("Invalid phone no");
                }
            }
        });

        llLogin = view.findViewById(R.id.llLogin);

        edName = view.findViewById(R.id.name);
        edPhoneno = view.findViewById(R.id.phone);

        btLogin = view.findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(borrowerList.otpflag.equalsIgnoreCase("1")){
                    CorrespondantLoginAsRequest request = new CorrespondantLoginAsRequest();
                    request.userId = String.valueOf(borrowerList.userId);

                    request.phone = edPhoneno.getText().toString();
                    setOtpLoginAs(request);
                } else {
                    if(edOtp != null && !edOtp.getText().toString().equalsIgnoreCase("")
                            && edOtp.getText().toString().length() == 5){
                        CorrespondantLoginAsRequest request = new CorrespondantLoginAsRequest();
                        request.userId = String.valueOf(borrowerList.userId);


                        request.otp = edOtp.getText().toString();
                        request.phone = edPhoneno.getText().toString();

                        setOtpLoginAs(request);
                    } else {
                        edOtp.setError("Please enter valid otp");
                    }
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
        if(borrowerList.otpflag.equalsIgnoreCase("1")){
            rlOtp.setVisibility(View.GONE);
            llOtp.setVisibility(View.GONE);
        } else {
            rlOtp.setVisibility(View.VISIBLE);
            llLogin.setVisibility(View.GONE);

        }
        setContent();

        return view;
    }

    private void setContent() {
        if(borrowerList.phoneno != null && !borrowerList.phoneno.equalsIgnoreCase("")){
            if(borrowerList.phoneno.substring(0,2).equalsIgnoreCase("03")){
                edPhoneno.setText("92" + borrowerList.phoneno.substring(1));
            } else {
                edPhoneno.setText(borrowerList.phoneno);
            }

        }
        if(borrowerList.name != null && !borrowerList.name.equalsIgnoreCase("")){
            edName.setText(borrowerList.name);
        }
    }

    private void setLoginAs(CorrespondantLoginAsRequest request) {
        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }
        BusinessCorrespondant userBusiness = new BusinessCorrespondant();
        userBusiness.loginAsSomeOne(request, new ResponseCallBack<OTPResponse>() {
            @Override
            public void onSuccess(OTPResponse body) {

                llOtp.setVisibility(View.VISIBLE);
                llLogin.setVisibility(View.VISIBLE);
                tvSendOtp.setText("Resend");
                dialog.dismiss();
//                if (body != null && body.userDate != null) {
//
//                    PreferenceUtils.getInstance().setLoginAsActive(true);
//                    PreferenceUtils.getInstance().addUserDetail(body.userDate);
//                    PreferenceUtils.getInstance().addAccountDetail(body.liteaccount);
//
//                    context.resetMainActivity();
//                }
            }

            @Override
            public void onFailure(String message) {
                dialog.dismiss();
                AlertUtils.showAlert(context, message);
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