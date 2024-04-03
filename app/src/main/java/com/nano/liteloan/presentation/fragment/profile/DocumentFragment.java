package com.nano.liteloan.presentation.fragment.profile;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.canhub.cropper.CropImageView;
import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.UserBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.ProfileCreateRequestInfo;
import com.nano.liteloan.info.ProfileCreateResponseInfo;
import com.nano.liteloan.info.UserDocumentsObject;
import com.nano.liteloan.info.UserProfile;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.fragment.dialoguefragment.HintDialogue;
import com.nano.liteloan.presentation.fragment.dialoguefragment.ProfileSuccessPopup;
import com.nano.liteloan.utils.AppConstantsUtils;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.squareup.picasso.Picasso;
import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageActivity;


import static android.app.Activity.RESULT_OK;

/**
 * Created by Muhammad Ahmad on 07/30/2020.
 */
public class DocumentFragment extends Fragment implements View.OnClickListener {

    private static final int MY_BLINKID_REQUEST_CODE = 123;
    private MainActivity context;
    private String back64;
    private String front64;
    private String utility64;
    private String salary64;
    private Bitmap backBitmap;
    private Bitmap frontBitmap;
    private Bitmap utilityBitmap;
    private Bitmap salaryBitmap;
    private int docType;
    private ImageView ivFront, ivBack, ivUtility, ivSalary;
    private UserDocumentsObject documents;
    private MainProfileFragment main;
    private boolean isMissingDoc;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;
    }

    public DocumentFragment() {
        // Required empty public constructor
    }

    DocumentFragment(MainProfileFragment main) {
        this.main = main;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_document, container, false);


        ivFront = view.findViewById(R.id.cnic_front);
        ivFront.setOnClickListener(this);

        ImageView hint = view.findViewById(R.id.hint);
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HintDialogue dialog1 = new HintDialogue();
                dialog1.show(getChildFragmentManager(),
                        HintDialogue.class.getName());
            }
        });

        ivBack = view.findViewById(R.id.cnic_back);
        ivBack.setOnClickListener(this);

        View llCnic = view.findViewById(R.id.ll_cnic);
        llCnic.setOnClickListener(this);


        ivSalary = view.findViewById(R.id.iv_salary);
        ivSalary.setOnClickListener(this);

        ivUtility = view.findViewById(R.id.iv_utility);
        ivUtility.setOnClickListener(this);


        Button button = view.findViewById(R.id.save_data);
        button.setOnClickListener(this);

        Button btnBack = view.findViewById(R.id.step2_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                main.viewPager.setCurrentItem(main.viewPager.getCurrentItem() - 1);
            }
        });


        if (documents != null) {

            frontBitmap = documents.cnicFrontB;
            backBitmap = documents.cnicBackB;

            utilityBitmap = documents.utilityB;
            salaryBitmap = documents.salaryB;
            Bitmap otherBitmap = documents.otherB;


            front64 = documents.cnicFront;
            back64 = documents.cnicBack;

            utility64 = documents.utility;
            salary64 = documents.salary;
            String other64 = documents.other;

        }


        return view;
    }

