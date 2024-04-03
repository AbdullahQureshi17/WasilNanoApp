package com.nano.liteloan.presentation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.info.BorrowerList;
import com.nano.liteloan.info.GetBorrowerList;
import com.nano.liteloan.utils.OnItemClickListener;

import java.util.List;


/**
 * Created by Muhammad Umer on 07/06/2020.
 */

public class SearchBorrowerAdapter extends RecyclerView.Adapter<SearchBorrowerAdapter.ViewHolder> {


    private Context context;

    private ViewHolder viewHolder;
    private List<BorrowerList> borrowerlist;

    private OnItemClickListener mItemClickListener;

    public SearchBorrowerAdapter(Context context, boolean isClick, GetBorrowerList body) {
        this.context = context;
        this.borrowerlist = body.borrowerList;

    }

    class ViewHolder extends RecyclerView.ViewHolder implements OnItemClickListener {


        TextView productName, disbursementDate, disbursmentAmount, status;


//        TextView tvRequestAmount;

        public ViewHolder(View itemView) {

            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.product_name);
            disbursementDate = (TextView) itemView.findViewById(R.id.disbursement_date);
            disbursmentAmount = (TextView) itemView.findViewById(R.id.loan_amount);
//            tvcnic = (TextView) itemView.findViewById(R.id.cnic);


            status = (TextView) itemView.findViewById(R.id.status);
//            tvRequestAmount = itemView.findViewById(R.id.request_amount);
        }

        @Override
        public void onItemClick(View view, int position) {
            mItemClickListener.onItemClick(view, getPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.my_loans_row, parent, false);

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

//        holder.status.setText(borrowerlist.get(position).status);
//
//        holder.disbursementDate.setText(borrowerlist.get(position).duedate);
//        holder.disbursmentAmount.setText(borrowerlist.get(position).loanAmount + " PKR/-");
//        holder.productName.setText(borrowerlist.get(position).name);
       

//        holder.productName.setText(info.productName + " Loan");
//
//        if (info.status.equalsIgnoreCase("Loan Rejected")) {
//            holder.ivCircle.setBackground(context.getResources().getDrawable(R.drawable.circle_red));
//            holder.status.setTextColor(context.getResources().getColor(R.color.Red));
//        } else if (info.status.contains("Loan Completed.")) {
//            holder.ivCircle.setBackground(context.getResources().getDrawable(R.drawable.circle_green));
//            holder.status.setTextColor(context.getResources().getColor(R.color.LimeGreen));
//        } else if (info.status.equalsIgnoreCase("Evaluation is in Process")) {
//            holder.ivCircle.setBackground(context.getResources().getDrawable(R.drawable.circle_darkorange));
//            holder.status.setTextColor(context.getResources().getColor(R.color.DarkOrange));
//        } else if (info.status.equalsIgnoreCase("<p>Application is approved <br> and loan is in process</p>")) {
//            holder.ivCircle.setBackground(context.getResources().getDrawable(R.drawable.circle_purple));
//            holder.status.setTextColor(context.getResources().getColor(R.color.Purple));
//        } else if (info.status.equalsIgnoreCase("<p>Loan approved and amount <br> will be transfered in 24 hours</p>")) {
//            holder.ivCircle.setBackground(context.getResources().getDrawable(R.drawable.circle_blue));
//            holder.status.setTextColor(context.getResources().getColor(R.color.CornflowerBlue));
//        }
//
//        holder.disbursmentAmount.setText(AppUtils.getFormatAmount(Double.valueOf(info.disbursementAmount)) + " PKR/-");
////        holder.tvRequestAmount.setText(AppUtils.getFormatAmount(Double.valueOf(info.requestedAmount)) + " PKR/-");
//        holder.status.setHtml(info.status);
//        holder.disbursementDate.setText(info.applicationDate);
//
//
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view, position);
            }
        });
//
//        if (position == 0) {
//
//            this.viewHolder = holder;
//        }
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {

        this.mItemClickListener = mItemClickListener;

    }


    @Override
    public int getItemCount() {
        return borrowerlist.size();
    }

    public void clickLoanDetail() {

        viewHolder.itemView.performClick();
    }


}

