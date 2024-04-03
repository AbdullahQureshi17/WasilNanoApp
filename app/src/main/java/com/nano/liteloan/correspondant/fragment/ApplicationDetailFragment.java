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
import android.os.CountDownTimer;
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
import com.nano.liteloan.info.LoanCategoryInfo;
import com.nano.liteloan.info.ProductInfo;
import com.nano.liteloan.info.ProfileCreateRequestInfo;
import com.nano.liteloan.info.ProfileCreateResponseInfo;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.fragment.dialoguefragment.SubmitApplicationDialogue;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Muhammad Ahmad on 12/06/2023.
 */
public class ApplicationDetailFragment extends Fragment implements ImageShowDialogPop.OnPicEdit {

    private MainActivity context;
    private final LoanApplyRequest loanApplyRequest;
    private EditText edAccountNumber;
    private ImageView ivCnicBack;
    private ImageView ivCnicFront;
    private TextView tvTimer;
    private TextView tvTimerHeader;

    private int picNumber = 1;
    private TextView tvMaskDesc;
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
    private String productId;
    private SubmitApplicationDialogue.OnApplicationSubmit onApplicationSubmit;
    private CardView cvPurpose;
    String otherPurpose = "";
    private EditText edOtherPurpose;
    private Spinner rvLoans;
    private int activeLoan = 0;
    String loanActive = "";
    private String rentPercentage;
    EditText edRental, edLoanMonths;
    ImageView ivAsset, ivMusharka, ivIjarah;
    TextView tvName, tvPhone, tvCnic, tvStaus;
    TextView tvRentPercentage;
    private CheckBox chVerify;
    private boolean canEdit = true;