//    private RecognizerBundle prepareRecognizerBundle(@NonNull Recognizer<?>... recognizers) {
//        mRecognizerBundle = new RecognizerBundle(recognizers);
//        return mRecognizerBundle;
//    }
//
//    private void scanAction(@NonNull UISettings activitySettings) {
//        scanAction(activitySettings, null);
//    }
//
//    private void scanAction(@NonNull UISettings activitySettings, @Nullable Intent helpIntent) {
//        setupActivitySettings(activitySettings, helpIntent);
//        ActivityRunner.startActivityForResult(this, MY_BLINKID_REQUEST_CODE, activitySettings);
//    }
//
//    private void setupActivitySettings(@NonNull UISettings settings, @Nullable Intent helpIntent) {
//        if (settings instanceof BeepSoundUIOptions) {
//            // optionally, if you want the beep sound to be played after a scan
//            // add a sound resource id
//            ((BeepSoundUIOptions) settings).setBeepSoundResourceID(R.raw.beep);
//        }
//        if (settings instanceof OcrResultDisplayUIOptions) {
//            // If you want, you can disable drawing of OCR results on scan activity. Drawing OCR results can be visually
//            // appealing and might entertain the user while waiting for scan to complete, but might introduce a small
//            // performance penalty.
//            // ((ShowOcrResultUIOptions) settings).setShowOcrResult(false);
//
//            // Enable showing of OCR results as animated dots. This does not have effect if non-OCR recognizer like
//            // barcode recognizer is active.
//            ((OcrResultDisplayUIOptions) settings).setOcrResultDisplayMode(OcrResultDisplayMode.ANIMATED_DOTS);
//        }
//    }

    public void setContent() {
        if (main.userProfile != null) {
            UserProfile profile = main.userProfile;

            documents = new UserDocumentsObject();
            if (main.userProfile.cnicFront != null
                    && !main.userProfile.cnicFront.isEmpty()) {


                documents.cnicFrontURL = main.userProfile.cnicFront;
            }
            if (main.userProfile.cnicBack != null
                    && !main.userProfile.cnicBack.isEmpty()) {

                documents.cnicBackURL = main.userProfile.cnicBack;
            }

            if (main.userProfile.utilityBill != null
                    && !main.userProfile.utilityBill.isEmpty()) {


                documents.utilityURL = main.userProfile.utilityBill;
            }

            if (main.userProfile.other != null
                    && !main.userProfile.other.isEmpty()) {


                documents.salaryURL = main.userProfile.other;
            }

            setCnicFrontCard();
            setCnicbackCard();
            setUtilityBillCard();
            setSalarySlipCard();
        }

    }

    private void setUtilityBillCard() {


        docType = AppConstantsUtils.CameraCodeConstantUtils.UTILITY_BILL;

        if (utilityBitmap != null) {
            ivUtility.setImageBitmap(utilityBitmap);

        } else if (documents.utilityURL != null) {

            Picasso.get().load(documents.utilityURL).into(ivUtility);

        }

    }

    private void setSalarySlipCard() {


        docType = AppConstantsUtils.CameraCodeConstantUtils.SALARY_SLIP;

        if (salaryBitmap != null) {

            ivSalary.setImageBitmap(salaryBitmap);

        } else if (documents.salaryURL != null) {
            Picasso.get().load(documents.salaryURL).into(ivSalary);
        }

    }

    private void setCnicFrontCard() {


        if (frontBitmap != null) {

            ivFront.setImageBitmap(frontBitmap);

        } else if (documents.cnicFrontURL != null) {

            Picasso.get().load(documents.cnicFrontURL).into(ivFront);

        }


    }

    private void setCnicbackCard() {



        if (backBitmap != null) {

            ivBack.setImageBitmap(backBitmap);

        } else if (documents.cnicBackURL != null) {

            Picasso.get().load(documents.cnicBackURL).into(ivBack);

        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.save_data:

                if (main.userProfile.iseditable == 0) {
                    createUser();
                } else {

                }

                break;

            case R.id.cnic_back:
                if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context,
                            new String[]{Manifest.permission.CAMERA}, 101);
                } else {

                    docType = AppConstantsUtils.CameraCodeConstantUtils.CNIC_BACK;
                    dispatchTakePictureIntent();
                }

                break;

            case R.id.ll_cnic:
