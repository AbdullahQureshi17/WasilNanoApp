package com.nano.liteloan.presentation.fragment.dialoguefragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nano.liteloan.R;
import com.nano.liteloan.info.GetApplicationInfo;
import com.nano.liteloan.presentation.activity.MainActivity;

/**
 * Created by Muhammad Ahmad on 07/28/2020.
 */
public class PaymentMethodDialogue extends DialogueFragment {

    private MainActivity context;
    private Button btContinue , btnCancel;
    private GetApplicationInfo getApplicationInfo;
    private TextView tvTerm;

    public PaymentMethodDialogue(MainActivity context, GetApplicationInfo getApplicationInfo) {

        this.getApplicationInfo = getApplicationInfo;
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_method_dialogue, container, false);

        tvTerm = view.findViewById(R.id.termstext);

        if (getApplicationInfo.applicationDate != null) {
            tvTerm.setText("Your due date to pay loan amount of Rs. " + getApplicationInfo.disbursementAmount + " is:" +
                    " " + getApplicationInfo.applicationDate +
                    ". You can pay your loan either in installments or in full within your due date.\n" +
                    ". Application processing fee for loan is added in the due amount alongwith the loan amount approved.");
        }

        btContinue = view.findViewById(R.id.btn_continue);
        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentMethodDialogue.this.dismiss();
                context.paymentPaidFragment(getApplicationInfo);
            }
        });
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentMethodDialogue.this.dismiss();

            }
        });


        return view;
    }
}