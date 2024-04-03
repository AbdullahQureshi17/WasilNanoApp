package com.nano.liteloan.presentation.fragment.dialoguefragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nano.liteloan.R;

/**
 * Created by Muhammad Umer Jamil on 11/05/2020.
 */

public class HintDialogue extends DialogueFragment {


    public HintDialogue() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hint_dialogue, container, false);

        Button btOk = view.findViewById(R.id.ok);
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HintDialogue.this.dismiss();
            }
        });

        return view;

    }
}