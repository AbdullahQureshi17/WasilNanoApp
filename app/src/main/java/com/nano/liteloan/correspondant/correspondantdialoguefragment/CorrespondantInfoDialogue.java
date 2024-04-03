package com.nano.liteloan.correspondant.correspondantdialoguefragment;

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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageView;
import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.business.impl.UserBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.BranchesList;
import com.nano.liteloan.info.ProfileCreateRequestInfo;
import com.nano.liteloan.info.ProfileCreateResponseInfo;
import com.nano.liteloan.info.UserProfile;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.fragment.dialoguefragment.DialogueFragment;
import com.nano.liteloan.presentation.fragment.dialoguefragment.UserSuccessPopup;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.santalu.maskedittext.MaskEditText;
import com.squareup.picasso.Picasso;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class CorrespondantInfoDialogue extends DialogueFragment {

    private ImageView ivProfile;
    private String profileImageBase64;

    private TextView tvDOB;
    private int branchId = 0;

    private BranchesList branchesList;
    private SearchableSpinner spBranch;

    private EditText edEmail, edPhone, edName;
    private MainActivity context;
    private String selectedDate = null;
    private Spinner spMarital;
    private String marital;
    private EditText tvParentage , edBranch;

    private MaskEditText edCnic;


    private ImageView ivMale, ivFemale, ivTransgender;
    private LinearLayout lMale, lFemale, lTransgender;
    private String gender = "male";

    private EditText edEmployeeCode;

    private UserProfile userData;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_correspondant_info_dialogue, container, false);

        edCnic = view.findViewById(R.id.ed_cnic);

        edBranch = view.findViewById(R.id.branch);

        ivMale = view.findViewById(R.id.male);
        ivFemale = view.findViewById(R.id.female);
        ivTransgender = view.findViewById(R.id.transgender);
        lMale = view.findViewById(R.id.l_male);
        lMale.setOnClickListener(this);
        lFemale = view.findViewById(R.id.l_female);
        lFemale.setOnClickListener(this);
        lTransgender = view.findViewById(R.id.l_transgender);
        lTransgender.setOnClickListener(this);

        edEmployeeCode = view.findViewById(R.id.ed_employeecode);

        tvDOB = view.findViewById(R.id.ed_dob);
        tvDOB.setOnClickListener(this);

        tvParentage = view.findViewById(R.id.ed_parentage);

        spMarital = view.findViewById(R.id.sp_marital_status);
        edEmail = view.findViewById(R.id.ed_emil);
        edPhone = view.findViewById(R.id.ed_phone);
        edPhone.setEnabled(false);

        edName = view.findViewById(R.id.ed_name);

        Button button = view.findViewById(R.id.btn_submit);
        button.setOnClickListener(this);

//        spBranch = view.findViewById(R.id.branch);

        ivProfile = view.findViewById(R.id.iv_profile_image);
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dispatchTakePictureIntent();


            }
        });

        edPhone.setText(PreferenceUtils.getInstance().getCorrespondentUserDetail().getPhone());

        if (PreferenceUtils.getInstance().getCorrespondentUserDetail() != null) {

            if (PreferenceUtils.getInstance().getCorrespondentUserDetail().getEmail() != null) {
                edEmail.setText(PreferenceUtils.getInstance().getCorrespondentUserDetail().getEmail());
            }
            if (PreferenceUtils.getInstance().getCorrespondentUserDetail().getName() != null) {
                edName.setText(PreferenceUtils.getInstance().getCorrespondentUserDetail().getName());
            }
            if (PreferenceUtils.getInstance().getCorrespondentUserDetail().getImageUrl() != null) {

                Picasso.get().load(PreferenceUtils
                        .getInstance().getCorrespondentUserDetail()
                        .getImageUrl()).into(ivProfile);
            }
//            if(PreferenceUtils.getInstance().getCorrespondentUserDetail() != null &&
//                    PreferenceUtils.getInstance().getCorrespondentUserDetail().getBranchname()!= null){
//                edBranch.setText(PreferenceUtils.getInstance().getUserDetail().getBranchname());
//            }
        }


//        setMarital("");
//        branchesDetail();

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

        if (userData != null && userData.getMaritalStatus() != null
                && !userData.getMaritalStatus().equalsIgnoreCase("")) {
            for (int i = 0; i < names.size(); i++) {
                if (names.get(i).toString().equalsIgnoreCase(userData.getMaritalStatus())) {
                    spBranch.setSelection(i);
                    return;
                }
            }


        }

    }

