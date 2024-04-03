package com.nano.liteloan.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.AuthBusiness;
import com.nano.liteloan.business.impl.UserBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.ForgetPinRequest;
import com.nano.liteloan.info.OTPRequestInfo;
import com.nano.liteloan.info.OTPResponse;
import com.nano.liteloan.info.PinSetResponse;
import com.nano.liteloan.utils.AppSignatureHashHelper;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;

/**
 * Created by Muhammad Ahmad on 08/07/2020.
 */
public class LoginWithPin extends AppCompatActivity implements View.OnClickListener {

    EditText etCode6, etCode7, etCode8, etCode9;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_pin);

        btnSubmit = findViewById(R.id.btn_login);
        btnSubmit.setOnClickListener(this);

        TextView tvForget = findViewById(R.id.tv_forget_pin);
        tvForget.setOnClickListener(this);

        initViews();
        addTextWatchers();

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_login) {

            if (isValidFields()) {

                String pin = etCode6.getText().toString() +
                        etCode7.getText().toString() +
                        etCode8.getText().toString() +
                        etCode9.getText().toString();

                logInWithPin(pin);
            }
        } else if (v.getId() == R.id.tv_forget_pin) {

            forgetPinCode();
        }
    }

    private void forgetPinCode() {

        final AlertDialog dialog = AlertUtils.showLoader(LoginWithPin.this);
        if (dialog != null) {
            dialog.show();
        }

        UserBusiness userBusiness = new UserBusiness();
        ForgetPinRequest request = new ForgetPinRequest();
        request.userId = PreferenceUtils.getInstance().getUserId();

        userBusiness.forgetPinCode(request,
                new ResponseCallBack<PinSetResponse>() {
                    @Override
                    public void onSuccess(PinSetResponse body) {

                        if (dialog != null) {
                            dialog.dismiss();
                        }

                        PreferenceUtils.getInstance().setPinCode(null);

                        PreferenceUtils.getInstance().clearPreferences();

                        startActivity(new Intent(LoginWithPin.this,
                                Login.class));

                        finishAffinity();

                    }

                    @Override
                    public void onFailure(String message) {

                        if (dialog != null) {
                            dialog.dismiss();
                        }

                        AlertUtils.showAlert(LoginWithPin.this, message);
                    }
                });
    }

    private boolean isValidFields() {

        if (etCode6.getText().toString().isEmpty()) {
            return false;
        }
        if (etCode7.getText().toString().isEmpty()) {
            return false;
        }
        if (etCode8.getText().toString().isEmpty()) {
            return false;
        }

        if (etCode9.getText().toString().isEmpty()) {
            return false;
        }

        return true;
    }

    public void initViews() {


        etCode6 = findViewById(R.id.etCode6);

        etCode7 = findViewById(R.id.etCode7);
        etCode8 = findViewById(R.id.etCode8);
        etCode9 = findViewById(R.id.etCode9);


    }

    private void addTextWatchers() {

        etCode6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {

//                    etCode7.requestFocus();
                }

                if (s.length() > 0
                        && etCode7.getText().toString().trim().length() > 0
                        && etCode8.getText().toString().trim().length() > 0
                        && etCode9.getText().toString().trim().length() > 0) {
                    btnSubmit.setEnabled(true);

                } else {
                    btnSubmit.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {

                } else {
                    etCode7.requestFocus();
                }

            }
        });


        etCode7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {

//                    etCode8.requestFocus();

                } else {

                    etCode6.requestFocus();
                }


                if (s.length() > 0
                        && etCode6.getText().toString().trim().length() > 0
                        && etCode7.getText().toString().trim().length() > 0
                        && etCode8.getText().toString().trim().length() > 0) {
                    btnSubmit.setEnabled(true);
                } else {
                    btnSubmit.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {

                } else {
                    etCode8.requestFocus();
                }


            }
        });

        etCode8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
//                    etCode9.requestFocus();

                } else {
                    etCode7.requestFocus();
                }
                if (s.length() > 0
                        && etCode6.getText().toString().trim().length() > 0
                        && etCode7.getText().toString().trim().length() > 0
                        && etCode9.getText().toString().trim().length() > 0) {
                    btnSubmit.setEnabled(true);
                } else {
                    btnSubmit.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {

                } else {
                    etCode9.requestFocus();
                }

            }
        });

        etCode9.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() <= 0) {

                }
                if (s.length() > 0
                        && etCode6.getText().toString().trim().length() > 0
                        && etCode7.getText().toString().trim().length() > 0
                        && etCode8.getText().toString().trim().length() > 0) {
                    btnSubmit.setEnabled(true);
                    btnSubmit.performClick();
                } else {
                    btnSubmit.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    private void logInWithPin(final String pinCode) {

        final AppSignatureHashHelper appSignatureHashHelper =
                new AppSignatureHashHelper(this);

        String phoneNumber = "";

        if (PreferenceUtils.getInstance().isLoginAsActive()
                && PreferenceUtils.getInstance()
                .getCorrespondentUserDetail() != null) {

            phoneNumber = "92" + PreferenceUtils.getInstance()
                    .getCorrespondentUserDetail().getPhone()
                    .substring(1);
        } else {
            phoneNumber = "92" + PreferenceUtils.getInstance()
                    .getPhoneNumber().substring(1);
        }

        final AlertDialog dialog = AlertUtils.showLoader(this);

        if (dialog != null) {
            dialog.show();
        }

        OTPRequestInfo requestInfo = new OTPRequestInfo();
        requestInfo.phone = phoneNumber;
        //  requestInfo.hashKey = appSignatureHashHelper.getAppSignatures().get(0);
        requestInfo.pinCode = pinCode;


        AuthBusiness authBusinessImpl = new AuthBusiness();
        authBusinessImpl.otpAuth(requestInfo, new ResponseCallBack<OTPResponse>() {
            @Override
            public void onSuccess(OTPResponse body) {

                if (dialog != null) {
                    dialog.dismiss();
                }

                PreferenceUtils.getInstance().setPinCode(pinCode);
                if (body != null) {

                    PreferenceUtils.getInstance().addAccountDetail(body.liteaccount);
                    if (body.userDate.getUsertype() == 0) {

                        PreferenceUtils.getInstance().addUserDetail(body.userDate);
                        PreferenceUtils.getInstance().setLoginAsActive(true);

                    } else if (body.userDate.getUsertype() == 1 &&
                            !PreferenceUtils.getInstance().isLoginAsActive()) {

                        PreferenceUtils.getInstance().addCorrespondentUserDetail(body.userDate);
                        PreferenceUtils.getInstance().setLoginAsActive(false);
                    }

                    PreferenceUtils.getInstance().setLoggedin(true);
                    PreferenceUtils.getInstance().setPinCode(pinCode);
                    PreferenceUtils.getInstance().setSessionActive(true);

                    loadMainActivity();
                }

            }

            @Override
            public void onFailure(String message) {

                if (dialog != null) {
                    dialog.dismiss();
                }

                AlertUtils.showAlert(LoginWithPin.this, message);
            }
        });
    }

    private void loadMainActivity() {

        startActivity(new Intent(LoginWithPin.this, MainActivity.class));
        finishAffinity();
    }

}
