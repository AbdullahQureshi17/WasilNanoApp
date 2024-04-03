package com.nano.liteloan.presentation.activity;

import android.app.KeyguardManager;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.AuthBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.OTPRequestInfo;
import com.nano.liteloan.info.OTPResponse;
import com.nano.liteloan.info.SocailLoginRequestInfo;
import com.nano.liteloan.info.SocialLoginResponse;
import com.nano.liteloan.presentation.fragment.dialoguefragment.BioMetricDilogFragment;
import com.nano.liteloan.presentation.fragment.dialoguefragment.ForgotPasswordFragment;
import com.nano.liteloan.utils.AppSignatureHashHelper;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

/**
 * Created by Muhammad Ahmad on 07/06/2020.
 */
public class Login extends AppCompatActivity implements View.OnClickListener, BioMetricDilogFragment.OnSuccess {

    private CallbackManager callbackManager;
    // TextView tvRegister, tvForgotpassword;
    Button btSignin, btgoolge, btFacebook;
    EditText edEmail, edPassword, edPin;
    private GoogleSignInClient mGoogleSignInClient;
    RelativeLayout rlBackgroud, rlAnimation;
    private TextView tvPin;
    private SwitchCompat bioSwitch;
    private LinearLayout bioLayout;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private ImageView ivBioIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        edEmail = findViewById(R.id.email);
        tvPin = findViewById(R.id.tv_pin);
        tvPin.setOnClickListener(this);
        edPin = findViewById(R.id.ed_pin);
        bioSwitch = findViewById(R.id.bio_switch);
        bioLayout = findViewById(R.id.ll_biometric);
        ivBioIcon = findViewById(R.id.iv_fingerprint);



//        edPassword = (EditText) findViewById(R.id.password);
//        tvForgotpassword = (TextView) findViewById(R.id.forgot_password);
//        tvForgotpassword.setOnClickListener(this);
//
//        tvRegister = (TextView) findViewById(R.id.register);
//        tvRegister.setOnClickListener(this);

        rlBackgroud = findViewById(R.id.background);
        rlAnimation = findViewById(R.id.animation);
        Animation startFadeOutAnimation
                = AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.top_down);
        rlBackgroud.startAnimation(startFadeOutAnimation);
        startFadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rlAnimation.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        btSignin = findViewById(R.id.signin);
        btSignin.setOnClickListener(this);

        btgoolge = findViewById(R.id.btn_google);
        btgoolge.setOnClickListener(this);

        btFacebook = findViewById(R.id.btn_facebook);
        btFacebook.setOnClickListener(this);

        callbackManager = CallbackManager.Factory.create();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        if (PreferenceUtils.getInstance().isBiometric()) {

            bioSwitch.setChecked(true);
        } else {
            bioSwitch.setChecked(false);
        }

        bioSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {

                    if (!PreferenceUtils.getInstance().isBiometric()) {

                        AlertUtils.showAlert(Login.this, "Please login using your " +
                                "Phone Number and PIN Code for the first time to enable the " +
                                "Biometric Login.");

                    }

                } else {

                    if (PreferenceUtils.getInstance().isBiometric()) {

                        AlertUtils.showBioMetricDialog(Login.this, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                bioSwitch.setChecked(false);
                                PreferenceUtils.getInstance().setInBioMetric(false);


                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                bioSwitch.setChecked(true);
                            }
                        });
                    }
                }
            }
        });

        ivBioIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (PreferenceUtils.getInstance().isBiometric()) {
                    setBioMetricDialog();
                } else {

                    AlertUtils.showAlert(Login.this, "You need to login using your " +
                            "Phone Number and PIN Code for the first time to enable the " +
                            "Biometric Login, for next time onward just tap on the " +
                            "biometric icon to login with biometric");
                }

            }
        });
        settingBiometric();
    }

    private void settingBiometric() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            keyguardManager =
                    (KeyguardManager) Login.this.getSystemService(KEYGUARD_SERVICE);
            fingerprintManager =
                    (FingerprintManager) Login.this.getSystemService(FINGERPRINT_SERVICE);

            if (fingerprintManager != null) {

                if (!fingerprintManager.isHardwareDetected()) {

                    bioLayout.setVisibility(View.GONE);
                    ivBioIcon.setVisibility(View.GONE);

                } else {

                    bioLayout.setVisibility(View.VISIBLE);
                    ivBioIcon.setVisibility(View.VISIBLE);
                }
            }
        } else {

            bioLayout.setVisibility(View.GONE);
            ivBioIcon.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin:
                Boolean IP = AppUtils.isLocalIP();

                if (!edEmail.getText().toString().isEmpty()
                        && edPin.getText().toString().isEmpty() && edEmail.getText().toString().length() == 11) {
                    simpleLogin();
                } else if (!edEmail.getText().toString().isEmpty()
                        && !edPin.getText().toString().isEmpty()) {
                    logInWithPin();
                } else {
                    edEmail.setError("please provide your valid phone number.!");
                }

                break;

            case R.id.btn_google:
                // loginWithGoogle();
                break;

            case R.id.btn_facebook:
                // loginWithFacebook();
                break;

            case R.id.forgot_password:
                //  forgotPassword();
                break;

            case R.id.tv_pin:
                setNewPinRequest();
                break;

        }
    }

    private void setNewPinRequest() {

        if (edEmail.getText().toString().isEmpty()) {

            edEmail.setError("please provide your valid phone number.!");
            return;
        }

        AppUtils.isPinSet = false;
        simpleLogin();

    }

    private boolean isValidLoginRequest() {
        return isValidEmail();
    }

    private boolean isValidEmail() {
        String empEmail = edEmail.getText().toString().trim();
        if (empEmail.isEmpty()) {
            edEmail.setError("Please enter your Mobile number !");
            requestFocus(edEmail);
            return false;
        }

        if (edPin.getText().toString().isEmpty()) {

            edPin.setError("please enter 4 digit Pin code.!");
            requestFocus(edPin);
            return false;
        }

//        if (empEmail.length() < 11) {
//            edEmail.setError("Please enter your valid number !");
//            requestFocus(edEmail);
//            return false;
//        }
        return true;
    }