//
//                mRecognizer = new BlinkIdCombinedRecognizer();
//                ImageSettings.enableAllImages(mRecognizer);
//                scanAction(new BlinkIdUISettings(prepareRecognizerBundle(mRecognizer)));

                break;
            case R.id.cnic_front:
                if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context,
                            new String[]{Manifest.permission.CAMERA}, 101);
                } else {
                    docType = AppConstantsUtils.CameraCodeConstantUtils.CNIC_FRONT;
                    dispatchTakePictureIntent();
                }


                break;

            case R.id.iv_salary:
                if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context,
                            new String[]{Manifest.permission.CAMERA}, 101);
                } else {
                    docType = AppConstantsUtils.CameraCodeConstantUtils.SALARY_SLIP;
                    dispatchTakePictureIntent();
                }

                break;

            case R.id.iv_utility:
                if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context,
                            new String[]{Manifest.permission.CAMERA}, 101);
                } else {
                    docType = AppConstantsUtils.CameraCodeConstantUtils.UTILITY_BILL;
                    dispatchTakePictureIntent();
                }

                break;

        }
    }

    private void dispatchTakePictureIntent() {
        Log.d("eQard" , "dispatchTakePictureIntent");
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityTitle("Crop Image")
                .setAutoZoomEnabled(true)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setCropMenuCropButtonTitle("Done")
                .setRequestedSize(400, 400)
                .setCropMenuCropButtonIcon(R.drawable.ic_tick)
                .start(context, DocumentFragment.this);

    }


    public void onDataReturn(int requestCode, int resultCode, @Nullable Intent data) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                String pathe = result.getUriFilePath(context, false);
                Bitmap bitmap = AppUtils.checkPhotoOrientationAndGetImage(pathe);
                Log.d("eQard" , " Activity result crop is ok");
                handleCrop(resultCode, bitmap);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(context, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }


    }
    //    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        Log.d("eQard" , "enetr activity crop");
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                String pathe = result.getUriFilePath(context, false);
//                Bitmap bitmap = AppUtils.checkPhotoOrientationAndGetImage(pathe);
//                Log.d("eQard" , " Activity result crop is ok");
//                handleCrop(resultCode, bitmap);
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Toast.makeText(context, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
//            }
//        }
//
//        // For MicroBlink Auto Scan Documents for OCR
//
////        if (requestCode == MY_BLINKID_REQUEST_CODE) {
////            if (resultCode == RESULT_OK && data != null) {
////                // load the data into all recognizers bundled within your RecognizerBundle
////
////                List<Recognizer> recognizersWithResult = new ArrayList<>();
////
////                mRecognizerBundle.loadFromIntent(data);
////
////                for (Recognizer<Recognizer.Result> r : mRecognizerBundle.getRecognizers()) {
////                    if (r.getResult().getResultState() != Recognizer.Result.State.Empty) {
////                        recognizersWithResult.add(r);
////                    }
////                }
////
////
////                // now every recognizer object that was bundled within RecognizerBundle
////                // has been updated with results obtained during scanning session
////
////                // you can get the result by invoking getResult on recognizer
////                BlinkIdCombinedRecognizer.Result result = mRecognizer.getResult();
////
////                if (result.getResultState() == Recognizer.Result.State.Valid) {
////
////                    // result is valid, you can use it however you wish
////                    ivFront.setImageBitmap(result
////                            .getFullDocumentFrontImage()
////                            .convertToBitmap());
////
////                    ivBack.setImageBitmap(result
////                            .getFullDocumentBackImage()
////                            .convertToBitmap());
////                }
////            } else {
////
////                // do nothing for now
////            }
////        }
//
//    }

    /**
     * handle crop after image is taken and then set it on views.
     *
     * @param resultCode result code

     */
    private void handleCrop(int resultCode,Bitmap bitmap) {
        if (resultCode == RESULT_OK) {


            Log.d("eQard" , "enter handle crop");
            if (documents == null) {
                documents = new UserDocumentsObject();
            }
            assert bitmap != null;
            if (docType == AppConstantsUtils.CameraCodeConstantUtils.SALARY_SLIP) {

                salaryBitmap = bitmap;
                salary64 = AppUtils.encodeImageBase64(bitmap);
                documents.salaryB = bitmap;
                documents.salary = salary64;

                setSalarySlipCard();

            } else if (docType == AppConstantsUtils.CameraCodeConstantUtils.CNIC_FRONT) {
                Log.d("eQard" , "enter Cnic crop");
                frontBitmap = bitmap;
                front64 = AppUtils.encodeImageBase64(bitmap);
                documents.cnicFrontB = bitmap;
                documents.cnicFront = front64;

                setCnicFrontCard();

            } else if (docType == AppConstantsUtils.CameraCodeConstantUtils.CNIC_BACK) {

                backBitmap = bitmap;
                back64 = AppUtils.encodeImageBase64(bitmap);
                documents.cnicBackB = bitmap;
                documents.cnicBack = back64;

                setCnicbackCard();

            } else if (docType == AppConstantsUtils.CameraCodeConstantUtils.UTILITY_BILL) {

                utilityBitmap = bitmap;
                utility64 = AppUtils.encodeImageBase64(bitmap);
                documents.utilityB = bitmap;
                documents.utility = utility64;

                setUtilityBillCard();

            }
        }
    }

    private void createUser() {

        main.ivStep3.setBackgroundResource(R.drawable.tick);

        ProfileCreateRequestInfo createProfile4 = new ProfileCreateRequestInfo();

        createProfile4.setCnicback(documents.cnicBack);
        createProfile4.setCnicfront(documents.cnicFront);
        createProfile4.setUtilitybill(documents.utility);
        createProfile4.setOther(documents.salary);

        createProfile4.stage = "4";

        if ((documents.cnicBack == null && documents.cnicBackURL == null) ||
                (documents.cnicFront == null && documents.cnicFrontURL == null) ||
                (documents.utility == null && documents.utilityURL == null)) {
            AlertUtils.showAlert(context, "Upload your documents images");

        } else {
            submitUserDocuments(createProfile4);
        }


    }

    private void submitUserDocuments(ProfileCreateRequestInfo createProfile) {

        if (createProfile.getCnicback() != null
                || createProfile.getCnicfront() != null || createProfile.getUtilitybill() != null) {
            final AlertDialog dialog = AlertUtils.showLoader(getActivity());

            UserBusiness userBusiness = new UserBusiness();
            userBusiness.setProfile(createProfile, new ResponseCallBack<ProfileCreateResponseInfo>() {
                @Override
                public void onSuccess(ProfileCreateResponseInfo body) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    ProfileSuccessPopup popup = new ProfileSuccessPopup(context);
                    popup.show(getChildFragmentManager(), ProfileSuccessPopup.class.getName());


                }

                @Override
                public void onFailure(String message) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }

                    AlertUtils.showAlert(getContext(), message);
                }
            });
        } else {

            ProfileSuccessPopup popup = new ProfileSuccessPopup(context);
            popup.show(getChildFragmentManager(), ProfileSuccessPopup.class.getName());
        }

    }
}
