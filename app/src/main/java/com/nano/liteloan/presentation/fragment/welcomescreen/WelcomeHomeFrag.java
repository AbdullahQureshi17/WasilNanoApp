package com.nano.liteloan.presentation.fragment.welcomescreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.nano.liteloan.R;
import com.nano.liteloan.presentation.activity.WelcomeScreen;
/**
 * Created by Muhammad Umer on 04/06/2020.
 */

public class WelcomeHomeFrag extends Fragment {

    private Button btProduct;
    private WelcomeScreen context;

    public WelcomeHomeFrag(WelcomeScreen context) {
        this.context = context;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_welcome_home, container, false);

        btProduct = v.findViewById(R.id.see_product);
        btProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.welcomePagerFrag();
            }
        });



        return v;
    }
}