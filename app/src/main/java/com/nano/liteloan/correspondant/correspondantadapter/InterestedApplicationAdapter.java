package com.nano.liteloan.correspondant.correspondantadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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
import com.nano.liteloan.utils.OnPotentialBorrowerClickListener;
import com.nano.liteloan.utils.PreferenceUtils;

import java.util.HashMap;

public class InterestedApplicationAdapter extends RecyclerView.Adapter<InterestedApplicationAdapter.ViewHolder> {
    private Context context;

    private OnPotentialBorrowerClickListener mItemClickListener;
    private GetBorrowerList borrowerlist;
    private int showView = 0;

    public InterestedApplicationAdapter(Context context, GetBorrowerList borrowerlist, int flag) {
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
    public InterestedApplicationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.interested_applicant_list, parent, false);

        InterestedApplicationAdapter.ViewHolder vh = new InterestedApplicationAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull InterestedApplicationAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        // if (showView == 1) {
        holder.rlView.setVisibility(View.GONE);
        //  } else {
        //     holder.rlView.setVisibility(View.VISIBLE);
        // }

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
            Log.d("TAG", borrowerlist.borrowerList.get(position).cnic);

            holder.tvResponse.setText(borrowerlist.borrowerList.get(position).borrowerstatus);
        } else {
            holder.llllresponse.setVisibility(View.GONE);
        }

        // if (borrowerlist.borrowerList.get(position).borrowerstatus.equalsIgnoreCase("Interested")) {
        holder.rlAdd.setVisibility(View.VISIBLE);
        //  } else {
        //      holder.rlAdd.setVisibility(View.GONE);
        //  }

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
            //holder.rlAdd.setVisibility(View.GONE);
            // holder.rlAdd.setAlpha(0.4f);
            holder.rlAdd.setClickable(false);
            //  holder.rlView.setAlpha(0.4f);
            holder.rlView.setClickable(false);

            holder.rlAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, position, "add");
                }
            });
        }


        if (PreferenceUtils.getInstance().getBorrowerList() != null) {

            HashMap<String, BorrowerList> map = new HashMap<>();
            map = PreferenceUtils.getInstance().getBorrowerList();
            map.put(borrowerlist.borrowerList.get(position).cnic, borrowerlist.borrowerList.get(position));
            PreferenceUtils.getInstance().addBorrowerList(map);
        } else {
            HashMap<String, BorrowerList> borrowerMap = new HashMap<>();
            borrowerMap.put(borrowerlist.borrowerList.get(position).cnic, borrowerlist.borrowerList.get(position));
            PreferenceUtils.getInstance().addBorrowerList(borrowerMap);

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

        if (!borrowerlist.borrowerList.get(position)
                .status.equals("Added")) {
            holder.rlAdd.setVisibility(View.GONE);
        }
    }

    public void setOnItemClickListener(final OnPotentialBorrowerClickListener mItemClickListener) {

        this.mItemClickListener = mItemClickListener;

    }


    @Override
    public int getItemCount() {
        return borrowerlist.borrowerList.size();
    }


}
