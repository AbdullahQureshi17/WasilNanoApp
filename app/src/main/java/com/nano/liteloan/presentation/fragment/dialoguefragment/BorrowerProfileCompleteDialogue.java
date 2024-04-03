package com.nano.liteloan.presentation.fragment.dialoguefragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nano.liteloan.R;
import com.nano.liteloan.info.BorrowerReceiptObj;
import com.nano.liteloan.presentation.activity.MainActivity;
/**
 * Created by Muhammad Umer on 07/06/2020.
 */

public class BorrowerProfileCompleteDialogue extends DialogueFragment {

    private MainActivity context;
    BorrowerReceiptObj borrowerReceiptObj;

    public BorrowerProfileCompleteDialogue(MainActivity context, BorrowerReceiptObj borrowerReceiptObj) {
        this.context = context;
        this.borrowerReceiptObj = borrowerReceiptObj;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_borrower_profile, container, false);

        Button btnCon = view.findViewById(R.id.btn_continue);

        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (borrowerReceiptObj.type.equalsIgnoreCase("Mobile Wallet")) {
//                    context.userWalletFragment(borrowerReceiptObj, loanInfoRequest);
//                } else if (borrowerReceiptObj.type.equalsIgnoreCase("Cheque")) {
//                    context.userChequeFragment(borrowerReceiptObj, loanInfoRequest);
//                } else {
//                    context.homeFrag();
//                }
                BorrowerProfileCompleteDialogue.this.dismiss();


            }
        });
        return view;
    }
}