package com.nano.liteloan.presentation.fragment.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.UserBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.ProfileCreateRequestInfo;
import com.nano.liteloan.info.ProfileCreateResponseInfo;
import com.nano.liteloan.info.UserDocumentsObject;
import com.nano.liteloan.info.UserProfile;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.fragment.dialoguefragment.ApplicationDocumentDialogue;
import com.nano.liteloan.presentation.fragment.dialoguefragment.ImagePopupDialog;
import com.nano.liteloan.utils.AppConstantsUtils;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.victor.loading.rotate.RotateLoading;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class ProfileStep3Fragment extends Fragment implements View.OnClickListener,
        ApplicationDocumentDialogue.OnDocumentSubmit {

    private MainProfileFragment main;
    private Spinner spDependent, spCloseLoan,
            spAkhuwatLoan, spEarnig, spAbrod, spAbrosPurpose;
    private ImageView logoCnicFront,
            logoCnicBack, logoSalary, logoUtility;
    private TextView tvCnicBack,
            tvCnicFront, tvSalary, tvUtility;

    private int requestCamera;
    private String imagePath;
    private String back64, front64, utility64,
            other64, salary64;
    private Bitmap backBitmap, frontBitmap,
            utilityBitmap, salaryBitmap, otherBitmap;
    private int docType;
    private int activeLoan = 0;
    private int visit = 0;
    private String visitPurpose, earnigSourse;
    private int loanFromAkhuwat = 0;
    private int dependent = 1;

    private String dependents = "1";
    private int durationOfStay;

    private RotateLoading loading;
    MainActivity context;

    private String completeAddress;
    private UserDocumentsObject documents;
    private View llVisitPurpose;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;
    }

    public ProfileStep3Fragment() {
        // Required empty public constructor
    }

    public ProfileStep3Fragment(MainProfileFragment main) {
        this.main = main;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step3, container, false);


        spDependent = view.findViewById(R.id.sp_dependent);

        spEarnig = view.findViewById(R.id.sp_earning_source);
        spAbrod = view.findViewById(R.id.sp_abroad);
        spAbrosPurpose = view.findViewById(R.id.sp_abroad_pur);

        llVisitPurpose = view.findViewById(R.id.visit_purpose);

        spCloseLoan = view.findViewById(R.id.sp_open_close_loan);
        spAkhuwatLoan = view.findViewById(R.id.sp_akhuwat_loan);

        // spDuration = view.findViewById(R.id.sp_stat_duration);


        Button btSave = (Button) view.findViewById(R.id.save_data);
        btSave.setOnClickListener(this);


        TextView tvSkip = view.findViewById(R.id.tv_skip);
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.homeFrag();
            }
        });

        Button btnBack = view.findViewById(R.id.step2_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                main.viewPager.setCurrentItem(main.viewPager.getCurrentItem() - 1);
            }
        });

        View rlCnicBack = view.findViewById(R.id.rl_cnic_back);
        rlCnicBack.setOnClickListener(this);
        logoCnicBack = view.findViewById(R.id.logo2);
        logoCnicBack.setOnClickListener(this);

        View rlCnicFront = view.findViewById(R.id.rl_cnic_front);
        rlCnicFront.setOnClickListener(this);
        logoCnicFront = view.findViewById(R.id.logo);
        logoCnicFront.setOnClickListener(this);

        View rlSalary = view.findViewById(R.id.rl_salary);
        rlSalary.setOnClickListener(this);
        logoSalary = view.findViewById(R.id.logo4);
        logoSalary.setOnClickListener(this);

        View rlUtility = view.findViewById(R.id.rl_utility);
        rlUtility.setOnClickListener(this);
        logoUtility = view.findViewById(R.id.logo3);
        logoUtility.setOnClickListener(this);

        tvCnicBack = view.findViewById(R.id.documentation_cnic_back);
        tvCnicBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (backBitmap != null) {

                    openImagePopup(null, backBitmap);
                } else if (documents != null && documents.cnicBackURL != null) {

                    openImagePopup(documents.cnicBackURL, null);
                }
            }
        });

        tvCnicFront = view.findViewById(R.id.documentation_cinc_front);
        tvCnicFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (frontBitmap != null) {

                    openImagePopup(null, frontBitmap);
                } else if (documents != null && documents.cnicFrontURL != null) {

                    openImagePopup(documents.cnicFrontURL, null);
                }
            }
        });

        tvSalary = view.findViewById(R.id.documentation_salary);
        tvSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (salaryBitmap != null) {

                    openImagePopup(null, salaryBitmap);
                } else if (documents != null && documents.salaryURL != null) {

                    openImagePopup(documents.salaryURL, null);
                }
            }
        });

        tvUtility = view.findViewById(R.id.documentation_utility);
        tvUtility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (utilityBitmap != null) {

                    openImagePopup(null, utilityBitmap);
                } else if (documents != null && documents.utilityURL != null) {

                    openImagePopup(documents.utilityURL, null);
                }
            }
        });

        return view;
    }

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


                documents.otherURL = main.userProfile.other;
            }
            if (main.userProfile.dependents != null) {
                dependent = Integer.parseInt(main.userProfile.dependents);
            }
            if (main.userProfile.activeLoan != null) {

                activeLoan = (Integer.parseInt(main.userProfile.activeLoan));
            }

            if (main.userProfile.previousLoan != null) {

                loanFromAkhuwat = (Integer.parseInt(main.userProfile.previousLoan));
            }

            setCnicCard();
            setUtilityBillCard();
            setSalarySlipCard();
        }

        setDependentSpinner();
        // setDurationSpinner();


        setOpenCloseLoan();
        setAkhuwatLoan();

        setAbroadPurposeSp();
        setEarningSp();
        setAbroadSp();
    }

    private void setDurationSpinner() {
//        List<String> names = new ArrayList<>();
//
//        names.add("1 year");
//        names.add("2 year");
//        names.add("3 year");
//        names.add("4 year");
//        names.add("5 year");
//        names.add("6 year");
//        names.add("7 year");
//
//        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
//                R.layout.custom_spinner_text, names);
//        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spDuration.setAdapter(adp1);
//
//        spDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                durationOfStay = position + 1;
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    private void setDependentSpinner() {

        List<String> names = new ArrayList<>();

        names.add("1");
        names.add("2");
        names.add("3");
        names.add("4");
        names.add("5");
        names.add("6");
        names.add("7");


        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_text, names);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDependent.setAdapter(adp1);
        spDependent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dependents = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        int index = 0;
        for (int i = 0; i < names.size(); i++) {

            if (String.valueOf(dependent).equals(names.get(i))) {
                index = i;
                break;
            }
        }

        spDependent.setSelection(index);
    }

    private void setOpenCloseLoan() {

        List<String> names = new ArrayList<>();

        names.add("No");
        names.add("Yes");


        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_text, names);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCloseLoan.setAdapter(adp1);
        spCloseLoan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                activeLoan = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCloseLoan.setSelection(activeLoan);
    }

    private void setEarningSp() {

        final List<String> names = new ArrayList<>();

        names.add("Govt. Employee");
        names.add("Pvt. Employee");
        names.add("Pvt. Owned Business/Services");
        names.add("Farmer");
        names.add("Un-employed");


        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_text, names);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEarnig.setAdapter(adp1);
        spEarnig.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                earnigSourse = names.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setAbroadSp() {

        List<String> names = new ArrayList<>();

        names.add("No");
        names.add("Yes");


        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_text, names);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAbrod.setAdapter(adp1);
        spAbrod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                visit = position;
                if (position == 1) {

                    llVisitPurpose.setVisibility(View.VISIBLE);
                } else {
                    visitPurpose = null;
                    llVisitPurpose.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setAbroadPurposeSp() {

        final List<String> names = new ArrayList<>();

        names.add("Study");
        names.add("Hajj/Umra/Ziarat");
        names.add("Work/Job");
        names.add("Visit");


        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_text, names);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAbrosPurpose.setAdapter(adp1);
        spAbrosPurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                visitPurpose = names.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setAkhuwatLoan() {

        List<String> names = new ArrayList<>();

        names.add("No");
        names.add("Yes");


        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_text, names);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAkhuwatLoan.setAdapter(adp1);
        spAkhuwatLoan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loanFromAkhuwat = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spAkhuwatLoan.setSelection(loanFromAkhuwat);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.documentation_cinc_front:

                openDocumentDialog();

                break;
            case R.id.logo:
                if (front64 != null || documents.cnicFrontURL != null) {
                    AlertUtils.showAlertYesNo(context,
                            "are you sure you want to delete this document.?",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    front64 = null;
                                    documents.cnicFrontURL = null;
                                    documents.cnicFront = null;

                                    setCnicCard();
                                }
                            });
                } else {
                    docType = AppConstantsUtils.CameraCodeConstantUtils.CNIC_FRONT;
                    dispatchTakePictureIntent();
                }

                break;

            case R.id.logo2:
                if (back64 != null || documents.cnicBackURL != null) {
                    AlertUtils.showAlertYesNo(context,
                            "are you sure you want to delete this document.?",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    back64 = null;
                                    documents.cnicBackURL = null;
                                    documents.cnicBack = null;

                                    setCnicCard();
                                }
                            });
                } else {
                    docType = AppConstantsUtils.CameraCodeConstantUtils.CNIC_BACK;
                    dispatchTakePictureIntent();
                }

                break;

            case R.id.logo3:
                if (utility64 != null || documents.utilityURL != null) {

                    AlertUtils.showAlertYesNo(context,
                            "are you sure you want to delete this document.?",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    utility64 = null;
                                    documents.utilityURL = null;
                                    documents.utility = null;

                                    setUtilityBillCard();
                                }
                            });
                } else {
                    docType = AppConstantsUtils.CameraCodeConstantUtils.UTILITY_BILL;
                    dispatchTakePictureIntent();
                }

                break;

            case R.id.logo4:
                docType = AppConstantsUtils.CameraCodeConstantUtils.SALARY_SLIP;
                dispatchTakePictureIntent();
                break;

            case R.id.save_data:
                if (isValidFields()) {

                    createUser();
                    break;
                }


                break;


        }
    }


    private boolean isValidFields() {


        return true;
    }


    private void createUser() {


        saveContent();

        ProfileCreateRequestInfo createProfile = new ProfileCreateRequestInfo();
        createProfile.setName(main.userProfile.name);
        createProfile.setParentage(main.userProfile.parentage);
        createProfile.setDob(main.userProfile.dob);
        createProfile.setGender(main.userProfile.gender);
        createProfile.setCnic(main.userProfile.cnic);

        createProfile.setLongitude(main.userProfile.latitude);
        createProfile.setLatitude(main.userProfile.longitude);

        createProfile.setParmentaddress(main.userProfile.parmentAddress);
        createProfile.setDurationofstay(main.userProfile.durationOfStay);
        createProfile.setCity(Integer.parseInt(main.userProfile.city));

        createProfile.setAccountnumber(main.userProfile.accountNumber);
        createProfile.setBankid(1);

        ProfileCreateRequestInfo createProfile3 = new ProfileCreateRequestInfo();
        createProfile3.setProfession(main.userProfile.profession);
        createProfile3.setDependents(main.userProfile.dependents);
        createProfile3.setIncometo(main.userProfile.monthlyIncome);
        createProfile3.activeLoan = activeLoan + "";
        createProfile3.previouseLoan = loanFromAkhuwat + "";
        createProfile3.visitAbroad = visit + "";
        createProfile3.visitPurpose = visitPurpose;
        createProfile3.earnigSourse = earnigSourse;
        createProfile3.stage = "3";


        ProfileCreateRequestInfo createProfile4 = new ProfileCreateRequestInfo();

        createProfile4.setCnicback(documents.cnicBack);
        createProfile4.setCnicfront(documents.cnicFront);
        createProfile4.setUtilitybill(documents.utility);

//        if (documents.cnicBack != null) {
//            createProfile4.setCnicback(documents.cnicBack);
//        } else if (documents.cnicBackURL != null) {
//
//            Bitmap bitmap = AppUtils.getBitmap(documents.cnicBackURL);
//            if (bitmap != null) {
//                createProfile4.setCnicback(AppUtils.encodeImageBase64(bitmap));
//            }
//        }
//
//        if (documents.cnicFront != null) {
//            createProfile4.setCnicfront(documents.cnicFront);
//        } else if (documents.cnicFrontURL != null) {
//
//            Bitmap bitmap = AppUtils.getBitmap(documents.cnicFrontURL);
//            if (bitmap != null) {
//                createProfile4.setCnicfront(AppUtils.encodeImageBase64(bitmap));
//            }
//        }
//        if (documents.utility != null) {
//            createProfile4.setUtilitybill(documents.utility);
//        } else if (documents.cnicBackURL != null) {
//
//            Bitmap bitmap = AppUtils.getBitmap(documents.utilityURL);
//            if (bitmap != null) {
//                createProfile4.setUtilitybill(AppUtils.encodeImageBase64(bitmap));
//            }
//        }


        //createProfile4.setOther(documents.other);
        createProfile4.stage = "4";

        submitUserInformation(createProfile3, createProfile4);


//        createProfile.setImage(main.userProfile.image);
//        createProfile.setIsUpdate(main.isUpdate);
//        createProfile.issueDate = main.userProfile.issueDate;
//        createProfile.expiryDate = main.userProfile.expiryDate;
    }


    private void saveContent() {

        main.userProfile.dependents = dependents;
        main.userProfile.durationOfStay = "" + durationOfStay;

        main.userProfile.cnicBack = back64;
        main.userProfile.cnicFront = front64;
        main.userProfile.utilityBill = utility64;

    }


    private void openDocumentDialog() {

        ApplicationDocumentDialogue dfrag = new ApplicationDocumentDialogue(context, documents);
        dfrag.show(getFragmentManager(),
                ApplicationDocumentDialogue.class.getName());


        dfrag.setOnDocumentSubmit(this);
    }

    private void openImagePopup(String path, Bitmap bitmap) {

        ImagePopupDialog imagePopupDialog = new ImagePopupDialog(context, path, bitmap);
        imagePopupDialog.show(getFragmentManager(), ImagePopupDialog.class.getName());
    }

    @Override
    public void onDocSubmit(UserDocumentsObject documentsObject) {

        main.userProfile.cnicBack = documentsObject.cnicBack;
        main.userProfile.cnicFront = documentsObject.cnicFront;
        main.userProfile.utilityBill = documentsObject.utility;
        main.userProfile.other = documentsObject.other;

        documents = documentsObject;

    }

    private void submitUserInformation(ProfileCreateRequestInfo createProfile, final ProfileCreateRequestInfo doc) {

        final AlertDialog dialog = AlertUtils.showLoader(getActivity());

        UserBusiness userBusiness = new UserBusiness();
        userBusiness.setProfile(createProfile, new ResponseCallBack<ProfileCreateResponseInfo>() {
            @Override
            public void onSuccess(ProfileCreateResponseInfo body) {
                dialog.dismiss();

                submitUserDocuments(doc);

            }

            @Override
            public void onFailure(String message) {
                dialog.dismiss();

                AlertUtils.showAlert(getContext(), message);
            }
        });
    }

    private void submitUserDocuments(ProfileCreateRequestInfo createProfile) {

        if (createProfile.getCnicback() != null
                || createProfile.getCnicfront() != null || createProfile.getUtilitybill() != null) {
            final AlertDialog dialog = AlertUtils.showLoader(getActivity());

            UserBusiness userBusiness = new UserBusiness();
            userBusiness.setProfile(createProfile, new ResponseCallBack<ProfileCreateResponseInfo>() {
                @Override
                public void onSuccess(ProfileCreateResponseInfo body) {
                    dialog.dismiss();

                    AlertUtils.showAlert(getContext(), "Profile Created Successfully",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    context.homeFrag();
                                }
                            });

                }

                @Override
                public void onFailure(String message) {
                    dialog.dismiss();

                    AlertUtils.showAlert(getContext(), message);
                }
            });
        } else {

            AlertUtils.showAlert(getContext(), "Profile Created Successfully",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            context.homeFrag();
                        }
                    });
        }

    }

    private void updateUserProfile(ProfileCreateRequestInfo createProfile) {

        final AlertDialog dialog = AlertUtils.showLoader(getActivity());

        UserBusiness userBusiness = new UserBusiness();
        userBusiness.createProfile(createProfile, new ResponseCallBack<ProfileCreateResponseInfo>() {
            @Override
            public void onSuccess(ProfileCreateResponseInfo body) {
                dialog.dismiss();

                AlertUtils.showAlert(getContext(), "Profile Edited Successfully",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                context.homeFrag();
                            }
                        });


            }

            @Override
            public void onFailure(String message) {
                dialog.dismiss();

                AlertUtils.showAlert(getContext(), message);
            }
        });
    }

    private void setUtilityBillCard() {


        docType = AppConstantsUtils.CameraCodeConstantUtils.UTILITY_BILL;

        if (utilityBitmap != null) {
            logoUtility.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_delete));
            tvUtility.setText("utility_bill.jpg");

        } else if (documents.utilityURL != null) {
            logoUtility.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_delete));
            tvUtility.setText("utility_bill.jpg");
        } else {
            logoUtility.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_doc_add));
            tvUtility.setText("Utility Bill");
        }

    }

    private void setSalarySlipCard() {


        docType = AppConstantsUtils.CameraCodeConstantUtils.SALARY_SLIP;

        if (salaryBitmap != null) {

            tvSalary.setText("salary_slip.jpg");
        } else {
            tvSalary.setText("Salary Slip");
        }

    }

    private void setCnicCard() {


        if (frontBitmap != null) {

            logoCnicFront.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_delete));
            tvCnicFront.setText("cnic_front.jpg");

        } else if (documents.cnicFrontURL != null) {
            logoCnicFront.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_delete));
            tvCnicFront.setText("cnic_front.jpg");

        } else {
            logoCnicFront.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_doc_add));
            tvCnicFront.setText("CNIC Front");
        }

        if (backBitmap != null) {

            logoCnicBack.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_delete));
            tvCnicBack.setText("cnic_back.jpg");

        } else if (documents.cnicBackURL != null) {
            logoCnicBack.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_delete));
            tvCnicBack.setText("cnic_back.jpg");

        } else {
            logoCnicBack.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_doc_add));
            tvCnicBack.setText("CNIC Back");
        }

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
//            if (documents == null) {
//                documents = new UserDocumentsObject();
//            }
//            assert bitmap != null;
//            if (docType == AppConstantsUtils.CameraCodeConstantUtils.SALARY_SLIP) {
//
//                salaryBitmap = bitmap;
//                salary64 = AppUtils.encodeImageBase64(bitmap);
//                tvSalary.setText("salary)slip.jpg");
//                documents.salaryB = bitmap;
//                documents.salary = salary64;
//
//            } else if (docType == AppConstantsUtils.CameraCodeConstantUtils.CNIC_FRONT) {
//
//                frontBitmap = bitmap;
//                front64 = AppUtils.encodeImageBase64(bitmap);
//                tvCnicFront.setText("cnic_front.jpg");
//                documents.cnicFrontB = bitmap;
//                documents.cnicFront = front64;
//
//                setCnicCard();
//
//            } else if (docType == AppConstantsUtils.CameraCodeConstantUtils.CNIC_BACK) {
//
//                backBitmap = bitmap;
//                back64 = AppUtils.encodeImageBase64(bitmap);
//                tvCnicBack.setText("cnic_back.jpg");
//                documents.cnicBackB = bitmap;
//                documents.cnicBack = back64;
//
//                setCnicCard();
//
//            } else if (docType == AppConstantsUtils.CameraCodeConstantUtils.UTILITY_BILL) {
//
//                utilityBitmap = bitmap;
//                utility64 = AppUtils.encodeImageBase64(bitmap);
//                tvUtility.setText("utility_bill.jpg");
//                documents.utilityB = bitmap;
//                documents.utility = utility64;
//
//                setUtilityBillCard();
//
//            }
//
//        } else if (resultCode == Crop.RESULT_ERROR) {
//            Toast.makeText(context, Crop.getError(data).getMessage(),
//                    Toast.LENGTH_SHORT).show();
//        }
    }


}