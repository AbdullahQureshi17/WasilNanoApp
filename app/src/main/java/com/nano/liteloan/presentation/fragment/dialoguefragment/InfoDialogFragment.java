package com.nano.liteloan.presentation.fragment.dialoguefragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.canhub.cropper.CropImageView;
import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.UserBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.ProfileCreateRequestInfo;
import com.nano.liteloan.info.ProfileCreateResponseInfo;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.squareup.picasso.Picasso;
import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageActivity;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Muhammad Ahmad on 07/10/2020.
 */

public class InfoDialogFragment extends DialogFragment implements View.OnClickListener {

    private ImageView ivProfile;
    private String profileImageBase64;

    private EditText edEmail, edPhone, edName;
    private MainActivity context;


    public InfoDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.person_info_dialog, container, false);
//        ImageView ivClose = (ImageView) view.findViewById(R.id.close);
//        ivClose.setOnClickListener(this);

        edEmail = view.findViewById(R.id.ed_emil);
        edPhone = view.findViewById(R.id.ed_phone);
        edPhone.setEnabled(false);

        edName = view.findViewById(R.id.ed_name);

        Button button = view.findViewById(R.id.btn_submit);
        button.setOnClickListener(this);


        ivProfile = view.findViewById(R.id.iv_profile_image);
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dispatchTakePictureIntent();


            }
        });

        edPhone.setText(PreferenceUtils.getInstance().getUserDetail().getPhone());

        if (PreferenceUtils.getInstance().getUserDetail() != null) {

            if (PreferenceUtils.getInstance().getUserDetail().getEmail() != null) {
                edEmail.setText(PreferenceUtils.getInstance().getUserDetail().getEmail());
            }
            if (PreferenceUtils.getInstance().getUserDetail().getName() != null) {
                edName.setText(PreferenceUtils.getInstance().getUserDetail().getName());
            }
            if (PreferenceUtils.getInstance().getUserDetail().getImageUrl() != null) {

                Picasso.get().load(PreferenceUtils
                        .getInstance().getUserDetail()
                        .getImageUrl()).into(ivProfile);
            }
        }


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
                .start(context, InfoDialogFragment.this);
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
                ivProfile.setImageBitmap(bitmap);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(context, "Cropping failed: " + result.getError(),
                        Toast.LENGTH_LONG).show();
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
            ivProfile.setImageBitmap(bitmap);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.close:
//                InfoDialogFragment.this.dismiss();
//                break;

            case R.id.btn_submit:
                submitLoan();
                break;

        }
    }

    private void submitLoan() {

        if (edPhone.getText().toString().isEmpty()) {

            edPhone.setError("Please enter a valid phone number");
            edPhone.requestFocus();
            return;
        }

        if (edName.getText().toString().isEmpty()) {

            edName.setError("Please enter your full name");
            edName.requestFocus();
            return;
        }

//        if (edEmail.getText().toString().isEmpty()) {
//
//            edEmail.setError("Please enter a valid email address");
//            edEmail.requestFocus();
//            return;
//        }
        if (profileImageBase64 == null &&
                PreferenceUtils.getInstance().getUserDetail().getImageUrl() == null) {

            AlertUtils.showAlert(context, "Please upload your picture");
            return;
        }
        ProfileCreateRequestInfo requestInfo = new ProfileCreateRequestInfo();

        requestInfo.email = edEmail.getText().toString();
        requestInfo.setName(edName.getText().toString());
        if (profileImageBase64 == null && PreferenceUtils
                .getInstance().getUserDetail().getImageUrl() != null) {

//            ((BitmapDrawable)((LayerDrawable)image.getDrawable()).getDrawable(0)).getBitmap();
            Bitmap bitmap = ((BitmapDrawable) ivProfile.getDrawable()).getBitmap();

            profileImageBase64 = AppUtils.encodeImageBase64(bitmap);
            requestInfo.setImage(profileImageBase64);


        } else {
            requestInfo.setImage(profileImageBase64);
        }

        requestInfo.stage = "0";

        submitUserInformation(requestInfo);

    }

    private void submitUserInformation(ProfileCreateRequestInfo createProfile) {

        final AlertDialog dialog = AlertUtils.showLoader(getActivity());

        UserBusiness userBusiness = new UserBusiness();
        userBusiness.setProfile(createProfile, new ResponseCallBack<ProfileCreateResponseInfo>() {
            @Override
            public void onSuccess(ProfileCreateResponseInfo body) {
                dialog.dismiss();


                MainActivity.isInfoShowing = false;
                AppUtils.isPinSet = true;

                UserSuccessPopup popup = new UserSuccessPopup(context, new UserSuccessPopup.OnContinue() {
                    @Override
                    public void onContinue() {

                        InfoDialogFragment.this.dismiss();
                        context.homeFrag();
                    }
                });
                popup.show(getChildFragmentManager(), UserSuccessPopup.class.getName());

            }

            @Override
            public void onFailure(String message) {
                dialog.dismiss();

                AlertUtils.showAlert(getContext(), message);
            }
        });
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

    @Override
    public void onResume() {
        super.onResume();

        if (getDialog() != null) {
            getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    if ((i == KeyEvent.KEYCODE_BACK)) {
                        AlertUtils.showAlert(context, "Please provide information to continue");
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();

        if (d != null) {
            d.getWindow()
                    .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

}