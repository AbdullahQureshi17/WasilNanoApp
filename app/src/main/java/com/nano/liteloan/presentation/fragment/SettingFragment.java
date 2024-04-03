package com.nano.liteloan.presentation.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.nano.liteloan.R;
import com.nano.liteloan.presentation.activity.MainActivity;

/**
 * Created by Muhammad Umer on 04/06/2020.
 */

public class SettingFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout rlEng, rlRoman, rlUrdu , rlContinue;
    private ImageView ivEng, ivRoman, ivUrdu;
    private TextView tvEng, tvRoman, tvUrdu;

    private MainActivity context;

    public SettingFragment(MainActivity mainActivity) {
        this.context = mainActivity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        rlContinue = view.findViewById(R.id.rlContinue);
        rlContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.homeFrag();
            }
        });

        rlEng = view.findViewById(R.id.rlenglish);
        rlEng.setOnClickListener(this);
        rlRoman = view.findViewById(R.id.rlRoman);
        rlRoman.setOnClickListener(this);
        rlUrdu = view.findViewById(R.id.rlurdu);
        rlUrdu.setOnClickListener(this);

        ivEng = view.findViewById(R.id.iv_english);
        ivRoman = view.findViewById(R.id.iv_roman);
        ivUrdu = view.findViewById(R.id.iv_urdu);

        tvEng = view.findViewById(R.id.english_text);
        tvRoman = view.findViewById(R.id.Roman_text);
        tvUrdu = view.findViewById(R.id.Urdu_text);



        return view;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rlenglish:

                ivRoman.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_unchecked));
                ivEng.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_checked));
                ivUrdu.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_unchecked));

                tvUrdu.setTextColor(ContextCompat.getColor(context, R.color.black));
                tvEng.setTextColor(ContextCompat.getColor(context, R.color.white));
                tvRoman.setTextColor(ContextCompat.getColor(context, R.color.black));

                rlEng.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.language_select_background_blue) );
                rlUrdu.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.language_select_background_white) );
                rlRoman.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.language_select_background_white) );

                break;

            case R.id.rlurdu:

                ivRoman.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_unchecked));
                ivEng.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_unchecked));
                ivUrdu.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_checked));

                tvUrdu.setTextColor(ContextCompat.getColor(context, R.color.white));
                tvEng.setTextColor(ContextCompat.getColor(context, R.color.black));
                tvRoman.setTextColor(ContextCompat.getColor(context, R.color.black));

                rlEng.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.language_select_background_white) );
                rlUrdu.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.language_select_background_blue) );
                rlRoman.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.language_select_background_white) );


                break;

            case R.id.rlRoman:

                ivRoman.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_checked));
                ivEng.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_unchecked));
                ivUrdu.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_unchecked));

                tvUrdu.setTextColor(ContextCompat.getColor(context, R.color.black));
                tvEng.setTextColor(ContextCompat.getColor(context, R.color.black));
                tvRoman.setTextColor(ContextCompat.getColor(context, R.color.white));

                rlEng.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.language_select_background_white) );
                rlUrdu.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.language_select_background_white) );
                rlRoman.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.language_select_background_blue) );



                break;

        }
    }
}