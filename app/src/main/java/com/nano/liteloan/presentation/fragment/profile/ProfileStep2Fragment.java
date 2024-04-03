package com.nano.liteloan.presentation.fragment.profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.UserBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.CityInfo;
import com.nano.liteloan.info.ProfileCreateRequestInfo;
import com.nano.liteloan.info.ProfileCreateResponseInfo;
import com.nano.liteloan.info.UserProfile;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.fragment.dialoguefragment.LocationFragment;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.santalu.maskedittext.MaskEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.View.GONE;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class ProfileStep2Fragment extends Fragment implements View.OnClickListener {


    private MainProfileFragment main;
    private EditText edCurrentaddress;
    private EditText durationodstay;
    private EditText issueDate, expiryDate;
    private MaskEditText cnic;
    private MainActivity context;
    EditText edOrganization;

    //    private String salary = "No Earning";
    private EditText edPermanentAddress;
    private Spinner spCity;
    String organizationName = "";

    private Spinner spSalary;
    private int city;
    private String earnigSourse = "Business";
    LinearLayout llOrganizationName;

    private String dependents = "1";
    private double latitude = 0.0, longitude = 0.0;
    private Calendar issueCalender;
    private TextView tvLocationPoints;
    private Spinner spDependent;

    String startDate = "null";
    String expieryDate = "null";

    private Spinner spEarnig;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = (MainActivity) context;
    }

    public ProfileStep2Fragment() {
        // Required empty public constructor
    }

    ProfileStep2Fragment(MainProfileFragment main) {
        this.main = main;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step2, container, false);
        Button btStep2 = view.findViewById(R.id.step2);
        btStep2.setOnClickListener(this);
        spEarnig = view.findViewById(R.id.sp_earning_source);

        spDependent = view.findViewById(R.id.sp_dependent);
        llOrganizationName = view.findViewById(R.id.organizationname);
        edOrganization = view.findViewById(R.id.edorganization);

        tvLocationPoints = view.findViewById(R.id.tv_location_ponits);
        tvLocationPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                } else {
                    tvLocationPoints.setClickable(false);
                    launchLocationFragment();

                    tvLocationPoints.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            tvLocationPoints.setClickable(true);
                        }
                    }, 500);

                }
            }
        });

        spCity = view.findViewById(R.id.sp_city);
//        spSalary = view.findViewById(R.id.sp_salary);

        durationodstay = view.findViewById(R.id.ed_durationoffstay);

        edCurrentaddress = view.findViewById(R.id.current_address);

        edPermanentAddress = view.findViewById(R.id.permanent_address);


        cnic = view.findViewById(R.id.ed_cnic);

        issueDate = view.findViewById(R.id.ed_issue_date);
        issueDate.setFocusable(false);
        issueDate.setClickable(true);
        issueDate.setOnClickListener(this);

        expiryDate = view.findViewById(R.id.ed_expiry_date);
        expiryDate.setFocusable(false);
        expiryDate.setClickable(true);
        expiryDate.setOnClickListener(this);

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

        setDependentSpinner();
        setEarningSp();

        return view;
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

        if (main.userProfile != null && main.userProfile.sourceOfEarning != null) {

            int index = names.indexOf(main.userProfile.sourceOfEarning);
            if (index >= 0) {
                spEarnig.setSelection(index);
            }

        }

    }

    private void setDependentSpinner() {

        List<String> names = new ArrayList<>();

        names.add("0");
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

        if (main.userProfile != null && main.userProfile.dependents != null) {

            int index = names.indexOf(main.userProfile.dependents);
            if (index >= 0) {
                spDependent.setSelection(index);
            }

        }


    }

    @SuppressLint("SetTextI18n")
    private void launchLocationFragment() {
        LocationFragment dialogFragment = new LocationFragment("0" , "0");
        dialogFragment.show(context.getSupportFragmentManager(),
                LocationFragment.class.getName());

        dialogFragment.setOnLocationSaved(new LocationFragment.OnLocationSaved() {

            @Override
            public void onSave(double lat, double longi) {

                latitude = lat;
                longitude = longi;


                tvLocationPoints.setText("Lat: " + lat + " Long: " + longi);
                tvLocationPoints.setClickable(true);

            }
        });
    }

