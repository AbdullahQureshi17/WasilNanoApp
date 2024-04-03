package com.nano.liteloan.presentation.fragment.dialoguefragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nano.liteloan.R;
/**
 * Created by Muhammad Umer on 11/07/2020.
 */
public class AlertDailogueFragment extends DialogueFragment {

    private String message;

    public AlertDailogueFragment(String message) {
        this.message = message;
        // Required empty public constructor
    }
    public AlertDailogueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alert_dailogue, container, false);
        TextView tvMessage = view.findViewById(R.id.message);
        tvMessage.setText(message);
        Button btCont = view.findViewById(R.id.btn_continue);
        btCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDailogueFragment.this.dismiss();
            }
        });

        return view;
    }
}