package com.nano.liteloan.presentation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.PaymentInfo;
import com.nano.liteloan.info.responce.GETPaymentResponseInfo;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.adapters.PaymentTransctionAdapter;
import com.nano.liteloan.utils.alerts.AlertUtils;

import java.util.List;


/**
 * Created by Muhammad Umer on 07/06/2020.
 */

public class PaymentTransactionFragment extends Fragment implements View.OnClickListener {


    private RecyclerView recycle;
    MainActivity context;
    TextView noRecord;


    public PaymentTransactionFragment() {
        // Required empty public constructor
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_done, container, false);
        recycle = view.findViewById(R.id.customRecyclerView);
        recycle.setLayoutManager(new LinearLayoutManager(context));
        noRecord = view.findViewById(R.id.tv_no_recode_found);

        ImageView ivBack = view.findViewById(R.id.back);
        ivBack.setOnClickListener(this);

        getPayments();

        return view;
    }

    private void setPaymentDoneRecyclerView(List<PaymentInfo> list) {
        PaymentTransctionAdapter paymentPaid = new PaymentTransctionAdapter(context, list);
        recycle.setAdapter(paymentPaid);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            context.onBackPressed();
        }
    }

    private void getPayments() {

        final AlertDialog dialog = AlertUtils.showLoader(getActivity());

        EasyLoanBusiness business = new EasyLoanBusiness();
        business.getPayments(new ResponseCallBack<GETPaymentResponseInfo>() {

            @Override
            public void onSuccess(GETPaymentResponseInfo body) {
                if (dialog != null) {
                    dialog.dismiss();

                }

                if (body != null && body.paymentInfoList
                        != null && body.paymentInfoList.size() > 0) {
                    noRecord.setVisibility(View.GONE);
                    setPaymentDoneRecyclerView(body.paymentInfoList);
                } else {
                    noRecord.setVisibility(View.VISIBLE);
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
}