//    private void setSalarySpinner() {
//
//        final List<String> salarylist = new ArrayList<>();
//
//        salarylist.add("No Earning");
//        salarylist.add("Upto 5,000");
//        salarylist.add("5,001-10,000");
//        salarylist.add("10,001-15,000");
//        salarylist.add("15,001-20,000");
//        salarylist.add("20,001-25,000");
//        salarylist.add("25,001-30,000");
//        salarylist.add("30,001-35,000");
//        salarylist.add("35,001-40,000");
//        salarylist.add("Above 40,000");
//
//
//        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
//                R.layout.custom_spinner_text, salarylist);
//        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spSalary.setAdapter(adp1);
//        spSalary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                salary = parent.getItemAtPosition(position).toString();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }

    private void setCitySpinner() {

        List<String> professions = new ArrayList<>();

        final List<CityInfo> cityInfoList = PreferenceUtils.getInstance().getCityCategory();
        for (CityInfo listItemInfo : cityInfoList) {

            professions.add(listItemInfo.name);
        }

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_text, professions);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spCity.setAdapter(adp1);
        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city = cityInfoList.get(position).id;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setContent() {
        if (main.userProfile != null) {
            UserProfile profile = main.userProfile;
            if (profile.currentAddress != null) {
                edCurrentaddress.setText(profile.currentAddress);
            }

            if (profile.parmentAddress != null) {
                edPermanentAddress.setText(profile.parmentAddress);
            }
            if (profile.cnic != null) {
                cnic.setText(profile.cnic);
            }
            if (profile.issueDate != null) {

                issueDate.setText(profile.issueDate);
                startDate = profile.issueDate;
            }

            if (profile.expiryDate != null) {

                expiryDate.setText(profile.expiryDate);
                expieryDate = profile.expiryDate;
            }

            if (profile.durationOfStay != null) {

                durationodstay.setText(profile.durationOfStay);
            }
            if (profile.latitude != null && !profile.latitude.isEmpty()
                    && profile.longitude != null && !profile.longitude.isEmpty()) {

                longitude = Double.parseDouble(profile.longitude);
                latitude = Double.parseDouble(profile.latitude);

                tvLocationPoints.setText("Lat: " + profile.latitude + " Long: " + profile.longitude);
            }


            if (profile.accountNumber != null) {
                // edAccountNumber.setText(profile.accountNumber);
            }
            setCitySpinner();
//            setSalarySpinner();
        }

    }

