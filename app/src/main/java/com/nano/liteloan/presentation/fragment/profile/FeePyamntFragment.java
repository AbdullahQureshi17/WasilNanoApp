package com.nano.liteloan.presentation.fragment.profile;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageView;
import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.UserBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.ApplicationfeeResponse;
import com.nano.liteloan.info.LiteAccount;
import com.nano.liteloan.info.SetApplicationFeeRequest;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.fragment.dialoguefragment.DialogueFragment;
import com.nano.liteloan.presentation.fragment.dialoguefragment.FeeSucessDialog;
import com.nano.liteloan.presentation.fragment.dialoguefragment.ImageViewFullScreen;
import com.nano.liteloan.presentation.fragment.dialoguefragment.InfoDialogFragment;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Muhammad Ahmad on 08/04/2020.
 */
public class FeePyamntFragment extends DialogueFragment {

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

    private TextView tvJazzAccount, tvJazzName, tvEasyAccount, tvEasyName;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;
    }

    public FeePyamntFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fee_payment, container, false);


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
            tvfeetext.setText("Pay Application fee of Rs "+PreferenceUtils.getInstance().getFee()+" in any of the following account and upload payment receipt / screenshot.");
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
//            if (accountDetail.get(0) != null) {
//
//
//                if (accountDetail.get(1).title.equalsIgnoreCase("Jazzcash Account")) {
//                    tvJazzAccount.setText(accountDetail.get(1).accountNo);
//                    tvJazzName.setText(accountDetail.get(1).accountTitle);
//                    bankName = "JazzCash";
//                } else if (accountDetail.get(1).title.equalsIgnoreCase("HBL Islamic Banking")) {
//                    tvEasyAccount.setText(accountDetail.get(1).accountNo);
//                    tvEasyName.setText(accountDetail.get(1).accountTitle);
//                    bankName = "EasyPasia";
//                }
////                tvAccountBank.setText(accountDetail.get(0).title + " (" + accountDetail.get(0).accountNo + ")\n"
////                        + "Account Title # " + accountDetail.get(0).accountTitle);
//            }
//            if (accountDetail.get(1).accountNo != null) {
//                tvAccountDetail.setText(accountDetail.get(1).title + " (" + accountDetail.get(1).accountNo + ")\n"
//                        + "Account Title # " + accountDetail.get(1).accountTitle);
//
//            }


        }

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


//    private void setSpinnerFee() {
//        List<LoanCategoryItems> items = new ArrayList<>();
//
//
//        if (PreferenceUtils.getInstance().getLaonCategoryDetail() != null) {
//            items = PreferenceUtils.getInstance().getLaonCategoryDetail();
//        }
//
//        final List<String> names = new ArrayList<>();
//
//        final List<String> amount = new ArrayList<>();
//        final List<String> productsId = new ArrayList<>();
//
//        for (int i = 0; i < items.size(); i++) {
//            if (items.get(i).isactive == 1) {
//                names.add(items.get(i).name + "  ( Rs. " + items.get(i).amount + ")");
//                amount.add(items.get(i).amount);
//                productsId.add(items.get(i).id.toString());
//            }
//        }
//
//
//        ArrayAdapter<String> adp1 = new ArrayAdapter<>(context,
//                R.layout.custom_spinner_text, names);
//        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spAppFee.setAdapter(adp1);
//
//
//        spAppFee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//                product = amount.get(position);
//                productid = productsId.get(position);
//                tvfeetext.setText("Please transfer application fee of Rs. " + product + " to the following " +
//                        "account number and upload screenshot of the receipt.");
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//
//    }

    private void dispatchTakePictureIntent() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityTitle("Crop Image")
                .setAutoZoomEnabled(true)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setCropMenuCropButtonTitle("Done")
                .setRequestedSize(400, 400)
                .setCropMenuCropButtonIcon(R.drawable.ic_tick)
                .start(context, FeePyamntFragment.this);
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

        SetApplicationFeeRequest request = new SetApplicationFeeRequest();

        request.setImageURL(url);
        request.setFee(product);

        request.setReceivedate(date);


        request.setWalletno(edWalletno.getText().toString());

        request.setTxnid(recieptno.getText().toString());
        request.setStage(13);

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

        return true;
    }

    private void submitUserInformation(SetApplicationFeeRequest request) {

        final AlertDialog dialog = AlertUtils.showLoader(getActivity());

        UserBusiness userBusiness = new UserBusiness();
        userBusiness.setApplicationFee(request, new ResponseCallBack<ApplicationfeeResponse>() {
            @Override
            public void onSuccess(ApplicationfeeResponse body) {
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