package com.nano.liteloan.presentation.fragment.businesscorrespondance;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.canhub.cropper.CropImageView;
import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.BorrowerReceiptObj;
import com.nano.liteloan.info.LoanInfo;
import com.nano.liteloan.info.LoanInfoRequest;
import com.nano.liteloan.info.LoanReceipt;
import com.nano.liteloan.info.LoanReceiptRequest;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.fragment.dialoguefragment.AmountDisburseFragment;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageActivity;


/**
 * Created by Muhammad Umer on 07/06/2020.
 */

public class ChequeReceiptFragment extends Fragment {

    private ImageView reciept;
    private String profileImageBase64;
    private MainActivity context;
    private ImageView ivBack;
    private Button btSubmit;
    private BorrowerReceiptObj loanInfo;
    private LoanInfoRequest loanInfoRequest;

    public ChequeReceiptFragment(MainActivity mainActivity, BorrowerReceiptObj body) {
        this.context = mainActivity;
        this.loanInfo = body;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cheque_receipt, container, false);

        if (this.getArguments() != null
                && this.getArguments().getSerializable("LoanInfoRequest") != null) {
            loanInfoRequest = (LoanInfoRequest) this.getArguments()
                    .getSerializable("LoanInfoRequest");
        }

        reciept = view.findViewById(R.id.recipt);
        reciept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dispatchTakePictureIntent();

            }
        });
        ivBack = view.findViewById(R.id.back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.onBackPressed();
            }
        });

        btSubmit = view.findViewById(R.id.btn_save);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateAllFields() && loanInfoRequest != null) {
                    LoanReceiptRequest loanReceiptRequest = new LoanReceiptRequest();
//                    loanReceiptRequest.setBorrowerid(loanInfo.borrowerid);
                    loanReceiptRequest.setDisbursementImage(profileImageBase64);
//                    loanReceiptRequest.setLoanid(loanInfo.loanId);
                    loanReceiptRequest.setType(loanInfo.type);
                    loanReceiptRequest.setDisbursementAmount(loanInfo.amount);

                    loanReceiptRequest.setAffidavitImage(loanInfoRequest.disbursementImage);
                    loanReceiptRequest.setAcounttype(loanInfoRequest.acounttype);
                    loanReceiptRequest.setCorrenpondentid(loanInfoRequest.correnpondentid);
                    loanReceiptRequest.setCnic(loanInfoRequest.cnic);
                    loanReceiptRequest.setCategoryId(loanInfoRequest.categoryId);
                    loanReceiptRequest.setLoanamount(loanInfoRequest.loanamount);
                    loanReceiptRequest.setDuedate(loanInfoRequest.duedate);
                    loanReceiptRequest.setFee(loanInfoRequest.fee);
                    loanReceiptRequest.setName(loanInfoRequest.name);
                    loanReceiptRequest.setParentage(loanInfoRequest.parentage);
                    loanReceiptRequest.setHavecellphone(loanInfoRequest.havecellphone);
                    loanReceiptRequest.setPhoneno(loanInfoRequest.phoneno);
                    loanReceiptRequest.setLoanpurpose(loanInfoRequest.getLoanporpose());
                    loanReceiptRequest.setProductId(loanInfoRequest.productId);




//                    borrowerInfoApplication(loanInfoRequest);
                    borrowerApplication(loanReceiptRequest);

//                    context.homeFrag();
                } else {
                    AlertUtils.showAlert(context , "Errored occured , please fill the form again.");
                    context.homeFrag();
                }
            }
        });

//        context.onBackPressed();
        return view;
    }

    void borrowerInfoApplication(final LoanInfoRequest loanInfoRequest) {

        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }

        EasyLoanBusiness business = new EasyLoanBusiness();
        business.loanInfo(loanInfoRequest, new ResponseCallBack<LoanInfo>() {
            @Override
            public void onSuccess(LoanInfo body) {

//                AddNewUserFragment popup = new AddNewUserFragment(context, edCnic.getText().toString() , body);
//                popup.show(getChildFragmentManager(), EvaluationSuccessPopup.class.getName());


                dialog.dismiss();

            }

            @Override
            public void onFailure(String message) {

                dialog.dismiss();
                AlertUtils.showAlert(getActivity(), message);
            }
        });
    }

    void borrowerApplication(LoanReceiptRequest loanReceiptRequest) {

        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }

        EasyLoanBusiness business = new EasyLoanBusiness();
        business.loanReceipt(loanReceiptRequest, new ResponseCallBack<LoanReceipt>() {
            @Override
            public void onSuccess(LoanReceipt body) {

                dialog.dismiss();
                AmountDisburseFragment dialog1 =
                        new AmountDisburseFragment(context);

                dialog1.show(getChildFragmentManager(),
                        AmountDisburseFragment.class.getName());


            }

            @Override
            public void onFailure(String message) {

                dialog.dismiss();
                AlertUtils.showAlert(getActivity(), message);
            }
        });
    }

    private boolean validateAllFields() {
        if (profileImageBase64 == null || profileImageBase64.equalsIgnoreCase("")) {
            AlertUtils.showAlert(context, "Please add image of reciept");
            return false;
        }
        if (reciept.getDrawable() == null) {
            AlertUtils.showAlert(context, "Please upload Disbursement form image");
            return false;
        }
        return true;
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
                .start(context, ChequeReceiptFragment.this);

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
}