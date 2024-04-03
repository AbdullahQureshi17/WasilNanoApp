package com.nano.liteloan.presentation.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;
/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragment;



    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> frag) {
        super(fm);
        this.fragment = frag;

    }

    @Override
    public Fragment getItem(int position) {
        return fragment.get(position);
    }

    @Override
    public int getCount() {
        return fragment.size(); //three fragments
    }
}