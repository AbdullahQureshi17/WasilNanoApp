package com.nano.liteloan.presentation.fragment.businesscorrespondance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.nano.liteloan.R;
import com.nano.liteloan.info.LoanInfoRequest;
import com.nano.liteloan.info.SearchBorrower;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.PreferenceUtils;
import com.santalu.maskedittext.MaskEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class UserDetailFragment extends Fragment {

    private EditText edName, edLoanAmouont, edFee, edDuedate, edPhoneno;
    private MaskEditText edCnic;
    private String cnic;
    private ImageView ivBack;
    private MainActivity context;
    private EditText edParentage;
    private SearchBorrower searchBorrower;
    private Spinner spDueDate;


    Button btAddLoan;

    public UserDetailFragment() {
    }

    public UserDetailFragment(String cnic, MainActivity mainActivity) {
        this.cnic = cnic;
        this.context = mainActivity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);

        if (this.getArguments() != null
                && this.getArguments().getSerializable("searchBorrower") != null) {
            searchBorrower = (SearchBorrower) this.getArguments()
                    .getSerializable("searchBorrower");
        }
        spDueDate = view.findViewById(R.id.spDuedate);

        edPhoneno = view.findViewById(R.id.phoneno);
        edParentage = view.findViewById(R.id.ed_parentage);
        edCnic = view.findViewById(R.id.ed_cnic);
        edDuedate = view.findViewById(R.id.ed_duedate);
        edFee = view.findViewById(R.id.ed_fee);
        edLoanAmouont = view.findViewById(R.id.ed_loanAmount);
        edName = view.findViewById(R.id.ed_name);

        setContent();
        try {
            setDueDate(15);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        setdueDate();

        btAddLoan = view.findViewById(R.id.addUser);
        btAddLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAllFields();
            }
        });

        ivBack = view.findViewById(R.id.back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.onBackPressed();
            }
        });

        return view;
    }

    private void setdueDate() {

        List<String> names = new ArrayList<>();

        if (searchBorrower.user.loanduration != null) {
            for (int i = 0; i < searchBorrower.user.loanduration.size(); i++) {
                names.add(searchBorrower.user.loanduration.get(i));
            }
        }

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, names);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDueDate.setAdapter(adp1);

        spDueDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                try {
                    setDueDate(Integer.parseInt(adapterView.getItemAtPosition(i).toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }




    private void setContent() {

        edCnic.setFocusable(false);

        if (searchBorrower.user != null && searchBorrower.user.name != null) {
            edName.setText(searchBorrower.user.name);
            edName.setFocusable(false);
        }
        if (searchBorrower.user != null && searchBorrower.user.parentage != null) {
            edParentage.setText(searchBorrower.user.parentage);
            edParentage.setFocusable(false);
        }
        if (searchBorrower.user != null && searchBorrower.user.phoneno != null) {
            edPhoneno.setText(searchBorrower.user.phoneno);

        }


    }

    private boolean validateAllFields() {
        if (edCnic.getText().toString().equalsIgnoreCase("")
                || edCnic.getText().toString().length() < 15) {
            edCnic.setError("Please enter valid cnic");
            return false;

        }
        if (edParentage.getText().toString().equalsIgnoreCase("")
                || edParentage.getText().toString() == null) {
            edParentage.setError("Please enter parentage");
            return false;

        }
        if (edPhoneno.getText().toString().equalsIgnoreCase("")
                || edParentage.getText().toString() == null) {
            edPhoneno.setError("Please enter phone no");
            return false;

        }
        if (edDuedate.getText().toString().equalsIgnoreCase("")
                || edDuedate.getText().toString() == null) {
            edDuedate.setError("Please enter valid duedate");
            return false;
        }
        if (edFee.getText().toString().equalsIgnoreCase("")
                || edFee.getText().toString() == null
                || edFee.getText().toString().equalsIgnoreCase("0")) {
            edFee.setError("Please enter valid fee");
            return false;
        }

        if (edLoanAmouont.getText().toString().equalsIgnoreCase("")
                || edLoanAmouont.getText().toString() == null
                || edLoanAmouont.getText().toString().equalsIgnoreCase("0")
                || Integer.parseInt(edLoanAmouont.getText().toString()) > 10000
                ||  Integer.parseInt(edLoanAmouont.getText().toString()) < 0 ) {
            edLoanAmouont.setError("Please enter valid loan amount less than 10000");
            return false;
        }

        if (edName.getText().toString().equalsIgnoreCase("")
                || edName.getText().toString() == null) {
            edName.setError("Please enter valid name");
            return false;
        }

        LoanInfoRequest loanInfoRequest = new LoanInfoRequest();

        loanInfoRequest.setParentage(edParentage.getText().toString());
        loanInfoRequest.setCnic(edCnic.getText().toString());
        loanInfoRequest.setCorrenpondentid(PreferenceUtils.getInstance().getcorrespondantId());
        loanInfoRequest.setFee(Integer.parseInt(edFee.getText().toString()));
        loanInfoRequest.setName(edName.getText().toString());
        loanInfoRequest.setDuedate(edDuedate.getText().toString());
        loanInfoRequest.setLoanamount(Integer.parseInt(edLoanAmouont.getText().toString()));
        loanInfoRequest.setPhoneno(edPhoneno.getText().toString());

        context.userInfoFragment(loanInfoRequest);

        return true;
    }

    private void setDueDate(int days) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Using today's date
        c.add(Calendar.DATE, days); // Adding 5 days

        String output = sdf.format(c.getTime());

        Date myDate = sdf.parse(output);
        sdf.applyPattern("EEE");
        String sMyDate = sdf.format(myDate);
        if(sMyDate.contains("Sat")){
            SimpleDateFormat sdff = new SimpleDateFormat("dd-MM-yyyy");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdff.parse(output));
            cal.add(Calendar.DATE, 2);  // number of days to add
            output = sdff.format(cal.getTime());

        } else if(sMyDate.contains("Sun")){
            SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
            Calendar cale = Calendar.getInstance();
            cale.setTime(sdformat.parse(output));
            cale.add(Calendar.DATE, 1);  // number of days to add
            output = sdformat.format(cale.getTime());
        }
        edDuedate.setText(output);

        edCnic.setText(cnic);
    }
}