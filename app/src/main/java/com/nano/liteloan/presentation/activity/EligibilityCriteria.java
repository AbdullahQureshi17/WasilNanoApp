package com.nano.liteloan.presentation.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.BusinessCorrespondant;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.GetProductResponce;
import com.nano.liteloan.info.businesscorrespondant.EligibiltyCriteria;
import com.nano.liteloan.utils.alerts.AlertUtils;

import org.sufficientlysecure.htmltextview.HtmlTextView;

public class EligibilityCriteria extends AppCompatActivity {



    private CountDownTimer countDownTimer;
    TextView tvTimer ;
    private HtmlTextView tvDesc;
    private Button btNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eligibility_criteria);

        tvTimer = findViewById(R.id.timer);
        btNext = findViewById(R.id.next);
        btNext.setAlpha(0.3f);
        btNext.setClickable(false);
        tvDesc = findViewById(R.id.tv_disclaimer);

        getProduct();
        timer();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                btNext.setAlpha(1f);
                btNext.setClickable(true);
                btNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(new Intent(EligibilityCriteria.this, WelcomeScreen.class)));
                        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
                        finishAffinity();
                    }
                });
            }
        }, 10000);

    }

    public void getProduct(){
        final AlertDialog dialog = AlertUtils.showLoader(EligibilityCriteria.this);
        GetProductResponce getProduct = new GetProductResponce();

        BusinessCorrespondant business = new BusinessCorrespondant();
        business.getEligibility(new ResponseCallBack<EligibiltyCriteria>() {
            @Override
            public void onSuccess(EligibiltyCriteria body) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                if(body.pages.get(0).content != null){
                    tvDesc.setHtml(body.pages.get(0).content);
                }

            }

            @Override
            public void onFailure(String message) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                AlertUtils.showAlert(EligibilityCriteria.this, message);
            }
        });

    }

    private void timer() {
        countDownTimer = new CountDownTimer((10 * 1000), 1000) {

            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            public void onTick(long millisUntilFinished) {

                @SuppressLint("DefaultLocale") String v = String.format("%02d", millisUntilFinished / 60000);
                int va = (int) ((millisUntilFinished % 60000) / 1000);
                tvTimer.setText( "Remaining Time : " +v + ":" + String.format("%02d", va));

            }

            public void onFinish() {
                tvTimer.setVisibility(View.GONE);
            }
        }.start();
    }
}