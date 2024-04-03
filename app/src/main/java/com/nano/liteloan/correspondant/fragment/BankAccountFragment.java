package com.nano.liteloan.correspondant.fragment;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageView;
import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.business.impl.UserBusiness;
import com.nano.liteloan.correspondant.correspondantdialoguefragment.ImageShowDialogPop;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.BankInfo;
import com.nano.liteloan.info.BorrowerList;
import com.nano.liteloan.info.ConfigurationRespondObject;
import com.nano.liteloan.info.LoanApplyRequest;
import com.nano.liteloan.info.LoanApplyResponse;
import com.nano.liteloan.info.LoanCategoryInfo;
import com.nano.liteloan.info.ProductInfo;
import com.nano.liteloan.info.ProfileCreateRequestInfo;
import com.nano.liteloan.info.ProfileCreateResponseInfo;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.fragment.dialoguefragment.SubmitApplicationDialogue;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Muhammad Ahmad on 08/04/2020.
 */
public class BankAccountFragment extends Fragment implements ImageShowDialogPop.OnPicEdit {

    private MainActivity context;
    private LoanApplyRequest loanApplyRequest;
    private EditText edAccountNumber;
    private ImageView ivCnicBack;
    private ImageView ivCnicFront;

    private int picNumber = 1;
    private TextView tvMaskDesc;
    private CheckBox chVerify;
    private EditText edAssetsName, edAssetPrice, edMarketValue;
    private BorrowerList borrower;
    private Spinner spBank;
    private String bank = "";
    private int bakId = 0;

    List<LoanCategoryInfo> loanCategories = new ArrayList<>();
    private ImageView ivProfile;
    private String profileImageBase64 = null;
    private String cnicFrontBase64 = null;
    private String cnicBackBase64 = null;
    private String assetPicBase64 = null;
    private final ArrayList<String> permissions = new ArrayList<>();
    private static final int ALL_PERMISSIONS_RESULT = 107;

