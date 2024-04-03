package com.nano.liteloan.presentation.fragment.dialoguefragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import com.nano.liteloan.R;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */

public class CalcultordialogueFragment extends DialogFragment implements View.OnClickListener {


    public CalcultordialogueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calcultor_dialogue_, container, false);
        ImageView ivClose = (ImageView) view.findViewById(R.id.close);
        ivClose.setOnClickListener(this);


        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                dismiss();
                break;


        }
    }
}