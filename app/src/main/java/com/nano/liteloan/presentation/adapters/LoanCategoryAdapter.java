package com.nano.liteloan.presentation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.info.LoanCategoryInfo;
import com.nano.liteloan.utils.OnItemClickListener;
import com.nano.liteloan.utils.PreferenceUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Muhammad Ahmad on 07/09/2020.
 */

public class LoanCategoryAdapter
        extends RecyclerView.Adapter<LoanCategoryAdapter.ViewHolder> {

    private List<LoanCategoryInfo> loanCategoryInfos;
    private Context context;
    private OnItemClickListener mItemClickListener;
    private int value;

    public LoanCategoryAdapter(Context context, List<LoanCategoryInfo> loanCategoryInfos, int value) {
        this.context = context;
        this.loanCategoryInfos = loanCategoryInfos;
        this.value = value;
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle;
        ImageView ivImage;
        View rlLoanBg;

        public ViewHolder(View itemView) {

            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_loan_title);
            ivImage = itemView.findViewById(R.id.student_image);
            rlLoanBg = itemView.findViewById(R.id.rl_loan_bg);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_lona_category_items, parent, false);

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        LoanCategoryInfo loans = loanCategoryInfos.get(position);

        holder.tvTitle.setText(loans.name);



        if (loans.logo != null && !loans.logo.isEmpty()) {

            Picasso.get()
                    .load(loans.logo)
                    .into(holder.ivImage);
        }
        if (value == 0) {
            if (loans.isactive == 1) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mItemClickListener.onItemClick(view, position);
                    }
                });
                holder.rlLoanBg.setBackgroundColor(Color.parseColor(loans.colorCode));
            } else {
                holder.rlLoanBg.setBackgroundColor(Color.parseColor(loans.colorCode));
                holder.rlLoanBg.setAlpha(Float.parseFloat("0.2"));
            }
        } else {
            if (loans.isactive == 1 && value == loans.id) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mItemClickListener.onItemClick(view, position);
                    }
                });
                holder.rlLoanBg.setBackgroundColor(Color.parseColor(loans.colorCode));
            } else {
                holder.rlLoanBg.setBackgroundColor(Color.parseColor(loans.colorCode));
                holder.rlLoanBg.setAlpha(Float.parseFloat("0.2"));
            }
        }



            holder.rlLoanBg.setAlpha(1f);
            holder.itemView.setClickable(true);


    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {

        this.mItemClickListener = mItemClickListener;

    }


    @Override
    public int getItemCount() {
        return loanCategoryInfos.size();
    }


}

