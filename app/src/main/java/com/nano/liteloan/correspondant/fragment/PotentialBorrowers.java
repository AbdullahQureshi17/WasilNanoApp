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
import com.nano.liteloan.correspondant.correspondantadapter.PotentialBorrowerListAdapter;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.BorrowerList;
import com.nano.liteloan.info.GetBorrowerList;
import com.nano.liteloan.info.businesscorrespondant.PotentialBorrowerRequest;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.OnPotentialBorrowerClickListener;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */

public class PotentialBorrowers extends Fragment {

    private MainActivity context;
    private EditText edCnic;
    private RecyclerView recyclerView;
    private ImageView ivNoBorrower;
    private ImageView btSearch;
    private TextView tvCount;
    private Spinner spMarital;
    private String marital = "CNIC";


    public PotentialBorrowers(MainActivity mainActivity) {
        this.context = mainActivity;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_potential_borrowers, container, false);


        spMarital = view.findViewById(R.id.sp_search);
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
        tvCount = view.findViewById(R.id.count);
//        getBorrowerList();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        getBorrowerList("", "");

        setMarital("");

        ImageView ivFresh = view.findViewById(R.id.iv_refresh);
        ivFresh.setOnClickListener(v -> {

            PreferenceUtils.getInstance().addPotentialBorrower(null);

            getBorrowerList("", "");
        });

        return view;
    }

    private void searchUser(String value) {

    }
//    void serachBorrower(String cnic) {
//        final AlertDialog dialog = AlertUtils.showLoader(context);
//
//        if (dialog != null) {
//            dialog.show();
//        }
//
//        SearchBorrowerRequest searchBorrowerRequest = new SearchBorrowerRequest();
//        searchBorrowerRequest.setCnic(cnic);
//
//        EasyLoanBusiness business = new EasyLoanBusiness();
//        business.searchBorrower(searchBorrowerRequest, new ResponseCallBack<SearchBorrower>() {
//            @Override
//            public void onSuccess(SearchBorrower body) {
//
//                dialog.dismiss();
////                setLoansRecyclerView(body);
//
//
//            }
//
//            @Override
//            public void onFailure(String message) {
//
//                dialog.dismiss();
//                AlertUtils.showAlert(getActivity(), message);
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
        potentialBorrowerRequest.type = "";

        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }
        if (PreferenceUtils.getInstance().getPotentialBorrower() == null) {
            EasyLoanBusiness business = new EasyLoanBusiness();
            business.getBorrowerList(potentialBorrowerRequest, new ResponseCallBack<GetBorrowerList>() {
                @Override
                public void onSuccess(GetBorrowerList body) {

                    tvCount.setText(body.count + " Potential Borrowers");
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
        } else {
            dialog.dismiss();
            GetBorrowerList body = PreferenceUtils.getInstance().getPotentialBorrower();
            tvCount.setText(body.count + " Potential Borrowers");
            dialog.dismiss();
            if (body.borrowerList.size() <= 0) {
                ivNoBorrower.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                PreferenceUtils.getInstance().addPotentialBorrower(body);
                ivNoBorrower.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                setLoansRecyclerView(body);
            }
        }

    }

    private void setLoansRecyclerView(GetBorrowerList body) {

        List<BorrowerList> borrowerLists = new ArrayList<>();

        for (BorrowerList borrower : body.borrowerList) {
            if (borrower.status.equals("Not Added")) {
                borrowerLists.add(borrower);
            }
        }

        body.borrowerList = borrowerLists;

        final PotentialBorrowerListAdapter myLoanAdapter = new PotentialBorrowerListAdapter(context, body, 0);
        recyclerView.setAdapter(myLoanAdapter);

        myLoanAdapter.setOnItemClickListener(new OnPotentialBorrowerClickListener() {
            @Override
            public void onItemClick(View view, int position, String value) {
                if (value.equalsIgnoreCase("view")) {
                    context.addNewborrowerFragment(body.borrowerList.get(position), value, body.responselist);
                } else {
                    context.borrowerLoginAsFragment(body.borrowerList.get(position));
                }

            }
        });


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

}