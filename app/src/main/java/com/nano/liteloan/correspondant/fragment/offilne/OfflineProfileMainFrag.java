package com.nano.liteloan.correspondant.fragment.offilne;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nano.liteloan.R;
import com.nano.liteloan.info.BorrowerList;
import com.nano.liteloan.info.ConfigurationRespondObject;
import com.nano.liteloan.info.UserProfile;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.adapters.ViewPagerAdapter;
import com.nano.liteloan.presentation.fragment.profile.DocumentFragment;
import com.nano.liteloan.presentation.fragment.profile.ProfileStep1Fragment;
import com.nano.liteloan.presentation.fragment.profile.ProfileStep2Fragment;
import com.nano.liteloan.presentation.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

public class OfflineProfileMainFrag extends Fragment implements ViewPager.OnPageChangeListener {
    private ImageView ivStep1, ivStep2;
    ImageView ivStep3;
    CustomViewPager viewPager;
    public TextView tvHeading;
    private MainActivity context;
    private List<Fragment> frag;
    UserProfile userProfile = new UserProfile();
    ConfigurationRespondObject configurations;
    private ProfileInfoStep1 step1Fragment;
    private OfflineProfileStep2 profileStep2Fragment;
    private OfflineProfileDocument profileStep3Fragment;
    private AlertDialog dialog;
    private int position;
    public UserProfile borrowerList;
    String cnic;
    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = (MainActivity) context;

    }

    public OfflineProfileMainFrag(UserProfile borrowerList, String cnic) {
        this.borrowerList = borrowerList;
        this.cnic = cnic;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offline_profile_main, container, false);
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




        setViewPager(pageNumber);



        return view;
    }

    private void setViewPager(int pageNumber) {

        step1Fragment = new ProfileInfoStep1(this);
        profileStep2Fragment = new OfflineProfileStep2(this);
        profileStep3Fragment = new OfflineProfileDocument(this);


        frag.add(step1Fragment);
        frag.add(profileStep2Fragment);
        frag.add(profileStep3Fragment);

        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), frag));
        viewPager.addOnPageChangeListener(this);

        viewPager.setOffscreenPageLimit(3);

        viewPager.setPagingEnabled(false);

        viewPager.setCurrentItem(pageNumber, true);
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