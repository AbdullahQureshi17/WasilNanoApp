package com.nano.liteloan.correspondant.correspondantadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.info.businesscorrespondant.VerifiedBorrower;
import com.nano.liteloan.utils.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class ReadyToDisburseAdapter
        extends RecyclerView.Adapter<ReadyToDisburseAdapter.ViewHolder> implements Filterable {
    private Context context;

    private OnItemClickListener mItemClickListener;
    private List<VerifiedBorrower> applicationlist;
    private List<VerifiedBorrower> originallist;

    public ReadyToDisburseAdapter(Context context, List<VerifiedBorrower> applicationlist) {
        this.context = context;
        this.applicationlist = applicationlist;
        this.originallist = applicationlist;


    }

    class ViewHolder extends RecyclerView.ViewHolder implements OnItemClickListener {


        TextView tvname, tvPhoneno, tvStatus , tvSerialno;
        RelativeLayout rlStatus;


//        TextView tvRequestAmount;

        public ViewHolder(View itemView) {

            super(itemView);

            tvSerialno = (TextView) itemView.findViewById(R.id.serialno);
            tvname = (TextView) itemView.findViewById(R.id.name);
            tvPhoneno = (TextView) itemView.findViewById(R.id.phoneno);
            rlStatus = (RelativeLayout) itemView.findViewById(R.id.loginas);


        }

        @Override
        public void onItemClick(View view, int position) {
            mItemClickListener.onItemClick(view, getPosition());
        }
    }

    @Override
    public ReadyToDisburseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ready_to_disburse_list, parent, false);

        ReadyToDisburseAdapter.ViewHolder vh = new ReadyToDisburseAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ReadyToDisburseAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        holder.tvname.setText(applicationlist.get(position).fullname);
        holder.tvPhoneno.setText(applicationlist.get(position).phoneno);

        holder.tvSerialno.setText(position+1 + ". ");

//        holder.tvStatus.setText("Rs. "+ AppUtils.getFormatAmount(Integer.valueOf(applicationlist.get(position).recommendedamount)));


        holder.rlStatus.setOnClickListener(new View.OnClickListener() {
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
        return applicationlist.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                List<VerifiedBorrower> filteredResults;
                if (charSequence.length() == 0) {

                    filteredResults = originallist;

                } else {

                    filteredResults = getFilterList(charSequence.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                applicationlist = (List<VerifiedBorrower>) filterResults.values;
                ReadyToDisburseAdapter.this.notifyDataSetChanged();
            }
        };
    }

    private List<VerifiedBorrower> getFilterList(String valueStr) {

        int charLength = valueStr.length();

        List<VerifiedBorrower> results = new ArrayList<>();
        for (Object item : originallist) {
            String regionName = ((VerifiedBorrower) item).fullname.toLowerCase();
            String region = ((VerifiedBorrower) item).cnic.toLowerCase();
            String area = ((VerifiedBorrower) item).phoneno.toLowerCase();

            String letter;
            String areaLatter;
            String branchLatter;

            if (region.length() > charLength) {

                letter = region.substring(0, charLength);
            } else {
                letter = region;
            }
            if (area.length() > charLength) {

                areaLatter = area.substring(0, charLength);
            } else {
                areaLatter = area;
            }
            if (regionName.length() > charLength) {

                branchLatter = regionName.substring(0, charLength);
            } else {
                branchLatter = regionName;
            }

            if (letter.equalsIgnoreCase(valueStr)) {
                results.add((VerifiedBorrower) item);
            } else if (areaLatter.equalsIgnoreCase(valueStr)) {
                results.add((VerifiedBorrower) item);
            } else if (branchLatter.equalsIgnoreCase(valueStr)) {
                results.add((VerifiedBorrower) item);
            }
        }
        return results;

    }

}
