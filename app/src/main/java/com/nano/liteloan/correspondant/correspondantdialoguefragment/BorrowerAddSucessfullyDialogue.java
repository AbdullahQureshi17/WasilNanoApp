package com.nano.liteloan.correspondant.correspondantdialoguefragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.nano.liteloan.R;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.fragment.dialoguefragment.EvaluationSuccessPopup;

import java.util.Objects;


public class BorrowerAddSucessfullyDialogue extends DialogFragment {


    private MainActivity context;

    public BorrowerAddSucessfullyDialogue(MainActivity context) {
        this.context = context;


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_borrower_add_sucessfully_dialogue, container, false);
        Button btnCon = view.findViewById(R.id.btn_continue);

        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BorrowerAddSucessfullyDialogue.this.dismiss();

                context.dashBoardFragment();
            }
        });

        return view;

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,
                R.style.dialogfragmentsettings);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Objects.requireNonNull(dialog.getWindow())
                .requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            int width = ViewGroup.LayoutParams.WRAP_CONTENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            Objects.requireNonNull(d.getWindow()).setLayout(width, height);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        windowDimensions();

        if (getDialog() != null) {
            getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    if ((i == KeyEvent.KEYCODE_BACK)) {

                      context.dashBoardFragment();

                        return false;
                    } else {
                        return false;
                    }
                }
            });
        }
    }

    /**
     * Applying fragment window dimensions.
     */
    private void windowDimensions() {
        Window window = Objects.requireNonNull(getDialog()).getWindow();
        assert window != null;
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setLayout(width, height);
        window.setGravity(Gravity.CENTER);
    }
}