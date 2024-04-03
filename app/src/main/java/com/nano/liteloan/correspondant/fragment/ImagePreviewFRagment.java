package com.nano.liteloan.correspondant.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nano.liteloan.R;
import com.nano.liteloan.presentation.activity.MainActivity;

/**
 * Created by Muhammad Ahmad on 11/10/2023.
 */
public class ImagePreviewFRagment extends Fragment {

    Bitmap bitmap;
    ImageView ivImage;

    MainActivity context;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;
    }

    public ImagePreviewFRagment() {
        // Required empty public constructor
    }

    public ImagePreviewFRagment(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_preview_f_ragment, container, false);

        ivImage = view.findViewById(R.id.iv_image);

        if(bitmap != null)
        ivImage.setImageBitmap(bitmap);

        return view;
    }
}