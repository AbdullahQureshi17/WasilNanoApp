package com.nano.liteloan.presentation.fragment.businesscorrespondance;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.canhub.cropper.CropImageView;
import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.BorrowerReceiptObj;
import com.nano.liteloan.info.BranchesDetail;
import com.nano.liteloan.info.BranchesList;
import com.nano.liteloan.info.LoanInfoRequest;
import com.nano.liteloan.info.LoanReceipt;
import com.nano.liteloan.info.LoanReceiptRequest;
import com.nano.liteloan.info.ProductInfo;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.fragment.dialoguefragment.AmountDisburseFragment;
import com.nano.liteloan.presentation.fragment.profile.ProfileStep1Fragment;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageActivity;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */

public class UserInfoFragment extends Fragment {

    private MainActivity context;
    private Spinner spProduct, spSmartPhone, spMobilewallet;

    private ImageView ivBack;
    private int branchId = 0;
    String product = "purpose1";
    String smartphone = "Yes";
    String mobileWallet = "Mobile Wallet";
    private int productId;
    private boolean isImageUrl;

    private BranchesList branchesList;

    private CardView cvPurpose;
    private ImageView reciept;
    private String profileImageBase64;

    private SearchableSpinner spBranch;
    private String loanPurpose = "Business Capital";

    private EditText edOtherPurpose;

    private Button btAddUser;
    private LoanInfoRequest loanInfoRequest;

    public UserInfoFragment(MainActivity context, LoanInfoRequest loanInfoRequest) {
        this.context = context;
        this.loanInfoRequest = loanInfoRequest;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        reciept = view.findViewById(R.id.recipt);
        reciept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setActivityTitle("Crop Image")
                        .setAutoZoomEnabled(true)
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setCropMenuCropButtonTitle("Done")
                        .setRequestedSize(400, 400)
                        .setCropMenuCropButtonIcon(R.drawable.ic_tick)
                        .start(context, UserInfoFragment.this);

            }
        });
        edOtherPurpose = view.findViewById(R.id.purposeother);

        spBranch = view.findViewById(R.id.branch);

        cvPurpose = view.findViewById(R.id.cv_purpose);
        spProduct = view.findViewById(R.id.sp_product);
        btAddUser = view.findViewById(R.id.addUser);
        btAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (validateAllField()){
                    borrowerApplication(loanInfoRequest);
//                    if (mobileWallet.equalsIgnoreCase("Mobile Wallet")) {
//                        context.userWalletFragment();
//                    } else {
//                        context.userChequeFragment();
//                    }
                }



            }
        });

        spMobilewallet = view.findViewById(R.id.MobileWallet);

        spSmartPhone = view.findViewById(R.id.smartphone);

        setSmartphone();
        setSpProduct();
        setWalletType();
        branchesDetail();


        ivBack = view.findViewById(R.id.back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.onBackPressed();
            }
        });

        return view;
    }

    private boolean validateAllField() {

        if (profileImageBase64 == null || profileImageBase64.equalsIgnoreCase("")) {
            AlertUtils.showAlert(context, "Please Add the Image");
            return false;
        }


        if (reciept.getDrawable() == null) {
            AlertUtils.showAlert(context, "Please upload Disbursement form image");
            return false;
        }

        if (smartphone.equalsIgnoreCase("Yes")) {
            loanInfoRequest.setHavecellphone(1);
        } else {
            loanInfoRequest.setHavecellphone(0);
        }

        loanInfoRequest.setDisbursementImage(profileImageBase64);

        loanInfoRequest.setDisbursementImage(profileImageBase64);

        loanInfoRequest.setAcounttype(mobileWallet);

        loanInfoRequest.setCategoryId(0);
        loanInfoRequest.setBranchid(branchId);
        loanInfoRequest.setProductId(loanPurpose);


//        if (productId == 12) {
//            if (edOtherPurpose.getText().toString() == null || edOtherPurpose.getText().toString().equalsIgnoreCase("")) {
//                edOtherPurpose.setError("Please filled the field ");
//            } else {
//                loanInfoRequest.setLoanporpose(edOtherPurpose.getText().toString());
//            }
//        } else {
//            loanInfoRequest.setLoanporpose(product);
//        }

        return true;
    }

    private void setSpProduct() {


        List<String> purposes = new ArrayList<>();

        purposes.add("Business Capital");
        purposes.add("Health");
        purposes.add("Education");
        purposes.add("Food");
        purposes.add("Utility Bills");
        purposes.add("Other");


        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, purposes);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProduct.setAdapter(adp1);

        final List<ProductInfo> finalProductInfos = PreferenceUtils.getInstance().getproductPurposeDetail();
        spProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                loanPurpose = purposes.get(i).toString();
