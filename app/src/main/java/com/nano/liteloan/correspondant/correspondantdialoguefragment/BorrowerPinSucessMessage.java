package com.nano.liteloan.correspondant.correspondantdialoguefragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.nano.liteloan.R;
import com.nano.liteloan.presentation.fragment.dialoguefragment.DialogueFragment;
import com.nano.liteloan.presentation.fragment.dialoguefragment.PinSucessMessage;
import com.nano.liteloan.utils.PreferenceUtils;

import java.util.Objects;
/**
 * Created by Muhammad Ahmad on 07/28/2020.
 */
@SuppressLint("ValidFragment")
public class BorrowerPinSucessMessage extends DialogueFragment {

    private Activity context;
    private PinSucessMessage.OnContinue onContinue;

    public BorrowerPinSucessMessage(Activity context, PinSucessMessage.OnContinue onContinue) {
        this.context = context;
        this.onContinue = onContinue;


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_borrower_pin_sucess_message, container, false);

        Button btnCon = view.findViewById(R.id.btn_continue);

        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BorrowerPinSucessMessage.this.dismiss();
                onContinue.onContinue();

            }
        });

        if(PreferenceUtils.getInstance().getCorrespondentUserDetail() != null &&
            PreferenceUtils.getInstance().getCorrespondentUserDetail().getName()!=null){

            TextView tvtext = view.findViewById(R.id.text);
            tvtext.setText("Mr " + PreferenceUtils.getInstance(). getCorrespondentUserDetail().getName()+
                    " (Correspondant) your pin has been successfully setup.");

        }


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

                        onContinue.onContinue();

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

    /**
     * Created by Muhammad Ahmad on 07/28/2020.
     */
    public interface OnContinue {

        void onContinue();
    }
}