    EditText edPhoneChange;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;
    }

    public ApplicationDetailFragment(BorrowerList borrower, LoanApplyRequest loanApplyRequest, boolean canEdit) {
        this.borrower = borrower;
        this.loanApplyRequest = loanApplyRequest;
        this.canEdit = canEdit;
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_application_detail, container, false);

        tvTimer = view.findViewById(R.id.tv_timer);
        tvTimerHeader = view.findViewById(R.id.tv_timer_header);

        edAssetPrice = view.findViewById(R.id.assest_price);
        if (!canEdit) {
            edAssetPrice.setEnabled(false);
            tvTimerHeader.setVisibility(GONE);
            tvTimer.setVisibility(GONE);
        }
        edMarketValue = view.findViewById(R.id.assest_market_value);
        if (!canEdit) {
            edMarketValue.setEnabled(false);
        }
        edAssetsName = view.findViewById(R.id.assest);
        if (!canEdit) {
            edAssetsName.setEnabled(false);
        }
        edPhoneChange = view.findViewById(R.id.phone_number_change);
        if (!canEdit) {
            edPhoneChange.setEnabled(false);
        }

        edLoanMonths = view.findViewById(R.id.ed_days);
        edRental = view.findViewById(R.id.ed_percentage);
        spBank = view.findViewById(R.id.sp_bank);
        if (!canEdit) {
            spBank.setEnabled(false);
        }
        tvStaus = view.findViewById(R.id.tv_status);
        chVerify = view.findViewById(R.id.ck_verify);
        tvRentPercentage = view.findViewById(R.id.tv_rent_percentage);
        ivCnicFront = view.findViewById(R.id.iv_cnic_front);
        ivCnicBack = view.findViewById(R.id.iv_cnic_back);

        tvName = view.findViewById(R.id.borrower_name);
        tvPhone = view.findViewById(R.id.borrower_phone);
        tvCnic = view.findViewById(R.id.borrower_cnic);
        LinearLayout llCapImage = view.findViewById(R.id.ll_cap_image);
        rvLoans = view.findViewById(R.id.sp_loans);
        ivMusharka = view.findViewById(R.id.iv_musharka);
        ivIjarah = view.findViewById(R.id.iv_ijarah);
        ivAsset = view.findViewById(R.id.iv_asset_pic);

        cvPurpose = view.findViewById(R.id.cv_purpose);

        edOtherPurpose = view.findViewById(R.id.purposeother);
        edAmount = view.findViewById(R.id.ed_amount);
        spProduct = view.findViewById(R.id.sp_product);
        if (!canEdit) {
            spProduct.setEnabled(false);
        }
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
                    < loanCategoryInfo.loanLimit) {

                edMarketValue.setError("Asset current market value can not be less than loan Pkr "
                        + loanCategoryInfo.loanLimit);

                edMarketValue.requestFocus();
                return;
            }

            fillLoanInfo(edRental.getText().toString().replace(",", ""), 1);

        });


        ImageView ivBack = view.findViewById(R.id.back);
        ivBack.setOnClickListener(view12 -> {
            context.onBackPressed();
        });

        setBankSpinner();

        if (borrower != null && borrower.bankDetail != null) {
            edAccountNumber.setText(borrower.bankDetail.accountNumber);

        }
        ivProfile = view.findViewById(R.id.iv_profile_image);
        llCapImage.setOnClickListener(view1 -> {
            picNumber = 1;
            if (loanApplyRequest.onlineProfile == null) {
                captureImage();
            } else {
                new ImageShowDialogPop(null, loanApplyRequest.onlineProfile, this)
                        .show(getChildFragmentManager(), "asdasd");
            }

        });
        ivCnicFront.setOnClickListener(view1 -> {
            picNumber = 2;
            if (loanApplyRequest.onLineFront == null) {
                captureImage();
            } else {
                new ImageShowDialogPop(null, loanApplyRequest.onLineFront, this)
                        .show(getChildFragmentManager(), "asdasd");
            }

        });
        ivCnicBack.setOnClickListener(view1 -> {
            picNumber = 3;
            if (loanApplyRequest.onlineCNICBack == null) {
                captureImage();
            } else {
                new ImageShowDialogPop(null, loanApplyRequest.onlineCNICBack, this)
                        .show(getChildFragmentManager(), "asdasd");
            }

        });
        ivAsset.setOnClickListener(v -> {

            picNumber = 4;
            if (loanApplyRequest.onlineAsset == null) {
                captureImage();
            } else {
                new ImageShowDialogPop(null, loanApplyRequest.onlineAsset, this)
                        .show(getChildFragmentManager(), "asdasd");
            }

        });
        ivIjarah.setOnClickListener(v -> {
            picNumber = 6;
            if (loanApplyRequest.onlineIjarha == null) {

                if (loanApplyRequest.onlineMusharka == null) {
                    Toast.makeText(context, "Please sign Musharka first!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!canDoIjarah) {
                    String time[] = tvTimer.getText().toString().split(":");
                    String hours = time[0];
                    String mints = time[1];
                    String seconds = time[2];

                    AlertUtils.showAlert(context, "Time Remaining to do Ijarah Agreement is: "
                            + hours + " hours " + mints + " min " + seconds + " seconds");
                    return;
                }

                fillLoanInfo(edRental.getText().toString(), 2);
            } else {
                new ImageShowDialogPop(null, loanApplyRequest.onlineIjarha, this)
                        .show(getChildFragmentManager(), "asdasd");
            }


        });

        ivMusharka.setOnClickListener(v -> {
            picNumber = 5;
            if (loanApplyRequest.onlineMusharka == null) {
                fillLoanInfo(edRental.getText().toString(), 1);
            } else {
                new ImageShowDialogPop(null, loanApplyRequest.onlineMusharka, this)
                        .show(getChildFragmentManager(), "asdasd");
            }


        });

        checkPermissionForCamera();
        setInputLimit();

        getLoanProducts();

        setData();
        setInputListner();

        setImages();

        return view;
    }

    private Boolean canDoIjarah = false;

    private void setTimerForIjarah(String date) {

        int hours = 2;
        if (loanApplyRequest.intervalTime != null
                && !loanApplyRequest.intervalTime.isEmpty()) {
            hours = (Integer.parseInt(loanApplyRequest.intervalTime));
        }

        tvTimer.setVisibility(View.VISIBLE);
        tvTimerHeader.setVisibility(View.VISIBLE);

        long initialTimestampMillis = parseTimestamp(date.substring(0, 18), hours);

        //long initialTimestampMillis = parseTimestamp("2024-03-06T11:19:00");

        long targetTimestampMillis = initialTimestampMillis + ((0 + 5) * 60 * 60 * 1000);

        long currentTimeMillis = System.currentTimeMillis();
        long remainingTimeMillis = targetTimestampMillis - currentTimeMillis;

        startCountdownTimer(remainingTimeMillis);
    }


    private long parseTimestamp(String timestamp, int mintsToAdd) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = dateFormat.parse(timestamp);
            return addMinutesToDate(date, mintsToAdd).getTime();
