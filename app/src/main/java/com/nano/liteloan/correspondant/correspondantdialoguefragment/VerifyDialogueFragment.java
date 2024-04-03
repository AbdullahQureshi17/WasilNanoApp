package com.nano.liteloan.correspondant.correspondantdialoguefragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.slider.Slider;
import com.nano.liteloan.R;
import com.nano.liteloan.info.businesscorrespondant.PendingList;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.alerts.AlertUtils;


public class VerifyDialogueFragment extends DialogFragment {

    private PendingList pendingList;
    TextView tvAmount , tvMaxAmount , tvmin , tvmax;
    private ImageView ivCross;
    private Slider slider;
    private RelativeLayout rlSave;
    private int value;
    private OnValueSaved onLocationSaved;
    private EditText edAmount;
    private MainActivity context;


    public VerifyDialogueFragment(PendingList pendingList , MainActivity context) {
        this.pendingList = pendingList;
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_verify_dialogue, container, false);

        tvAmount = view.findViewById(R.id.requestedAmount);

        slider = view.findViewById(R.id.slider);

        edAmount = view.findViewById(R.id.amount);

        slider.setValueFrom(0f);
        slider.setValueTo(Float.valueOf(pendingList.requestedAmount));

        tvMaxAmount = view.findViewById(R.id.maxamount);
        tvAmount.setText(pendingList.requestedAmount + "");
        tvMaxAmount.setText(0 + "PKR - " + pendingList.requestedAmount +" PKR");

        tvmax = view.findViewById(R.id.maxiamount);
        tvmin = view.findViewById(R.id.minamount);

        tvmin .setText("0");

        ivCross = view.findViewById(R.id.cross);
        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifyDialogueFragment.this.dismiss();
            }
        });

        rlSave = view.findViewById(R.id.rlverify);
        rlSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLocationSaved != null) {
                    if(edAmount.getText().toString() != null && !edAmount.getText().toString().isEmpty() && !edAmount.getText().toString().equals("") &&
                        Integer.valueOf( edAmount.getText().toString()) >= 1000 &&
                            Integer.valueOf( edAmount.getText().toString()) <= pendingList.requestedAmount ){

                        onLocationSaved.onSaveValue( edAmount.getText().toString());
                        VerifyDialogueFragment.this.dismiss();

                    } else{
                        AlertUtils.showAlert(getContext() , "Please enter valid amount");
                    }

                }

            }
        });

        slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                tvmax.setText(String.valueOf((int)slider.getValue()));

            }
        });
        return view;
    }

    public void setOnValueSaved(OnValueSaved onLocationSaved) {
        this.onLocationSaved = onLocationSaved;
    }

    /**
     * Created by Muhammad Umer.
     */
    public interface OnValueSaved {

        void onSaveValue(String value);
    }

}