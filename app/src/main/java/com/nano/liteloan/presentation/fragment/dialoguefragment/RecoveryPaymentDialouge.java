package com.nano.liteloan.presentation.fragment.dialoguefragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.canhub.cropper.CropImageView;
import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.LiteAccount;
import com.nano.liteloan.info.RecoveryPayRequest;
import com.nano.liteloan.info.Schedule;
import com.nano.liteloan.info.responce.RecoveryPayResponse;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.fragment.PaymentPaidFragment;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageActivity;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Muhammad Ahmad on 08/04/2020.
 */
public class RecoveryPaymentDialouge extends DialogueFragment {

    private MainActivity context;
    private ImageView reciept;
    private EditText recieptno;
    private TextView tvDate;
    private String date = "null";
    private String profileImageBase64 = "";
    private Schedule schedule;
    private TextView tvAccountDetail;
    private TextView tvAccountBank;
    private int position;
    private int id;
    private EditText edAmount;
    private TextView accountno;
    private EditText edPhoneno;

    private PaymentPaidFragment.OnRecoveryPaid onRecoveryPaid;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;
    }

    public RecoveryPaymentDialouge(int position, int id) {
        this.position = position;
        this.id = id;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recovery_payment, container, false);

        if (this.getArguments() != null
                && this.getArguments()
                .getSerializable("schedule_detail") != null) {
            schedule = (Schedule) this.getArguments()
                    .getSerializable("schedule_detail");
        }
        edPhoneno = view.findViewById(R.id.phoneno);
        tvAccountBank = view.findViewById(R.id.accountDetailBank);
        accountno = view.findViewById(R.id.jazzcash_account);
        tvAccountDetail = view.findViewById(R.id.accountDetailMobile);
        if (PreferenceUtils.getInstance().getAccountDetail() != null) {
            List<LiteAccount> accountDetail = PreferenceUtils.getInstance().getAccountDetail();

            if (accountDetail.get(0) != null) {

                accountno.setText(accountDetail.get(0).accountNo);
            }
//            if (accountDetail.get(1).accountNo != null) {
//                tvAccountDetail.setText(accountDetail.get(1).title + " (" + accountDetail.get(1).accountNo + ")\n"
//                        + "Account Title # " + accountDetail.get(1).accountTitle);
//
//            }


        }

        edAmount = view.findViewById(R.id.amount);
        tvDate = view.findViewById(R.id.ed_submissiondate);
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDate(tvDate);
            }
        });


        recieptno = view.findViewById(R.id.recieptno);
        recieptno.setText("");
        reciept = view.findViewById(R.id.recipt);
        reciept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dispatchTakePictureIntent();

            }
        });


        Button btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValidFields()) {

                    Bitmap bitmap = ((BitmapDrawable) reciept.getDrawable()).getBitmap();
                    profileImageBase64 = AppUtils.encodeImageBase64(bitmap);
                    payScheduleAmount(profileImageBase64);
                }
            }
        });

        ImageView ivBack = view.findViewById(R.id.back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.onBackPressed();
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
                .start(context, RecoveryPaymentDialouge.this);

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

    private void payScheduleAmount(String url) {

        RecoveryPayRequest recoveryPayRequest = new RecoveryPayRequest();
        recoveryPayRequest.setImage(url);


        recoveryPayRequest.setWalletno(edPhoneno.getText().toString());
        recoveryPayRequest.setTransferId(recieptno.getText().toString());
        if (schedule != null) {

            recoveryPayRequest.setId(String.valueOf(id));

        }

        recoveryPayRequest.setAmount(edAmount.getText().toString());
        submitUserInformation(recoveryPayRequest);
    }

    private boolean isValidFields() {

        if (profileImageBase64.equalsIgnoreCase("")) {

            AlertUtils.showAlert(context, "Upload image");
            return false;
        }
//        if (date.equals("null")) {
//            tvDate.setError("Add fee submission date");
//            return false;
//        }
//        if (recieptno.getText().toString().isEmpty()) {
//            recieptno.setError("Add receipt no");
//            return false;
//        }
        if (edPhoneno == null || edPhoneno.getText().toString().equals("")) {
            edPhoneno.setError("Enter Phone no");
            edPhoneno.requestFocus();
            return false;
        }
        if (edAmount == null || edAmount.getText().toString().equals("")) {
            edAmount.setError("Enter Amount");
            edAmount.requestFocus();
            return false;
        }
        if (edAmount == null || edAmount.getText().toString().equals("0")) {
            edAmount.setError("Value must be greater than 0");
            return false;
        }

        return true;
    }

    private void submitUserInformation(RecoveryPayRequest request) {

        final AlertDialog dialog = AlertUtils.showLoader(getActivity());

        EasyLoanBusiness business = new EasyLoanBusiness();
        business.payRecovery(request, new ResponseCallBack<RecoveryPayResponse>() {
            @Override
            public void onSuccess(RecoveryPayResponse body) {

                if (dialog != null) {
                    dialog.dismiss();
                }

                context.onBackPressed();
                onRecoveryPaid.onPaid();

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

    @SuppressLint("SetTextI18n")
    private void fromDate(final TextView tvDate) {


        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        String month = String.valueOf(monthOfYear);
                        if (month.length() == 1) {
                            month = "0" + month;
                        }
                        date = "" + year + "-" + (month) + "-" + dayOfMonth;

                        tvDate.setText("" + dayOfMonth + "-" + (month) + "-" + year);


                    }
                }, year, month, day);
        datePickerDialog.getDatePicker()
                .setMaxDate(new Date().getTime());
        datePickerDialog.show();
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

    public void setOnRecoveryPaid(PaymentPaidFragment.OnRecoveryPaid onRecoveryPaid) {
        this.onRecoveryPaid = onRecoveryPaid;
    }
}