package com.nano.liteloan.presentation.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.nano.liteloan.R;
import com.nano.liteloan.presentation.fragment.profile.MainProfileFragment;
import com.nano.liteloan.presentation.fragment.welcomescreen.WelcomeHomeFrag;
import com.nano.liteloan.presentation.fragment.welcomescreen.WelcomePager;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class WelcomeScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        welcomeHomeFrag();

    }



    public void welcomePagerFrag() {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof WelcomePager)) {


            WelcomePager mainFragment = new WelcomePager(WelcomeScreen.this);


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, mainFragment)
                    .addToBackStack(MainProfileFragment.class.getName())
                    .commit();


        }

    }

    public void welcomeHomeFrag() {


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if (!(fragment instanceof WelcomeHomeFrag)) {


            WelcomeHomeFrag mainFragment = new WelcomeHomeFrag(WelcomeScreen.this);


            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, mainFragment)
                    .addToBackStack(MainProfileFragment.class.getName())
                    .commit();


        }

    }

//    @Override
//    public void onBackPressed() {
//
//        System.exit(1);
//    }
}