//    void branchesDetail() {
//
//        final AlertDialog dialog = AlertUtils.showLoader(context);
//
//        if (dialog != null) {
//            dialog.show();
//        }
//
//        EasyLoanBusiness business = new EasyLoanBusiness();
//        business.branchesDetail(new ResponseCallBack<BranchesList>() {
//            @Override
//            public void onSuccess(BranchesList body) {
//
//                dialog.dismiss();
//
//                branchesList = body;
//                setBranches(body);
//
//
//            }
//
//            @Override
//            public void onFailure(String message) {
//
//                dialog.dismiss();
//                AlertUtils.showAlert(getActivity(), message);
//            }
//        });
//        dialog.dismiss();
//    }

    private void setBranches(BranchesList body) {

        List<String> branches = new ArrayList<>();

        for (int i = 0; i < body.borrowerList.size(); i++) {
            branches.add(body.borrowerList.get(i).getBranchname());
        }

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, branches);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBranch.setAdapter(adp1);

        spBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                branchId = body.borrowerList.get(i).getBranchid();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (userData != null && userData.getBranchId() != null
                && !userData.getBranchId().equalsIgnoreCase("")) {
            int index = branches.indexOf(Integer.parseInt(userData.getBranchId()));
            if (index >= 0) {
                spBranch.setSelection(index);
            }
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


            case R.id.ed_dob:
                openDatePicker(tvDOB);
                break;

            case R.id.btn_submit:
                submitLoan();

                break;

        }
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
                .start(context, CorrespondantInfoDialogue.this);
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


    private void submitLoan() {

        if (edPhone.getText().toString().isEmpty() ||
                edPhone.getText().toString().equalsIgnoreCase("")) {

            edPhone.setError("Please enter a valid phone number");
            edPhone.requestFocus();
            return;
        }

        if (edName.getText().toString().isEmpty() ||
                edName.getText().toString().equalsIgnoreCase("")) {

            edName.setError("Please enter your full name");
            edName.requestFocus();
            return;
        }
        if (edCnic.getText().toString().isEmpty() ||
                edCnic.getText().toString().equalsIgnoreCase("")) {

            edCnic.setError("Please enter your full name");
            edCnic.requestFocus();
            return;
        }
        if (tvParentage.getText().toString().isEmpty() ||
                tvParentage.getText().toString().equalsIgnoreCase("")) {

            tvParentage.setError("Please enter your parentage");
            tvParentage.requestFocus();
            return;
        }
        if (selectedDate != null && selectedDate.equalsIgnoreCase("")) {

            tvDOB.setError("Please enter date of birth");
            tvDOB.requestFocus();
            return;
        }
//        if(edEmployeeCode.getText().toString().isEmpty() ||
//            edEmployeeCode.getText().toString().equalsIgnoreCase("")){
//            edEmployeeCode.setError("Please enter your employee code");
//            edEmployeeCode.requestFocus();
//            return;
//        }

//        if (edEmail.getText().toString().isEmpty()) {
//
//            edEmail.setError("Please enter a valid email address");
//            edEmail.requestFocus();
//            return;
//        }
        if (profileImageBase64 == null &&
                PreferenceUtils.getInstance().getCorrespondentUserDetail().getImageUrl() == null) {

            AlertUtils.showAlert(context, "Please upload your picture");
            return;
        }
        ProfileCreateRequestInfo requestInfo = new ProfileCreateRequestInfo();

        requestInfo.email = edEmail.getText().toString();
        requestInfo.setName(edName.getText().toString());
        if (profileImageBase64 == null && PreferenceUtils
                .getInstance().getCorrespondentUserDetail().getImageUrl() != null) {

//            ((BitmapDrawable)((LayerDrawable)image.getDrawable()).getDrawable(0)).getBitmap();
            Bitmap bitmap = ((BitmapDrawable) ivProfile.getDrawable()).getBitmap();

            profileImageBase64 = AppUtils.encodeImageBase64(bitmap);
            requestInfo.setImage(profileImageBase64);


        } else {
            requestInfo.setImage(profileImageBase64);
        }

        requestInfo.setParentage(tvParentage.getText().toString());
        requestInfo.setGender(gender);
        requestInfo.setDob(selectedDate);
        requestInfo.setMartialStatus(marital);
//        requestInfo.setBranchId(branchId);
        requestInfo.setCnic(edCnic.getText().toString());

        requestInfo.stage = "0";

        submitUserInformation(requestInfo);

    }

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

    private void getProfile() {

        final androidx.appcompat.app.AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }

        UserBusiness userBusiness = new UserBusiness();
        userBusiness.getUserProfile(new ResponseCallBack<ProfileCreateResponseInfo>() {
            @Override
            public void onSuccess(ProfileCreateResponseInfo body) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (body.userDate != null) {
                    userData = body.userDate;
                }
                if (body.userDate != null && body.userDate.parentage != null && !body.userDate.parentage.equalsIgnoreCase("")) {
                    tvParentage.setText(body.userDate.parentage);
                }
                if (body.userDate.getEmail() != null &&
                        !body.userDate.getEmail().equalsIgnoreCase("")) {

                }
                if (body.userDate.getParentage() != null &&
                        !body.userDate.getParentage().equalsIgnoreCase("")) {

                }
                if (body.userDate.getName() != null &&
                        !body.userDate.getName().equalsIgnoreCase("")) {

                }
                if (body.userDate.getCnic() != null &&
                        !body.userDate.getCnic().equalsIgnoreCase("")) {

                }
                if (body.userDate.getPhone() != null &&
                        !body.userDate.getPhone().equalsIgnoreCase("")) {

                }
                if (body.userDate.getDob() != null &&
                        !body.userDate.getDob().equalsIgnoreCase("")) {

                }
                if (body.userDate.getCnic() != null &&
                        !body.userDate.getCnic().equalsIgnoreCase("")) {

                }
                if (body.userDate.getGender() != null &&
                        !body.userDate.getGender().equalsIgnoreCase("")) {

                    if (gender.equalsIgnoreCase("male")) {
                        lMale.performClick();
                    } else if (gender.equalsIgnoreCase("female")) {
                        lFemale.performClick();
                    } else if (gender.equalsIgnoreCase("transgender")) {
                        lTransgender.performClick();
                    }
                }
                if(body.userDate.getBranchId() != null){
                    edBranch.setText(String.valueOf(body.userDate.branchId));
                }


            }

            @Override
            public void onFailure(String message) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                AlertUtils.showAlert(context, message);
            }
        });
        if (dialog != null) {
            dialog.dismiss();
        }
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

                        CorrespondantInfoDialogue.this.dismiss();
                        context.dashBoardFragment();
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