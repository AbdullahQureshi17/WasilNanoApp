package com.nano.liteloan.correspondant.correspondantadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.info.businesscorrespondant.PendingList;
import com.nano.liteloan.utils.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class VerificationPendingListAdapter
        extends RecyclerView.Adapter<VerificationPendingListAdapter.ViewHolder> implements Filterable {

    private Context context;

    private OnItemClickListener mItemClickListener;
    private List<PendingList> pendinglist;
    private List<PendingList> originallist;

    public VerificationPendingListAdapter(Context context, List<PendingList> pendinglist) {
        this.context = context;
        this.pendinglist = pendinglist;
        this.originallist = pendinglist;


    }

    class ViewHolder extends RecyclerView.ViewHolder implements OnItemClickListener {


        TextView tvname, tvPhoneno, tvSerialno;


//        TextView tvRequestAmount;

        public ViewHolder(View itemView) {

            super(itemView);

            tvSerialno = (TextView) itemView.findViewById(R.id.serialno);
            tvname = (TextView) itemView.findViewById(R.id.name);
            tvPhoneno = (TextView) itemView.findViewById(R.id.phoneno);


        }

        @Override
        public void onItemClick(View view, int position) {
            mItemClickListener.onItemClick(view, getPosition());
        }
    }

    @Override
    public VerificationPendingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.verification_pending_list, parent, false);

        VerificationPendingListAdapter.ViewHolder vh = new VerificationPendingListAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VerificationPendingListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.tvPhoneno.setText(pendinglist.get(position).phoneno);
        holder.tvname.setText(pendinglist.get(position).fullname);

        holder.tvSerialno.setText(position+1 + ". ");

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
        return pendinglist.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                List<PendingList> filteredResults;
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
                pendinglist = (List<PendingList>) filterResults.values;
                VerificationPendingListAdapter.this.notifyDataSetChanged();
            }
        };
    }

    private List<PendingList> getFilterList(String valueStr) {

        int charLength = valueStr.length();

        List<PendingList> results = new ArrayList<>();
        for (Object item : originallist) {
            String regionName = ((PendingList) item).fullname.toLowerCase();
            String region = ((PendingList) item).cnic.toLowerCase();
            String area = ((PendingList) item).phoneno.toLowerCase();

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
                results.add((PendingList) item);
            } else if (areaLatter.equalsIgnoreCase(valueStr)) {
                results.add((PendingList) item);
            } else if (branchLatter.equalsIgnoreCase(valueStr)) {
                results.add((PendingList) item);
            }
        }
        return results;

    }


}
