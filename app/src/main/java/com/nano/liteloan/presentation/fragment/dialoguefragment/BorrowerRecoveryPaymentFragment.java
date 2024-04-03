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

import com.canhub.cropper.CropImageView;
import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.CityInfo;
import com.nano.liteloan.info.LiteAccount;
import com.nano.liteloan.info.RecoveryBorrowerPayRequest;
import com.nano.liteloan.info.Schedule;
import com.nano.liteloan.info.responce.RecoveryPayResponse;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.fragment.PaymentPaidFragment;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageActivity;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */

public class BorrowerRecoveryPaymentFragment extends DialogueFragment {

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
    private Spinner spMobilewallet;
    String mobileWallet = "Cash";
    private int id, borrowerId;
    private EditText edAmount;
    private PaymentPaidFragment.OnRecoveryPaid onRecoveryPaid;
    private LinearLayout llImage, llTransactionId;
    private String receiptnumber = "";
    private Spinner sp_have_wallet;
    private Spinner sp_another_loan;
    private Spinner sp_loan_purpose;

    private String have_wallet = "Yes";
    private String another_loan = "Yes";
    private String loan_purpose = "Business Capital";

    private ImageView ivRecoveryForm;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;
    }

    public BorrowerRecoveryPaymentFragment(int position, int id, int borrowerId) {
        this.position = position;
        this.id = id;
        this.borrowerId = borrowerId;
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_borrower_recovery_payment, container, false);

        ivRecoveryForm = view.findViewById(R.id.recipt2);
        ivRecoveryForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        if (this.getArguments() != null
                && this.getArguments()
                .getSerializable("schedule_detail") != null) {
            schedule = (Schedule) this.getArguments()
                    .getSerializable("schedule_detail");


        }

        sp_another_loan = view.findViewById(R.id.another_loan);
        sp_have_wallet = view.findViewById(R.id.have_wallet);
        sp_loan_purpose = view.findViewById(R.id.loan_purpose);

        setanotherLoanSpinner();
        sethaveWalletSpinner();
        setloanPurposeSpinner();

        llTransactionId = view.findViewById(R.id.lltransactionid);
        llImage = view.findViewById(R.id.image1);
        tvAccountBank = view.findViewById(R.id.accountDetailBank);
        spMobilewallet = view.findViewById(R.id.MobileWallet);
        tvAccountDetail = view.findViewById(R.id.accountDetailMobile);
//        if (PreferenceUtils.getInstance().getAccountDetail() != null) {
//            List<LiteAccount> accountDetail = PreferenceUtils.getInstance().getAccountDetail();
//
//            if (accountDetail.get(0) != null) {
//
//                tvAccountBank.setText(accountDetail.get(0).title + " (" + accountDetail.get(0).accountNo + ")\n"
//                        + "Account Title # " + accountDetail.get(0).accountTitle);
//            }
////            if (accountDetail.get(1).accountNo != null) {
////                tvAccountDetail.setText(accountDetail.get(1).title + " (" + accountDetail.get(1).accountNo + ")\n"
////                        + "Account Title # " + accountDetail.get(1).accountTitle);
////
////            }
//
//
//        }

