package com.nano.liteloan.presentation.fragment.profile;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageView;
import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.GetApplicationResponseInfo;
import com.nano.liteloan.info.LiteAccount;
import com.nano.liteloan.info.RecoveryPayRequest;
import com.nano.liteloan.info.responce.RecoveryPayResponse;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;

import java.util.List;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */

public class RecoveryFragment extends Fragment {


    private TextView tvfee;
    private EditText edAmountPaid;
    private EditText edPhoneno;
    private EditText edTranscationno;
    private Button btSave;
    private MainActivity context;
    private String outstandingAmount;
    private int id = -1;
    private TextView tvAccountno;
    private int remainingamount = -1;

    private ImageView reciept;
    private String profileImageBase64 = "";

    public RecoveryFragment(MainActivity context, String outstandingAmount) {
        this.context = context;
        this.outstandingAmount = outstandingAmount;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recovery, container, false);

        tvfee = view.findViewById(R.id.fee);
        edAmountPaid = view.findViewById(R.id.amount);
        edPhoneno = view.findViewById(R.id.walletnumber);
        edTranscationno = view.findViewById(R.id.recieptno);
        edTranscationno.setText("");

        tvAccountno = view.findViewById(R.id.jazzcash_account);

        if (PreferenceUtils.getInstance().getAccountDetail() != null) {
            List<LiteAccount> accountDetail = PreferenceUtils.getInstance().getAccountDetail();

            for (int i = 0; i < accountDetail.size(); i++) {
                tvAccountno.setText(accountDetail.get(i).accountNo);


            }

        }

        btSave = view.findViewById(R.id.btn_save);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allValidate()) {
                    payScheduleAmount();
                }
            }
        });

        reciept = view.findViewById(R.id.reciptimage);
        reciept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dispatchTakePictureIntent();

            }
        });


        getApplication();

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
                .start(context, RecoveryFragment.this);

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

    private void payScheduleAmount() {

        RecoveryPayRequest recoveryPayRequest = new RecoveryPayRequest();
        recoveryPayRequest.setImage(profileImageBase64);


        recoveryPayRequest.setWalletno(edPhoneno.getText().toString());
        recoveryPayRequest.setTransferId(edTranscationno.getText().toString());
        recoveryPayRequest.setId(String.valueOf(id));

        recoveryPayRequest.setAmount(edAmountPaid.getText().toString());
        submitUserInformation(recoveryPayRequest);
    }

    private boolean allValidate() {

        if (profileImageBase64.equalsIgnoreCase("")) {
            AlertUtils.showAlert(getActivity(), "please upload image ");
            return false;
        }
        if (edAmountPaid.getText().toString().equalsIgnoreCase("")
                || edAmountPaid.getText().toString().isEmpty()
                || edAmountPaid.getText().toString().equalsIgnoreCase("0")) {
            edAmountPaid.setError("Please enter valid Amount");
            edAmountPaid.requestFocus();
            return false;
        }
        if (remainingamount < Integer.parseInt(edAmountPaid.getText().toString())) {
            edAmountPaid.setError("Please enter valid Amount");
            edAmountPaid.requestFocus();
            return false;
        }

        if (edPhoneno.getText().toString().equalsIgnoreCase("")
                || edPhoneno.getText().toString().isEmpty()
                    || edPhoneno.getText().toString().length() != 11) {
            edPhoneno.setError("Please enter Phone no");
            edPhoneno.requestFocus();
            return false;
        }
        if (id == -1) {
            AlertUtils.showAlert(getActivity(), "Could not get your loan detail.");
            return false;
        }


        return true;
    }

    //
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

//    private void getSchedule() {
//
//        GetScheduleRequest request = new GetScheduleRequest();
////        request.applicationid = info.id;
//
//        final AlertDialog dialog = AlertUtils.showLoader(getActivity());
//
//        EasyLoanBusiness business = new EasyLoanBusiness();
//        business.getSchedule(request, new ResponseCallBack<GetScheduleResponse>() {
//            @Override
//            public void onSuccess(GetScheduleResponse body) {
//                if (dialog != null) {
//                    dialog.dismiss();
//                }
//
////                setPaymentRecyclerView(body, info.id);
////                tvNoRecord.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onFailure(String message) {
//                if (dialog != null) {
//                    dialog.dismiss();
//                }
//                if (message.equalsIgnoreCase("error occured data not found")) {
////                    tvNoRecord.setVisibility(View.VISIBLE);
//                } else {
//                    AlertUtils.showAlert(getActivity(), message);
//                }
//
//            }
//        });
//    }

    private void getApplication() {

        final AlertDialog dialog = AlertUtils.showLoader(getActivity());

        EasyLoanBusiness business = new EasyLoanBusiness();
        business.getApplication(new ResponseCallBack<GetApplicationResponseInfo>() {

            @Override
            public void onSuccess(GetApplicationResponseInfo body) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                if (body != null && body.getApplication
                        != null && body.getApplication.size() > 0) {

                    for (int i = 0; i < body.getApplication.size(); i++) {
                        if (body.getApplication.get(i).loanstatus.equalsIgnoreCase("disburse")) {
                            id = body.getApplication.get(i).id;
                            tvfee.setText(body.getApplication.get(i).status);
                            remainingamount = body.getApplication.get(i).getOutstanding();
                        }
                    }
                }
            }

            @Override
            public void onFailure(String message) {
                AlertUtils.showAlert(getActivity(), message);
            }
        });
    }
}