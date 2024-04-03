package com.nano.liteloan.presentation.fragment.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.UserBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.ProfileCreateRequestInfo;
import com.nano.liteloan.info.ProfileCreateResponseInfo;
import com.nano.liteloan.info.UserProfile;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.fragment.dialoguefragment.EvaluationSuccessPopup;
import com.nano.liteloan.utils.alerts.AlertUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

/**
 * Created by Muhammad Ahmad on 07/04/2020.
 */
public class EvaluationFragment extends Fragment implements View.OnClickListener {

    private Spinner spDependent, spCloseLoan,
            spEarnig;
    private UserProfile userProfile;
    private int activeLoan = 0;
    private int visit = 0;
    private String visitPurpose, earnigSourse;
    private String salary = "No Earning";
    String organizationName = "";

    private String dependents = "1";
    private Spinner spSalary;
    private Spinner spPurpose;
    String loanActive = "";
    private MainActivity context;

//    private View llVisitPurpose;
//    private TextView tvLocationPoints;

    private double latitude, longitude;
    LinearLayout banknameLayout, amountLayout;
    EditText edbankname, edAmount, edFincome;
    String loanBankName = "individual";
    String amount = "";
    LinearLayout llOrganizationName;
    EditText edOrganization;
    String purpose = "Utility bills";
    private LinearLayout llPurpose;
    private EditText edPurpose;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;
    }

    public EvaluationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_evaluation, container, false);


        llPurpose = view.findViewById(R.id.purpose);
        edFincome = view.findViewById(R.id.ed_fIncome);
        edPurpose = view.findViewById(R.id.edPurpose);
        spPurpose = view.findViewById(R.id.sp_purpose);
        spDependent = view.findViewById(R.id.sp_dependent);
        edAmount = view.findViewById(R.id.amount);
        edbankname = view.findViewById(R.id.bankname);
        spEarnig = view.findViewById(R.id.sp_earning_source);
//        spAbrod = view.findViewById(R.id.sp_abroad);
//        spAbrosPurpose = view.findViewById(R.id.sp_abroad_pur);
        spSalary = view.findViewById(R.id.sp_salary);

        llOrganizationName = view.findViewById(R.id.organizationname);
        edOrganization = view.findViewById(R.id.edorganization);

        banknameLayout = view.findViewById(R.id.banknamelayout);
        amountLayout = view.findViewById(R.id.amountlayout);

//        llVisitPurpose = view.findViewById(R.id.visit_purpose);

        spCloseLoan = view.findViewById(R.id.sp_open_close_loan);
//        spAkhuwatLoan = view.findViewById(R.id.sp_akhuwat_loan);

//        tvLocationPoints = view.findViewById(R.id.tv_location_ponits);


        Button btSave = view.findViewById(R.id.save_data);
        btSave.setOnClickListener(this);


        TextView tvSkip = view.findViewById(R.id.tv_skip);
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.homeFrag();
            }
        });

//        tvLocationPoints.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
//                } else {
//                    tvLocationPoints.setClickable(false);
//                    launchLocationFragment();
//
//                    tvLocationPoints.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            tvLocationPoints.setClickable(true);
//                        }
//                    }, 500);
//
//                }
//            }
//        });

        getProfile();

        ImageView ivBack = view.findViewById(R.id.back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.homeFrag();
            }
        });

        return view;
    }

