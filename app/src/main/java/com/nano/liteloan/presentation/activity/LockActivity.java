package com.nano.liteloan.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
public class LockActivity extends AppCompatActivity {

    private EditText edPin;
    private ImageView ivLogo;
    //    private RotateLoading loading;
    private Button button;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
//        loading = findViewById(R.id.rotateloading);

        ivLogo = findViewById(R.id.logo);

        edPin = findViewById(R.id.ed_pin);
        TextView forgetPin = findViewById(R.id.tv_pin);


        button = findViewById(R.id.signin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edPin.getText().toString().isEmpty()) {

                    edPin.setError("please enter your login PIN");
                    edPin.requestFocus();

                    return;
                }

                //startFadeOutAnimation();

                logInWithPin();
            }
        });

        forgetPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                forgetPinCode();
            }
        });

        edPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() == 4) {

                    button.performClick();

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void startFadeOutAnimation() {

        Animation startFadeOutAnimation
                = AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.fade_animation);
        ivLogo.startAnimation(startFadeOutAnimation);

        startFadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

//                ivLogo.setImageDrawable(ContextCompat.getDrawable(LockActivity.this,
//                        R.drawable.unlock_logo));
//
//                logInWithPin();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void forgetPinCode() {

        final AlertDialog dialog = AlertUtils.showLoader(LockActivity.this);
        if (dialog != null) {
            dialog.show();
        }

        UserBusiness userBusiness = new UserBusiness();

        ForgetPinRequest request = new ForgetPinRequest();
        if (PreferenceUtils.getInstance().getUserDetail() == null) {
            request.userId = PreferenceUtils.getInstance().getcorrespondantId();
        } else {
            request.userId = PreferenceUtils.getInstance()
                    .getUserDetail().getUserid();
        }


        userBusiness.forgetPinCode(request,
                new ResponseCallBack<PinSetResponse>() {
                    @Override
                    public void onSuccess(PinSetResponse body) {

                        if (dialog != null) {
                            dialog.dismiss();
                        }

                        PreferenceUtils.getInstance().clearPreferences();

                        startActivity(new Intent(LockActivity.this,
                                Login.class));

                        finishAffinity();

                    }

                    @Override
                    public void onFailure(String message) {

                        if (dialog != null) {
                            dialog.dismiss();
                        }

                        AlertUtils.showAlert(LockActivity.this, message);
                    }
                });
    }

    private void logInWithPin() {
//        loading.start();

        final AlertDialog dialog = AlertUtils.showLoader(this);

        if (dialog != null) {
            dialog.show();
        }
        if (PreferenceUtils.getInstance().getPinCode() == null) {

            final AppSignatureHashHelper appSignatureHashHelper =
                    new AppSignatureHashHelper(LockActivity.this);

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


            OTPRequestInfo requestInfo = new OTPRequestInfo();
            requestInfo.phone = phoneNumber;
            //  requestInfo.hashKey = appSignatureHashHelper.getAppSignatures().get(0);
            requestInfo.pinCode = edPin.getText().toString();


            AuthBusiness authBusinessImpl = new AuthBusiness();
            authBusinessImpl.otpAuth(requestInfo, new ResponseCallBack<OTPResponse>() {
                @Override
                public void onSuccess(OTPResponse body) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }

                    PreferenceUtils.getInstance().setPinCode(edPin.getText().toString());
                    if (body != null) {
                        if (body.userDate.getUsertype() == 0) {

                            PreferenceUtils.getInstance().addUserDetail(body.userDate);
                            //PreferenceUtils.getInstance().setLoginAsActive(true);

                        } else if (body.userDate.getUsertype() == 1) {

                            PreferenceUtils.getInstance().addCorrespondentUserDetail(body.userDate);
                            // PreferenceUtils.getInstance().setLoginAsActive(false);
                        }

                        PreferenceUtils.getInstance().setLoggedin(true);
                        PreferenceUtils.getInstance().setPinCode(edPin.getText().toString());
                        PreferenceUtils.getInstance().setSessionActive(true);

                        loadMainActivity();
                    }
                }

                @Override
                public void onFailure(String message) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
//                    loading.stop();
                    AlertUtils.showAlert(LockActivity.this, message);
                }
            });
        } else {

            if (edPin.getText().toString().equals(PreferenceUtils.getInstance().getPinCode())) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null) {
                            loadMainActivity();
                            dialog.dismiss();

                        } else {
                            loadMainActivity();
                        }


//                        loading.stop();
//                        ivLogo.setImageDrawable(ContextCompat.getDrawable(LockActivity.this,
//                                R.drawable.unlock_logo));
                    }
                }, 2000);

            } else {
                if (dialog != null) {
                    dialog.dismiss();
                }
//                loading.stop();

                edPin.setError("Incorrect PIN Code!");
                edPin.requestFocus();
            }

        }

    }


//    protected boolean checkPermission(){
//        if (ContextCompat.checkSelfPermission(LockActivity.this, Manifest.permission.READ_CALL_LOG)
//                + ContextCompat.checkSelfPermission(
//                LockActivity.this , Manifest.permission.READ_SMS)
//                + ContextCompat.checkSelfPermission(
//                LockActivity.this , Manifest.permission.READ_CONTACTS)
//                != PackageManager.PERMISSION_GRANTED){
//
//            return false;
//        } else {
//            return true;
//            // Do something, when permissions are already granted
//        }
//    }


    public void loadMainActivity() {


        startActivity(new Intent(LockActivity.this, MainActivity.class));
        finishAffinity();


    }
}
