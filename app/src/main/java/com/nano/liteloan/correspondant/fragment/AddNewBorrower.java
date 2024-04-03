package com.nano.liteloan.correspondant.fragment;

import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.nano.liteloan.R;
import com.nano.liteloan.business.PostResponseRequest;
import com.nano.liteloan.business.impl.BusinessCorrespondant;
import com.nano.liteloan.correspondant.correspondantdialoguefragment.BorrowerInterestedFragment;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.BorrowerList;
import com.nano.liteloan.info.GetBorrowerList;
import com.nano.liteloan.info.responce.RecoveryPayResponse;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;

import java.util.ArrayList;
import java.util.List;

public class AddNewBorrower extends Fragment {

    private EditText edName;
    private EditText phoneno, edSanctionno, edAddress;
    private EditText edCnic;
    private RelativeLayout btSave;
    private MainActivity context;
    private BorrowerList borrowerList;
    private RelativeLayout rlCall;
    private Spinner spResponse;
    private String value;
    private Button btAddResponse;
    private List<String> responselist;
    private ImageView ivBack;


    private String response = "";

    public AddNewBorrower(MainActivity mainActivity, BorrowerList borrowerList, List<String> responselist) {
        this.context = mainActivity;
        this.borrowerList = borrowerList;
        this.value = value;
        this.responselist = responselist;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add__new__borrower, container, false);

        edCnic = view.findViewById(R.id.ed_cnic);
        edName = view.findViewById(R.id.name);
        phoneno = view.findViewById(R.id.phone);
        edAddress = view.findViewById(R.id.address);

        ImageView image = view.findViewById(R.id.back);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.getSupportFragmentManager().popBackStackImmediate();
            }
        });
        btAddResponse = view.findViewById(R.id.addresponse);
        btAddResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AllFieldValid()) {
                    SubmitData();
                }
            }
        });


        rlCall = view.findViewById(R.id.rlCall);
        rlCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dail = new Intent(Intent.ACTION_DIAL);
                dail.setData(Uri.parse("tel:" + phoneno.getText().toString()));
                context.startActivity(dail);
            }
        });

        spResponse = view.findViewById(R.id.response);

        edSanctionno = view.findViewById(R.id.sanctionno);

        if (borrowerList != null) {
            if (borrowerList.name != null && !borrowerList.name.equalsIgnoreCase("")) {
                edName.setText(borrowerList.name);
            }
            if (borrowerList.cnic != null && !borrowerList.cnic.equalsIgnoreCase("")) {
                edCnic.setText(borrowerList.cnic);
            }
            if (borrowerList.sanctionno != null && !borrowerList.sanctionno.equalsIgnoreCase("")) {
                edSanctionno.setText(String.valueOf(borrowerList.sanctionno));
            }
            if (borrowerList.phoneno != null && !borrowerList.phoneno.equalsIgnoreCase("")) {
                phoneno.setText(String.valueOf(borrowerList.phoneno));
            }


        }


//        btSave = view.findViewById(R.id.save);
//        btSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(validateData()){
//                    SubmitData();
//                }
//            }
//        });

        setMarital("");

        return view;
    }

    private boolean AllFieldValid() {
        if (phoneno.getText().toString() == null || phoneno.getText().toString().equalsIgnoreCase("")) {
            phoneno.setError("Please enter phone no");
            return false;
        }
        return true;
    }

    private void setMarital(String value) {

        final List<String> names = new ArrayList<>();

        if (responselist != null && responselist.size() > 0) {
            response = responselist.get(0).toString();
            for (int i = 0; i < responselist.size(); i++) {
                names.add(responselist.get(i));
            }
        } else {
            names.add("Not Responding");
            names.add("Invalid Number");
            names.add("Interested for Loan");
            names.add("Not Interested");

        }


        ArrayAdapter<String> adp1 = new ArrayAdapter<>(context,
                R.layout.custom_spinner_text, names);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spResponse.setAdapter(adp1);
        if (!value.equals("")) {
            int spinnerPosition = adp1.getPosition(value);
            spResponse.setSelection(spinnerPosition);
        }

        spResponse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                response = names.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void SubmitData() {

        PostResponseRequest request = new PostResponseRequest();
        request.setResponse(response);


//        if(edAddress.getText().toString().equalsIgnoreCase("")){
//            edAddress.setError("Please enter address");
//            edAddress.requestFocus();
//            return;
//        } else {
//            request.setAddress(edAddress.getText().toString());
//        }


        if (phoneno.getText().toString().substring(0, 2).equalsIgnoreCase("03")) {
            request.setPhone("92" + phoneno.getText().toString().substring(1));

        } else {
            request.setPhone(phoneno.getText().toString());
        }
        request.setUser_d(borrowerList.borrowerid);

        final androidx.appcompat.app.AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }


        BusinessCorrespondant userBusiness = new BusinessCorrespondant();
        userBusiness.postResponse(request, new ResponseCallBack<RecoveryPayResponse>() {
            @Override
            public void onSuccess(RecoveryPayResponse body) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                removeBorrower();

            }

            @Override
            public void onFailure(String message) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                AlertUtils.showAlert(context, message);
            }
        });

    }

    private void removeBorrower() {
        if (PreferenceUtils.getInstance().getPotentialBorrower() != null) {
            ArrayList<BorrowerList> borrowerNew = new ArrayList<>();
            ArrayList<String> strNew = new ArrayList<>();
            for (BorrowerList borrower : PreferenceUtils.getInstance().getPotentialBorrower().borrowerList) {
                if (borrower.borrowerid != borrowerList.borrowerid) {
                    borrowerNew.add(borrower);
                    strNew.add("view");
                }
            }

            GetBorrowerList boResponse = new GetBorrowerList();
            boResponse.count = borrowerNew.size();
            boResponse.borrowerList = borrowerNew;
            boResponse.responselist = strNew;

            PreferenceUtils.getInstance().addPotentialBorrower(boResponse);
        }

        BorrowerInterestedFragment pinSucessMessage = new BorrowerInterestedFragment(context);
        pinSucessMessage.show(context.getSupportFragmentManager(),
                BorrowerInterestedFragment.class.getName());
    }

    private boolean validateData() {
        if (edCnic.getText().toString().equalsIgnoreCase("")
                || edCnic.getText().toString().equalsIgnoreCase("0")) {
            edCnic.setError("Please enter valid CNIC");
            edCnic.requestFocus();
            return false;
        }

        if (phoneno.getText().toString().equalsIgnoreCase("")
                || phoneno.getText().toString().equalsIgnoreCase("0")
                || phoneno.getText().toString().length() < 11) {
            phoneno.setError("Please enter valid Phone number");
            phoneno.requestFocus();
            return false;
        }

        if (edName.getText().toString().equalsIgnoreCase("")
                || edName.getText().toString().equalsIgnoreCase("0")) {
            edName.setError("Please enter valid name");
            edName.requestFocus();
            return false;
        }


        return true;
    }


}