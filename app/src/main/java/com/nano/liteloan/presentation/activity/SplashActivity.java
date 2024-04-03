package com.nano.liteloan.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.nano.liteloan.R;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.User;
import com.nano.liteloan.service.FireBaseConfig;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class SplashActivity extends AppCompatActivity implements ResponseCallBack<User> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startFadeOutAnimation();

//        FireBaseConfig
//                .getApplicationBaseURL(this, this);

    }

    public void startFadeOutAnimation() {
        ImageView fadeOutImage = findViewById(R.id.iv_logo);
        Animation startFadeOutAnimation
                = AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.fade_out_animation);
        fadeOutImage.startAnimation(startFadeOutAnimation);

        startFadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                checkUserLoggedIn();


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * check if user is logged in.
     */
    private void checkUserLoggedIn() {

        if (PreferenceUtils.getInstance().loggedin()
                && PreferenceUtils.getInstance().isSessionActive()) {

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
            finishAffinity();

        } else if (PreferenceUtils.getInstance().loggedin()
                && PreferenceUtils.getInstance().getPinCode() == null) {

            startActivity(new Intent(getApplicationContext(), SetPinActivity.class));

            overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);

            finishAffinity();
        } else if (PreferenceUtils.getInstance().loggedin()
                && !PreferenceUtils.getInstance().isSessionActive()) {

            startActivity(new Intent(getApplicationContext(), MainActivity.class));

            overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);

            finishAffinity();

        } else {

            startActivity(new Intent(new Intent(SplashActivity.this, WelcomeScreen.class)));
            overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);

            finishAffinity();
        }


    }


    @Override
    public void onSuccess(User body) {

        startFadeOutAnimation();
    }

    @Override
    public void onFailure(String message) {

        AlertUtils.showAlert(SplashActivity.this, message);
    }
}
