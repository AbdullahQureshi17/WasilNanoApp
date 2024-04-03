package com.nano.liteloan.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.UserBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.PinSetResponse;
import com.nano.liteloan.correspondant.correspondantdialoguefragment.BorrowerPinSucessMessage;
import com.nano.liteloan.presentation.fragment.dialoguefragment.PinSucessMessage;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;

/**
 * Created by Muhammad Ahmad on 07/13/2020.
 */
public class SetPinActivity extends AppCompatActivity
        implements View.OnClickListener, PinSucessMessage.OnContinue {


    Button btnSubmit;
    EditText etCode1, etCode2, etCode3, etCode4;
    EditText etCode5, etCode6, etCode7, etCode8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        btnSubmit = findViewById(R.id.btn_save);
        btnSubmit.setOnClickListener(this);

        initViews();
        addTextWatchers();
    }


    @Override
    public void onClick(View v) {

        if (v == btnSubmit && isValidFields()) {


            String et1 = etCode1.getText().toString();
            String et2 = etCode2.getText().toString();
            String et3 = etCode3.getText().toString();
            String et4 = etCode4.getText().toString();

            String code = et1 + et2 + et3 + et4;
            registerPinCode(code);

        }

    }

    private boolean isValidFields() {

        if (etCode1.getText().toString().isEmpty()) {

            return false;
        }
        if (etCode2.getText().toString().isEmpty()) {

            return false;
        }
        if (etCode3.getText().toString().isEmpty()) {

            return false;
        }
        if (etCode4.getText().toString().isEmpty()) {

            return false;
        }
        if (etCode5.getText().toString().isEmpty()) {

            return false;
        }
        if (etCode6.getText().toString().isEmpty()) {

            return false;
        }
        if (etCode7.getText().toString().isEmpty()) {

            return false;
        }
        if (etCode8.getText().toString().isEmpty()) {

            return false;
        }
        String pin = etCode1.getText().toString()
                + etCode2.getText().toString()
                + etCode3.getText().toString()
                + etCode4.getText().toString();

        String pin2 = etCode5.getText().toString()
                + etCode6.getText().toString()
                + etCode7.getText().toString()
                + etCode8.getText().toString();
        if (!(pin.equals(pin2))) {

            etCode5.setError("PIN not matched!");
            return false;
        }
        return true;
    }

    private void registerPinCode(final String code) {

        final AlertDialog dialog = AlertUtils.showLoader(this);
        if (dialog != null) {
            dialog.show();
        }

        UserBusiness business = new UserBusiness();
        business.setPinCode(code, new ResponseCallBack<PinSetResponse>() {
            @Override
            public void onSuccess(PinSetResponse body) {

                if (dialog != null) {
                    dialog.dismiss();
                }

                if(PreferenceUtils.getInstance().getBorrowertype() != null
                    && PreferenceUtils.getInstance().getBorrowertype().equalsIgnoreCase("1")){
                    PreferenceUtils.getInstance().setPinCode(code);

                    BorrowerPinSucessMessage pinSucessMessage = new BorrowerPinSucessMessage(SetPinActivity.this,
                            SetPinActivity.this );
                    pinSucessMessage.show(getSupportFragmentManager(),
                            BorrowerPinSucessMessage.class.getName());

                } else {
                    PreferenceUtils.getInstance().setPinCode(code);

                    PinSucessMessage pinSucessMessage = new PinSucessMessage(SetPinActivity.this,
                            SetPinActivity.this);
                    pinSucessMessage.show(getSupportFragmentManager(),
                            PinSucessMessage.class.getName());

                }


            }

            @Override
            public void onFailure(String message) {

                AlertUtils.showAlert(SetPinActivity.this, message);
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

    }

    private void loadMainLockActivity() {

        startActivity(new Intent(SetPinActivity.this, LockActivity.class));
        finishAffinity();
    }

    public void initViews() {


        btnSubmit = findViewById(R.id.btn_save);
        btnSubmit.setEnabled(false);
        etCode1 = findViewById(R.id.etCode1);
        etCode2 = findViewById(R.id.etCode2);
        etCode3 = findViewById(R.id.etCode3);
        etCode4 = findViewById(R.id.etCode4);

        etCode5 = findViewById(R.id.etCode5);
        etCode6 = findViewById(R.id.etCode6);
        etCode7 = findViewById(R.id.etCode7);
        etCode8 = findViewById(R.id.etCode8);


        btnSubmit.setOnClickListener(this);

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
                        && etCode4.getText().toString().trim().length() > 0) {
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
                        && etCode4.getText().toString().trim().length() > 0) {
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
                        && etCode4.getText().toString().trim().length() > 0) {
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


                if (s.length() <= 0) {

                }
                if (s.length() > 0
                        && etCode1.getText().toString().trim().length() > 0
                        && etCode2.getText().toString().trim().length() > 0
                        && etCode3.getText().toString().trim().length() > 0) {
                    etCode5.requestFocus();
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
                if (s.length() > 0) {
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

            }
        });


        etCode6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    etCode7.requestFocus();

                } else {

                    etCode5.requestFocus();
                }


                if (s.length() > 0
                        && etCode5.getText().toString().trim().length() > 0
                        && etCode7.getText().toString().trim().length() > 0
                        && etCode8.getText().toString().trim().length() > 0) {
                    btnSubmit.setEnabled(true);
                } else {
                    btnSubmit.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCode7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    etCode8.requestFocus();
                } else {
                    etCode7.requestFocus();
                }
                if (s.length() > 0
                        && etCode5.getText().toString().trim().length() > 0
                        && etCode6.getText().toString().trim().length() > 0
                        && etCode8.getText().toString().trim().length() > 0) {
                    btnSubmit.setEnabled(true);
                } else {
                    btnSubmit.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCode8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (s.length() <= 0) {
                    etCode7.requestFocus();
                }
                if (s.length() > 0
                        && etCode5.getText().toString().trim().length() > 0
                        && etCode6.getText().toString().trim().length() > 0
                        && etCode7.getText().toString().trim().length() > 0) {
                    btnSubmit.setEnabled(true);
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

    private void loadMainActivity() {

        startActivity(new Intent(SetPinActivity.this, MainActivity.class));
        finishAffinity();
    }

    @Override
    public void onContinue() {

        loadMainActivity();
    }
}
