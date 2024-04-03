package com.nano.liteloan.presentation.fragment.dialoguefragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.nano.liteloan.R;
import com.nano.liteloan.presentation.activity.MainActivity;

import java.util.Objects;

/**
 * Created by Muhammad Ahmad on 07/28/2020.
 */

@SuppressLint("ValidFragment")
public class WalletSuccessPopup extends DialogFragment {

    private MainActivity context;
    private TextView message;
    private String msgStr;
    private OnSuccessBack onSuccessBack;


    public WalletSuccessPopup(MainActivity context, String message) {
        this.context = context;
        this.msgStr = message;


    }

    public void setOnSuccessBack(OnSuccessBack onSuccessBack) {
        this.onSuccessBack = onSuccessBack;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_success_dialog,
                container, false);
        message = view.findViewById(R.id.tv_message);

        message.setText(msgStr);

        Button btnCon = view.findViewById(R.id.btn_continue);

        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WalletSuccessPopup.this.dismiss();

                if (onSuccessBack != null) {
                    onSuccessBack.onSuccessBack();
                } else {
                    context.homeFrag();
                }

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

//        if (getDialog() != null) {
//            getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
//                @Override
//                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
//                    if ((i == KeyEvent.KEYCODE_BACK)) {
//
//                        context.homeFrag();
//
//                        return false;
//                    } else {
//                        return false;
//                    }
//                }
//            });
//        }
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

    public interface OnSuccessBack {
        void onSuccessBack();
    }
}
