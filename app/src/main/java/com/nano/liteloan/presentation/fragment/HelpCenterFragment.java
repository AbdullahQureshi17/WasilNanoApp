package com.nano.liteloan.presentation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.nano.liteloan.R;
import com.nano.liteloan.presentation.activity.MainActivity;

import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * Created by Muhammad Umer on 04/06/2020.
 */
public class HelpCenterFragment extends Fragment {
    MainActivity context;
    private ImageView ivBack;
    private HtmlTextView tvDetail;
    String detail;

    public HelpCenterFragment(MainActivity context , String detail) {
        this.context = context;
        this.detail = detail;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help_center, container, false);

        ivBack = view.findViewById(R.id.back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.onBackPressed();
            }
        });

        tvDetail = view.findViewById(R.id.tv_detail);
        tvDetail.setHtml(detail);

        return view;
    }
}