package com.nano.liteloan.presentation.fragment.dialoguefragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.AuthBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.ForgotPasswordRequestInfo;
import com.nano.liteloan.info.ForgotPasswordResponseInfo;
import com.nano.liteloan.utils.alerts.AlertUtils;


/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class ForgotPasswordFragment extends DialogFragment implements View.OnClickListener {

    private EditText etPhoneno, etEmail;
    private ImageView ivPhone, ivEmail;
    private CardView cdPhone, cdEmail;
    private String type = "phone";
    private String info;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        etPhoneno = (EditText) view.findViewById(R.id.ed_phone);
        etEmail = (EditText) view.findViewById(R.id.ed_email);
        LinearLayout lPhone = (LinearLayout) view.findViewById(R.id.phoneLayout);
        lPhone.setOnClickListener(this);
        LinearLayout lEmail = (LinearLayout) view.findViewById(R.id.emailLayout);
        lEmail.setOnClickListener(this);
        cdPhone = (CardView) view.findViewById(R.id.phone);
        cdEmail = (CardView) view.findViewById(R.id.email);

        ivEmail = (ImageView) view.findViewById(R.id.iv_Email);
        ivPhone = (ImageView) view.findViewById(R.id.iv_phone);

        Button btForgotPassword = (Button) view.findViewById(R.id.bt_forgotPassword);
        btForgotPassword.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phoneLayout:
                ivEmail.setBackgroundResource(R.drawable.ic_checkinactive);
                ivPhone.setBackgroundResource(R.drawable.ic_checkactive);
                cdEmail.setVisibility(getView().GONE);
                cdPhone.setVisibility(getView().getVisibility());
                type = "phone"; //1 for reset password through phone

                break;
            case R.id.emailLayout:
                ivEmail.setBackgroundResource(R.drawable.ic_checkactive);
                ivPhone.setBackgroundResource(R.drawable.ic_checkinactive);
                cdEmail.setVisibility(getView().getVisibility());
                cdPhone.setVisibility(getView().GONE);
                type = "email"; //0 for reset password through email

                break;

            case R.id.bt_forgotPassword:
                forgotPassword();
                break;


        }

    }


    private void forgotPassword() {

        if (type.equals("email")) {
            info = etEmail.getText().toString();
        } else if (type.equals("phone")) {
            info = etPhoneno.getText().toString();
        }
        final AlertDialog dialog = AlertUtils.showLoader(getContext());
        final ForgotPasswordRequestInfo forgotPasswordRequestInfo = new ForgotPasswordRequestInfo();
        forgotPasswordRequestInfo.setType(type);
        forgotPasswordRequestInfo.setInfo(info);
        AuthBusiness authBusinessImpl = new AuthBusiness();

        authBusinessImpl.forgotPassword(forgotPasswordRequestInfo, new ResponseCallBack<ForgotPasswordResponseInfo>() {
            @Override
            public void onSuccess(ForgotPasswordResponseInfo body) {
                dialog.dismiss();
                getDialog().dismiss();


            }

            @Override
            public void onFailure(String message) {
                dialog.dismiss();
                AlertUtils.showAlert(getContext(), message);
            }
        });


    }
}