//            if (date != null) {
//                return date.getTime();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Date addMinutesToDate(Date date, int minutesToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutesToAdd);
        return calendar.getTime();
    }

    private CountDownTimer countDownTimer;

    private void startCountdownTimer(long millisInFuture) {
        countDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateCountdownText(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                updateCountdownText(0);
                canDoIjarah = true;
                // Perform any actions when the countdown finishes
            }
        };

        countDownTimer.start();
    }

    private void updateCountdownText(long millisUntilFinished) {
        long hours = millisUntilFinished / (60 * 60 * 1000);
        long minutes = (millisUntilFinished % (60 * 60 * 1000)) / (60 * 1000);
        long seconds = ((millisUntilFinished % (60 * 60 * 1000)) % (60 * 1000)) / 1000;

        String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        tvTimer.setText(formattedTime);
    }


    private void setImages() {

        if (loanApplyRequest.onlineProfile != null) {
            Picasso.get().load(loanApplyRequest.onlineProfile).into(ivProfile);

        }
        if (loanApplyRequest.onlineAsset != null) {
            Picasso.get().load(loanApplyRequest.onlineAsset).into(ivAsset);

        }
        if (loanApplyRequest.onlineMusharka != null) {
            Picasso.get().load(loanApplyRequest.onlineMusharka).into(ivMusharka);

        }
        if (loanApplyRequest.onlineIjarha != null) {
            Picasso.get().load(loanApplyRequest.onlineIjarha).into(ivIjarah);
            tvTimer.setVisibility(GONE);
            tvTimerHeader.setVisibility(GONE);

        } else {
            if (loanApplyRequest.onlineMusharka != null) {

                setTimerForIjarah(loanApplyRequest.dateTime);
            } else {

                tvTimer.setVisibility(GONE);
                tvTimerHeader.setVisibility(GONE);
            }

        }
        if (loanApplyRequest.onLineFront != null) {
            Picasso.get().load(loanApplyRequest.onLineFront).into(ivCnicFront);

        }
        if (loanApplyRequest.onlineCNICBack != null) {
            Picasso.get().load(loanApplyRequest.onlineCNICBack).into(ivCnicBack);

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
            if (borrower.status != null) {
                if (borrower.status.equals("pending")) {
                    tvStaus.setText("In Process");
                } else if (borrower.status.equals("verified")) {
                    tvStaus.setText("Ready To Disbursed");
                } else {
                    tvStaus.setText(borrower.status);
                }

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
                .start(context, ApplicationDetailFragment.this);
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

            }
        });

        edMarketValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loanApplyRequest.asstMarketValue = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

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

    private void fillLoanInfo(String share, int number) {

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

        submitLoan(share, number);
    }

    private void submitLoan(String share, int number) {


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

//        Bundle bundle = new Bundle();
//        bundle.putSerializable("loan_detail", loanCategoryInfo);
//        context.setAgreementFragment(1, bundle);

        if (isValidFields()) {
            saveMobileWallet(number);
        }
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


    private void saveMobileWallet(int number) {

        ProfileCreateRequestInfo requestInfo = new ProfileCreateRequestInfo();
        requestInfo.setAccountnumber(edAccountNumber.getText().toString());
        requestInfo.setAccounttitle(bank);
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


        submitUserInformation(requestInfo, number);
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

        if (borrower != null && borrower.bankDetail != null) {
            edAccountNumber.setText(borrower.bankDetail.accountNumber);
            if (borrower.bankDetail.verifyAt == null) {
                chVerify.setChecked(false);
                chVerify.setEnabled(true);
                edAccountNumber.setEnabled(true);
                edAccountNumber.setFocusable(true);
            } else {
                edAccountNumber.setEnabled(false);
                edAccountNumber.setFocusable(false);
                chVerify.setChecked(true);
                chVerify.setOnCheckedChangeListener((buttonView, isChecked) -> {

                    if (!isChecked) {
                        chVerify.setChecked(true);
                    }
                });
            }
            int selected = -1;
            for (int i = 0; i < banks.size(); i++) {

                if (banks.get(i) == borrower.bankDetail.accountTitle) {
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

    private void submitUserInformation(ProfileCreateRequestInfo createProfile, int number) {

        if (borrower != null
                && borrower.bankDetail != null
                && borrower.bankDetail.accountNumber != null
                && (edAccountNumber.getText().toString()
                .equals(borrower.bankDetail.accountNumber)
                || edPhoneChange.getText().toString().equals(borrower.phoneno))) {

            Bundle bundle = new Bundle();
            bundle.putSerializable("loan_detail", loanCategoryInfo);
            context.setAgreementFragment(number, bundle);

        } else {

            final AlertDialog dialog = AlertUtils.showLoader(getActivity());
            UserBusiness userBusiness = new UserBusiness();
            userBusiness.setProfile(createProfile, new ResponseCallBack<ProfileCreateResponseInfo>() {
                @Override
                public void onSuccess(ProfileCreateResponseInfo body) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("loan_detail", loanCategoryInfo);
                    context.setAgreementFragment(1, bundle);

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

    @Override
    public void onEditClick() {

        if (!canEdit) {
            Toast.makeText(context, "Can not be edited at this stage!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (picNumber == 5) {
            fillLoanInfo("", 1);
        } else if (picNumber == 6) {
            fillLoanInfo("", 2);
        } else {
            captureImage();
        }

    }

    @Override
    public void onDestroy() {
        countDownTimer = null;
        super.onDestroy();
    }
}