//    private void setProfessionSpinner() {
//
//        List<String> professions = new ArrayList<>();
//
//        if (main.configurations != null
//                && main.configurations.configList != null
//                && main.configurations.configList.profession != null) {
//
//            for (ListItemInfo listItemInfo : main.configurations.configList.profession) {
//
//                professions.add(listItemInfo.label);
//            }
//        }
//
//
//        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
//                R.layout.custom_spinner_text, professions);
//        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spProfession.setAdapter(adp1);
//
//        spProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                profession = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        if (main != null && main.userProfile != null &&
//                main.userProfile.profession != null) {
//
//            int index = professions.indexOf(main.userProfile.profession);
//
//            if (index >= 0) {
//
//                spProfession.setSelection(index);
//            }
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.step2:

                if (isAllFieldFilled()) {


                    saveContent();


                    break;
                }

                break;

            case R.id.ed_issue_date:
                openDatePicker(issueDate, true , 1);
                break;

            case R.id.ed_expiry_date:

                openDatePicker(expiryDate, false , 2);
                break;

        }
    }

    @SuppressLint("DefaultLocale")
    private void openDatePicker(final TextView tvDate, boolean value , int expiry) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.date_picked_dialog, null);
        dialog.setView(view);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());

        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(true);

        final DatePicker datePicker = view.findViewById(R.id.dg_datePicker);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);

        final Calendar cal = Calendar.getInstance();

        if (value) {

            datePicker.setMaxDate(cal.getTimeInMillis());
        }

        if (issueCalender != null) {

            if (value) {
                datePicker.setMaxDate(issueCalender.getTimeInMillis());
            } else {
                datePicker.setMinDate(issueCalender.getTimeInMillis());
            }
        }


        Button btnCancel = view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

        Button btnOk = view.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(expiry == 1){
                    startDate =
                            "" + datePicker.getYear() + "-" + String.format(
                                    "%02d-%02d",
                                    datePicker.getMonth() + 1, datePicker.getDayOfMonth());

                    issueCalender = Calendar.getInstance();
                    issueCalender.set(Calendar.YEAR, datePicker.getYear());
                    issueCalender.set(Calendar.MONTH, datePicker.getMonth() + 1);
                    issueCalender.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

                    tvDate.setText(datePicker.getDayOfMonth() + "-" + (datePicker.getMonth() + 1) + "-" +
                            datePicker.getYear());
                    dialog.dismiss();
                } else if(expiry == 2){
                    expieryDate =
                            "" + datePicker.getYear() + "-" + String.format(
                                    "%02d-%02d",
                                    datePicker.getMonth() + 1, datePicker.getDayOfMonth());

                    issueCalender = Calendar.getInstance();
                    issueCalender.set(Calendar.YEAR, datePicker.getYear());
                    issueCalender.set(Calendar.MONTH, datePicker.getMonth() + 1);
                    issueCalender.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

                    tvDate.setText(datePicker.getDayOfMonth() + "-" + (datePicker.getMonth() + 1) + "-" +
                            datePicker.getYear());
                    dialog.dismiss();
                }


            }
        });
    }

    private boolean isAllFieldFilled() {

        if (cnic.getText().toString().isEmpty()) {

            cnic.setError("Please enter your CNIC");
            cnic.requestFocus();
            return false;
        }

        if (longitude == 0.0 || latitude == 0.0) {
            tvLocationPoints.setError("Please enter your location points");
            tvLocationPoints.requestFocus();
            return false;
        }

        if (durationodstay.getText().toString().isEmpty()) {

            durationodstay.setError("Please enter your duration of stay");
            durationodstay.requestFocus();
            return false;
        }


        if (issueDate.getText().toString().isEmpty()) {

            issueDate.setError("Please provide your CINC issue date");
            issueDate.requestFocus();
            return false;
        }

        if (expiryDate.getText().toString().isEmpty()) {

            expiryDate.setError("Please provide your CINC expiry date");
            expiryDate.requestFocus();
            return false;
        }
        if (startDate == null) {

            issueDate.setError("Please provide your CINC issue date");
            issueDate.requestFocus();
            return false;
        }

        if (expieryDate == null) {

            expiryDate.setError("Please provide your CINC expiry date");
            expiryDate.requestFocus();
            return false;
        }


        if (edPermanentAddress.getText().toString().isEmpty()) {
            edPermanentAddress.setError("Please provide your current address");
            edPermanentAddress.requestFocus();
            return false;
        }


        if (edCurrentaddress.getText().toString().isEmpty()) {

            edCurrentaddress.setError("Please provide your permanent address");
            edCurrentaddress.requestFocus();
            return false;
        }

        return true;
    }

    private int getItem(int i) {
        return main.viewPager.getCurrentItem() + i;
    }

    private void saveContent() {

        boolean isUpdate = false;

        if (main.userProfile.currentAddress != null &&
                !main.userProfile.currentAddress.equals(edCurrentaddress.getText().toString())) {
            isUpdate = true;
        }
        if (main.userProfile.parmentAddress != null &&
                !main.userProfile.parmentAddress.equals(edPermanentAddress.getText().toString())) {

            isUpdate = true;
        }
        if (main.userProfile.issueDate != null &&
                !main.userProfile.issueDate.equals(issueDate.getText().toString())) {

            isUpdate = true;
        }
        if (main.userProfile.expiryDate != null &&
                !main.userProfile.expiryDate.equals(expiryDate.getText().toString())) {

            isUpdate = true;
        }
        if (main.userProfile.city != null && !main.userProfile.city.equals(city)) {

            isUpdate = true;
        }
        if (main.userProfile.getCnic() != null &&
                !main.userProfile.getCnic().equals(cnic.getText().toString())) {

            isUpdate = true;
        }
//        if (main.userProfile.getMonthlyIncome() != null &&
//                !main.userProfile.getMonthlyIncome().equals(edIncomefrom.getText().toString())) {
//
//            isUpdate = true;
//        }
        String profession = "Student";
        if (main.userProfile.profession != null &&
                !main.userProfile.profession.equals(profession)) {

            isUpdate = true;
        }


        main.userProfile.currentAddress = edCurrentaddress.getText().toString();
        main.userProfile.cnic = cnic.getText().toString();

        main.userProfile.issueDate = startDate;
        main.userProfile.expiryDate = expieryDate;

        main.userProfile.parmentAddress = edPermanentAddress.getText().toString();
        // main.userProfile.accountNumber = edAccountNumber.getText().toString();
        main.userProfile.city = String.valueOf(city);

        main.userProfile.longitude = longitude + "";
        main.userProfile.latitude = latitude + "";


        ProfileCreateRequestInfo createProfileRequest = new ProfileCreateRequestInfo();
        createProfileRequest.setParmentaddress(main.userProfile.parmentAddress);
        createProfileRequest.setCnic(main.userProfile.cnic);
        createProfileRequest.issueDate = main.userProfile.issueDate;
        createProfileRequest.expiryDate = main.userProfile.expiryDate;
        createProfileRequest.currentAddress = main.userProfile.currentAddress;
        createProfileRequest.setLatitude(latitude + "");
        createProfileRequest.setLongitude(longitude + "");
        createProfileRequest.setDependents(dependents);
        createProfileRequest.setEarnigSourse(earnigSourse);

//        createProfileRequest.setIncomefrom(salary);


        createProfileRequest.setCity(city);
        createProfileRequest.setDurationofstay(durationodstay.getText().toString());

        createProfileRequest.stage = "2";

        if (main.userProfile.iseditable == 0) {
            if (isUpdate) {
                if (earnigSourse.equalsIgnoreCase("Govt. Job") || earnigSourse.equalsIgnoreCase("Pvt. Job")) {
                    organizationName = edOrganization.getText().toString();
                    if (organizationName == null || edOrganization.getText().toString().equals("")) {
                        edOrganization.setError("Please enter your organization name");

                    } else {
                        createProfileRequest.setOrganizationName(organizationName);
                        submitUserInformation(createProfileRequest);

                    }
                } else {
                    createProfileRequest.setOrganizationName(organizationName);
                    submitUserInformation(createProfileRequest);

                }


            } else {

                main.viewPager.setCurrentItem(getItem(+1), true);
            }
        } else {
            main.viewPager.setCurrentItem(getItem(+1), true);
        }

    }

    private void submitUserInformation(ProfileCreateRequestInfo createProfile) {

        final AlertDialog dialog = AlertUtils.showLoader(getActivity());

        UserBusiness userBusiness = new UserBusiness();
        userBusiness.setProfile(createProfile, new ResponseCallBack<ProfileCreateResponseInfo>() {
            @Override
            public void onSuccess(ProfileCreateResponseInfo body) {
                dialog.dismiss();
                main.viewPager.setCurrentItem(getItem(+1), true);

            }

            @Override
            public void onFailure(String message) {
                dialog.dismiss();

                AlertUtils.showAlert(getContext(), message);
            }
        });
    }

}