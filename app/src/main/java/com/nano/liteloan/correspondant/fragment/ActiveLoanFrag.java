package com.nano.liteloan.correspondant.fragment;

import android.graphics.Color;
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
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.BusinessCorrespondant;
import com.nano.liteloan.correspondant.correspondantadapter.ActiveLoanListAdapter;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.OTPResponse;
import com.nano.liteloan.info.SortByName;
import com.nano.liteloan.info.Sort_Acesending;
import com.nano.liteloan.info.businesscorrespondant.ApplicationOverDueList;
import com.nano.liteloan.info.businesscorrespondant.CorrespondantLoginAsRequest;
import com.nano.liteloan.info.businesscorrespondant.GetDataResponce;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.OnItemClickListener;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.santalu.maskedittext.MaskEditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ActiveLoanFrag extends Fragment implements View.OnClickListener {

    private MainActivity context;
    private MaskEditText edCnic;
    private LinearLayout llDateDueIn;
    private Spinner spDueIn;
    private RecyclerView recyclerView;
    private ImageView ivNoBorrower;
    private ImageView btSearch;
    private ActiveLoanListAdapter myLoanAdapter;
    private TextView tvWeek, tvTommorrow, tvAll;
    List<ApplicationOverDueList> applicationlist = new ArrayList<>();


    public ActiveLoanFrag(MainActivity context) {
        this.context = context;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_active_loan, container, false);

        tvWeek = view.findViewById(R.id.dueweek);
        tvWeek.setOnClickListener(this);
        tvTommorrow = view.findViewById(R.id.duetomorrow);
        tvTommorrow.setOnClickListener(this);
        tvAll = view.findViewById(R.id.all);
        tvAll.setOnClickListener(this);
        spDueIn = view.findViewById(R.id.sp_due_in);
        llDateDueIn = view.findViewById(R.id.ll_due_in);
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
        setSearchView();
        setDueInSpinner();

        return view;
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

                } else if (myLoanAdapter != null){

                    myLoanAdapter.dateFilter(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getData() {

        final AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }

        BusinessCorrespondant userBusiness = new BusinessCorrespondant();
        userBusiness.getData("active_loans", new ResponseCallBack<GetDataResponce>() {
            @Override
            public void onSuccess(GetDataResponce body) {
                List<ApplicationOverDueList> applicationOverDueLists = new ArrayList<>();
                ApplicationOverDueList applicationOverDueList = new ApplicationOverDueList();
                for (int i = 0; i < body.activeloans.applicationlist.size(); i++) {
                    applicationOverDueList = body.activeloans.applicationlist.get(i);
                    long dif = getDate(body.activeloans.applicationlist.get(i).duedate);
                    if (dif != -100) {
                        applicationOverDueList.recoverydays = dif;

                    }
                    applicationOverDueLists.add(applicationOverDueList);

                }
                Collections.sort(applicationOverDueLists, new SortByName());

                Collections.sort(applicationOverDueLists, new Sort_Acesending());

                List<ApplicationOverDueList> positiveapplist = new ArrayList<>();
                List<ApplicationOverDueList> negativeapplist = new ArrayList<>();

                for (int i = 0; i < applicationOverDueLists.size(); i++) {
                    if (applicationOverDueLists.get(i).recoverydays < 0) {
                        negativeapplist.add(applicationOverDueLists.get(i));
                    } else {
                        positiveapplist.add(applicationOverDueLists.get(i));
                    }
                }
                for (int i = 0; i < negativeapplist.size(); i++) {
                    positiveapplist.add(negativeapplist.get(i));
                }
                applicationlist = positiveapplist;
                setLoansRecyclerView(positiveapplist);


                dialog.dismiss();

            }

            @Override
            public void onFailure(String message) {

                dialog.dismiss();


                AlertUtils.showAlert(context, message);
            }
        });

    }

    private void setLoansRecyclerView(List<ApplicationOverDueList> applicationlist) {

        myLoanAdapter = new ActiveLoanListAdapter(context, applicationlist);
        recyclerView.setAdapter(myLoanAdapter);

        myLoanAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CorrespondantLoginAsRequest request = new CorrespondantLoginAsRequest();
                request.userId = String.valueOf(applicationlist.get(position).userid);

                request.phone = applicationlist.get(position).phoneno;
                setOtpLoginAs(request);
//                context.borrowerRevcoveryFragment(applicationlist.get(position));

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

    private long getDate(String date) {

        try {
            Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(date);
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date2 = null;
            date2 = new SimpleDateFormat("dd-MM-yyyy").parse(dateFormat.format(new Date()));
            long dif = date1.getTime() - date2.getTime();
            return TimeUnit.DAYS.convert(dif, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
            return -100;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.all:

                spDueIn.setSelection(0);
                myLoanAdapter.filter(applicationlist, -2000);

                tvAll.setTextColor(Color.parseColor("#CCA348"));
                tvWeek.setTextColor(Color.parseColor("#FFFFFF"));
                tvTommorrow.setTextColor(Color.parseColor("#FFFFFF"));

                break;

            case R.id.duetomorrow:

                spDueIn.setSelection(0);
                myLoanAdapter.filter(applicationlist, 1);

                tvAll.setTextColor(Color.parseColor("#FFFFFF"));
                tvWeek.setTextColor(Color.parseColor("#FFFFFF"));
                tvTommorrow.setTextColor(Color.parseColor("#CCA348"));
                break;

            case R.id.dueweek:


                spDueIn.setSelection(0);
                myLoanAdapter.filter(applicationlist, 7);
                tvAll.setTextColor(Color.parseColor("#FFFFFF"));
                tvWeek.setTextColor(Color.parseColor("#CCA348"));
                tvTommorrow.setTextColor(Color.parseColor("#FFFFFF"));
                break;


        }
    }

}