//        setWalletType();
        edAmount = view.findViewById(R.id.amount);
        tvDate = view.findViewById(R.id.ed_submissiondate);
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDate(tvDate);
            }
        });


        recieptno = view.findViewById(R.id.recieptno);
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

    private void setWalletType() {

        List<String> names = new ArrayList<>();

        names.add("Mobile Wallet");
        names.add("Cheque");
        names.add("Cash");

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, names);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMobilewallet.setAdapter(adp1);

        spMobilewallet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mobileWallet = adapterView.getItemAtPosition(i).toString();
                if (mobileWallet.equalsIgnoreCase("Cash")) {
                    llImage.setVisibility(View.GONE);
                    profileImageBase64 = "";
                    llTransactionId.setVisibility(View.GONE);
                } else {
                    llImage.setVisibility(View.VISIBLE);
                    llTransactionId.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
                .start(context, BorrowerRecoveryPaymentFragment.this);

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
                ivRecoveryForm.setImageBitmap(bitmap);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(context, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }


    private void payScheduleAmount(String url) {

        RecoveryBorrowerPayRequest recoveryPayRequest = new RecoveryBorrowerPayRequest();
        recoveryPayRequest.setImage(profileImageBase64);

        recoveryPayRequest.setDate(date);
        recoveryPayRequest.setTransferId("");



        recoveryPayRequest.setBorrowerid(borrowerId);
        recoveryPayRequest.setId(String.valueOf(id));

        recoveryPayRequest.setAnotherLoan(another_loan);
        recoveryPayRequest.setHouseholdPhone(have_wallet);
        recoveryPayRequest.setLoanUseFor(loan_purpose);

        recoveryPayRequest.setPaymentType("Cash");

        recoveryPayRequest.setAmount(edAmount.getText().toString());
        submitUserInformation(recoveryPayRequest);
    }

    private boolean isValidFields() {



        if (date.equals("null")) {
            tvDate.setError("Add fee submission date");
            return false;
        }
        if ( profileImageBase64.equals("")) {
            AlertUtils.showAlert(context, "Please upload Disbursement form image");
            return false;
        }
        if (recieptno.getText().toString().isEmpty()) {

        }
        if (edAmount == null || edAmount.getText().toString().equals("")) {
            edAmount.setError("Enter Amount");
            return false;
        }
        if (edAmount == null || edAmount.getText().toString().equals("0") ) {
            edAmount.setError("please enter valid amount");
            return false;
        }
//        if (mobileWallet.equalsIgnoreCase("Cash")) {
//            receiptnumber = "0";
//        }
//        else if (recieptno.getText().toString().isEmpty()) {
//            recieptno.setError("Add receipt no");
//            return false;
//        } else if (!recieptno.getText().toString().isEmpty()) {
//            receiptnumber = recieptno.getText().toString();
//        }

        return true;
    }

    private void submitUserInformation(RecoveryBorrowerPayRequest request) {

        final AlertDialog dialog = AlertUtils.showLoader(getActivity());

        EasyLoanBusiness business = new EasyLoanBusiness();
        business.payBorrowerRecovery(request, new ResponseCallBack<RecoveryPayResponse>() {
            @Override
            public void onSuccess(RecoveryPayResponse body) {

                if (dialog != null) {
                    dialog.dismiss();
                }
                BorrowerRecoverySuccessfulldialgoue dialog1 =
                        new BorrowerRecoverySuccessfulldialgoue(context);

                dialog1.show(getChildFragmentManager(),
                        AmountDisburseFragment.class.getName());


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

    private void setanotherLoanSpinner() {

        List<String> anotherLoan = new ArrayList<>();

        anotherLoan.add("Yes");
        anotherLoan.add("No");
        anotherLoan.add("Maybe / Not now");


        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_text, anotherLoan);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_another_loan.setAdapter(adp1);
        sp_another_loan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                another_loan = anotherLoan.get(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setloanPurposeSpinner() {

        List<String> purposes = new ArrayList<>();

        purposes.add("Business Capital");
        purposes.add("Health");
        purposes.add("Education");
        purposes.add("Food");
        purposes.add("Utility Bills");
        purposes.add("Other");




        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_text, purposes);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_loan_purpose.setAdapter(adp1);
        sp_loan_purpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loan_purpose = purposes.get(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void sethaveWalletSpinner() {

        List<String> haveWalletList = new ArrayList<>();

        haveWalletList.add("Yes");
        haveWalletList.add("No");


        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_text, haveWalletList);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_have_wallet.setAdapter(adp1);
        sp_have_wallet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                have_wallet = haveWalletList.get(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}