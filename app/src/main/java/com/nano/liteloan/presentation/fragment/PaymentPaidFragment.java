package com.nano.liteloan.presentation.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.GetApplicationInfo;
import com.nano.liteloan.info.GetScheduleRequest;
import com.nano.liteloan.info.GetScheduleResponse;
import com.nano.liteloan.info.Schedule;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.adapters.PaymentPaidAdapter;
import com.nano.liteloan.presentation.fragment.dialoguefragment.RecoveryPaymentDialouge;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.OnItemClickListener;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class PaymentPaidFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private GetApplicationInfo info;
    private TextView tvName, tvAmount, tvLoantype;
    private TextView tvRequestAmount;
    private HtmlTextView tvprogress;
    private ImageView ivProfile;
    private TextView tvNoRecord;

    MainActivity context;


    public PaymentPaidFragment() {
        // Required empty public constructor
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_paid, container, false);


        if (this.getArguments() != null
                && this.getArguments().getSerializable("loan_detail") != null) {
            info = (GetApplicationInfo) this.getArguments()
                    .getSerializable("loan_detail");
        }

        tvNoRecord = view.findViewById(R.id.norecord);
        tvNoRecord.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.customRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        tvName = view.findViewById(R.id.name);
        tvAmount = view.findViewById(R.id.amount);
        tvRequestAmount = view.findViewById(R.id.request_amount);
        tvLoantype = view.findViewById(R.id.loan_type);
        tvprogress = view.findViewById(R.id.progress);
        ivProfile = view.findViewById(R.id.profile);
        ImageView ivBack = view.findViewById(R.id.back);
        ivBack.setOnClickListener(this);

        setContent();

        getSchedule();
        return view;
    }

    private void getSchedule() {

        GetScheduleRequest request = new GetScheduleRequest();
        request.applicationid = info.id;

        final AlertDialog dialog = AlertUtils.showLoader(getActivity());

        EasyLoanBusiness business = new EasyLoanBusiness();
        business.getSchedule(request, new ResponseCallBack<GetScheduleResponse>() {
            @Override
            public void onSuccess(GetScheduleResponse body) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                setPaymentRecyclerView(body, info.id);
                tvNoRecord.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(String message) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (message.equalsIgnoreCase("error occured data not found")) {
                    tvNoRecord.setVisibility(View.VISIBLE);
                } else {
                    AlertUtils.showAlert(getActivity(), message);
                }

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setContent() {
        tvLoantype.setText(info.productName + " Loan");
        tvprogress.setHtml(info.status);
        tvAmount.setText(AppUtils
                .getFormatAmount(Double.valueOf(info.disbursementAmount)) + " PKR /-");
        tvRequestAmount.setText(AppUtils
                .getFormatAmount(Double.valueOf(info.requestedAmount)) + " PKR /-");

        Integer applicationId = info.id;

        Picasso.get().load(PreferenceUtils.getInstance()
                .getUserDetail().getImageUrl()).into(ivProfile);

        tvName.setText(PreferenceUtils.getInstance().getUserDetail().getName());

    }

    private void setPaymentRecyclerView(final GetScheduleResponse body, final int id) {

        tvName.setText(body.user.name);
        Picasso.get()
                .load(body.user.image)
                .into(ivProfile);
        if (body.outstanding.remaining != null &&
                !body.outstanding.remaining.isEmpty()) {
            Schedule schedule = new Schedule();
            schedule.setPaidDate(body.outstanding.duedate);
            schedule.setRecoveryAmount(body.outstanding.remaining);
            schedule.setStatus(body.outstanding.status);

            if (body.schedule != null) {
                body.schedule.add(0, schedule);
            }

        }
        final PaymentPaidAdapter paymentPaid = new PaymentPaidAdapter(context, body.schedule);
        recyclerView.setAdapter(paymentPaid);

        paymentPaid.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

//                if (body.schedule.get(position).getStatus().equalsIgnoreCase("Call Help Desk")) {
//                    context.helpCenterFragment();
//                }
//
//                if (!body.schedule.get(position)
//
//                        .getStatus().equalsIgnoreCase("Rejected") && !body.schedule.get(position)
//                        .getStatus().equalsIgnoreCase("Un-Paid")) {
//
//                    //  context.paymentDoneFragment(body.schedule.get(position));
//
//                } else if (position > 0) {
//
//                    if (!body.schedule.get(position - 1)
//                            .getStatus().equalsIgnoreCase("Un-paid") &&
//                            !body.schedule.get(position - 1).getStatus().equalsIgnoreCase("Rejected") &&
//                            !body.schedule.get(position - 1).getStatus().equalsIgnoreCase("In-process")) {
//                        setRecoveryPaymentDialogue(body
//                                .schedule.get(position), paymentPaid, position, id);
//                    } else if (body.schedule.get(position - 1).getStatus().contains("Call Help Desk")) {
//                        context.helpCenterFragment();
//                    }
//
//                } else {
//
//                    setRecoveryPaymentDialogue(body
//                            .schedule.get(position), paymentPaid, position, id);
//                }


            }
        });


    }

    private void setRecoveryPaymentDialogue(Schedule schedule,
                                            final PaymentPaidAdapter paymentPaid,
                                            final int position, int id) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("schedule_detail", schedule);

        RecoveryPaymentDialouge dialouge = new RecoveryPaymentDialouge(position, id);
        dialouge.setArguments(bundle);
        dialouge.setOnRecoveryPaid(new OnRecoveryPaid() {
            @Override
            public void onPaid() {
                paymentPaid.getSchedule(position).setStatus("In-Process");
                paymentPaid.notifyDataSetChanged();
            }
        });

        dialouge.show(getChildFragmentManager(),
                RecoveryPaymentDialouge.class.getName());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            context.onBackPressed();
        }
    }

    /**
     * Interface to get call back from recovery.
     * created by Muhammad Ahmad
     */
    public interface OnRecoveryPaid {

        void onPaid();
    }
}