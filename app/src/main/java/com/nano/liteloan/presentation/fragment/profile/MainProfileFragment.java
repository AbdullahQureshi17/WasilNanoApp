package com.nano.liteloan.presentation.fragment.profile;


import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.canhub.cropper.CropImage;
import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.business.impl.UserBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.ConfigurationRespondObject;
import com.nano.liteloan.info.ProfileCreateResponseInfo;
import com.nano.liteloan.info.UserProfile;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.adapters.ViewPagerAdapter;
import com.nano.liteloan.presentation.view.CustomViewPager;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */

public class MainProfileFragment extends Fragment implements ViewPager.OnPageChangeListener {


    private ImageView ivStep1, ivStep2;
    ImageView ivStep3;
    CustomViewPager viewPager;
    public TextView tvHeading;
    private MainActivity context;
    private List<Fragment> frag;
    UserProfile userProfile;
    ConfigurationRespondObject configurations;
    private ProfileStep1Fragment step1Fragment;
    private ProfileStep2Fragment profileStep2Fragment;
    private DocumentFragment profileStep3Fragment;
    private AlertDialog dialog;
    private int position;

    public MainProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = (MainActivity) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        tvHeading = view.findViewById(R.id.heading);

        frag = new ArrayList<>();
        ivStep1 = view.findViewById(R.id.step1);
        ivStep2 = view.findViewById(R.id.step2);
        ivStep3 = view.findViewById(R.id.step3);
        viewPager = view.findViewById(R.id.view_pager);
        int pageNumber = 0;
        if (this.getArguments() != null) {
            pageNumber =
                    this.getArguments().getInt(context.getString(R.string.page_number), 0);
        }


        getConfigurations();

        setViewPager(pageNumber);

        return view;
    }

    private void setViewPager(int pageNumber) {

        step1Fragment = new ProfileStep1Fragment(this);
        profileStep2Fragment = new ProfileStep2Fragment(this);
        profileStep3Fragment = new DocumentFragment(this);


        frag.add(step1Fragment);
        frag.add(profileStep2Fragment);
        frag.add(profileStep3Fragment);

        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), frag));
        viewPager.addOnPageChangeListener(this);

        viewPager.setOffscreenPageLimit(3);

        viewPager.setPagingEnabled(false);

        viewPager.setCurrentItem(pageNumber, true);
    }

    private void getConfigurations() {

        dialog = AlertUtils.showLoader(getActivity());

        EasyLoanBusiness business = new EasyLoanBusiness();
        business.getConfiguration(new ResponseCallBack<ConfigurationRespondObject>() {
            @Override
            public void onSuccess(ConfigurationRespondObject body) {

                configurations = body;
                getProfile();

            }

            @Override
            public void onFailure(String message) {

                AlertUtils.showAlert(context, message);

                getProfile();
            }
        });
    }

    private void getProfile() {

        UserBusiness userBusiness = new UserBusiness();
        userBusiness.getUserProfile(new ResponseCallBack<ProfileCreateResponseInfo>() {
            @Override
            public void onSuccess(ProfileCreateResponseInfo body) {

                dialog.dismiss();

                userProfile = body.userDate;
                step1Fragment.setContent();
                profileStep2Fragment.setContent();
                profileStep3Fragment.setContent();


            }

            @Override
            public void onFailure(String message) {

                dialog.dismiss();

                AlertUtils.showAlert(context, message);
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        if (position == 0) {
            tvHeading.setText("Basic Information");
            ivStep1.setBackgroundResource(R.drawable.ic_step);
            ivStep2.setBackgroundResource(R.drawable.ic_step);
            ivStep3.setBackgroundResource(R.drawable.tick_grey);
            this.position = position;

        }

        if (position == 1) {
            tvHeading.setText("Basic Information");
            ivStep1.setBackgroundResource(R.drawable.ic_step_complete);
            ivStep2.setBackgroundResource(R.drawable.ic_step);

            ivStep3.setBackgroundResource(R.drawable.tick_grey);
            this.position = position;

        }
        if (position == 2) {
            tvHeading.setText("Documents");
            ivStep1.setBackgroundResource(R.drawable.ic_step_complete);
            ivStep2.setBackgroundResource(R.drawable.ic_step_complete);
            ivStep3.setBackgroundResource(R.drawable.tick_grey);
            this.position = position;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public void setDataIntent(int requestCode, int resultCode, @Nullable Intent data) {
        if(position==0){

            step1Fragment.onDataResult(requestCode , resultCode , data);
        } else if(position ==2){
            profileStep3Fragment.onDataReturn(requestCode , resultCode , data);
        }
    }
}