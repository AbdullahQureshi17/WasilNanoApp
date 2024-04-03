package com.nano.liteloan.presentation.fragment.dialoguefragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.nano.liteloan.R;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */

public class DialogueFragment extends DialogFragment implements View.OnClickListener {

    private LinearLayout lIdcard, lSalary, lUtility, lOther;
    private ImageView ivIdcard;
    private ImageView ivSalary;
    private ImageView ivUtility;
    private ImageView ivOther;
    private TextView tvIdcard, tvSalary, tvUtility, tvOther;


    public DialogueFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dialogue, container, false);


        lIdcard = (LinearLayout) view.findViewById(R.id.id_card);
        lIdcard.setOnClickListener(this);
        lSalary = (LinearLayout) view.findViewById(R.id.salary_slip);
        lSalary.setOnClickListener(this);
        lUtility = (LinearLayout) view.findViewById(R.id.utility_bill);
        lUtility.setOnClickListener(this);
        lOther = (LinearLayout) view.findViewById(R.id.others);
        lOther.setOnClickListener(this);
        ImageView ivClose = (ImageView) view.findViewById(R.id.close);
        ivClose.setOnClickListener(this);


        ivIdcard = (ImageView) view.findViewById(R.id.id_card_image);
        ivSalary = (ImageView) view.findViewById(R.id.salary_slip_image);
        ivUtility = (ImageView) view.findViewById(R.id.utility_bill_image);
        ivOther = (ImageView) view.findViewById(R.id.others_image);

        tvIdcard = (TextView) view.findViewById(R.id.id_card_text);
        tvSalary = (TextView) view.findViewById(R.id.salary_slip_text);
        tvUtility = (TextView) view.findViewById(R.id.utility_bill_text);
        tvOther = (TextView) view.findViewById(R.id.others_text);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_card:

                lIdcard.setBackgroundResource(R.color.Black);
                ivIdcard.setBackgroundResource(R.drawable.ic_id_card_hover);
                tvIdcard.setTextColor(this.getResources().getColor(R.color.DarkOrange));

                break;

            case R.id.salary_slip:

                lSalary.setBackgroundResource(R.color.Black);
                ivSalary.setBackgroundResource(R.drawable.ic_salary_hover);
                tvSalary.setTextColor(this.getResources().getColor(R.color.DarkOrange));
                break;

            case R.id.utility_bill:
                lUtility.setBackgroundResource(R.color.Black);
                ivUtility.setBackgroundResource(R.drawable.ic_bill_hover);
                tvUtility.setTextColor(this.getResources().getColor(R.color.DarkOrange));
                break;

            case R.id.others:

                lOther.setBackgroundResource(R.color.Black);
                ivOther.setBackgroundResource(R.drawable.ic_other_hover);
                tvOther.setTextColor(this.getResources().getColor(R.color.DarkOrange));
                break;

            case R.id.close:

                dismiss();
                break;
        }
    }
}