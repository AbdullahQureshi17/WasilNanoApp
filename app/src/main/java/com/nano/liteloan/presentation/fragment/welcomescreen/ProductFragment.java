package com.nano.liteloan.presentation.fragment.welcomescreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.nano.liteloan.R;
import com.nano.liteloan.info.LoanCategoryInfo;

import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * Created by Muhammad Umer on 04/06/2020.
 */

public class ProductFragment extends Fragment {

    private TextView tvName;
    private WelcomePager context;
    private int lastPage;
    private int currentPage;
    Button btnext;
    private LoanCategoryInfo info;
    private HtmlTextView tvDesc;
    private HtmlTextView tvDetail;
    private HtmlTextView tvDisclaimer;
    private TextView tvText;
    private Button back;

    public ProductFragment(LoanCategoryInfo info, int i, int currentPage, WelcomePager context) {
        this.info = info;
        this.lastPage = i - 1;
        this.currentPage = currentPage;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        back = view.findViewById(R.id.back);

        tvDetail = view.findViewById(R.id.tv_detail);
        if (info != null && info.description != null) {
            tvDetail.setHtml(info.description);
        }
        tvDesc = view.findViewById(R.id.tv_desc);
        if (info != null && info.shortDescription != null) {
            tvDesc.setHtml(info.shortDescription);
        }
        tvDisclaimer = view.findViewById(R.id.tv_disclaimer);

        if (info != null && info.disclaimer != null) {
            tvDisclaimer.setHtml(info.disclaimer);
        }



        tvText = view.findViewById(R.id.loan);
        tvText.setText(info.name);

        btnext = view.findViewById(R.id.next);

        if (lastPage == currentPage) {
            btnext.setText("CONTINUE");
            back.setVisibility(View.GONE);
            btnext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    context.callLogin();
                }
            });
        } else if (currentPage == 0) {
            back.setVisibility(View.GONE);
            btnext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.viewPager.setCurrentItem(getItem(+1));
                }
            });

        } else {
            back.setVisibility(View.VISIBLE);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.viewPager.setCurrentItem(getItem(-1));
                }
            });
            btnext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.viewPager.setCurrentItem(getItem(+1));
                }
            });

        }

        return view;
    }

    private int getItem(int i) {
        return context.viewPager.getCurrentItem() + i;
    }


}