package com.nano.liteloan.presentation.fragment.dialoguefragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nano.liteloan.R;
import com.nano.liteloan.presentation.activity.MainActivity;

/**
 * Created by Muhammad Umer on 11/07/2020.
 */
public class AmountDisburseFragment extends DialogueFragment {


    private Button btContinue;
    private MainActivity context;


    public AmountDisburseFragment(MainActivity context) {
        this.context = context;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_amount_disburse, container, false);

        btContinue = view.findViewById(R.id.btn_continue);
        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.homeFrag();
            }
        });


        return view;
    }
}