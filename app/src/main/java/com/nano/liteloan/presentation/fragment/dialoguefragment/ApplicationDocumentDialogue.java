package com.nano.liteloan.presentation.fragment.dialoguefragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.nano.liteloan.R;
import com.nano.liteloan.info.UserDocumentsObject;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.AppConstantsUtils;
import com.nano.liteloan.utils.AppUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Muhammad Ahmad on 07/10/2020.
 */

public class ApplicationDocumentDialogue extends DialogFragment implements View.OnClickListener {

    private LinearLayout lIdcard, lSalary, lUtility, lOther;
    private ImageView ivIdcard, ivSalary, ivUtility, ivOther;
    private TextView tvIdcard, tvSalary, tvUtility, tvOther;
    private View cardLine, salaryLine, billLine, otherLine;
    private View llCnicImage, llDocImage;
    private MainActivity context;
    private int requestCamera;
    private String imagePath;
    private String back64, front64, utility64, other64, salary64;
    private Bitmap backBitmap, frontBitmap, utilityBitmap, salaryBitmap, otherBitmap;
    private int docType;
    private ImageView ivFront, ivBack, ivOtherDoc;
    private OnDocumentSubmit onDocumentSubmit;
    private UserDocumentsObject documents;
    private TextView tvImageHeading;


    public ApplicationDocumentDialogue(MainActivity context, UserDocumentsObject documents) {
        // Required empty public constructor
        this.context = context;
        this.documents = documents;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.documents_dialog, container, false);
        ImageView ivClose = view.findViewById(R.id.close);
        ivClose.setOnClickListener(this);

        tvImageHeading = view.findViewById(R.id.tv_imageHeading);

        ivFront = view.findViewById(R.id.cnic_front);
        ivFront.setOnClickListener(this);

        ivBack = view.findViewById(R.id.cnic_back);
        ivBack.setOnClickListener(this);


        ivOtherDoc = view.findViewById(R.id.rl_doc_image);
        ivOtherDoc.setOnClickListener(this);

        lIdcard = view.findViewById(R.id.id_card);
        lIdcard.setOnClickListener(this);
        lSalary = view.findViewById(R.id.salary_slip);
        lSalary.setOnClickListener(this);
        lUtility = view.findViewById(R.id.utility_bill);
        lUtility.setOnClickListener(this);
        lOther = view.findViewById(R.id.others);
        lOther.setOnClickListener(this);
        ivClose = view.findViewById(R.id.close);
        ivClose.setOnClickListener(this);

        cardLine = view.findViewById(R.id.car_line);
        salaryLine = view.findViewById(R.id.salary_line);
        billLine = view.findViewById(R.id.bill_line);
        otherLine = view.findViewById(R.id.other_line);

        ivIdcard = view.findViewById(R.id.id_card_image);
        ivSalary = view.findViewById(R.id.salary_slip_image);
        ivUtility = view.findViewById(R.id.utility_bill_image);
        ivOther = view.findViewById(R.id.others_image);

        tvIdcard = view.findViewById(R.id.id_card_text);
        tvSalary = view.findViewById(R.id.salary_slip_text);
        tvUtility = view.findViewById(R.id.utility_bill_text);
        tvOther = view.findViewById(R.id.others_text);

        llCnicImage = view.findViewById(R.id.ll_cnic_image);
        llDocImage = view.findViewById(R.id.doc_image);

        Button button = view.findViewById(R.id.btn_submit);
        button.setOnClickListener(this);

        requestCamera = AppConstantsUtils.CameraCodeConstantUtils.UPDATE_PROFILE_PIC;


        if (documents != null) {

            frontBitmap = documents.cnicFrontB;
            backBitmap = documents.cnicBackB;

            utilityBitmap = documents.utilityB;
            salaryBitmap = documents.salaryB;
            otherBitmap = documents.otherB;


            front64 = documents.cnicFront;
            back64 = documents.cnicBack;

            utility64 = documents.utility;
            salary64 = documents.salary;
            other64 = documents.other;

        }

