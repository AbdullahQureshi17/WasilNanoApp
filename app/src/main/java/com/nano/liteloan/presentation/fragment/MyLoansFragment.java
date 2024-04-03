package com.nano.liteloan.presentation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.GetApplicationResponseInfo;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.adapters.MyLoanAdapter;
import com.nano.liteloan.presentation.fragment.dialoguefragment.PaymentMethodDialogue;
import com.nano.liteloan.presentation.fragment.dialoguefragment.PinSucessMessage;
import com.nano.liteloan.utils.OnItemClickListener;
import com.nano.liteloan.utils.alerts.AlertUtils;

/**
 * Created by Muhammad Umer on 07/06/2020.
 * Modified by Muhammad Ahmad
 */

public class MyLoansFragment extends Fragment implements View.OnClickListener {

    MainActivity context;
    private RecyclerView recyclerView;
    private TextView tvNoRecode;
    private RelativeLayout btnRepay;


    public MyLoansFragment() {
        // Required empty public constructor
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_loans_, container, false);
        ImageView ivBack = view.findViewById(R.id.back);
        ivBack.setOnClickListener(this);

        tvNoRecode = view.findViewById(R.id.tv_no_recode_found);

        recyclerView = view.findViewById(R.id.customRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        btnRepay = view.findViewById(R.id.btn_repay);


        getApplication();
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            context.onBackPressed();
        }
    }

    private void getApplication() {

        final AlertDialog dialog = AlertUtils.showLoader(getActivity());

        EasyLoanBusiness business = new EasyLoanBusiness();
        business.getApplication(new ResponseCallBack<GetApplicationResponseInfo>() {

            @Override
            public void onSuccess(GetApplicationResponseInfo body) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                if (body != null && body.getApplication
                        != null && body.getApplication.size() > 0) {
                    setLoansRecyclerView(body);

                    tvNoRecode.setVisibility(View.GONE);
                    btnRepay.setVisibility(View.VISIBLE);

                } else {

                    btnRepay.setVisibility(View.GONE);
                    tvNoRecode.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(String message) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                AlertUtils.showAlert(getActivity(), message);
            }
        });
    }

    private void setLoansRecyclerView(final GetApplicationResponseInfo body) {

        final MyLoanAdapter myLoanAdapter = new MyLoanAdapter(context, body.getApplication, false);
        recyclerView.setAdapter(myLoanAdapter);

        myLoanAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                context.paymentPaidFragment(body.getApplication.get(position));
//                PaymentMethodDialogue pinSucessMessage = new PaymentMethodDialogue(context, body.getApplication.get(position));
//                pinSucessMessage.show(context.getSupportFragmentManager(),
//                        PinSucessMessage.class.getName());

            }
        });


        btnRepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myLoanAdapter.clickLoanDetail();
            }
        });
    }
}