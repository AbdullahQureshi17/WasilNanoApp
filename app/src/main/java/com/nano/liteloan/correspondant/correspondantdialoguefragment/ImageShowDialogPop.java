package com.nano.liteloan.correspondant.correspondantdialoguefragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.fragment.app.DialogFragment;

import com.nano.liteloan.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ImageShowDialogPop extends DialogFragment {

    private Bitmap bitmapImage;
    private String image;
    private OnPicEdit onEdit;
    private ProgressBar progress;

    public ImageShowDialogPop(Bitmap bitmapImage, String image, OnPicEdit onEdit) {
        this.bitmapImage = bitmapImage;
        this.image = image;
        this.onEdit = onEdit;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pic_image_layout, container, false);

        Button rlClose = view.findViewById(R.id.btn_back);
        rlClose.setOnClickListener(view1 -> dismiss());

        Button btnEdit = view.findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(v -> {
            onEdit.onEditClick();
            dismiss();
        });

        ImageView ivSlip = view.findViewById(R.id.iv_slip_image);
        progress = view.findViewById(R.id.progress);

        if (bitmapImage != null) {
            ivSlip.setImageBitmap(bitmapImage);
            progress.setVisibility(View.GONE);
        }

        if (image != null && !image.isEmpty()) {
            Picasso.get().load(image)
                    .fit()
                    .into(ivSlip, new Callback() {
                        @Override
                        public void onSuccess() {
                            progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            progress.setVisibility(View.GONE);
                        }
                    });
        }

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.dialogfragmentsettings);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            Objects.requireNonNull(d.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public interface OnPicEdit {
        void onEditClick();
    }
}
