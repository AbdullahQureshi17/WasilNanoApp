package com.nano.liteloan.correspondant.correspondantadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.info.BorrowerList;
import com.nano.liteloan.info.GetBorrowerList;
import com.nano.liteloan.utils.OnItemClickListener;
import com.nano.liteloan.utils.OnPotentialBorrowerClickListener;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PotentialBorrowerListAdapter extends RecyclerView.Adapter<PotentialBorrowerListAdapter.ViewHolder> {
    private Context context;

    private OnPotentialBorrowerClickListener mItemClickListener;
    private GetBorrowerList borrowerlist;
    private int showView = 0;

    public PotentialBorrowerListAdapter(Context context, GetBorrowerList borrowerlist, int flag) {
        this.context = context;
        this.borrowerlist = borrowerlist;
        showView = flag;


    }

    class ViewHolder extends RecyclerView.ViewHolder implements OnPotentialBorrowerClickListener {


        TextView tvname, tvPhoneno, tvCnic, tvCalledBy, tvResponse, tvLoanStatus, tvSerialno;
        //        RelativeLayout rlAddNew;
        RelativeLayout rlView, rlAdd, rlmain;
        LinearLayout llcalledby, llllresponse;


//        TextView tvRequestAmount;

        public ViewHolder(View itemView) {

            super(itemView);

            tvname = (TextView) itemView.findViewById(R.id.name);

            tvSerialno = (TextView) itemView.findViewById(R.id.serialno);

            rlmain = (RelativeLayout) itemView.findViewById(R.id.rlmain);
            tvPhoneno = (TextView) itemView.findViewById(R.id.phoneno);
            tvCnic = (TextView) itemView.findViewById(R.id.cnic);
//            rlAddNew = (RelativeLayout) itemView.findViewById(R.id.rladd);

            llcalledby = (LinearLayout) itemView.findViewById(R.id.llcalledby);
            llllresponse = (LinearLayout) itemView.findViewById(R.id.llresponse);

            tvCalledBy = (TextView) itemView.findViewById(R.id.calledby);
            tvResponse = (TextView) itemView.findViewById(R.id.callresponce);

            tvLoanStatus = (TextView) itemView.findViewById(R.id.loanstatus);

            rlAdd = (RelativeLayout) itemView.findViewById(R.id.rladd);
            rlView = (RelativeLayout) itemView.findViewById(R.id.rlView);

        }

        @Override
        public void onItemClick(View view, int position, String value) {
            mItemClickListener.onItemClick(view, getPosition(), value);
        }
    }

    @Override
    public PotentialBorrowerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.potential_borrower_list, parent, false);

        PotentialBorrowerListAdapter.ViewHolder vh = new PotentialBorrowerListAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PotentialBorrowerListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        if (showView == 1) {
            holder.rlView.setVisibility(View.GONE);
        } else {
            holder.rlView.setVisibility(View.VISIBLE);
        }

        holder.tvSerialno.setText(position + 1 + ". ");

        if (borrowerlist.borrowerList.get(position).approachedby != null &&
                !borrowerlist.borrowerList.get(position).approachedby.equalsIgnoreCase("")) {
            holder.llcalledby.setVisibility(View.VISIBLE);
            holder.tvCalledBy.setText(borrowerlist.borrowerList.get(position).approachedby);
        } else {
            holder.llcalledby.setVisibility(View.GONE);
        }
        if (borrowerlist.borrowerList.get(position).borrowerstatus != null &&
                !borrowerlist.borrowerList.get(position).borrowerstatus.equalsIgnoreCase("")) {
            holder.llllresponse.setVisibility(View.VISIBLE);
            holder.tvResponse.setText(borrowerlist.borrowerList.get(position).borrowerstatus);
        } else {
            holder.llllresponse.setVisibility(View.GONE);
        }

        if (borrowerlist.borrowerList.get(position).borrowerstatus.equalsIgnoreCase("Interested")) {
            holder.rlAdd.setVisibility(View.VISIBLE);
        } else {
            holder.rlAdd.setVisibility(View.GONE);
        }

        holder.tvPhoneno.setText(borrowerlist.borrowerList.get(position).phoneno);
        holder.tvname.setText(borrowerlist.borrowerList.get(position).name);

        if (borrowerlist.borrowerList.get(position).approachedby != null) {
            holder.tvCalledBy.setText(borrowerlist.borrowerList.get(position).approachedby);
        }
        if (borrowerlist.borrowerList.get(position).status != null) {
            holder.tvResponse.setText(borrowerlist.borrowerList.get(position).borrowerstatus);
        }

        holder.tvLoanStatus.setText(borrowerlist.borrowerList.get(position).status);

        if (borrowerlist.borrowerList.get(position).status.equalsIgnoreCase("Not Added")) {


            holder.rlAdd.setAlpha(1f);
            holder.rlAdd.setClickable(true);
            holder.rlView.setAlpha(1f);
            holder.rlView.setClickable(true);
            holder.rlAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, position, "add");
                }
            });

            holder.rlView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, position, "view");
                }
            });
        } else {
            holder.rlView.setVisibility(View.GONE);
            holder.rlAdd.setVisibility(View.GONE);
            holder.rlAdd.setAlpha(0.4f);
            holder.rlAdd.setClickable(false);
            holder.rlView.setAlpha(0.4f);
            holder.rlView.setClickable(false);
        }






//        holder.rlAddNew.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mItemClickListener.onItemClick(view, position);
//            }
//        });
//
//        if (position == 0) {
//
//            this.viewHolder = holder;
//        }
    }

    public void setOnItemClickListener(final OnPotentialBorrowerClickListener mItemClickListener) {

        this.mItemClickListener = mItemClickListener;

    }


    @Override
    public int getItemCount() {
        return borrowerlist.borrowerList.size();
    }


}
