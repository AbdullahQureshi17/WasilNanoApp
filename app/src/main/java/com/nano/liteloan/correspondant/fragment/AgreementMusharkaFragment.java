package com.nano.liteloan.correspondant.fragment;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.LoanApplyRequest;
import com.nano.liteloan.info.LoanApplyResponse;
import com.nano.liteloan.info.LoanCategoryInfo;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.fragment.dialoguefragment.ApplicationSubmitSuccessDialog;
import com.nano.liteloan.presentation.fragment.dialoguefragment.SubmitApplicationDialogue;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Crated by Muhammad Ahmad on 11/07/2023.
 */
@SuppressLint("SetTextI18n")
public class AgreementMusharkaFragment extends Fragment implements SubmitApplicationDialogue.OnApplicationSubmit {

    String name = "Name";
    String cnic = "CNIC";
    private LoanCategoryInfo loanCategoryInfo;
    private Integer clientAmount = 0;
    private TextView tvTime;
    String address = "Address";
    private Button btnApply;
    String price = "Price";
    String wasilShare;
    String borrowerShare;
    boolean isAppliedLoad = false;
    ScrollView mainView;
    String salePrice = "SalePrice";
    SignaturePad owner, costumer, witness;
    MainActivity context;
    ImageButton ivSave;
    TextView tvSellerPad, tvBuyerPad, tvWitnessPad, tvSeller, tvBuyer;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;
    }

    public AgreementMusharkaFragment() {
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
        View view = inflater.inflate(R.layout.fragment_agreement, container, false);
        tvSellerPad = view.findViewById(R.id.tv_clear_seller);

        tvBuyerPad = view.findViewById(R.id.tv_clear_buyer);
        tvWitnessPad = view.findViewById(R.id.tv_clear_witness);
        TextView tvContent = view.findViewById(R.id.tv_content);
        owner = view.findViewById(R.id.signature_pad_owner);
        costumer = view.findViewById(R.id.signature_pad_costumer);
        witness = view.findViewById(R.id.signature_pad_witness);
        ivSave = view.findViewById(R.id.iv_save);
        mainView = view.findViewById(R.id.main_view);
        tvSeller = view.findViewById(R.id.tv_seller_heading);
        tvBuyer = view.findViewById(R.id.tv_buyer_heading);
        TextView tvClientPer = view.findViewById(R.id.client_percentage);
        TextView tvClintAmount = view.findViewById(R.id.client_amount);
        TextView tvWasilAmount = view.findViewById(R.id.tv_wasil_amount);
        TextView tvWasilPerc = view.findViewById(R.id.wasil_per);
        TextView tvTotalAmount = view.findViewById(R.id.tv_total_amount);
        TextView tvTotalPercent = view.findViewById(R.id.tv_total_percentage);

        if (this.getArguments() != null
                && this.getArguments()
                .getSerializable("loan_detail") != null) {
            loanCategoryInfo = (LoanCategoryInfo) this.getArguments().getSerializable("loan_detail");

        }

        if (loanCategoryInfo != null && loanCategoryInfo.borrower != null) {

            name = loanCategoryInfo.borrower.name;
            cnic = loanCategoryInfo.borrower.cnic;

            tvSeller.setText("Seller (" + name + ")");
            tvBuyer.setText("Buyer (Wasil Foundation)");

            address = loanCategoryInfo.borrower.userDetail.currentAddress;

            price = loanCategoryInfo.borrower.assetMarketValue;

            salePrice = (loanCategoryInfo.borrower.loanApplyRequest.amount);
            wasilShare = loanCategoryInfo.borrower.wasilShare;

        }
        double percentage = (Double.parseDouble(loanCategoryInfo.borrower
                .loanApplyRequest.amount) / Double.parseDouble(price)) * 100;

        int roundPercent = (int) Math.round(percentage);

        Integer shareAmount = Integer.parseInt(salePrice) / 100 * Integer.parseInt(wasilShare);

        if (wasilShare != null && !wasilShare.isEmpty()) {

            borrowerShare = String.valueOf(100 - roundPercent);

            tvTotalPercent.setText("100");
            tvTotalAmount.setText(AppUtils.getFormatAmount(price));

            clientAmount = Integer.parseInt(price) - clientAmount;

            int wasilAmount1 = Integer.parseInt(price) - Integer.parseInt(loanCategoryInfo.borrower
                    .loanApplyRequest.amount);

            tvClientPer.setText(borrowerShare);
            tvClintAmount.setText(AppUtils.getFormatAmount(wasilAmount1));

            tvWasilPerc.setText(String.valueOf(roundPercent));
            tvWasilAmount.setText(AppUtils.getFormatAmount(loanCategoryInfo.borrower.loanApplyRequest.amount));
            loanCategoryInfo.borrower.loanApplyRequest.rentPercentage = String.valueOf(roundPercent);
        }

        StringBuffer cnicRevers = new StringBuffer(cnic).reverse();

        String line1 = "میں مسمٰی/مسماۃ:" + " " + name + " " + "حامل شناختی کارڈ نمبر: " + cnic + " \n";
        String line2 = "رہائشی:" + " " + address + " " + "واصل فاونڈیشن سے درج ذیل شرائط پر مندرجہ ذیل اثاثہ  پر مشارکہ کرنے کا/کی خواہشمند ہوں۔";
        String line3 = "(1) میں واصل فاونڈیشن کو اپنے مندرجہ ذیل اثاثہ  جس کی موجودہ قیمت " + " " + AppUtils.getFormatAmount(price) + " " + "روپے ہے " + "کا :" + " " + roundPercent + " " + "فی صد حصہ : " + " " + AppUtils.getFormatAmount(salePrice) + " " + "روپے (فنانسنگ کی رقم )  قیمت کے عوض فروخت کرتا /کرتی ہوں۔ ";
        String line4 = "(2) واصل فاونڈیشن بطورِ شریک مندرجہ ذیل اثاثہ  کا" + " " + " " + roundPercent + " " + "فی صد حصہ مسمیٰ/مسماۃ" + " " + name + " " + "سے:" + " " + AppUtils.getFormatAmount(salePrice) + " " + "روپے(فنانسنگ کی رقم )  قیمت کے عوض خریدتا ہے۔";
        String line5 = "اس خریداری کے بعد دونوں فریقین اس اثاثہ   میں  اپنے اپنے حصے کے بقدر مشترکہ مالک ہوں گے  جس کی تفصیل درج ذیل ہے:";

        tvContent.setText(line1 + "\n" + line2 + "\n\n" + line3 + "\n\n" + line4 + "\n\n" + line5);

        btnApply = view.findViewById(R.id.btn_apply);
        btnApply.setOnClickListener(view1 -> {

            if (isAppliedLoad) {
                context.dashBoardFragment();
                return;
            }
            if (owner.isEmpty()) {
                Toast.makeText(context, "Owner Signature required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (costumer.isEmpty()) {
                Toast.makeText(context, "Costumer Signature required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (witness.isEmpty()) {
                Toast.makeText(context, "Witness Signature required", Toast.LENGTH_SHORT).show();
                return;
            }

            Bitmap bitmap = AppUtils.getBitmapFromView(mainView, mainView.getChildAt(0).getHeight(),
                    mainView.getChildAt(0).getWidth() + 90);

            loanCategoryInfo.borrower.loanApplyRequest.mussharkaPic = AppUtils.encodeImageBase64(bitmap);

            onSubmit(loanCategoryInfo.borrower.loanApplyRequest);

        });

        ivSave.setOnClickListener(view1 -> {

            if (owner.isEmpty()) {
                Toast.makeText(context, "Owner Signature required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (costumer.isEmpty()) {
                Toast.makeText(context, "Costumer Signature required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (witness.isEmpty()) {
                Toast.makeText(context, "Witness Signature required", Toast.LENGTH_SHORT).show();
                return;
            }

            btnApply.setVisibility(View.GONE);
            Bitmap bitmap = AppUtils.getBitmapFromView(mainView, mainView.getChildAt(0).getHeight(),
                    mainView.getChildAt(0).getWidth() + 90);

            btnApply.setVisibility(View.VISIBLE);
            saveImage(bitmap);
        });

        tvWitnessPad.setOnClickListener(v -> {

            witness.clearView();
        });

        tvSellerPad.setOnClickListener(v -> {

            owner.clearView();
        });

        tvBuyerPad.setOnClickListener(v -> {

            costumer.clearView();
        });

        TextView tvDateTime = view.findViewById(R.id.tv_date);
        tvTime = view.findViewById(R.id.tv_time);

        SimpleDateFormat date = new SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault());
        String currentDate = date.format(new Date());
        tvDateTime.setText("Dated: " + currentDate);

        updateCurrentTime();

        return view;
    }

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            updateCurrentTime();
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(updateTimeRunnable, 1000);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(updateTimeRunnable);
    }

    private void updateCurrentTime() {

        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
        String currentTime = time.format(new Date());
        tvTime.setText("Date Time: " + currentTime);
    }

    @Override
    public void onSubmit(LoanApplyRequest loanApplyRequest) {

        PreferenceUtils.getInstance().addLoanRequest(loanApplyRequest,
                loanCategoryInfo.borrower.cnic);

//        Bundle bundle = new Bundle();
//        bundle.putSerializable("loan_detail", loanCategoryInfo);
//        context.setAgreementFragment(2, bundle);

        postMushrkaAndExit(loanApplyRequest);


    }

    private void postMushrkaAndExit(LoanApplyRequest loanApplyRequest) {

        loanApplyRequest.borrowerId = loanCategoryInfo.borrower.borrowerid;
        loanApplyRequest.userId = loanCategoryInfo.borrower.userDetail.userId;

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        loanApplyRequest.dateTime = formatter.format(date);


        final AlertDialog dialog = AlertUtils.showLoader(getActivity());

        EasyLoanBusiness business = new EasyLoanBusiness();

        business.applyForLoan(loanApplyRequest, new ResponseCallBack<LoanApplyResponse>() {
            @Override
            public void onSuccess(LoanApplyResponse body) {

                dialog.dismiss();
                PreferenceUtils.getInstance().addLoanRequest(null, loanCategoryInfo.borrower.cnic);
                isAppliedLoad = true;
                btnApply.setText("Go Back");

                btnApply.setVisibility(View.GONE);

                successApply();

                btnApply.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(String message) {

                dialog.dismiss();

                AlertUtils.showAlert(getActivity(), message);
            }
        });
    }

    private void saveImage(Bitmap bm) {

        String saveImagePath = null;
        File imageFile;

        String imageFilePath = System.currentTimeMillis() + ".png";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            ContentResolver resolver = context.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, imageFilePath);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
            contentValues.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis());

            Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

            try {
                OutputStream fOut = resolver.openOutputStream(uri);
                bm.compress(Bitmap.CompressFormat.PNG, 80, fOut);
                fOut.close();

                AlertUtils.showAlert(context, "Agreement has been saved to the Gallery.", (dialog, which) -> {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("loan_detail", loanCategoryInfo);
                    context.setAgreementFragment(2, bundle);
                });


            } catch (Exception e) {
                AlertUtils.showAlert(context, "Failed to save Agreement in Gallery, you can take ScreenShot.!");
                btnApply.setText("Continue");
            }
        } else {

            File storageDir;

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                storageDir = new File(
                        Environment
                                .getExternalStorageDirectory()
                                + "/cih");
            } else {
                storageDir = new File(
                        Environment
                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                                + "/cih");
            }

            boolean success = true;
            if (!storageDir.exists()) {
                try {
                    success = storageDir.mkdirs();
                } catch (RuntimeException e) {
                    AlertUtils.showAlert(context, "Failed to save Agreement in Gallery, you can take ScreenShot.!");
                    btnApply.setText("Continue");
                }

            }

            if (success) {
                imageFile = new File(storageDir, imageFilePath);
                saveImagePath = imageFile.getAbsolutePath();

                try {
                    OutputStream fOut = new FileOutputStream(imageFile);
                    bm.compress(Bitmap.CompressFormat.PNG, 80, fOut);
                    fOut.close();

                    AlertUtils.showAlert(context, "Agreement has been saved to the Gallery.", (dialog, which) -> {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("loan_detail", loanCategoryInfo);
                        context.setAgreementFragment(2, bundle);
                    });

                    galleryAddPic(saveImagePath);
                } catch (Exception e) {
                    AlertUtils.showAlert(context, "Failed to save Agreement in Gallery, you can take ScreenShot.!");
                    btnApply.setText("Continue");
                }

            } else {

                AlertUtils.showAlert(context, "Failed to save Agreement in Gallery, you can take ScreenShot.!");
                btnApply.setText("Continue");
            }
        }

    }

    private void galleryAddPic(String imagePath) {

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);

    }

    private void successApply() {

        PreferenceUtils.getInstance().addLoanRequest(null,
                loanCategoryInfo.borrower.cnic);
        PreferenceUtils.getInstance().saveImageBase64(null,
                loanCategoryInfo.borrower.cnic + "_" + 1);
        PreferenceUtils.getInstance().saveImageBase64(null,
                loanCategoryInfo.borrower.cnic + "_" + 2);
        PreferenceUtils.getInstance().saveImageBase64(null,
                loanCategoryInfo.borrower.cnic + "_" + 3);
        PreferenceUtils.getInstance().saveImageBase64(null,
                loanCategoryInfo.borrower.cnic + "_" + 4);

        ApplicationSubmitSuccessDialog dialog1 =
                new ApplicationSubmitSuccessDialog(context);

        dialog1.show(getChildFragmentManager(),
                ApplicationSubmitSuccessDialog.class.getName());
    }
}