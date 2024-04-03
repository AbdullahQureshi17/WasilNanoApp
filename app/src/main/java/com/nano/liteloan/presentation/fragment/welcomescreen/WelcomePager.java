package com.nano.liteloan.presentation.fragment.welcomescreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.GetProductResponce;
import com.nano.liteloan.info.LoanCategoryInfo;
import com.nano.liteloan.presentation.activity.Login;
import com.nano.liteloan.presentation.activity.WelcomeScreen;
import com.nano.liteloan.presentation.adapters.ViewPagerAdapter;
import com.nano.liteloan.presentation.view.CustomViewPager;
import com.nano.liteloan.utils.alerts.AlertUtils;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Muhammad Umer on 04/06/2020.
 */
public class WelcomePager extends Fragment implements ViewPager.OnPageChangeListener {

    public CustomViewPager viewPager;
    private List<Fragment> frag;
    WelcomeScreen context;
    List<Integer> show = new ArrayList<>();

    public WelcomePager(WelcomeScreen context) {
        this.context = context;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome_pager, container, false);

        show.add(0);
        show.add(1);

        frag = new ArrayList<>();
        viewPager = view.findViewById(R.id.view_pager);
        getProduct();
        viewPager.setCurrentItem(0, true);

        return view;
    }

    private void addTabs(GetProductResponce responce) {

        List<LoanCategoryInfo> info = new ArrayList<>();
        for (int i = 0; i < responce.loanCategories.size(); i++) {
            if (responce.loanCategories.get(i).isactive == 1) {
                info.add(responce.loanCategories.get(i));
            }
        }

        for (int i = 0; i < info.size(); i++) {
            ProductFragment pro = new ProductFragment(info.get(i), info.size() , i, WelcomePager.this);
            frag.add(pro);
        }
        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), frag));
        viewPager.addOnPageChangeListener(this);

        viewPager.setOffscreenPageLimit(responce.loanCategories.size());


    }

    public void callLogin() {

        startActivity(new Intent(getApplicationContext(), Login.class));

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int s = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    private void clearBackStack() {
        FragmentManager manager = context.getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void getProduct(){
        final AlertDialog dialog = AlertUtils.showLoader(getActivity());
        GetProductResponce getProduct = new GetProductResponce();

        EasyLoanBusiness business = new EasyLoanBusiness();
        business.getProducts(new ResponseCallBack<GetProductResponce>() {
            @Override
            public void onSuccess(GetProductResponce body) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                addTabs(body);

            }

            @Override
            public void onFailure(String message) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                AlertUtils.showAlert(getActivity(), message);
            }
        });

    }
}