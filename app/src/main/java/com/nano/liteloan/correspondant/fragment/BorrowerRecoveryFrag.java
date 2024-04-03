package com.nano.liteloan.correspondant.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageView;
import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.BusinessCorrespondant;
import com.nano.liteloan.business.impl.UserBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.ApplicationfeeResponse;
import com.nano.liteloan.info.LiteAccount;
import com.nano.liteloan.info.RecoveryPayRequest;
import com.nano.liteloan.info.SetApplicationFeeRequest;
import com.nano.liteloan.info.businesscorrespondant.ApplicationOverDueList;
import com.nano.liteloan.info.responce.RecoveryPayResponse;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.fragment.dialoguefragment.FeeSucessDialog;
import com.nano.liteloan.presentation.fragment.dialoguefragment.ImageViewFullScreen;
import com.nano.liteloan.presentation.fragment.dialoguefragment.InfoDialogFragment;
import com.nano.liteloan.presentation.fragment.profile.FeePyamntFragment;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class BorrowerRecoveryFrag extends Fragment {


    private MainActivity context;

    private ImageView reciept;
    private TextView tvFee;

    private EditText recieptno;

    private TextView tvfeetext;
    private String product = "";

    private RelativeLayout llbackground;

    private TextView tvDate;
    private String date = "null";
    private TextView tvAccountDetail, tvAccountBank;
    private String profileImageBase64;

    private String bankName = "JazzCash";
    private EditText edWalletno;
    private EditText llAmount;

    private TextView tvJazzAccount, tvJazzName, tvEasyAccount, tvEasyName;
    private  ApplicationOverDueList applicationOverDueList;



    public BorrowerRecoveryFrag(ApplicationOverDueList applicationOverDueList) {
        this.applicationOverDueList = applicationOverDueList;
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
        View view = inflater.inflate(R.layout.fragment_borrower_recovery2, container, false);

        llAmount = view.findViewById(R.id.amount);

        tvJazzAccount = view.findViewById(R.id.jazzcash_account);
        tvJazzName = view.findViewById(R.id.jazzcash_name);
        tvEasyAccount = view.findViewById(R.id.easypaisa_account);
        tvEasyName = view.findViewById(R.id.easypaisa_name);

        edWalletno = view.findViewById(R.id.walletnumber);
        tvfeetext = view.findViewById(R.id.fee);

        llbackground = view.findViewById(R.id.rltext);
        llbackground.setAlpha(0.4f);

        tvDate = view.findViewById(R.id.ed_submissiondate);
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDate(tvDate);
            }
        });


        if (PreferenceUtils.getInstance().getFee() != null) {
            tvfeetext.setText("Transfer Recovery of amount Rs "+PreferenceUtils.getInstance().getFee()+" to any of the follwing account and upload receipt.");
            product = PreferenceUtils.getInstance().getFee();
        }


        tvAccountDetail = view.findViewById(R.id.accountDetailMobile);
        tvAccountBank = view.findViewById(R.id.accountDetailBank);

        if (PreferenceUtils.getInstance().getAccountDetail() != null) {
            List<LiteAccount> accountDetail = PreferenceUtils.getInstance().getAccountDetail();

            for(int i = 0; i<accountDetail.size() ; i++) {
                tvJazzAccount.setText(accountDetail.get(i).accountNo);
                tvJazzName.setText(accountDetail.get(i).title);

                tvEasyName.setText(accountDetail.get(i).title);
                tvEasyAccount.setText(accountDetail.get(i).accountNo);

            }


        }

        recieptno = view.findViewById(R.id.recieptno);
        reciept = view.findViewById(R.id.recipt);
        reciept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(reciept.getDrawable() != null && ((BitmapDrawable) reciept.getDrawable()).getBitmap() != null){
                    ImageViewFullScreen infoDialogFragment = new ImageViewFullScreen(context , reciept.getDrawable()
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
                    dispatchTakePictureIntent();
                }
            }
        });

        Button btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValidFields()) {
                    Bitmap bitmap = ((BitmapDrawable) reciept.getDrawable()).getBitmap();
                    profileImageBase64 = AppUtils.encodeImageBase64(bitmap);
                    saveMobileWallet(profileImageBase64);
                }
            }
        });

        ImageView ivBack = view.findViewById(R.id.back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.homeFrag();
            }
        });

//        setSpinnerFee();


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
                .start(context, BorrowerRecoveryFrag.this);
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
                reciept.setImageBitmap(bitmap);


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
            reciept.setImageBitmap(bitmap);
        }
    }

    private void saveMobileWallet(String url) {

        RecoveryPayRequest request = new RecoveryPayRequest();

        request.setId(String.valueOf(applicationOverDueList.id));
        request.setWalletno(edWalletno.getText().toString());
        request.setTransferId(recieptno.getText().toString());
        request.setImage(profileImageBase64);
        request.setDate(date);
        request.setAmount(llAmount.getText().toString());


//        SetApplicationFeeRequest request = new SetApplicationFeeRequest();
//
//        request.setImageURL(url);
//        request.setFee(product);
//
//        request.setReceivedate(date);
//
//
//        request.setWalletno(edWalletno.getText().toString());
//
//        request.setTxnid(recieptno.getText().toString());
//        request.setStage(13);

        submitUserInformation(request);
    }

    private boolean isValidFields() {

        if (profileImageBase64 == null) {

            AlertUtils.showAlert(context, "Upload image");
            return false;
        }

        if (edWalletno.getText().toString().equalsIgnoreCase("") ||
                edWalletno.getText().toString().isEmpty()) {
            edWalletno.setError("Enter mobile number fee paid from.");
            edWalletno.setFocusable(true);
            return false;
        }
        if (edWalletno.getText().toString().length() > 11) {
            edWalletno.setError("Enter 11 digit mobile number");
            edWalletno.setFocusable(true);
            return false;
        }

        if (product.equalsIgnoreCase("") ||
                product.isEmpty()) {
            AlertUtils.showAlert(context, "add product fee ");
            return false;
        }
        if (date.equals("null")) {
            tvDate.setError("Add fee submission date");
            return false;
        }
        if (recieptno.getText().toString().isEmpty()) {
            recieptno.setError("Add receipt no");
            recieptno.requestFocus();
            return false;
        }
        if (llAmount.getText().toString().isEmpty()
        || llAmount.getText().toString().equalsIgnoreCase("0")) {
            llAmount.setError("Add amount you paid");
            llAmount.requestFocus();
            return false;
        }

        return true;
    }



    private void submitUserInformation(RecoveryPayRequest request) {

        final AlertDialog dialog = AlertUtils.showLoader(getActivity());

        BusinessCorrespondant userBusiness = new BusinessCorrespondant();
        userBusiness.payRecovery(request, new ResponseCallBack<RecoveryPayResponse>() {
            @Override
            public void onSuccess(RecoveryPayResponse body) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                FeeSucessDialog dialog1 = new FeeSucessDialog(context);
                dialog1.show(getChildFragmentManager(), FeeSucessDialog.class.getName());
            }

            @Override
            public void onFailure(String message) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                AlertUtils.showAlert(getContext(), message);
            }
        });
    }

    private void fromDate(final TextView tvDate) {


        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        String month = String.valueOf(monthOfYear);
                        if (month.length() == 1) {
                            month = "0" + month;
                        }
                        date = dayOfMonth + "-" + month + "-" + dayOfMonth;

                        tvDate.setText("" + year + "-" + (month) + "-" + dayOfMonth);

                    }
                }, year, month, day);

        datePickerDialog.getDatePicker()
                .setMaxDate(new Date().getTime());

        datePickerDialog.show();
    }

}