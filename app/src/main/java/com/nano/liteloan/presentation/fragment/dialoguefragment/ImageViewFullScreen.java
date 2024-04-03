package com.nano.liteloan.presentation.fragment.dialoguefragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageView;
import com.nano.liteloan.R;
import com.nano.liteloan.info.LoanApplyRequest;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.fragment.profile.FeePyamntFragment;
import com.nano.liteloan.presentation.fragment.profile.ProfileStep1Fragment;
import com.nano.liteloan.utils.AppUtils;
import com.squareup.picasso.Picasso;


public class ImageViewFullScreen extends DialogFragment {

   private ImageView ivImage , ivClose;
   private MainActivity context;
   private Drawable image;
   private Button btBack, btEdit;
   private String profileImageBase64;
    private ImageViewFullScreen.OnImageSaved onImageSaved;



    public ImageViewFullScreen(MainActivity context , Drawable image) {
        this.context = context;
        this.image = image;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_view, container, false);

        ivImage = view.findViewById(R.id.image);
        ivImage.setImageDrawable(image);

        ivClose = view.findViewById(R.id.close);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageViewFullScreen.this.dismiss();
            }
        });

        btBack = view.findViewById(R.id.back);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageViewFullScreen.this.dismiss();
            }
        });

        btEdit = view.findViewById(R.id.edit);
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
                ImageViewFullScreen.this.dismiss();
            }
        });


        return view;
    }

    private void dispatchTakePictureIntent() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityTitle("Crop Image")
                .setAutoZoomEnabled(true)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setCropMenuCropButtonTitle("Done")
                .setRequestedSize(400, 400)
                .setCropMenuCropButtonIcon(R.drawable.ic_tick)
                .start(context, ImageViewFullScreen.this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                String pathe = result.getUriFilePath(context, false);
                Bitmap bitmap = AppUtils.checkPhotoOrientationAndGetImage(pathe);
                assert bitmap != null;
                profileImageBase64 = AppUtils.encodeImageBase64(bitmap);
                ivImage.setImageBitmap(bitmap);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(context, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * handle crop after image is taken and then set it on views.
     *
     * @param resultCode result code
     * @param data       Intent data
     */
    private void handleCrop(int resultCode, Uri data) {
        if (resultCode == RESULT_OK) {

            Bitmap bitmap = AppUtils.checkPhotoOrientationAndGetImage(AppUtils
                    .getRealPathFromURI(data,
                            context));
            assert bitmap != null;
            profileImageBase64 = AppUtils.encodeImageBase64(bitmap);

        }
    }


    public interface OnImageSaved {

        void onSubmit(String path);
    }
    public void setonImageSaved(OnImageSaved onImageSaved) {
        this.onImageSaved = onImageSaved;
    }


}