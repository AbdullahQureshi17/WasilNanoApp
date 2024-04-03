package com.nano.liteloan.presentation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.info.PaymentInfo;
import com.nano.liteloan.utils.AppUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */

public class PaymentTransctionAdapter extends RecyclerView.Adapter<PaymentTransctionAdapter.ViewHolder> {

    Context context;
    private List<PaymentInfo> infoList;

    public PaymentTransctionAdapter(Context context, List<PaymentInfo> list) {
        this.context = context;
        this.infoList = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvAmount, tvType, tvId, tvDate;

        public ViewHolder(View itemView) {

            super(itemView);

            tvAmount = itemView.findViewById(R.id.tv_amount);
            tvType = itemView.findViewById(R.id.tv_payment_type);
//            tvId = itemView.findViewById(R.id.tv_transaction_id);
//            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_total_paid, parent, false);

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

//        holder.tvDate.setText(getDate(position));
//        holder.tvId.setText(infoList.get(position).transactionId);
        holder.tvAmount.setText("Rs. " + AppUtils
                .getFormatAmount(Double.valueOf(infoList
                        .get(position).amount)) + "/-");
        holder.tvType.setText(infoList.get(position).paymentType);
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    private String getDate(int position) {

        String dateS = infoList.get(position).date;
        Date date = null;

        SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
        try {
            date = dt.parse(dateS);
        } catch (ParseException e) {
            Log.e("ahmad", e.getMessage());
        }

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if (date == null) {
            return "";
        } else {
            return df.format(date);
        }
    }

}

