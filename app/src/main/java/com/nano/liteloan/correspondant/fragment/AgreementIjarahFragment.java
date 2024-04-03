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
import android.widget.LinearLayout;
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
 * Created by Muhammad Ahmad on 11/07/2023.
 */
public class AgreementIjarahFragment extends Fragment implements SubmitApplicationDialogue.OnApplicationSubmit {

    String name, parentage, cnic, address, loanMonths, loanAmount;
    String percentage;
    LinearLayout ivSave;
    private TextView tvTime;
    boolean isAppliedLoad = false;
    private Button btnApply;
    SignaturePad owner, costumer, witness;
    TextView tvAsstName, tvLoanAmount, tvLoanMonths, tvAssetAmount, tvAssetMarketValue;
    ScrollView mainView;
    TextView tvSellerPad, tvBuyerPad, tvWitnessPad, tvTenat;
    private LoanCategoryInfo loanCategoryInfo;
    MainActivity context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;
    }

    public AgreementIjarahFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agreement_ijarah, container, false);
        tvSellerPad = view.findViewById(R.id.tv_clear_seller);
        tvBuyerPad = view.findViewById(R.id.tv_clear_buyer);
        tvWitnessPad = view.findViewById(R.id.tv_clear_witness);
        tvTenat = view.findViewById(R.id.tv_tenat_heading);
        owner = view.findViewById(R.id.signature_pad_owner);
        costumer = view.findViewById(R.id.signature_pad_costumer);
        witness = view.findViewById(R.id.signature_pad_witness);
        tvAssetAmount = view.findViewById(R.id.tv_asset_price);
        tvAssetMarketValue = view.findViewById(R.id.tv_asst_market_value);
        tvAsstName = view.findViewById(R.id.tv_assetName);
        tvLoanAmount = view.findViewById(R.id.tv_loan_amount);
        tvLoanMonths = view.findViewById(R.id.tv_loan_months);
        ivSave = view.findViewById(R.id.iv_save);
        mainView = view.findViewById(R.id.main_view);

        if (this.getArguments() != null
                && this.getArguments()
                .getSerializable("loan_detail") != null) {
            loanCategoryInfo = (LoanCategoryInfo) this.getArguments().getSerializable("loan_detail");

        }
        if (loanCategoryInfo != null && loanCategoryInfo.borrower != null) {

            name = loanCategoryInfo.borrower.name;
            cnic = loanCategoryInfo.borrower.cnic;
            parentage = loanCategoryInfo.borrower.parentage;
            address = loanCategoryInfo.borrower.userDetail.currentAddress;
            loanMonths = loanCategoryInfo.loanDuration.toString();

            loanAmount = loanCategoryInfo.borrower.loanApplyRequest.amount;
            tvTenat.setText("Tenant " + "(" + name + ")");

            String rentPercentage = loanCategoryInfo.borrower.loanApplyRequest.rentAmount;
            int percentage = Integer.parseInt(rentPercentage);
            Integer rentAmount = Integer.parseInt(loanCategoryInfo.borrower.loanApplyRequest.amount) +
                    ((Integer.parseInt(loanCategoryInfo.borrower.loanApplyRequest.amount) / 100) * percentage);

            loanAmount = rentAmount.toString();

            tvLoanMonths.setText(loanCategoryInfo.loanDuration + " Days");
            tvLoanAmount.setText(AppUtils.getFormatAmount(loanAmount));
            tvAsstName.setText(loanCategoryInfo.borrower.assetName);
            tvAssetAmount.setText(AppUtils.getFormatAmount(loanCategoryInfo.borrower.assetPrice));
            tvAssetMarketValue.setText(loanCategoryInfo.borrower.assetMarketValue);

        }

        String line1 = "واصل فاونڈیشن مندرجہ ذیل اثاثہ میں اپنا حصہ مسمٰی/مسماۃ " + " " + name + " " + "ولد/زوجہ" + " " + " " + parentage + " " + "حامل شناختی کارڈ نمبر" + " " + cnic + " " + "، رہائشی" + " " + address + " " + "کو" + " " + loanMonths + " " + "دن  کے لیے" + " " + AppUtils.getFormatAmount(loanAmount) + " " + "روپے ماہانہ کرایہ پر دیتا ہے" + " ";
        String line2 = "دونوں فریقین اس کرایہ داری کے معاہدہ کے پابند ہوں گے۔";

        TextView tvContent = view.findViewById(R.id.tv_content);
        tvContent.setText(line1 + "\n\n" + line2);

        String line3 = "1-\tواصل فاونڈیشن مندرجہ  بالااثاثہ کا" + " " + loanCategoryInfo.borrower.loanApplyRequest.rentPercentage + " " + " فیصد مالک ہے اور گاہک کو اثاثہ میں اپنا حقِ استعمال کرائے پر دے دیا ہے۔";
        String line4 = "2-\tکرایہ دار   اثاثہ   کی روز مرہ اور معمول کی دیکھ بھال کا ذمہ دار ہو گا  ۔";
        String line5 = "3-\tاثاثہ   کی تباہی  یا نقصان کی صورت میں جب اثاثہ قابل استعمال حالت میں نہ رہے تو واصل فاونڈیشن اس دورانیے کا کرا یہ وصول نہیں کرے گا  ۔";
        String line6 = "4-\tاگر کرایہ دار  کی تعدی  ( (willful misconductیا غفلت (negligence) برتنے کی وجہ سےاثاثہ کو کوئی نقصان  پہنچا تو وہ تمام نقصان  کرایہ دار   برداشت کرے گا  ۔";
        String line7 = "5-\tکرایہ داری کے معاہدے  کی میعاد مکمل ہونے تک کرایہ دار   اس  اثاثہ  کو   فروخت نہیں  کرے گا اور نہ ہی کرایہ پر دے گا اور نہ ہی گروی رکھوائے گا۔";
        String line8 = "6-\tاگر کرایہ دار  نے اثاثہ /جائیداد  کی  طے شدہ   مدت  کی پاسداری نہ کی ،تو واصل فاونڈیشن مزید مدت  کے لئے  اثاثہ استعمال کرنے پر  کرایہ وصول کرنے کا حق رکھتا ہے۔";

        TextView tvTerms = view.findViewById(R.id.tv_terms);

        tvTerms.setText(line3 + "\n\n" + line4 + "\n\n" + line5 + "\n\n" + line6 + "\n\n" + line7 + "\n\n" + line8);

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
            String ownerBase64 = AppUtils.encodeImageBase64(owner.getSignatureBitmap());
            String costumerBase64 = AppUtils.encodeImageBase64(costumer.getSignatureBitmap());
            String witnessBase64 = AppUtils.encodeImageBase64(witness.getSignatureBitmap());

            loanCategoryInfo.borrower.ownerSignature = ownerBase64;
            loanCategoryInfo.borrower.borrowerSignature = costumerBase64;
            loanCategoryInfo.borrower.witnessSignature = witnessBase64;

            Bitmap bitmap = AppUtils.getBitmapFromView(mainView, mainView.getChildAt(0).getHeight(),
                    mainView.getChildAt(0).getWidth() + 90);

            loanCategoryInfo.borrower.loanApplyRequest.ijarahPic = AppUtils.encodeImageBase64(bitmap);

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
            if (bitmap == null) {
                AlertUtils.showAlert(context, "Failed to save Agreement in Gallery, you can take ScreenShot.!");
            } else {
                saveImage(bitmap);
            }

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

                    context.dashBoardFragment();

//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setDataAndType(uri, "image/jpeg");
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    context.startActivity(intent);
                });


            } catch (Exception e) {

                AlertUtils.showAlert(context, "Failed to save Agreement in Gallery, you can take ScreenShot.!");

                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                // Log.e("ahmad", "Error: " + e.getMessage());
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
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    // Log.e("ahmad", e.getMessage());
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
                        context.dashBoardFragment();
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setDataAndType(uri, "image/jpeg");
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    context.startActivity(intent);
                    });

                    galleryAddPic(saveImagePath);
                } catch (Exception e) {
                    AlertUtils.showAlert(context, "Failed to save Agreement in Gallery, you can take ScreenShot.!");

                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    // Log.e("ahmad", "Error: " + e.getMessage());
                }


            } else {

                AlertUtils.showAlert(context, "Failed to save Agreement in Gallery, you can take ScreenShot.!");

                // AlertUtils.showAlert(context, "Error");
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

    @Override
    public void onSubmit(LoanApplyRequest loanApplyRequest) {

        loanApplyRequest.borrowerId = loanCategoryInfo.borrower.borrowerid;
        loanApplyRequest.userId = loanCategoryInfo.borrower.userDetail.userId;

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