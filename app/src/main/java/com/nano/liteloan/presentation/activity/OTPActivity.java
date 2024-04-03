package com.nano.liteloan.presentation.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.AuthBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.OTPRequestInfo;
import com.nano.liteloan.info.OTPResponse;
import com.nano.liteloan.receivers.SMSReceiverService;
import com.nano.liteloan.utils.AppSignatureHashHelper;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by Muhammad Ahmad on 07/13/2020.
 */
public class OTPActivity extends AppCompatActivity
        implements View.OnClickListener, SMSReceiverService.OTPReceiveListener {


    Button btnSubmit;
    private SMSReceiverService smsReceiverService;
    EditText etCode1, etCode2, etCode3, etCode4, etCode5;
    TextView tvTitle;
    TextView tvResend;
    // private PreferenceUtils preferenceUtils;
    //   int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        // preferenceUtils = PreferenceUtils.getInactivity_otpstance();


        initViews();
        addTextWatchers();

        // Bundle bundle = getIntent().getExtras();

        startSMSListener();
    }


    @Override
    public void onClick(View v) {

        if (v == btnSubmit) {


            String et1 = etCode1.getText().toString();
            String et2 = etCode2.getText().toString();
            String et3 = etCode3.getText().toString();
            String et4 = etCode4.getText().toString();
            String et5 = etCode5.getText().toString();

            String code = et1 + et2 + et3 + et4 + et5;

            if (!code.isEmpty()) {
                final String phoneNumber = "92" + PreferenceUtils.getInstance().getPhoneNumber().substring(1);
                OTPRequestInfo requestInfo = new OTPRequestInfo();
                requestInfo.phone = phoneNumber;
                requestInfo.password = code;

                simpleLogin(requestInfo);

            }


        } else if (v == tvResend) {


            simpleLogin();

        }

    }

    public void initViews() {

        // userId = SessionManager.getInstance().getUserData().getId();

        tvResend = findViewById(R.id.tv_resendCode);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setEnabled(false);
        etCode1 = findViewById(R.id.etCode1);
        etCode2 = findViewById(R.id.etCode2);
        etCode3 = findViewById(R.id.etCode3);
        etCode4 = findViewById(R.id.etCode4);
        etCode5 = findViewById(R.id.etCode5);
        tvTitle = findViewById(R.id.tvTitle);


        btnSubmit.setOnClickListener(this);
        tvResend.setOnClickListener(this);

    }

    private void addTextWatchers() {

        etCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    etCode2.requestFocus();
                }

                if (s.length() > 0
                        && etCode2.getText().toString().trim().length() > 0
                        && etCode3.getText().toString().trim().length() > 0
                        && etCode4.getText().toString().trim().length() > 0
                        && etCode5.getText().toString().trim().length() > 0) {
                    btnSubmit.setEnabled(true);

                } else {
                    btnSubmit.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    etCode3.requestFocus();

                } else {

                    etCode1.requestFocus();
                }


                if (s.length() > 0
                        && etCode1.getText().toString().trim().length() > 0
                        && etCode3.getText().toString().trim().length() > 0
                        && etCode4.getText().toString().trim().length() > 0
                        && etCode5.getText().toString().trim().length() > 0) {
                    btnSubmit.setEnabled(true);
                } else {
                    btnSubmit.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    etCode4.requestFocus();
                } else {
                    etCode2.requestFocus();
                }
                if (s.length() > 0
                        && etCode1.getText().toString().trim().length() > 0
                        && etCode2.getText().toString().trim().length() > 0
                        && etCode4.getText().toString().trim().length() > 0
                        && etCode5.getText().toString().trim().length() > 0) {
                    btnSubmit.setEnabled(true);
                } else {
                    btnSubmit.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0) {
                    etCode5.requestFocus();
                }

                if (s.length() <= 0) {
                    etCode3.requestFocus();
                }
                if (s.length() > 0
                        && etCode1.getText().toString().trim().length() > 0
                        && etCode2.getText().toString().trim().length() > 0
                        && etCode3.getText().toString().trim().length() > 0
                        && etCode5.getText().toString().trim().length() > 0) {
                    btnSubmit.setEnabled(true);
                } else {
                    btnSubmit.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() <= 0) {
                    etCode5.requestFocus();
                }
                if (s.length() > 0
                        && etCode1.getText().toString().trim().length() > 0
                        && etCode2.getText().toString().trim().length() > 0
                        && etCode3.getText().toString().trim().length() > 0
                        && etCode5.getText().toString().trim().length() > 0) {
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
//        showDialogToDiscardCodeVerification();
        super.onBackPressed();
    }

    private void startSMSListener() {

        smsReceiverService = new SMSReceiverService();
        smsReceiverService.setOTPListener(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        this.registerReceiver(smsReceiverService, intentFilter);

        SmsRetrieverClient client = SmsRetriever.getClient(this);

        Task<Void> task = client.startSmsRetriever();
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("smsRes", "started api");
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("smsRes", "failed api " + e.getMessage());
            }
        });
        task.addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Log.i("smsRes", "cancel api ");
            }
        });
        task.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Log.i("smsRes", "completed api ");
            }
        });

    }

    private void updateMessage(String optStr) {

        try {

            String[] strArr = optStr.split("");

            if (strArr[0].equals("")) {

                etCode1.setText(strArr[1]);
                etCode2.setText(strArr[2]);
                etCode3.setText(strArr[3]);
                etCode4.setText(strArr[4]);

                if (strArr[5] != null) {
                    etCode5.setText(strArr[5]);
                    btnSubmit.performClick();
                }
            } else {

                etCode1.setText(strArr[0]);
                etCode2.setText(strArr[1]);
                etCode3.setText(strArr[2]);
                etCode4.setText(strArr[3]);

                if (strArr[4] != null) {
                    etCode5.setText(strArr[4]);
                    btnSubmit.performClick();
                }
            }


        } catch (RuntimeException e) {

            if (e.getMessage() != null) {
                Log.e("OPT", e.getMessage());
            } else {
                Log.e("ahmad", "ahmad");
            }
        }
    }


    @Override
    public void onOTPReceived(String otp) {

        updateMessage(otp);
        unRegisterSmsReceiver();
    }

    @Override
    public void onOTPTimeOut() {
        unRegisterSmsReceiver();
    }

    @Override
    public void onOTPReceivedError(String error) {

        unRegisterSmsReceiver();
    }

    private void unRegisterSmsReceiver() {
        if (smsReceiverService != null) {
            this.unregisterReceiver(smsReceiverService);
            LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiverService);

        }
    }


    private void simpleLogin(OTPRequestInfo requestInfo) {

        final AlertDialog dialog = AlertUtils.showLoader(this);

        if (dialog != null) {

            dialog.show();
        }

        AuthBusiness authBusinessImpl = new AuthBusiness();
        authBusinessImpl.otpAuth(requestInfo, new ResponseCallBack<OTPResponse>() {
            @Override
            public void onSuccess(OTPResponse body) {

                if (dialog != null) {

                    dialog.dismiss();
                }
                if (body != null) {
                    PreferenceUtils.getInstance().addAccountDetail(body.liteaccount);
                    if (body.userDate.getUsertype() == 0){

                        PreferenceUtils.getInstance().addUserDetail(body.userDate);
                        PreferenceUtils.getInstance().setLoginAsActive(true);

                    } else if (body.userDate.getUsertype() == 1) {

                        PreferenceUtils.getInstance().addCorrespondentUserDetail(body.userDate);
                        PreferenceUtils.getInstance().setLoginAsActive(false);
                    }
                    PreferenceUtils.getInstance().setLoggedin(true);
                    PreferenceUtils.getInstance().setPinCode(null);
                    PreferenceUtils.getInstance().setBorrowerType(String.valueOf(body.userDate.getUsertype()));

                    PreferenceUtils.getInstance().setSessionActive(false);

                    if (body.userDate.getPin() == 1) {

                        lockActivity();

                    } else {

                        loadSetPinActivity();
                    }

                }

            }

            @Override
            public void onFailure(String message) {

                if (dialog != null) {

                    dialog.dismiss();
                }
                AlertUtils.showAlert(OTPActivity.this, message);
            }
        });
    }

    private void lockActivity() {

        startActivity(new Intent(OTPActivity.this, LockActivity.class));
        finishAffinity();
    }

    private void loadMainActivity() {

        startActivity(new Intent(OTPActivity.this, MainActivity.class));
        finishAffinity();
    }

    private void loadSetPinActivity() {

        startActivity(new Intent(OTPActivity.this, SetPinActivity.class));
        finishAffinity();
    }

    private void simpleLogin() {

        final AppSignatureHashHelper appSignatureHashHelper =
                new AppSignatureHashHelper(this);

        final AlertDialog dialog = AlertUtils.showLoader(this);

        if (dialog != null) {
            dialog.show();
        }
        final String phoneNumber = "92" + PreferenceUtils.getInstance().getPhoneNumber().substring(1);

        OTPRequestInfo requestInfo = new OTPRequestInfo();
        requestInfo.phone = phoneNumber;
        requestInfo.hashKey = appSignatureHashHelper
                .getAppSignatures().get(0);


        AuthBusiness authBusinessImpl = new AuthBusiness();
        authBusinessImpl.otpAuth(requestInfo, new ResponseCallBack<OTPResponse>() {
            @Override
            public void onSuccess(OTPResponse body) {

                if (dialog != null) {
                    dialog.dismiss();
                }

//                PreferenceUtils.getInstance().setPhoneNumber(edEmail.getText().toString());
//                loadOTPActivity();
            }

            @Override
            public void onFailure(String message) {

                if (dialog != null) {
                    dialog.dismiss();
                }

                AlertUtils.showAlert(OTPActivity.this, message);
            }
        });

    }
}
