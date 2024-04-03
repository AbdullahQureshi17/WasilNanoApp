package com.nano.liteloan.correspondant.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.BusinessCorrespondant;
import com.nano.liteloan.correspondant.correspondantadapter.OverDueListAdapter;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.OTPResponse;
import com.nano.liteloan.info.businesscorrespondant.ApplicationOverDueList;
import com.nano.liteloan.info.businesscorrespondant.CorrespondantLoginAsRequest;
import com.nano.liteloan.info.businesscorrespondant.GetDataResponce;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.OnItemClickListener;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.santalu.maskedittext.MaskEditText;

import java.util.ArrayList;
import java.util.List;


public class OverDueFragment extends Fragment {

    private MainActivity context;
    private MaskEditText edCnic;
    private RecyclerView recyclerView;
    private ImageView ivNoBorrower;

    private LinearLayout llDateDueIn;
    private Spinner spDueIn;
    private ImageView btSearch;
    private List<ApplicationOverDueList> applicationlist;
    private OverDueListAdapter myLoanAdapter;


    public OverDueFragment(MainActivity context) {
        this.context = context;
        this.applicationlist = applicationlist;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_over_due, container, false);
        edCnic = view.findViewById(R.id.ed_cnic);
        ivNoBorrower = view.findViewById(R.id.no_borrower);

        llDateDueIn = view.findViewById(R.id.ll_due_in);

        spDueIn = view.findViewById(R.id.sp_due_in);
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
        setSearchView();
        setDueInSpinner();
        return view;
    }

    private void setDueInSpinner() {

        final List<String> salarylist = new ArrayList<>();

        salarylist.add("No Filter");
        salarylist.add("3 Days");
        salarylist.add("5 Days");
        salarylist.add("10 Days");


        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_text, salarylist);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spDueIn.setAdapter(adp1);
        spDueIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0 && myLoanAdapter != null) {

                    myLoanAdapter.dateFilter(position);

                } else if (myLoanAdapter != null) {

                    myLoanAdapter.dateFilter(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

    private void getData() {

        final androidx.appcompat.app.AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }

        BusinessCorrespondant userBusiness = new BusinessCorrespondant();
        userBusiness.getData("overdue_loans", new ResponseCallBack<GetDataResponce>() {
            @Override
            public void onSuccess(GetDataResponce body) {

                dialog.dismiss();


                setLoansRecyclerView(body.overdueloans.applicationlist);

            }

            @Override
            public void onFailure(String message) {

                dialog.dismiss();


                AlertUtils.showAlert(context, message);
            }
        });

    }


    private void setLoansRecyclerView(List<ApplicationOverDueList> applicationlist) {

        myLoanAdapter = new OverDueListAdapter(context, applicationlist);
        recyclerView.setAdapter(myLoanAdapter);
        myLoanAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CorrespondantLoginAsRequest request = new CorrespondantLoginAsRequest();
                request.userId = String.valueOf(applicationlist.get(position).userid);

                request.phone = applicationlist.get(position).phoneno;
                setOtpLoginAs(request);
            }
        });

        setDueInSpinner();
    }

    private void setOtpLoginAs(CorrespondantLoginAsRequest request) {

        BusinessCorrespondant userBusiness = new BusinessCorrespondant();
        userBusiness.loginAsSomeOne(request, new ResponseCallBack<OTPResponse>() {
            @Override
            public void onSuccess(OTPResponse body) {

                if (body != null && body.userDate != null) {

                    PreferenceUtils.getInstance().setLoginAsActive(true);
                    PreferenceUtils.getInstance().addUserDetail(body.userDate);
                    PreferenceUtils.getInstance().addAccountDetail(body.liteaccount);

                    context.resetMainActivity();
                }
            }

            @Override
            public void onFailure(String message) {

                AlertUtils.showAlert(context, message);
            }
        });
    }

}