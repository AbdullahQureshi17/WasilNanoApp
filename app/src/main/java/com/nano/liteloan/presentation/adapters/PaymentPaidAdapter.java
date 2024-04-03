package com.nano.liteloan.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.info.Schedule;
import com.nano.liteloan.utils.OnItemClickListener;

import java.util.List;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class PaymentPaidAdapter extends RecyclerView.Adapter<PaymentPaidAdapter.CustomViewHolder> {


    Context context;
    private OnItemClickListener mItemClickListener;

    private List<Schedule> userSchedule;

    public PaymentPaidAdapter(Context context, List<Schedule> userSchedule) {
        this.context = context;

        this.userSchedule = userSchedule;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements OnItemClickListener {


        TextView tvDate, tvAmount, tvStatus;
        View llBg;

        public CustomViewHolder(View itemView) {

            super(itemView);
            tvAmount = (TextView) itemView.findViewById(R.id.amount);
            tvDate = (TextView) itemView.findViewById(R.id.date);
//            tvNumber = (TextView) itemView.findViewById(R.id.number);
            tvStatus = itemView.findViewById(R.id.status);
            llBg = itemView.findViewById(R.id.ll_bg);


        }

        @Override
        public void onItemClick(View view, int position) {
            mItemClickListener.onItemClick(view, getPosition());
        }


    }

    @NonNull
    @Override
    public PaymentPaidAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_amount_paid, parent, false);
        return new PaymentPaidAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentPaidAdapter.CustomViewHolder holder, final int position) {


        Schedule schedule = userSchedule.get(position);

        holder.tvDate.setText(schedule.paidDate);
        holder.tvAmount.setText("PKR " + schedule.recoveryAmount);
//        holder.tvNumber.setText(String.valueOf(position + 1));
        holder.tvStatus.setText(schedule.getStatus());

        if (position % 2 == 0) {

            holder.llBg.setBackground(ContextCompat.getDrawable(context, R.color.colorGray_transparent));

        } else {

            holder.llBg.setBackground(ContextCompat.getDrawable(context, R.color.white));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view, position);
            }
        });

    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {

        this.mItemClickListener = mItemClickListener;

    }

    @Override
    public int getItemCount() {
        return userSchedule.size();
    }

    public Schedule getSchedule(int position){

        return userSchedule.get(position);
    }

}
