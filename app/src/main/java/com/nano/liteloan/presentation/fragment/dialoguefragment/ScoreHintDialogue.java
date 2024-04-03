package com.nano.liteloan.presentation.fragment.dialoguefragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nano.liteloan.R;
/**
 * Created by Muhammad Umer Jamil on 27/10/2021.
 */

public class ScoreHintDialogue extends DialogueFragment {



    public ScoreHintDialogue() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_score_hint_dialogue, container, false);

        Button btOk = view.findViewById(R.id.ok);
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScoreHintDialogue.this.dismiss();
            }
        });
        return view;
    }
}