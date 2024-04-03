package com.nano.liteloan.correspondant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.correspondant.correspondantadapter.InterestedApplicationAdapter;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.BorrowerList;
import com.nano.liteloan.info.GetBorrowerList;
import com.nano.liteloan.info.businesscorrespondant.PotentialBorrowerRequest;
import com.nano.liteloan.info.businesscorrespondant.PotentialBorrowersList;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.OnPotentialBorrowerClickListener;
import com.nano.liteloan.utils.alerts.AlertUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


public class MyInterestedApplicants extends Fragment {

    private MainActivity context;
    private EditText edCnic;
    private RecyclerView recyclerView;
    private ImageView ivNoBorrower;
    private ImageView btSearch;
    private TextView tvCount;
    private Spinner spMarital;
    private String marital = "CNIC";

    private List<PotentialBorrowersList> borrowerlist;

    public MyInterestedApplicants(MainActivity mainActivity) {
        this.context = mainActivity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_borrower, container, false);

        spMarital = view.findViewById(R.id.sp_search);
        tvCount = view.findViewById(R.id.count);
        edCnic = view.findViewById(R.id.ed_cnic);
        ivNoBorrower = view.findViewById(R.id.no_borrower);
        btSearch = view.findViewById(R.id.search);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edCnic.getText().toString().equalsIgnoreCase("")) {

                    getBorrowerList(edCnic.getText().toString(), marital);
                } else {
                    edCnic.setError("Please add" + marital);
                }

            }
        });
//        getBorrowerList();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        getBorrowerList("", "");
        setMarital("");

        return view;
    }

    private void setMarital(String value) {

        final List<String> names = new ArrayList<>();

        names.add("CNIC");
        names.add("Name");
        names.add("Phone No");


        ArrayAdapter<String> adp1 = new ArrayAdapter<>(context,
                R.layout.spinner_text, names);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarital.setAdapter(adp1);
        if (!value.equals("")) {
            int spinnerPosition = adp1.getPosition(value);
            spMarital.setSelection(spinnerPosition);
        }

        spMarital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (names.get(position).equalsIgnoreCase("CNIC")) {
                    marital = "cnic";
                    edCnic.setText("");
                } else if (names.get(position).equalsIgnoreCase("Name")) {
                    marital = "name";
                    edCnic.setText("");
                } else if (names.get(position).equalsIgnoreCase("Phone No")) {
                    marital = "phone_no";
                    edCnic.setText("");
                }

                edCnic.setHint("Please enter " + marital);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


//    private void getData() {
//
//        final AlertDialog dialog = AlertUtils.showLoader(context);
//
//        if (dialog != null) {
//            dialog.show();
//        }
//
//        BusinessCorrespondant userBusiness = new BusinessCorrespondant();
//
//        userBusiness.getData("my_applicants", new ResponseCallBack<GetDataResponce>() {
//            @Override
//            public void onSuccess(GetDataResponce body) {
//                dialog.dismiss();
//
//                setLoansRecyclerView(body.myApplicant.borrowerlist);
//            }
//
//            @Override
//            public void onFailure(String message) {
//                dialog.dismiss();
//
//
//                AlertUtils.showAlert(context, message);
//            }
//        });
//
//    }
//
//    private void setLoansRecyclerView(List<PotentialBorrowersList> applicationlist) {
//
//        final MyBorrowersAdapter myLoanAdapter = new MyBorrowersAdapter(context, applicationlist);
//        recyclerView.setAdapter(myLoanAdapter);
//
//        myLoanAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//
//                setLoginAs(applicationlist.get(position));
//            }
//        });
//
//
//    }

//    private void setLoginAs(PotentialBorrowersList potentialBorrowersList) {
//
//        CorrespondantLoginAsRequest request = new CorrespondantLoginAsRequest();
//        request.userId = String.valueOf(potentialBorrowersList.userId);
//        BusinessCorrespondant userBusiness = new BusinessCorrespondant();
//
//        userBusiness.loginAsSomeOne(request, new ResponseCallBack<OTPResponse>() {
//            @Override
//            public void onSuccess(OTPResponse body) {
//
//                if (body != null && body.userDate != null) {
//
//                    PreferenceUtils.getInstance().setLoginAsActive(true);
//                    PreferenceUtils.getInstance().addUserDetail(body.userDate);
//                    PreferenceUtils.getInstance().addAccountDetail(body.liteaccount);
//
//                    context.resetMainActivity();
//                }
//            }
//
//            @Override
//            public void onFailure(String message) {
//
//                AlertUtils.showAlert(context, message);
//            }
//        });
//    }

    void getBorrowerList(String value, String search) {

        PotentialBorrowerRequest potentialBorrowerRequest = new PotentialBorrowerRequest();

        if (search.equalsIgnoreCase("phone_no")) {
            if (edCnic.getText().toString().substring(0, 2).equalsIgnoreCase("03")) {
                potentialBorrowerRequest.parameter = ("92" + edCnic.getText().toString().substring(1));

            } else {
                potentialBorrowerRequest.parameter = (edCnic.getText().toString());
            }
        } else {
            potentialBorrowerRequest.parameter = value;
        }

        potentialBorrowerRequest.search = search;
        potentialBorrowerRequest.type = "interested";

        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }

        EasyLoanBusiness business = new EasyLoanBusiness();
        business.getBorrowerList(potentialBorrowerRequest, new ResponseCallBack<GetBorrowerList>() {
            @Override
            public void onSuccess(GetBorrowerList body) {


                dialog.dismiss();
                if (body.borrowerList.size() <= 0) {
                    ivNoBorrower.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    ivNoBorrower.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    setLoansRecyclerView(body);
                }


            }

            @Override
            public void onFailure(String message) {

                dialog.dismiss();
                AlertUtils.showAlert(getActivity(), message);
            }
        });
    }

    private void setLoansRecyclerView(GetBorrowerList body) {

        List<BorrowerList> tempList = new ArrayList<>();


        for (int i = 0; i < body.borrowerList.size(); i ++){
            if (Objects.equals(body.borrowerList.get(i).status, "Added")){
                tempList.add(body.borrowerList.get(i));
                //body.borrowerList.remove(i);
            }
        }
        body.borrowerList = tempList;
        if (body.borrowerList.size() <= 0) {
            ivNoBorrower.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else  {
            ivNoBorrower.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        tvCount.setText(body.borrowerList.size() + " Interested Applicants");

//        for (BorrowerList item : tempList) {
//
//            body.borrowerList.remove(item);
//        }
//
//        body.borrowerList.addAll(0, tempList);

        final InterestedApplicationAdapter myLoanAdapter = new InterestedApplicationAdapter(context, body, 1);
        recyclerView.setAdapter(myLoanAdapter);

        myLoanAdapter.setOnItemClickListener(new OnPotentialBorrowerClickListener() {
            @Override
            public void onItemClick(View view, int position, String value) {
                if (value.equalsIgnoreCase("view")) {

                    //context.setLoanProductFragment(body.borrowerList.get(position));

                    context.setBankDetailFragment(body.borrowerList.get(position));
                    // context.addNewborrowerFragment(body.borrowerList.get(position) , value , body.responselist);
                } else {

                    //context.setLoanProductFragment(body.borrowerList.get(position));

                    context.setBankDetailFragment(body.borrowerList.get(position));

                    //context.borrowerLoginAsFragment(body.borrowerList.get(position));
                }

            }
        });


    }

}