//    private boolean isValidPassword() {
//        String passcode = edPassword.getText().toString().trim();
//        if (passcode.isEmpty()) {
//            edPassword.setError("Please enter your password !");
//            requestFocus(edPassword);
//            return false;
//        }
//        return true;
//    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void forgotPassword() {
        ForgotPasswordFragment dfrag = new ForgotPasswordFragment();
        dfrag.show(getSupportFragmentManager(), "My Fargment");


    }

    private void simpleLogin() {

        final AppSignatureHashHelper appSignatureHashHelper =
                new AppSignatureHashHelper(this);

        final String phoneNumber = "92" + edEmail.getText().toString().substring(1);

        final AlertDialog dialog = AlertUtils.showLoader(this);

        if (dialog != null) {
            dialog.show();
        }

        if (bioLayout.getVisibility() ==
                View.VISIBLE && bioSwitch.isChecked()) {

            PreferenceUtils.getInstance().setInBioMetric(true);
            PreferenceUtils.getInstance().setPhoneNumber(edEmail.getText().toString());
        }

        OTPRequestInfo requestInfo = new OTPRequestInfo();
        requestInfo.phone = phoneNumber;
        requestInfo.hashKey = appSignatureHashHelper.getAppSignatures().get(0);


        AuthBusiness authBusinessImpl = new AuthBusiness();
        authBusinessImpl.otpAuth(requestInfo, new ResponseCallBack<OTPResponse>() {
            @Override
            public void onSuccess(OTPResponse body) {

                if (dialog != null) {
                    dialog.dismiss();
                }
                PreferenceUtils.getInstance().setPhoneNumber(edEmail.getText().toString());


                if (body != null &&
                        body.userDate != null) {

                    PreferenceUtils.getInstance().setUserId(body
                            .userDate.userFirstId);

                    if (body.userDate.getPin() == 1) {

                        loadLoginWithPinActivity();

                    } else {

                        loadOTPActivity();
                    }
                } else {
                    loadOTPActivity();
                }

            }

            @Override
            public void onFailure(String message) {

                if (dialog != null) {
                    dialog.dismiss();
                }

                AlertUtils.showAlert(Login.this, message);
            }
        });

    }

    private void logInWithPin() {

        final AppSignatureHashHelper appSignatureHashHelper =
                new AppSignatureHashHelper(this);

        final String phoneNumber = "92" + edEmail.getText().toString().substring(1);

        final AlertDialog dialog = AlertUtils.showLoader(this);

        if (dialog != null) {
            dialog.show();
        }

        OTPRequestInfo requestInfo = new OTPRequestInfo();
        requestInfo.phone = phoneNumber;
        //  requestInfo.hashKey = appSignatureHashHelper.getAppSignatures().get(0);
        requestInfo.pinCode = edPin.getText().toString();

        if (bioLayout.getVisibility() ==
                View.VISIBLE && bioSwitch.isChecked()) {

            PreferenceUtils.getInstance().setInBioMetric(true);
            PreferenceUtils.getInstance().setPhoneNumber(edEmail.getText().toString());
            PreferenceUtils.getInstance().setPinCode(requestInfo.pinCode);
        }

        AuthBusiness authBusinessImpl = new AuthBusiness();
        authBusinessImpl.otpAuth(requestInfo, new ResponseCallBack<OTPResponse>() {
            @Override
            public void onSuccess(OTPResponse body) {

                if (dialog != null) {
                    dialog.dismiss();
                }


                PreferenceUtils.getInstance().setPhoneNumber(edEmail.getText().toString());
                PreferenceUtils.getInstance().setPinCode(edPin.getText().toString());
                if (body != null) {

                    if (body.userDate.getUsertype() == 0){

                        PreferenceUtils.getInstance().addUserDetail(body.userDate);
                        PreferenceUtils.getInstance().setLoginAsActive(true);

                    } else if (body.userDate.getUsertype() == 1) {

                        PreferenceUtils.getInstance().addCorrespondentUserDetail(body.userDate);
                        PreferenceUtils.getInstance().setLoginAsActive(false);
                    }
                    PreferenceUtils.getInstance().setLoggedin(true);
                    PreferenceUtils.getInstance().setPinCode(edPin.getText().toString());
                    loadMainActivity();
                }
                // loadOTPActivity();
            }

            @Override
            public void onFailure(String message) {

                if (dialog != null) {
                    dialog.dismiss();
                }

                AlertUtils.showAlert(Login.this, message);
            }
        });

    }

    private void loginWithFacebook() {

        LoginManager loginManager = LoginManager.getInstance();
        if (AccessToken.getCurrentAccessToken() != null) {
            loginManager.logOut();
        }

        loginManager.logInWithReadPermissions(this,
                Collections.singletonList("email, public_profile"));

        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("ahmad", "onSuccess");
            }

            @Override
            public void onCancel() {
                Log.e("ahmad", "onCancel");
            }

            @Override
            public void onError(FacebookException error) {

                if (error.getMessage() != null) {
                    Log.e("ahmad", error.getMessage());
                }

            }
        });
    }

    private void loginWithGoogle() {


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {

            mGoogleSignInClient.signOut();
        }
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {

        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            if (currentAccessToken != null) {
                loadUserProfile(currentAccessToken);
            }
        }
    };

    private void loadUserProfile(final AccessToken accessToken) {

        GraphRequest request = GraphRequest.newMeRequest(accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse response) {

                        try {

                            if (jsonObject != null) {
                                String fullName = jsonObject.getString("name");
                                String id = jsonObject.getString("id");
                                String imageUrl = "https://graph.facebook.com/" + id + "/picture?type=normal";


                                SocailLoginRequestInfo loginRequest = new SocailLoginRequestInfo();
                                loginRequest.setAccountid(id);
                                loginRequest.setEmail(jsonObject.getString("email"));
                                loginRequest.setName(fullName);
                                loginRequest.setImage(imageUrl);
                                loginRequest.setLoginby("FACEBOOK");

                                socialLogin(loginRequest);
                            }

                        } catch (JSONException e) {
                            Log.e("ahmad", e.getMessage());
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,first_name,last_name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {

        try {

            GoogleSignInAccount account = task.getResult(ApiException.class);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri imageUri = acct.getPhotoUrl();

                SocailLoginRequestInfo loginRequest = new SocailLoginRequestInfo();
                loginRequest.setAccountid(personId);
                loginRequest.setName(personGivenName);
                loginRequest.setEmail(personEmail);
                loginRequest.setLoginby("GOOGLE");
                if (imageUri != null) {
                    loginRequest.setImage(imageUri.toString());
                }

                socialLogin(loginRequest);

            }

        } catch (ApiException e) {
            Log.e("ahmad", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void socialLogin(SocailLoginRequestInfo loginRequest) {

        final AlertDialog dialog = AlertUtils.showLoader(this);

        AuthBusiness business = new AuthBusiness();
        business.socialLogin(loginRequest, new ResponseCallBack<SocialLoginResponse>() {
            @Override
            public void onSuccess(SocialLoginResponse body) {

                if (dialog != null) {
                    dialog.dismiss();
                }

                if (body != null) {

                    PreferenceUtils.getInstance().addUserDetail(body.userDate);
                    PreferenceUtils.getInstance().setLoggedin(true);
                    loadMainActivity();
                }
            }

            @Override
            public void onFailure(String message) {

                if (dialog != null) {
                    dialog.dismiss();
                }
                AlertUtils.showAlert(Login.this, message);
            }
        });
    }

    private void loadMainActivity() {

        startActivity(new Intent(Login.this, MainActivity.class));
        finishAffinity();
    }

    private void loadOTPActivity() {

        startActivity(new Intent(Login.this, OTPActivity.class));
    }

    private void loadLoginWithPinActivity() {

        startActivity(new Intent(Login.this, LoginWithPin.class));
    }

    private void setBioMetricDialog() {

        BioMetricDilogFragment dilogFragment = new BioMetricDilogFragment(Login.this, this);
        dilogFragment.show(getSupportFragmentManager(), BioMetricDilogFragment.class.getName());
    }

    @Override
    public void onBioSuccess() {

        edEmail.setText(PreferenceUtils.getInstance().getPhoneNumber());
        edPin.setText(PreferenceUtils.getInstance().getPinCode());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                btSignin.performClick();
            }
        }, 1000);
    }
}