//
//                productId = finalProductInfos.get(i).id;
//                cvPurpose.setVisibility(GONE);
//                if (productId == 12){
//                    cvPurpose.setVisibility(View.VISIBLE);
//                }
//                product = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void borrowerApplication(final LoanInfoRequest loanInfoRequest) {

        if (mobileWallet.equalsIgnoreCase("Mobile Wallet")) {

            loanInfoRequest.setLoanporpose(loanPurpose);
            BorrowerReceiptObj borrowerReceiptObj = new BorrowerReceiptObj();

            borrowerReceiptObj.type = loanInfoRequest.acounttype;
            borrowerReceiptObj.amount = String.valueOf(loanInfoRequest.loanamount);

            context.userWalletFragment(borrowerReceiptObj, loanInfoRequest);

//                    BorrowerProfileCompleteDialogue dialog1 =
//                            new BorrowerProfileCompleteDialogue(context, borrowerReceiptObj);
//
//                    dialog1.show(getChildFragmentManager(),
//                            AmountDisburseFragment.class.getName());

        } else if (mobileWallet.equalsIgnoreCase("Cheque")) {

            BorrowerReceiptObj borrowerReceiptObj = new BorrowerReceiptObj();

            loanInfoRequest.setLoanporpose(loanPurpose);
            borrowerReceiptObj.type = loanInfoRequest.acounttype;
            borrowerReceiptObj.amount = String.valueOf(loanInfoRequest.loanamount);

            context.userChequeFragment(borrowerReceiptObj, loanInfoRequest);
//
//                    BorrowerProfileCompleteDialogue dialog1 =
//                            new BorrowerProfileCompleteDialogue(context, borrowerReceiptObj);
//
//                    dialog1.show(getChildFragmentManager(),
//                            AmountDisburseFragment.class.getName());
        } else if (mobileWallet.equalsIgnoreCase("Cash")) {

            LoanReceiptRequest loanReceiptRequest = new LoanReceiptRequest();
//                    loanReceiptRequest.setBorrowerid(loanInfo.borrowerid);
//            loanReceiptRequest.setDisbursementImage(profileImageBase64);
////                    loanReceiptRequest.setLoanid(loanInfo.loanId);
            loanReceiptRequest.setType(loanInfoRequest.acounttype);
//            loanReceiptRequest.setDisbursementAmount(loanInfo.amount);
            loanReceiptRequest.setBranchid(branchId);
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

            loanReceiptRequest.setProductId(loanPurpose);
            loanReceiptRequest.setLoanpurpose(loanPurpose);

            borrowerApplication(loanReceiptRequest);


        }
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
        dialog.dismiss();
    }


    void branchesDetail() {

        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }

        EasyLoanBusiness business = new EasyLoanBusiness();
        business.branchesDetail(new ResponseCallBack<BranchesList>() {
            @Override
            public void onSuccess(BranchesList body) {

                dialog.dismiss();

                branchesList = body;
                setBranches(body);


            }

            @Override
            public void onFailure(String message) {

                dialog.dismiss();
                AlertUtils.showAlert(getActivity(), message);
            }
        });
        dialog.dismiss();
    }


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
    }


    private void setSmartphone() {

        List<String> names = new ArrayList<>();

        names.add("Yes");
        names.add("No");

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, names);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSmartPhone.setAdapter(adp1);

        spSmartPhone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                smartphone = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
                .start(context, UserInfoFragment.this);

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
            if (bitmap != null) {
                profileImageBase64 = AppUtils.encodeImageBase64(bitmap);
                reciept.setImageBitmap(bitmap);
            }

        }
    }
}