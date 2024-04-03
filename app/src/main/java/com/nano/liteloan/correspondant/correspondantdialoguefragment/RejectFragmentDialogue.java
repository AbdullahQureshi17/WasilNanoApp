package com.nano.liteloan.correspondant.correspondantdialoguefragment;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nano.liteloan.R;
import com.nano.liteloan.presentation.activity.MainActivity;


public class RejectFragmentDialogue extends DialogFragment {


    private MainActivity context;
    private EditText tvComment;
    private OnReasonSaved onLocationSaved;
    private RelativeLayout rlReject;
    private ImageView ivCross;

    public RejectFragmentDialogue(MainActivity mainActivity) {

        context = mainActivity;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reject_dialogue, container, false);

        ivCross = view.findViewById(R.id.cross);
        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RejectFragmentDialogue.this.dismiss();
            }
        });
        rlReject = view.findViewById(R.id.rlreject);
        rlReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tvComment.getText().toString().equalsIgnoreCase("") &&
                    !tvComment.getText().toString().isEmpty()){
                    if (onLocationSaved != null) {
                        onLocationSaved.onSavereson( tvComment.getText().toString());

                    }
                    View view = context.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    RejectFragmentDialogue.this.dismiss();
                } else {
                    tvComment.setError("Please enter reson of rejection");
                }
            }
        });
        tvComment = view.findViewById(R.id.comment);
        tvComment.requestFocus();
        InputMethodManager imm = (InputMethodManager)   context.getSystemService(context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        return view;
    }

    public void setOnReasonSaved(OnReasonSaved onLocationSaved) {
        this.onLocationSaved = onLocationSaved;
    }

    /**
     * Created by Muhammad Umer.
     */
    public interface OnReasonSaved {

        void onSavereson(String value);
    }

}