    private EditText edAmount;
    private Spinner spProduct;
    private LoanCategoryInfo loanCategoryInfo;
    private int catId;
    private String salary = "No Earning";
    private String incomeSource = "No Earning";
    private String productId;
    private SubmitApplicationDialogue.OnApplicationSubmit onApplicationSubmit;
    private CardView cvPurpose;
    String otherPurpose = "";
    private EditText edOtherPurpose;
    private Spinner spSalary;
    private Spinner spIncomeSource;
    private Spinner rvLoans;
    private int activeLoan = 0;
    String loanActive = "";
    private String rentPercentage;
    EditText edRental, edLoanMonths;
    ImageView ivAsset;
    TextView tvName, tvPhone, tvCnic;
    TextView tvRentPercentage;
    EditText edPhoneChange;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;
    }

    public BankAccountFragment() {
        // Required empty public constructor
    }

    public BankAccountFragment(BorrowerList borrower) {
        this.borrower = borrower;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mobile_wallet, container, false);

        if (PreferenceUtils.getInstance().getLoanRequest(borrower.cnic) != null) {
            loanApplyRequest = PreferenceUtils.getInstance().getLoanRequest(borrower.cnic);
        } else {
            loanApplyRequest = new LoanApplyRequest();
        }
        edAssetPrice = view.findViewById(R.id.assest_price);
        edMarketValue = view.findViewById(R.id.assest_market_value);
        edAssetsName = view.findViewById(R.id.assest);
        edLoanMonths = view.findViewById(R.id.ed_days);
        edPhoneChange = view.findViewById(R.id.phone_number_change);
        edRental = view.findViewById(R.id.ed_percentage);
        spBank = view.findViewById(R.id.sp_bank);
        tvRentPercentage = view.findViewById(R.id.tv_rent_percentage);
        ivCnicFront = view.findViewById(R.id.iv_cnic_front);
        ivCnicBack = view.findViewById(R.id.iv_cnic_back);

        tvName = view.findViewById(R.id.borrower_name);
        tvPhone = view.findViewById(R.id.borrower_phone);
        tvCnic = view.findViewById(R.id.borrower_cnic);
        LinearLayout llCapImage = view.findViewById(R.id.ll_cap_image);
        rvLoans = view.findViewById(R.id.sp_loans);
        ivAsset = view.findViewById(R.id.iv_asset_pic);
        spSalary = view.findViewById(R.id.sp_salary);
        spIncomeSource = view.findViewById(R.id.sp_income_source);
        cvPurpose = view.findViewById(R.id.cv_purpose);
        chVerify = view.findViewById(R.id.ck_verify);

        edOtherPurpose = view.findViewById(R.id.purposeother);
        edAmount = view.findViewById(R.id.ed_amount);
        spProduct = view.findViewById(R.id.sp_product);
        loanCategoryInfo = new LoanCategoryInfo();


        edAccountNumber = view.findViewById(R.id.accountno);
        tvMaskDesc = view.findViewById(R.id.tv_mask_desc);

        Button btnSave = view.findViewById(R.id.btn_save);


        btnSave.setOnClickListener(view1 -> {

            if (profileImageBase64 == null) {
                AlertUtils.showAlert(context, "Please provide borrower picture!");
                return;
            }

            if (cnicBackBase64 == null) {
                AlertUtils.showAlert(context, "Please provide CNIC picture from back!");
                return;
            }
            if (cnicFrontBase64 == null) {
                AlertUtils.showAlert(context, "Please provide CNIC picture from front!");
                return;
            }
            if (assetPicBase64 == null) {
                AlertUtils.showAlert(context, "Please provide Asset picture!");
                return;
            }
            if (edAssetsName.getText().toString().isEmpty()) {
                edAssetsName.setError("Please provide asset name!");
                edAssetsName.requestFocus();
                return;
            }
            if (edAssetPrice.getText().toString().isEmpty()) {
                edAssetPrice.setError("Please provide asset price!");
                edAssetPrice.requestFocus();
                return;
            }
            if (edMarketValue.getText().toString().isEmpty()) {
                edMarketValue.setError("Please provide asset current market value!");
                edMarketValue.requestFocus();
                return;
            }

            if (Integer.parseInt(edMarketValue.getText().toString().replace(",", ""))
                    < (loanCategoryInfo.loanLimit + 1000)) {

                edMarketValue.setError("Asset current market value can not be less than Pkr "
                        + (loanCategoryInfo.loanLimit + 1000));

                edMarketValue.requestFocus();
                return;
            }

            fillLoanInfo(edRental.getText().toString().replace(",", ""));

        });


        ImageView ivBack = view.findViewById(R.id.back);
        ivBack.setOnClickListener(view12 -> {
            context.onBackPressed();
        });

        setBankSpinner();

        if (borrower != null && borrower.bankDetails != null) {
            edAccountNumber.setText(borrower.bankDetails.account_no);

        }
        ivProfile = view.findViewById(R.id.iv_profile_image);
        llCapImage.setOnClickListener(view1 -> {
            picNumber = 1;
            if (profileImageBase64 == null) {
                captureImage();
            } else {
                new ImageShowDialogPop(AppUtils.getBitmapFromBase64(profileImageBase64), null, this)
                        .show(getChildFragmentManager(), "asdasd");
            }

        });
        ivCnicFront.setOnClickListener(view1 -> {
            picNumber = 2;
            if (cnicFrontBase64 == null) {
                captureImage();
            } else {
                new ImageShowDialogPop(AppUtils.getBitmapFromBase64(cnicFrontBase64), null, this)
                        .show(getChildFragmentManager(), "asdasd");
            }

        });
        ivCnicBack.setOnClickListener(view1 -> {
            picNumber = 3;
            if (cnicBackBase64 == null) {
                captureImage();
            } else {
                new ImageShowDialogPop(AppUtils.getBitmapFromBase64(cnicBackBase64), null, this)
                        .show(getChildFragmentManager(), "asdasd");
            }

        });
        ivAsset.setOnClickListener(v -> {

            picNumber = 4;
            if (assetPicBase64 == null) {
                captureImage();
            } else {
                new ImageShowDialogPop(AppUtils.getBitmapFromBase64(assetPicBase64), null, this)
                        .show(getChildFragmentManager(), "asdasd");
            }

        });

        checkPermissionForCamera();
        setSalarySpinner();
        setIncomeSource();
        setInputLimit();

        getLoanProducts();

        setData();
        setInputListner();

        setImages();
        return view;
    }

    private void setImages() {

        if (PreferenceUtils.getInstance().getBase64Image(borrower.cnic + "_" + 1) != null) {

            profileImageBase64 = PreferenceUtils.getInstance().getBase64Image(borrower.cnic + "_" + 1);

            ivProfile.setImageBitmap(AppUtils.getBitmapFromBase64(profileImageBase64));
        }

        if (PreferenceUtils.getInstance().getBase64Image(borrower.cnic + "_" + 2) != null) {

            cnicFrontBase64 = PreferenceUtils.getInstance().getBase64Image(borrower.cnic + "_" + 2);

            ivCnicFront.setImageBitmap(AppUtils.getBitmapFromBase64(cnicFrontBase64));
        }

        if (PreferenceUtils.getInstance().getBase64Image(borrower.cnic + "_" + 3) != null) {

            cnicBackBase64 = PreferenceUtils.getInstance().getBase64Image(borrower.cnic + "_" + 3);

            ivCnicBack.setImageBitmap(AppUtils.getBitmapFromBase64(cnicBackBase64));
        }

        if (PreferenceUtils.getInstance().getBase64Image(borrower.cnic + "_" + 4) != null) {

            assetPicBase64 = PreferenceUtils.getInstance().getBase64Image(borrower.cnic + "_" + 4);

            ivAsset.setImageBitmap(AppUtils.getBitmapFromBase64(assetPicBase64));
        }

        if (loanApplyRequest != null) {
            if (loanApplyRequest.assetName != null) {
                edAssetsName.setText(loanApplyRequest.assetName);
            }
            if (loanApplyRequest.assetPrice != null) {
                edAssetPrice.setText(loanApplyRequest.assetPrice);
            }
            if (loanApplyRequest.asstMarketValue != null) {
                edMarketValue.setText(loanApplyRequest.asstMarketValue);
            }
        }
    }

    private void captureImage() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityTitle("Crop Image")
                .setAutoZoomEnabled(true)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setCropMenuCropButtonTitle("Done")
                .setRequestedSize(400, 400)
                .setCropMenuCropButtonIcon(R.drawable.ic_tick)
                .start(context, BankAccountFragment.this);
    }

    private void setInputListner() {

        edAssetsName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                loanApplyRequest.assetName = s.toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

                PreferenceUtils.getInstance().addLoanRequest(loanApplyRequest, borrower.cnic);
            }
        });

        edAssetPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loanApplyRequest.assetPrice = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                PreferenceUtils.getInstance().addLoanRequest(loanApplyRequest, borrower.cnic);
            }
        });

        edMarketValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    return;
                }

                loanApplyRequest.asstMarketValue = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                PreferenceUtils.getInstance().addLoanRequest(loanApplyRequest, borrower.cnic);
            }
        });

        edAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loanApplyRequest.amount = s.toString().replace(",", "");
            }

            @Override
            public void afterTextChanged(Editable s) {
                PreferenceUtils.getInstance().addLoanRequest(loanApplyRequest, borrower.cnic);
            }
        });

        edOtherPurpose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loanApplyRequest.purpose = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                PreferenceUtils.getInstance().addLoanRequest(loanApplyRequest, borrower.cnic);
            }
        });

    }

    private void setData() {

        if (borrower != null) {
            tvName.setText(borrower.name);
            tvPhone.setText(borrower.phoneno);
            edPhoneChange.setText(borrower.phoneno);
            tvCnic.setText(borrower.cnic);
        }
    }

    private void getLoanProducts() {


        EasyLoanBusiness business = new EasyLoanBusiness();
        business.getLoanCategories(new ResponseCallBack<ConfigurationRespondObject>() {
            @Override
            public void onSuccess(ConfigurationRespondObject body) {

                loanCategories = body.loanCategories;

                setLoansRecyclerView(body, 0);

            }

            @Override
            public void onFailure(String message) {
                AlertUtils.showAlert(context, message);
            }
        });
    }

    private void setLoansRecyclerView(final ConfigurationRespondObject body, final int value) {


        List<String> names = new ArrayList<>();

        for (LoanCategoryInfo cat : body.loanCategories) {
            names.add(cat.name);
        }

        ArrayAdapter<String> adp1 = new ArrayAdapter<>(context,
                R.layout.custom_spinner_text, names);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rvLoans.setAdapter(adp1);
        rvLoans.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                loanCategoryInfo = loanCategories.get(position);
                loanCategoryInfo.borrower = borrower;
                edAmount.setText(AppUtils.getFormatAmount(loanCategories.get(position).loanLimit));
                rentPercentage = loanCategories.get(position).rentalPercentage;
                int percentage = Integer.parseInt(rentPercentage);
                Integer rentAmount = loanCategories.get(position).loanLimit +
                        ((loanCategories.get(position).loanLimit / 100) * percentage);

                edRental.setText(AppUtils.getFormatAmount(rentAmount.toString()));
                edLoanMonths.setText(loanCategories.get(position).loanDuration);
                loanApplyRequest.rentAmount = loanCategories.get(position).rentalPercentage;

                setSpProduct(loanCategories.get(position).productInfoList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void fillLoanInfo(String share) {

        if (productId == "12") {
            if (edOtherPurpose.getText().toString() == null
                    || edOtherPurpose.getText().toString().equalsIgnoreCase("")) {
                edOtherPurpose.setError("Please filled the field ");
            } else {
                otherPurpose = edOtherPurpose.getText().toString();
                submitLoan(share);
            }
        } else {
            submitLoan(share);
        }
    }

    private void submitLoan(String share) {


        loanApplyRequest.assetName = edAssetsName.getText().toString();
        loanApplyRequest.assetPrice = edAssetPrice.getText().toString();
        loanApplyRequest.asstMarketValue = edMarketValue.getText().toString();
        loanApplyRequest.profilePic = profileImageBase64;

        loanApplyRequest.cateId = catId;
        loanApplyRequest.productId = productId;
        loanApplyRequest.purpose = otherPurpose;
        loanApplyRequest.amount = edAmount.getText().toString().replace(",", "");
        loanApplyRequest.income = salary;
        loanApplyRequest.futureIncome = "";

        loanApplyRequest.activeLoan = loanActive + "";

        if (PreferenceUtils.getInstance().getCorrespondentUserDetail() != null &&
                PreferenceUtils.getInstance().getCorrespondentUserDetail().getUserid() != null) {
            loanApplyRequest.correspondantid = PreferenceUtils.getInstance().getCorrespondentUserDetail().getUserid();
        } else {
            loanApplyRequest.correspondantid = "0";
        }

        if (loanActive.equals("No")) {

            loanApplyRequest.loanamount = "0";
            loanApplyRequest.monthlyRepayment = "0";

        }

        borrower.assetName = edAssetsName.getText().toString();
        borrower.assetMarketValue = edMarketValue.getText().toString();
        borrower.assetPrice = edAssetPrice.getText().toString();
        borrower.profileImage = profileImageBase64;

        loanApplyRequest.profilePic = profileImageBase64;
        loanApplyRequest.cnicBackPic = cnicBackBase64;
        loanApplyRequest.cnicFrontPic = cnicFrontBase64;
        loanApplyRequest.assetPic = assetPicBase64;

        borrower.wasilShare = rentPercentage;

        borrower.agreementType = "musharka";

        borrower.loanApplyRequest = loanApplyRequest;

        if (isValidFields()) {
            saveMobileWallet();
        }
    }

    private void setSalarySpinner() {

        final List<String> salarylist = new ArrayList<>();

        salarylist.add("No Earning");
        salarylist.add("Upto 5,000");
        salarylist.add("5,001-10,000");
        salarylist.add("10,001-15,000");
        salarylist.add("15,001-20,000");
        salarylist.add("20,001-25,000");
        salarylist.add("25,001-30,000");
        salarylist.add("30,001-35,000");
        salarylist.add("35,001-40,000");
        salarylist.add("Above 40,000");


        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_text, salarylist);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spSalary.setAdapter(adp1);
        spSalary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                salary = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setIncomeSource() {

        final List<String> salarylist = new ArrayList<>();

        salarylist.add("Private");
        salarylist.add("Government");
        salarylist.add("Business");
        salarylist.add("Labour");
        salarylist.add("UnEmploy");


        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_text, salarylist);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spIncomeSource.setAdapter(adp1);
        spIncomeSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                incomeSource = parent.getItemAtPosition(position).toString();
                loanApplyRequest.incomeSource = incomeSource;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSpProduct(List<ProductInfo> productInfoList) {

        List<String> names = new ArrayList<>();

        for (ProductInfo productInfo : productInfoList) {

            names.add(productInfo.name);
        }

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, names);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProduct.setAdapter(adp1);

        spProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                catId = loanCategoryInfo.productInfoList.get(i).categoryId;
                productId = loanCategoryInfo.productInfoList.get(i).id;

                PreferenceUtils.getInstance().getLoanRequest(borrower.cnic).cateId = catId;
                PreferenceUtils.getInstance().getLoanRequest(borrower.cnic).productId = productId;

                cvPurpose.setVisibility(GONE);
                if (productId == "12") {
                    cvPurpose.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setInputLimit() {


    }

    private void checkPermissionForCamera() {


        permissions.add(CAMERA);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissions.add(READ_EXTERNAL_STORAGE);
        ArrayList<String> permissionsToRequest = AppUtils.findUnAskedPermissions(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                permissionsToRequest.size() > 0) {
            requestPermissions(permissionsToRequest
                    .toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
    }

    public void setDataIntent(int requestCode, int resultCode, @Nullable Intent data) {

        //        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode != RESULT_CANCELED) {
            if (resultCode == RESULT_OK) {
                String pathe = result.getUriFilePath(context, false);
                Bitmap bitmap = AppUtils.checkPhotoOrientationAndGetImage(pathe);
                if (picNumber == 1) {
                    assert bitmap != null;
                    profileImageBase64 = AppUtils.encodeImageBase64(bitmap);
                    ivProfile.setImageBitmap(bitmap);

                    PreferenceUtils.getInstance().saveImageBase64(profileImageBase64,
                            borrower.cnic + "_" + picNumber);
                } else if (picNumber == 2) {
                    assert bitmap != null;
                    cnicFrontBase64 = AppUtils.encodeImageBase64(bitmap);
                    ivCnicFront.setImageBitmap(bitmap);
                    PreferenceUtils.getInstance().saveImageBase64(cnicFrontBase64,
                            borrower.cnic + "_" + picNumber);
                } else if (picNumber == 3) {
                    assert bitmap != null;
                    cnicBackBase64 = AppUtils.encodeImageBase64(bitmap);
                    ivCnicBack.setImageBitmap(bitmap);
                    PreferenceUtils.getInstance().saveImageBase64(cnicBackBase64,
                            borrower.cnic + "_" + picNumber);
                } else if (picNumber == 4) {
                    assert bitmap != null;
                    assetPicBase64 = AppUtils.encodeImageBase64(bitmap);
                    ivAsset.setImageBitmap(bitmap);
                    PreferenceUtils.getInstance().saveImageBase64(assetPicBase64,
                            borrower.cnic + "_" + picNumber);
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(context, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
//            }
        }
    }


    private void saveMobileWallet() {

        ProfileCreateRequestInfo requestInfo = new ProfileCreateRequestInfo();
        requestInfo.setAccountnumber(edAccountNumber.getText().toString());
        requestInfo.setAccounttitle(bank);
        requestInfo.phoneNumber = edPhoneChange.getText().toString();
        requestInfo.setBankid(bakId);
        requestInfo.borrowerId = String.valueOf(borrower.userDetail.userId);
        requestInfo.stage = "5";
        if (chVerify.isChecked()) {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            requestInfo.verifyAt = df.format(c);
        } else {
            requestInfo.verifyAt = null;
        }

        submitUserInformation(requestInfo);
    }

    private void setBankSpinner() {

        final List<String> banks = new ArrayList<>();
        final List<BankInfo> bankList = PreferenceUtils.getInstance().getBankList();

        if (bankList != null && bankList.size() > 0) {
            for (BankInfo bankInfo : bankList) {
                banks.add(bankInfo.bankName);
            }
        }


        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_text, banks);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spBank.setAdapter(adp1);
        spBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bank = banks.get(position).toString();
                bakId = bankList.get(position).bankId;
                // edAccountNumber.setText("");

                if (bankList != null && bankList.size() > 0
                        && bankList.get(position).mask != null) {

                    int maxLength = bankList.get(position).mask.length();
                    edAccountNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});


                }
                if (bankList != null && bankList.size() > 0
                        && bankList.get(position).desc != null) {
                    tvMaskDesc.setText(bankList.get(position).desc);
                }
                if (bankList != null && bankList.size() > 0
                        && bankList.get(position).logo != null
                        && !bankList.get(position).logo.equals("")) {
                    //  Picasso.get().load(bankList.get(position).logo).into(ivBankLogo);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (borrower != null && borrower.bankDetails != null) {

            int selected = -1;
            for (int i = 0; i < banks.size(); i++) {

                if (banks.get(i) == borrower.bankDetails.account_title) {
                    selected = i;
                    break;
                }
            }

            if (selected != -1) {
                spBank.setSelection(selected);
            }
        }
    }

    private boolean isValidFields() {

        if (edAccountNumber.getText().toString().isEmpty()) {

            edAccountNumber.setError("Please enter your valid account number");
            edAccountNumber.requestFocus();
            return false;

        }

        return true;
    }

    private void submitUserInformation(ProfileCreateRequestInfo createProfile) {

        if (borrower != null
                && borrower.bankDetails != null
                && borrower.bankDetails.account_no != null
                && (edAccountNumber.getText().toString()
                .equals(borrower.bankDetails.account_no)
                || edPhoneChange.getText().toString().equals(borrower.phoneno))) {
            submitLoanInformation();

        } else {

            final AlertDialog dialog = AlertUtils.showLoader(getActivity());
            UserBusiness userBusiness = new UserBusiness();
            userBusiness.setProfile(createProfile, new ResponseCallBack<ProfileCreateResponseInfo>() {
                @Override
                public void onSuccess(ProfileCreateResponseInfo body) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }

                    submitLoanInformation();
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
    }

    private void submitLoanInformation() {

        loanApplyRequest.borrowerId = loanCategoryInfo.borrower.borrowerid;
        loanApplyRequest.userId = loanCategoryInfo.borrower.userDetail.userId;

        final AlertDialog dialog = AlertUtils.showLoader(getActivity());

        EasyLoanBusiness business = new EasyLoanBusiness();


        business.applyForLoan(loanApplyRequest, new ResponseCallBack<LoanApplyResponse>() {
            @Override
            public void onSuccess(LoanApplyResponse body) {

                dialog.dismiss();

                loanCategoryInfo.borrower.loanApplyRequest.applicationId = body.application.id;

                PreferenceUtils.getInstance().addLoanRequest(null, loanCategoryInfo.borrower.cnic);

                Bundle bundle = new Bundle();
                bundle.putSerializable("loan_detail", loanCategoryInfo);
                context.setAgreementFragment(1, bundle);

            }

            @Override
            public void onFailure(String message) {

                dialog.dismiss();

                AlertUtils.showAlert(getActivity(), message);
            }
        });
    }

    @Override
    public void onEditClick() {

        captureImage();
    }
}