        setCnicCard();
        return view;
    }


    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.id_card:

                setCnicCard();

                break;

            case R.id.salary_slip:

                setSalarySlipCard();

                break;

            case R.id.utility_bill:

                setUtilityBillCard();

                break;

            case R.id.others:

                setOtherCard();

                break;

            case R.id.close:
                ApplicationDocumentDialogue.this.dismiss();
                break;

            case R.id.btn_submit:
                submitDocuments();
                break;

            case R.id.cnic_back:
                docType = AppConstantsUtils.CameraCodeConstantUtils.CNIC_BACK;
                dispatchTakePictureIntent();
                break;

            case R.id.cnic_front:
                docType = AppConstantsUtils.CameraCodeConstantUtils.CNIC_FRONT;
                dispatchTakePictureIntent();
                break;

            case R.id.rl_doc_image:
                dispatchTakePictureIntent();
                break;

        }
    }

    private void setOtherCard() {
        tvImageHeading.setText("Other Documents");
        otherLine.setBackgroundResource(R.color.DarkOrange);
        ivOther.setBackgroundResource(R.drawable.ic_other_hover);
        tvOther.setTextColor(this.getResources().getColor(R.color.DarkOrange));

        llCnicImage.setVisibility(View.GONE);
        llDocImage.setVisibility(View.VISIBLE);

        docType = AppConstantsUtils.CameraCodeConstantUtils.OTHER_DOC;

        if (otherBitmap != null) {

            ivOtherDoc.setImageBitmap(otherBitmap);

        } else if (documents.otherURL != null) {

            Picasso.get().load(documents.otherURL).into(ivOtherDoc);
        } else {
            ivOtherDoc.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.iv_image_place));
        }

        salaryLine.setBackgroundResource(R.color.Light_Grey);
        ivSalary.setBackgroundResource(R.drawable.ic_salary);
        tvSalary.setTextColor(this.getResources().getColor(R.color.Gray));

        cardLine.setBackgroundResource(R.color.Light_Grey);
        ivIdcard.setBackgroundResource(R.drawable.ic_id_card);
        tvIdcard.setTextColor(this.getResources().getColor(R.color.Gray));

        billLine.setBackgroundResource(R.color.Light_Grey);
        ivUtility.setBackgroundResource(R.drawable.ic_bill);
        tvUtility.setTextColor(this.getResources().getColor(R.color.Gray));
    }

    private void setUtilityBillCard() {
        tvImageHeading.setText("Utility Bill image");
        billLine.setBackgroundResource(R.color.DarkOrange);
        ivUtility.setBackgroundResource(R.drawable.ic_bill_hover);
        tvUtility.setTextColor(this.getResources().getColor(R.color.DarkOrange));

        llCnicImage.setVisibility(View.GONE);
        llDocImage.setVisibility(View.VISIBLE);

        docType = AppConstantsUtils.CameraCodeConstantUtils.UTILITY_BILL;

        if (utilityBitmap != null) {

            ivOtherDoc.setImageBitmap(utilityBitmap);

        } else if (documents.utilityURL != null) {

            Picasso.get().load(documents.utilityURL).into(ivOtherDoc);
        } else {
            ivOtherDoc.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.iv_image_place));
        }

        salaryLine.setBackgroundResource(R.color.Light_Grey);
        ivSalary.setBackgroundResource(R.drawable.ic_salary);
        tvSalary.setTextColor(this.getResources().getColor(R.color.Gray));

        cardLine.setBackgroundResource(R.color.Light_Grey);
        ivIdcard.setBackgroundResource(R.drawable.ic_id_card);
        tvIdcard.setTextColor(this.getResources().getColor(R.color.Gray));

        otherLine.setBackgroundResource(R.color.Light_Grey);
        ivOther.setBackgroundResource(R.drawable.ic_other);
        tvOther.setTextColor(this.getResources().getColor(R.color.Gray));
    }

    private void setSalarySlipCard() {

        tvImageHeading.setText("Salary slip image");
        salaryLine.setBackgroundResource(R.color.DarkOrange);
        ivSalary.setBackgroundResource(R.drawable.ic_salary_hover);
        tvSalary.setTextColor(this.getResources().getColor(R.color.DarkOrange));

        llCnicImage.setVisibility(View.GONE);
        llDocImage.setVisibility(View.VISIBLE);

        docType = AppConstantsUtils.CameraCodeConstantUtils.SALARY_SLIP;

        if (salaryBitmap != null) {

            ivOtherDoc.setImageBitmap(salaryBitmap);
        } else {
            ivOtherDoc.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.iv_image_place));
        }

        cardLine.setBackgroundResource(R.color.Light_Grey);
        ivIdcard.setBackgroundResource(R.drawable.ic_id_card);
        tvIdcard.setTextColor(this.getResources().getColor(R.color.Gray));

        billLine.setBackgroundResource(R.color.Light_Grey);
        ivUtility.setBackgroundResource(R.drawable.ic_bill);
        tvUtility.setTextColor(this.getResources().getColor(R.color.Gray));

        otherLine.setBackgroundResource(R.color.Light_Grey);
        ivOther.setBackgroundResource(R.drawable.ic_other);
        tvOther.setTextColor(this.getResources().getColor(R.color.Gray));
    }

    private void setCnicCard() {

        cardLine.setBackgroundResource(R.color.DarkOrange);
        ivIdcard.setBackgroundResource(R.drawable.ic_id_card_hover);
        tvIdcard.setTextColor(this.getResources().getColor(R.color.DarkOrange));

        llCnicImage.setVisibility(View.VISIBLE);
        llDocImage.setVisibility(View.GONE);

        if (frontBitmap != null) {

            ivFront.setImageBitmap(frontBitmap);
        } else if (documents.cnicFrontURL != null) {

            Picasso.get()
                    .load(documents.cnicFrontURL).into(ivFront);
        } else {
            ivFront.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.iv_image_place));
        }

        if (backBitmap != null) {

            ivBack.setImageBitmap(backBitmap);

        } else if (documents.cnicBackURL != null) {

            Picasso.get().load(documents.cnicBackURL)
                    .into(ivBack);
        } else {
            ivBack.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.iv_image_place));
        }

        salaryLine.setBackgroundResource(R.color.Light_Grey);
        ivSalary.setBackgroundResource(R.drawable.ic_salary);
        tvSalary.setTextColor(this.getResources().getColor(R.color.Gray));

        billLine.setBackgroundResource(R.color.Light_Grey);
        ivUtility.setBackgroundResource(R.drawable.ic_bill);
        tvUtility.setTextColor(this.getResources().getColor(R.color.Gray));

        otherLine.setBackgroundResource(R.color.Light_Grey);
        ivOther.setBackgroundResource(R.drawable.ic_other);
        tvOther.setTextColor(this.getResources().getColor(R.color.Gray));
    }

    private void submitDocuments() {

        if (onDocumentSubmit != null) {

            UserDocumentsObject documentsObject = new UserDocumentsObject();
            documentsObject.cnicBack = back64;
            documentsObject.cnicFront = front64;
            documentsObject.other = other64;
            documentsObject.salary = salary64;
            documentsObject.utility = utility64;

            documentsObject.cnicBackB = backBitmap;
            documentsObject.cnicFrontB = frontBitmap;
            documentsObject.otherB = otherBitmap;
            documentsObject.salaryB = salaryBitmap;
            documentsObject.utilityB = utilityBitmap;

            onDocumentSubmit.onDocSubmit(documentsObject);

            ApplicationDocumentDialogue.this.dismiss();
        }

        ApplicationDocumentDialogue.this.dismiss();
    }

    private void dispatchTakePictureIntent() {

        Intent pictureIntent;
        pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(
                context.getPackageManager()) != null) {
            File photFile = null;
            Uri photoURI;
            try {
                photFile = createImageFile();
            } catch (IOException e) {
                Log.e("ahmad", e.getMessage());
            }

            if (photFile != null) {
                if (AppUtils.isMarshMallowOrGreater()) {
                    photoURI = FileProvider.getUriForFile(context,
                            "com.akhuwat.easyloan.fileprovider",
                            photFile);
                } else {
                    photoURI = Uri.fromFile(photFile);
                }

                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                startActivityForResult(pictureIntent, requestCamera);
            }
        }

    }

    @SuppressLint("SimpleDateFormat")
    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat(
                        "yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storeDir = context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storeDir      /* directory */
        );
        imagePath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

