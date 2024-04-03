package com.nano.liteloan.presentation.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mukesh.MarkdownView;
import com.nano.liteloan.R;
import com.nano.liteloan.info.LoanApplyRequest;
import com.nano.liteloan.info.LoanCategoryInfo;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.fragment.dialoguefragment.ApplicationSubmitSuccessDialog;
import com.nano.liteloan.presentation.fragment.dialoguefragment.SubmitApplicationDialogue;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.PreferenceUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by Muhammad Umer on 08/04/2021.
 */
public class MobileFinancing extends Fragment implements View.OnClickListener,
        SubmitApplicationDialogue.OnApplicationSubmit {


    private TextView tvText;
    private ImageView ivicon;
    MainActivity context;
    private LoanCategoryInfo loanCategoryInfo;
    private TextView tvDesc;
    private MarkdownView tvDetail;
    private MarkdownView tvDisclaimer;
    private TextView tvLoanRange, tvMonths;
    private View rlHeaderBg;
    private RelativeLayout applyLoan;
    private Button button , btnBack;


    public MobileFinancing() {
        // Required empty public constructor
    }
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mobile_financing, container, false);


        if (this.getArguments() != null
                && this.getArguments().getSerializable("loan_detail") != null) {
            loanCategoryInfo = (LoanCategoryInfo) this.getArguments()
                    .getSerializable("loan_detail");
        }

        button = view.findViewById(R.id.btn_apply);
        applyLoan = view.findViewById(R.id.rl_bottom_panel);

        if (PreferenceUtils.getInstance().isFeePaid() == 1){
            applyLoan.setVisibility(View.VISIBLE);
            button.setAlpha(1.0f);
            button.setClickable(true);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    applyLoanPopUp();
                }
            });
        } else {

            applyLoan.setVisibility(View.VISIBLE);
            button.setAlpha(0.4f);
            button.setClickable(false);
        }

        btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.homeFrag();
            }
        });

        tvDetail = view.findViewById(R.id.tv_detail);
        tvDisclaimer = view.findViewById(R.id.tv_disclaimer);
        tvDesc = view.findViewById(R.id.tv_desc);

        rlHeaderBg = view.findViewById(R.id.rl_header_bg);

        tvText = view.findViewById(R.id.loan);
        LinearLayout lCalculator = view.findViewById(R.id.calculator);
        lCalculator.setOnClickListener(this);

        ImageView ivBack = view.findViewById(R.id.back);
        ivBack.setOnClickListener(this);
        ivicon = view.findViewById(R.id.general_image);

        tvLoanRange = view.findViewById(R.id.tv_loanRang);
        tvMonths = view.findViewById(R.id.tv_installment_month);

        setContent();



        return view;
    }

    private void applyLoanPopUp() {

        SubmitApplicationDialogue dialogue = new SubmitApplicationDialogue(context);
        dialogue.setOnApplicationSubmit(this);
        Bundle bundle = new Bundle();
        bundle.putSerializable("loan_detail", loanCategoryInfo);
        dialogue.setArguments(bundle);
        if (getFragmentManager() != null) {
            dialogue.show(getFragmentManager(),
                    SubmitApplicationDialogue.class.getName());
        }
    }

    @SuppressLint("SetTextI18n")
    private void setContent() {

        tvText.setText(loanCategoryInfo.name);

        if (loanCategoryInfo.logo != null
                && !loanCategoryInfo.logo.isEmpty()) {

            Picasso.get().load(loanCategoryInfo.logo).into(ivicon);
        } else {
            ivicon.setImageResource(R.drawable.ic_general);
        }

        tvDesc.setText(loanCategoryInfo.shortDescription);


        tvDetail.setMarkDownText(loanCategoryInfo.description);
        tvDisclaimer.setMarkDownText(loanCategoryInfo.disclaimer);

        tvMonths.setText(loanCategoryInfo.installmentMonth + " Months");
        tvLoanRange.setText("Up to Rs. " +
                AppUtils.getFormatAmount(Double.valueOf(loanCategoryInfo.loanLimit)));

        rlHeaderBg.setBackgroundColor(Color.parseColor(loanCategoryInfo.colorCode));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.back:

                context.onBackPressed();
                break;

        }
    }

    @Override
    public void onSubmit(LoanApplyRequest loanApplyRequest) {

        ApplicationSubmitSuccessDialog dialog1 =
                new ApplicationSubmitSuccessDialog(context);

        dialog1.show(getChildFragmentManager(),
                ApplicationSubmitSuccessDialog.class.getName());
    }
}