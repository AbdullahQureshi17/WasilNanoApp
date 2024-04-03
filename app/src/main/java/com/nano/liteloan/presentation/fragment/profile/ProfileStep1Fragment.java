package com.nano.liteloan.presentation.fragment.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.canhub.cropper.CropImageView;
import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.UserBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.ProfileCreateRequestInfo;
import com.nano.liteloan.info.ProfileCreateResponseInfo;
import com.nano.liteloan.info.UserProfile;
import com.nano.liteloan.presentation.activity.Login;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.fragment.dialoguefragment.ImageViewFullScreen;
import com.nano.liteloan.presentation.fragment.dialoguefragment.InfoDialogFragment;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.squareup.picasso.Picasso;
import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Muhammad Umer on 07/06/2020.
 * Modified by Muhammad Ahmad
 */
public class ProfileStep1Fragment extends Fragment implements View.OnClickListener {

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private static final int ALL_PERMISSIONS_RESULT = 107;
    String Imagepath = null;

    private ImageView ivMale, ivFemale, ivTransgender;
    private LinearLayout lMale, lFemale, lTransgender;
    private EditText tvName;
    private EditText tvParentage;
    private EditText edEmail;
    private TextView tvDOB;
    private String gender = "male";
    private MainActivity context;
    private ImageView ivProfile;
    private String profileImageBase64 = null;
    private boolean isImageUrl;
    private MainProfileFragment main;
    private String selectedDate = null;
    private Spinner spMarital;
    private String marital;

    private ImageView ivAdd;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;
    }

    public ProfileStep1Fragment() {
        // Required empty public constructor
    }

    ProfileStep1Fragment(MainProfileFragment main) {
        this.main = main;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step1, container, false);



        ivMale = view.findViewById(R.id.male);
        ivFemale = view.findViewById(R.id.female);
        ivTransgender = view.findViewById(R.id.transgender);
        spMarital = view.findViewById(R.id.sp_marital_status);
        lMale = view.findViewById(R.id.l_male);
        lMale.setOnClickListener(this);
        lFemale = view.findViewById(R.id.l_female);
        lFemale.setOnClickListener(this);
        lTransgender = view.findViewById(R.id.l_transgender);
        lTransgender.setOnClickListener(this);

        Button btnext = view.findViewById(R.id.nextstep1);
        btnext.setOnClickListener(this);

        tvName = view.findViewById(R.id.ed_name);
        tvDOB = view.findViewById(R.id.ed_dob);
        tvDOB.setOnClickListener(this);

        edEmail = view.findViewById(R.id.ed_email);

        tvParentage = view.findViewById(R.id.ed_parentage);

