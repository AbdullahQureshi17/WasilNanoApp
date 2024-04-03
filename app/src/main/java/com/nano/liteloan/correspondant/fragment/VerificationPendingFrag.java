package com.nano.liteloan.correspondant.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.BusinessCorrespondant;
import com.nano.liteloan.correspondant.correspondantadapter.VerificationPendingListAdapter;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.businesscorrespondant.GetDataResponce;
import com.nano.liteloan.info.businesscorrespondant.PendingList;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.OnItemClickListener;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.santalu.maskedittext.MaskEditText;

import java.util.List;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */

public class VerificationPendingFrag extends Fragment {

    private MainActivity context;
    private MaskEditText edCnic;
    private RecyclerView recyclerView;
    private ImageView ivNoBorrower;
    private ImageView btSearch;
    private List<PendingList> pendinglist;
    private VerificationPendingListAdapter myLoanAdapter;


    public VerificationPendingFrag(MainActivity mainActivity) {
        this.context = mainActivity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_verification_pending, container, false);

        edCnic = view.findViewById(R.id.ed_cnic);
        ivNoBorrower = view.findViewById(R.id.no_borrower);
        btSearch = view.findViewById(R.id.search);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (myLoanAdapter != null) {
                    myLoanAdapter.getFilter().filter(edCnic.getText().toString());
                }
            }
        });
//        getBorrowerList();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        getData();

        checkLocationPermission();

        setSearchView();

        return view;
    }

    private void getData() {

        final androidx.appcompat.app.AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }

        BusinessCorrespondant userBusiness = new BusinessCorrespondant();
        userBusiness.getData("pending_verifications", new ResponseCallBack<GetDataResponce>() {
            @Override
            public void onSuccess(GetDataResponce body) {


                setLoansRecyclerView(body.pendingverifications.pendinglist);
                if (dialog != null) {
                    dialog.dismiss();
                }
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

    private void checkLocationPermission() {
        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            onGPS();
        }
    }

    private void onGPS() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        final android.app.AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onShow(DialogInterface arg0) {
                alertDialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.Black);
                alertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.Black);
            }
        });
        alertDialog.show();
    }


    private void setLoansRecyclerView(List<PendingList> pendinglist) {

        myLoanAdapter = new VerificationPendingListAdapter(context, pendinglist);
        recyclerView.setAdapter(myLoanAdapter);

        myLoanAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                context.verificationFragment(pendinglist.get(position));

            }
        });


    }

    private void setSearchView() {

        edCnic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (myLoanAdapter != null) {
                    myLoanAdapter.getFilter().filter(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


}