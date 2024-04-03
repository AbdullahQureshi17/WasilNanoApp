package com.nano.liteloan.presentation.fragment.dialoguefragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nano.liteloan.R;
import com.nano.liteloan.info.BorrowerReceiptObj;
import com.nano.liteloan.presentation.activity.MainActivity;
/**
 * Created by Muhammad Umer on 07/06/2020.
 */

public class RecoveryInfoDialgoue extends DialogueFragment {

    private MainActivity context;
    private Button btContinue , btnCancel;
    private BorrowerReceiptObj getApplicationInfo;
    private TextView tvTerm;
    public RecoveryInfoDialgoue(MainActivity context, BorrowerReceiptObj borrowerReceiptObj) {

        this.getApplicationInfo = borrowerReceiptObj;
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_borrower_payment_dialogue, container, false);


        tvTerm = view.findViewById(R.id.termstext);

        if (getApplicationInfo.duedate != null) {
            tvTerm.setText("Your due date to pay loan amount of Rs. " + getApplicationInfo.amount + " is:" +
                    " " + getApplicationInfo.duedate +
                    " You can pay your loan either in installments or in full within your due date.");
        }

        btContinue = view.findViewById(R.id.btn_continue);
        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecoveryInfoDialgoue.this.dismiss();
                context.borrowerRecoveryFragment(getApplicationInfo);
            }
        });

        btnCancel = view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecoveryInfoDialgoue.this.dismiss();
            }
        });

        return view;
    }
}