//        if (requestCode ==
//                requestCamera
//                && resultCode == RESULT_OK) {
//
//            File file = new File(imagePath);
//
//            beginCrop(Uri.fromFile(file));
//
//        } else if (requestCode == Crop.REQUEST_CROP) {
//            handleCrop(resultCode, data);
//        }
    }

    /**
     * begin crop after taking image.
     *
     * @param source source for image URL
     */
    @SuppressLint("SimpleDateFormat")
    private void beginCrop(Uri source) {

//        //for getting screen dimensions to increase
//        // crop size of image according to need
//        DisplayMetrics displayMetrics = AppUtils.getScreenDimensions(context);
//        int width = displayMetrics.widthPixels;
//        int height = displayMetrics.heightPixels;
//
//        String timeStamp = new SimpleDateFormat(
//                "yyyyMMdd_HHmmss")
//                .format(new Date());
//        String imageFileName = "JPEG_"
//                + timeStamp
//                + "_";
//
//        Uri destination = Uri.fromFile(
//                new File(context.getCacheDir(),
//                        imageFileName));
//        Crop.of(source, destination)
//                .withMaxSize(width,
//                        height)
//                .start(context,
//                        this);


    }

    /**
     * handle crop after image is taken and then set it on views.
     *
     * @param resultCode result code
     * @param data       Intent data
     */
    private void handleCrop(int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//
//            data.getData();
//            Bitmap bitmap = AppUtils.checkPhotoOrientationAndGetImage(AppUtils
//                    .getRealPathFromURI(Crop.getOutput(data),
//                            context));
//            assert bitmap != null;
//            if (docType == AppConstantsUtils.CameraCodeConstantUtils.SALARY_SLIP) {
//
//                salaryBitmap = bitmap;
//                salary64 = AppUtils.encodeImageBase64(bitmap);
//                ivOtherDoc.setImageBitmap(bitmap);
//
//            } else if (docType == AppConstantsUtils.CameraCodeConstantUtils.CNIC_FRONT) {
//
//                frontBitmap = bitmap;
//                front64 = AppUtils.encodeImageBase64(bitmap);
//                ivFront.setImageBitmap(bitmap);
//
//            } else if (docType == AppConstantsUtils.CameraCodeConstantUtils.CNIC_BACK) {
//
//                backBitmap = bitmap;
//                back64 = AppUtils.encodeImageBase64(bitmap);
//                ivBack.setImageBitmap(bitmap);
//
//            } else if (docType == AppConstantsUtils.CameraCodeConstantUtils.UTILITY_BILL) {
//
//                utilityBitmap = bitmap;
//                utility64 = AppUtils.encodeImageBase64(bitmap);
//                ivOtherDoc.setImageBitmap(bitmap);
//
//            } else if (docType == AppConstantsUtils.CameraCodeConstantUtils.OTHER_DOC) {
//
//                otherBitmap = bitmap;
//                other64 = AppUtils.encodeImageBase64(bitmap);
//                ivOtherDoc.setImageBitmap(bitmap);
//            }
//
//        } else if (resultCode == Crop.RESULT_ERROR) {
//            Toast.makeText(context, Crop.getError(data).getMessage(),
//                    Toast.LENGTH_SHORT).show();
//        }
    }

    public void setOnDocumentSubmit(OnDocumentSubmit onDocumentSubmit) {
        this.onDocumentSubmit = onDocumentSubmit;
    }

    /**
     * Created by Muhammad Ahmad on 07/17/2020.
     */
    public interface OnDocumentSubmit {

        void onDocSubmit(UserDocumentsObject documentsObject);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,
                R.style.dialogfragmentsettings);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Objects.requireNonNull(dialog.getWindow())
                .requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();

        if (d != null) {
            d.getWindow()
                    .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

}