package com.nano.liteloan.presentation.fragment.dialoguefragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.nano.liteloan.R;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Objects;

/**
 * Created by Muhammad Ahmad on 07/28/2020.
 */

@SuppressLint("ValidFragment")
public class ImagePopupDialog extends DialogFragment {

    private String imageStr;
    private Bitmap bitmap;


    public ImagePopupDialog(MainActivity context, String image, Bitmap bitmap) {
        this.imageStr = image;
        this.bitmap = bitmap;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.panel_dialog_fragment,
                container, false);
        ImageView imageView = view.findViewById(R.id.image);

        if (bitmap != null) {

            imageView.setImageBitmap(bitmap);

        } else if (imageStr != null && !imageStr.isEmpty()) {

            Picasso.get().load(imageStr)
                    .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                    .into(imageView);
        }

        RelativeLayout icCross = view.findViewById(R.id.iv_cross);
        icCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePopupDialog.this.dismiss();
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
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            Objects.requireNonNull(d.getWindow()).setLayout(width, height);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        windowDimensions();
    }

    /**
     * Applying fragment window dimensions.
     */
    private void windowDimensions() {
        Window window = Objects.requireNonNull(getDialog()).getWindow();
        assert window != null;
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setLayout(width, height);
        window.setGravity(Gravity.CENTER);
    }
}