//    @SuppressLint("SetTextI18n")
//    private void launchLocationFragment() {
//        LocationFragment dialogFragment = new LocationFragment();
//        dialogFragment.show(context.getSupportFragmentManager(),
//                LocationFragment.class.getName());
//
//        dialogFragment.setOnLocationSaved(new LocationFragment.OnLocationSaved() {
//
//            @Override
//            public void onSave(double lat, double longi) {
//
//                latitude = lat;
//                longitude = longi;
//
//
//                tvLocationPoints.setText("Lat: " + lat + " Long: " + longi);
//                tvLocationPoints.setClickable(true);
//
//            }
//        });
//    }

    private void getProfile() {

        UserBusiness userBusiness = new UserBusiness();
        userBusiness.getUserProfile(new ResponseCallBack<ProfileCreateResponseInfo>() {
            @Override
            public void onSuccess(ProfileCreateResponseInfo body) {

                userProfile = body.userDate;

                setContent();

            }

            @Override
            public void onFailure(String message) {

                setContent();

                AlertUtils.showAlert(context, message);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setContent() {

//        if (userProfile != null
//                && userProfile.latitude != null
//                && userProfile.longitude != null) {
//
//            tvLocationPoints.setText("Lat: " + userProfile.latitude
//                    + " Longitude: " + userProfile.longitude);
//
//            latitude = Double.valueOf(userProfile.latitude);
//            longitude = Double.valueOf(userProfile.longitude);
//        }


        setDependentSpinner();
        setOpenCloseLoan();
//        setAkhuwatLoan();
//        setAbroadPurposeSp();
        setSalarySpinner();
        setEarningSp();
        setPurposeSpinner();
//        setAbroadSp();
//        setMarital();
    }

    private void setPurposeSpinner() {

        final List<String> purposeList = new ArrayList<>();

        purposeList.add("Utility bills");
        purposeList.add("Education");
        purposeList.add("Health");
        purposeList.add("Rent");
        purposeList.add("Food");
        purposeList.add("Other");


        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_text, purposeList);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spPurpose.setAdapter(adp1);
        spPurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                purpose = parent.getItemAtPosition(position).toString();
                if (purpose.equalsIgnoreCase("Other")) {
                    llPurpose.setVisibility(View.VISIBLE);
                } else {
                    llPurpose.setVisibility(GONE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

    private void setDependentSpinner() {

        List<String> names = new ArrayList<>();

        names.add("1");
        names.add("2");
        names.add("3");
        names.add("4");
        names.add("5");
        names.add("6");
        names.add("7");


        ArrayAdapter<String> adp1 = new ArrayAdapter<>(context,
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

        if (userProfile != null && userProfile.dependents != null) {

            int index = names.indexOf(userProfile.dependents);
            if (index >= 0) {
                spDependent.setSelection(index);
            }

        }


    }

    private void setOpenCloseLoan() {

        List<String> names = new ArrayList<>();

        names.add("Yes");
        names.add("No");


        ArrayAdapter<String> adp1 = new ArrayAdapter<>(context,
                R.layout.custom_spinner_text, names);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCloseLoan.setAdapter(adp1);
        spCloseLoan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                activeLoan = position;
                loanActive = parent.getItemAtPosition(position).toString();
                if (loanActive.equalsIgnoreCase("No")
                ) {
                    banknameLayout.setVisibility(GONE);
                    amountLayout.setVisibility(GONE);

                } else if (loanActive.equalsIgnoreCase("Yes")) {
                    banknameLayout.setVisibility(GONE);
                    amountLayout.setVisibility(View.VISIBLE);
                } else {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCloseLoan.setSelection(activeLoan);

        if (userProfile != null && userProfile.activeLoan != null) {

            if (userProfile.activeLoan.equals("0")) {

                spCloseLoan.setSelection(0);
            } else {
                spCloseLoan.setSelection(names.indexOf(userProfile.activeLoan));
            }

        }


    }

    private void setEarningSp() {

        final List<String> names = new ArrayList<>();

        names.add("Business");
        names.add("Govt. Job");
        names.add("Pvt. Job");
        names.add("Farmer");
        names.add("House wife");
        names.add("Retired");
        names.add("Student");
        names.add("Unemployed");


        ArrayAdapter<String> adp1 = new ArrayAdapter<>(context,
                R.layout.custom_spinner_text, names);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEarnig.setAdapter(adp1);
        spEarnig.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                earnigSourse = names.get(position);
                if (names.get(position).equalsIgnoreCase("Govt. Job") || names.get(position).equalsIgnoreCase("Pvt. Job")) {
                    llOrganizationName.setVisibility(View.VISIBLE);

                } else {
                    llOrganizationName.setVisibility(GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (userProfile != null && userProfile.sourceOfEarning != null) {

            int index = names.indexOf(userProfile.sourceOfEarning);
            if (index >= 0) {
                spEarnig.setSelection(index);
            }

        }

    }

//    private void setAbroadSp() {
//
//        List<String> names = new ArrayList<>();
//
//        names.add("No");
//        names.add("Yes");
//
//
//        ArrayAdapter<String> adp1 = new ArrayAdapter<>(context,
//                R.layout.custom_spinner_text, names);
//        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spAbrod.setAdapter(adp1);
//        spAbrod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                visit = position;
//                if (position == 1) {
//
//                    llVisitPurpose.setVisibility(View.VISIBLE);
//                } else {
//                    visitPurpose = null;
//                    llVisitPurpose.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        if (userProfile != null && userProfile.visitAbroad != null) {
//
//            if (userProfile.visitAbroad.equals("0")) {
//
//                spAbrod.setSelection(0);
//            } else {
//                spAbrod.setSelection(1);
//            }
//
//        }
//    }

//    private void setMarital() {
//
//        final List<String> names = new ArrayList<>();
//
//        names.add("Single");
//        names.add("Married");
//        names.add("Divorced");
//        names.add("Widow");
//
//
//        ArrayAdapter<String> adp1 = new ArrayAdapter<>(context,
//                R.layout.custom_spinner_text, names);
//        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spMarital.setAdapter(adp1);
//        spMarital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                marital = names.get(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        if (userProfile != null && userProfile.maritalStatus != null) {
//
//            int index = names.indexOf(userProfile.maritalStatus);
//            if (index >= 0) {
//                spMarital.setSelection(index);
//            }
//
//        }
//
//    }

//    private void setAbroadPurposeSp() {
//
//        final List<String> names = new ArrayList<>();
//
//        names.add("Study");
//        names.add("Hajj/Umrah/Ziarat");
//        names.add("Work/Job");
//        names.add("Visit");
//
//
//        ArrayAdapter<String> adp1 = new ArrayAdapter<>(context,
//                R.layout.custom_spinner_text, names);
//        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spAbrosPurpose.setAdapter(adp1);
//        spAbrosPurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                visitPurpose = names.get(position);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        if (userProfile != null && userProfile.visitPurpose != null) {
//
//            int index = names.indexOf(userProfile.visitPurpose);
//            if (index >= 0) {
//                spAbrosPurpose.setSelection(index);
//            }
//
//        }
//    }

//    private void setAkhuwatLoan() {
//
//        List<String> names = new ArrayList<>();
//
//        names.add("No");
//        names.add("Yes");
//
//
//        ArrayAdapter<String> adp1 = new ArrayAdapter<>(context,
//                R.layout.custom_spinner_text, names);
//        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spAkhuwatLoan.setAdapter(adp1);
//        spAkhuwatLoan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                loanFromAkhuwat = position;
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        spAkhuwatLoan.setSelection(loanFromAkhuwat);
//
//        if (userProfile != null && userProfile.previousLoan != null) {
//
//            if (userProfile.previousLoan.equals("0")) {
//
//                spAkhuwatLoan.setSelection(0);
//            } else {
//                spAkhuwatLoan.setSelection(1);
//            }
//
//        }
//    }


    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.save_data && isValidFields()) {


            createUser();
        }
    }


    private boolean isValidFields() {

        if (loanActive.equalsIgnoreCase("Yes")) {
            if (edAmount.getText().toString().equalsIgnoreCase("") || edAmount.getText().toString().isEmpty()) {
                edAmount.setVisibility(View.VISIBLE);
                edAmount.setError("Please enter loan amount");
                return false;

            }
        } else if (loanActive.equalsIgnoreCase("")) {
            edAmount.setVisibility(GONE);
        }


//        if (loanActive.equalsIgnoreCase("from bank") || loanActive.equalsIgnoreCase("from institution")
//                || loanActive.equalsIgnoreCase("from individual")) {
//
//            if (loanActive.equalsIgnoreCase("from individual")) {
//
//                if (edAmount.getText().toString().equals("") || edAmount.getText().toString().isEmpty()) {
//                    edAmount.setError("Please enter loan amount");
//                    edAmount.requestFocus();
//                    return false;
//                } else {
//                    loanBankName = "individual";
//                    amount = edAmount.getText().toString();
//                }
//            } else {
//                if (edbankname.getText().toString().equals("") || edbankname.getText().toString().isEmpty()
//                        || edAmount.getText().toString().equals("") || edAmount.getText().toString().isEmpty()) {
//                    edbankname.setError("Please enter bank name");
//                    edbankname.requestFocus();
//                    edAmount.setError("Please enter loan amount");
//                    edAmount.requestFocus();
//                    return false;
//                } else {
//                    loanBankName = edbankname.getText().toString();
//                    amount = edAmount.getText().toString();
//                }
//            }
//        }

        if (purpose.equals("Other") && (edPurpose.getText().toString().equals("") || edPurpose.getText().toString() == null)) {

            llPurpose.setVisibility(View.VISIBLE);
            edPurpose.setError("Please enter purpose");
            return false;

        }
        if (edFincome == null || edFincome.getText().toString().equalsIgnoreCase("")
                || edFincome.getText().toString().equalsIgnoreCase("0")) {
            edFincome.setError("Please enter Future Income");

            return false;
        }
        if (edAmount == null || edAmount.getText().toString().equalsIgnoreCase("")
                || edAmount.getText().toString().equalsIgnoreCase("0")) {
            edAmount.setError("Please enter expected Income ");

            return false;
        }


//        if (tvLocationPoints.getText().toString().isEmpty()) {
//
//            tvLocationPoints.setError("Please select your current location.!");
//            tvLocationPoints.requestFocus();
//            return false;
//        }
        return true;
    }

    private boolean validateIndiviualLoan() {
        return true;
    }


    private void createUser() {

        ProfileCreateRequestInfo createProfile3 = new ProfileCreateRequestInfo();
        createProfile3.setDependents(dependents);
        createProfile3.activeLoan = loanActive + "";
        createProfile3.visitAbroad = "";
        if (loanActive.equals("No Loan")) {

            createProfile3.setLoanamount(0);

        } else {

            createProfile3.setLoanamount(Integer.parseInt(edAmount.getText().toString()));
        }

        createProfile3.setLoanbank("");
        createProfile3.visitPurpose = "";
        createProfile3.earnigSourse = earnigSourse;
        createProfile3.fIncome = edFincome.getText().toString();

        createProfile3.setSalarySlip(salary);

//        createProfile3.setLatitude(latitude + "");
//        createProfile3.setLongitude(longitude + "");
        createProfile3.stage = "3";

        if (earnigSourse.equalsIgnoreCase("Govt. Job") || earnigSourse.equalsIgnoreCase("Pvt. Job")) {
            organizationName = edOrganization.getText().toString();
            if (organizationName == null || edOrganization.getText().toString().equals("")) {
                edOrganization.setError("Please enter your organization name");

            } else {
                createProfile3.setOrganizationName(organizationName);
                submitUserInformation(createProfile3);

            }
        } else {
            createProfile3.setOrganizationName("");
            submitUserInformation(createProfile3);
        }


    }

    private void submitUserInformation(ProfileCreateRequestInfo createProfile) {

        final AlertDialog dialog = AlertUtils.showLoader(getActivity());

        UserBusiness userBusiness = new UserBusiness();
        userBusiness.setProfile(createProfile, new ResponseCallBack<ProfileCreateResponseInfo>() {
            @Override
            public void onSuccess(ProfileCreateResponseInfo body) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                EvaluationSuccessPopup popup = new EvaluationSuccessPopup(context);
                popup.show(getChildFragmentManager(), EvaluationSuccessPopup.class.getName());

//                AlertUtils.showAlert(getContext(), "Information added successfully.!",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                                context.homeFrag();
//                            }
//                        });
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