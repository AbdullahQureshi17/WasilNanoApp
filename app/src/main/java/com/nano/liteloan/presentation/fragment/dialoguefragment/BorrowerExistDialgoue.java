package com.nano.liteloan.presentation.fragment.dialoguefragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nano.liteloan.R;
import com.nano.liteloan.info.SearchBorrower;
import com.nano.liteloan.presentation.activity.MainActivity;

/**
 * Created by Muhammad Umer on 11/07/2020.
 */

public class BorrowerExistDialgoue extends DialogueFragment {


    Button btAdd, btCancel;
    MainActivity context;
    String cnic;
    SearchBorrower searchBorrower;
    private TextView tvMsg;

    public BorrowerExistDialgoue(MainActivity context, String s, SearchBorrower body) {
        this.context = context;
        this.cnic = s;
        this.searchBorrower = body;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_exist_dialgoue, container, false);

        tvMsg = view.findViewById(R.id.message);
        tvMsg.setText(searchBorrower.user.name + " "+"with Cnic number " + searchBorrower.user.cnic + " and parentage "+  searchBorrower.user.parentage+
                " is approved for a loan. " + searchBorrower.user.name + "" +". Would you like to proceed ?");

        btAdd = view.findViewById(R.id.add);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.userDetailFragment(cnic, searchBorrower);
                BorrowerExistDialgoue.this.dismiss();
            }
        });
        btCancel = view.findViewById(R.id.cancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BorrowerExistDialgoue.this.dismiss();
            }
        });


        return view;
    }
}