package com.nano.liteloan.presentation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nano.liteloan.R;
import com.nano.liteloan.presentation.activity.MainActivity;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class TotalpaidFragment extends Fragment implements View.OnClickListener {

    MainActivity context;


    public TotalpaidFragment() {
        // Required empty public constructor
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_totalpaid, container, false);
        ImageView ivBack = view.findViewById(R.id.back);
        ivBack.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            context.onBackPressed();
        }
    }
}