//        ivAdd = view.findViewById(R.id.add);
//        ivAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


        setContent();
        setMarital("");
        checkPermissionForCamera();

        ivProfile = view.findViewById(R.id.iv_profile_image);
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ivProfile.getDrawable() != null && ((BitmapDrawable) ivProfile.getDrawable()).getBitmap() != null){
                    ImageViewFullScreen infoDialogFragment = new ImageViewFullScreen(context , ivProfile.getDrawable()
                            );
                    infoDialogFragment.show(context.getSupportFragmentManager(),
                            InfoDialogFragment.class.getName());

                    infoDialogFragment.setonImageSaved(new ImageViewFullScreen.OnImageSaved() {
                        @Override
                        public void onSubmit(String path) {
                            if(path != null && !path.isEmpty()){
                                profileImageBase64 = path;
                            } else {
                                profileImageBase64 = null;
                            }
                        }
                    });
                } else {
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setActivityTitle("Crop Image")
                            .setAutoZoomEnabled(true)
                            .setCropShape(CropImageView.CropShape.RECTANGLE)
                            .setCropMenuCropButtonTitle("Done")
                            .setRequestedSize(400, 400)
                            .setCropMenuCropButtonIcon(R.drawable.ic_tick)
                            .start(context, ProfileStep1Fragment.this);
                }



            }
        });

        TextView tvSkip = view.findViewById(R.id.tv_skip);
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.homeFrag();
            }
        });

        return view;
    }

    private void setMarital(String value) {

        final List<String> names = new ArrayList<>();

        names.add("Single");
        names.add("Married");
        names.add("Divorced");
        names.add("Seperated");
        names.add("Widow");


        ArrayAdapter<String> adp1 = new ArrayAdapter<>(context,
                R.layout.custom_spinner_text, names);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarital.setAdapter(adp1);
        if (!value.equals("")) {
            int spinnerPosition = adp1.getPosition(value);
            spMarital.setSelection(spinnerPosition);
        }

        spMarital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                marital = names.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void checkPermissionForCamera() {


        permissions.add(CAMERA);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissionsToRequest = AppUtils.findUnAskedPermissions(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                permissionsToRequest.size() > 0) {
            requestPermissions(permissionsToRequest
                    .toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
    }


    public void onDataResult(int requestCode, int resultCode, @Nullable Intent data) {


//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode != RESULT_CANCELED) {
                if (resultCode == RESULT_OK) {
                    String pathe = result.getUriFilePath(context, false);
                    Bitmap bitmap = AppUtils.checkPhotoOrientationAndGetImage(pathe);
                    assert bitmap != null;
                    profileImageBase64 = AppUtils.encodeImageBase64(bitmap);
                    ivProfile.setImageBitmap(bitmap);



                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Toast.makeText(context, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
                }
//            }
        }
    }

    void setContent() {

        if (main.userProfile != null) {
            UserProfile profile = main.userProfile;
            if (profile.name != null) {
                tvName.setText(profile.name);
            }
            if (profile.email != null) {

                edEmail.setText(profile.email);
            }
            if (profile.dob != null) {
                tvDOB.setText(profile.dob);
                selectedDate = profile.dob;
            }

            if (profile.parentage != null) {
                tvParentage.setText(profile.parentage);
            }

            if (profile.image != null && !profile.image.isEmpty()) {
                Picasso.get()
                        .load(profile.image)
                        .into(ivProfile);
                if (ivProfile.getDrawable() != null) {
                    isImageUrl = true;
                    Bitmap bitmap = ((BitmapDrawable) ivProfile.getDrawable()).getBitmap();
                    profileImageBase64 = AppUtils.encodeImageBase64(bitmap);
                }
                isImageUrl = true;

            } else {
                isImageUrl = false;
            }
            if (profile.maritalStatus != null && !profile.maritalStatus.isEmpty()) {
                setMarital(profile.maritalStatus);

            }

//            if(profile.gender != null){
//                tvName.setText(profile.name);
//            }

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.l_male:
                ivMale.setBackgroundResource(R.drawable.ic_chcek_orange);
                ivFemale.setBackgroundResource(R.color.Transparent);
                ivTransgender.setBackgroundResource(R.color.Transparent);
                gender = "male";

                lMale.setBackground(ContextCompat
                        .getDrawable(context, R.drawable.border_orange_light));

                lFemale.setBackground(ContextCompat
                        .getDrawable(context, R.drawable.border_gray_light));

                lTransgender.setBackground(ContextCompat
                        .getDrawable(context, R.drawable.border_gray_light));
                break;

            case R.id.l_female:
                ivMale.setBackgroundResource(R.color.Transparent);
                ivFemale.setBackgroundResource(R.drawable.ic_chcek_orange);
                ivTransgender.setBackgroundResource(R.color.Transparent);
                gender = "female";

                lMale.setBackground(ContextCompat
                        .getDrawable(context, R.drawable.border_gray_light));

                lFemale.setBackground(ContextCompat
                        .getDrawable(context, R.drawable.border_orange_light));

                lTransgender.setBackground(ContextCompat
                        .getDrawable(context, R.drawable.border_gray_light));

                break;

            case R.id.l_transgender:

                ivMale.setBackgroundResource(R.color.Transparent);
                ivFemale.setBackgroundResource(R.color.Transparent);
                ivTransgender.setBackgroundResource(R.drawable.ic_chcek_orange);
                gender = "transgender";

                lMale.setBackground(ContextCompat
                        .getDrawable(context, R.drawable.border_gray_light));

                lFemale.setBackground(ContextCompat
                        .getDrawable(context, R.drawable.border_gray_light));

                lTransgender.setBackground(ContextCompat
                        .getDrawable(context, R.drawable.border_orange_light));
                break;

            case R.id.nextstep1:

                if (isAllFilled()) {


                    saveContent();


                    break;
                }
                break;


            case R.id.ed_dob:
                openDatePicker(tvDOB);
                break;


        }
    }

    private boolean isAllFilled() {

        if (tvName.getText().toString().isEmpty()) {

            tvName.setError("Please provide your full name");
            tvName.requestFocus();
            return false;
        }
//        if (edEmail.getText().toString().isEmpty()) {
//
//            edEmail.setError("Please provide your email address");
//            edEmail.requestFocus();
//            return false;
//        }

        if (tvParentage.getText().toString().isEmpty()) {

            tvParentage.setError("Please enter your parentage");
            tvParentage.requestFocus();
            return false;
        }

        if (tvDOB.getText().toString().isEmpty()) {

            tvDOB.setError("Please select your date of birth");
            return false;
        }

        if(selectedDate == null){
            tvDOB.setError("Please select your date of birth");
            return false;
        }

        if (profileImageBase64 == null && !isImageUrl) {

            AlertUtils.showAlert(context, "Please upload your photo");
            return false;
        }
        return true;
    }

    private void saveContent() {
        boolean isUpdate = false;

        if (main.userProfile.name != null &&
                !main.userProfile.name.equals(tvName.getText().toString())) {

            isUpdate = true;
        }

        if (main.userProfile.maritalStatus != null &&
                !main.userProfile.maritalStatus.equals(marital)) {

            isUpdate = true;
        }
        if (main.userProfile.parentage != null &&
                !main.userProfile.parentage.equals(tvParentage.getText().toString())) {

            isUpdate = true;
        }


        if (main.userProfile.dob != null &&
                !main.userProfile.dob.equals(tvDOB.getText().toString())) {

            isUpdate = true;
        }
        if (main.userProfile.gender != null &&
                !main.userProfile.gender.equals(gender)) {

            isUpdate = true;
        }
        if (main.userProfile.email != null &&
                !main.userProfile.email.equals(edEmail.getText().toString())) {

            isUpdate = true;
        }
        if (profileImageBase64 != null) {

            isUpdate = true;
        }
        main.userProfile.name = tvName.getText().toString().trim();
        main.userProfile.parentage = tvParentage.getText().toString();
        main.userProfile.dob = selectedDate;
        main.userProfile.gender = gender;
        main.userProfile.image = profileImageBase64;
        main.userProfile.email = edEmail.getText().toString();
        main.userProfile.maritalStatus = marital;


        ProfileCreateRequestInfo createProfileRequest = new ProfileCreateRequestInfo();
        createProfileRequest.setName(main.userProfile.name);
        createProfileRequest.setParentage(main.userProfile.parentage);
        createProfileRequest.setDob(main.userProfile.dob);
        createProfileRequest.setGender(main.userProfile.gender);
        createProfileRequest.email = edEmail.getText().toString();
        createProfileRequest.martialStatus = marital;

        if (profileImageBase64 == null && isImageUrl) {

            try {
                Bitmap bitmap = ((BitmapDrawable) ivProfile.getDrawable()).getBitmap();
                profileImageBase64 = AppUtils.encodeImageBase64(bitmap);
            } catch (NullPointerException e) {

            }
           // Bitmap bitmap = ((BitmapDrawable) ivProfile.getDrawable()).getBitmap();
           // profileImageBase64 = AppUtils.encodeImageBase64(bitmap);

        }

        createProfileRequest.setImage(profileImageBase64);
        createProfileRequest.stage = "1";

        if (main.userProfile.iseditable == 0) {
            if (isUpdate) {
                submitUserInformation(createProfileRequest);
            } else {
                main.viewPager.setCurrentItem(getItem(+1), true);
            }
        } else {
            main.viewPager.setCurrentItem(getItem(+1), true);
        }

    }

    private void submitUserInformation(ProfileCreateRequestInfo createProfile) {

        final AlertDialog dialog = AlertUtils.showLoader(getActivity());

        UserBusiness userBusiness = new UserBusiness();
        userBusiness.setProfile(createProfile, new ResponseCallBack<ProfileCreateResponseInfo>() {
            @Override
            public void onSuccess(ProfileCreateResponseInfo body) {
                dialog.dismiss();

                main.viewPager.setCurrentItem(getItem(+1), true);

            }

            @Override
            public void onFailure(String message) {
                dialog.dismiss();

                AlertUtils.showAlert(getContext(), message);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void openDatePicker(final TextView tvDate) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.date_picked_dialog, null);
        dialog.setView(view);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());

        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(true);

        final DatePicker datePicker = view.findViewById(R.id.dg_datePicker);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year - 18);
        datePicker.setMaxDate(cal.getTimeInMillis());

        Button btnCancel = view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

        Button btnOk = view.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedDate =
                        "" + datePicker.getYear() + "-" + String.format(
                                "%02d-%02d",
                                datePicker.getMonth() + 1, datePicker.getDayOfMonth());


                tvDate.setText(datePicker.getDayOfMonth() + "-" + (datePicker.getMonth() + 1) + "-" +
                        datePicker.getYear());
                dialog.dismiss();
            }
        });
    }

    private int getItem(int i) {
        return main.viewPager.getCurrentItem() + i;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == ALL_PERMISSIONS_RESULT) {
            for (String perms : permissionsToRequest) {
                if (!AppUtils.hasPermission(perms)) {
                    permissionsRejected.add(perms);
                }
            }
            if (permissionsRejected.size() > 0 &&
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && (shouldShowRequestPermissionRationale(permissionsRejected.get(0)))) {

                AlertUtils.showAlert(getActivity(),
                        "These permissions are mandatory for the application. Please allow access.",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(permissionsRejected
                                                    .toArray(new String[permissionsRejected.size()]),
                                            ALL_PERMISSIONS_RESULT);
                                }
                            }
                        });
                return;
            }
        }
    }
}