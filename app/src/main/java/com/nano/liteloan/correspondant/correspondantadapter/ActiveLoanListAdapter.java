package com.nano.liteloan.correspondant.correspondantadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
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
import com.nano.liteloan.info.businesscorrespondant.ApplicationOverDueList;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class ActiveLoanListAdapter
        extends RecyclerView.Adapter<ActiveLoanListAdapter.ViewHolder> implements Filterable {
    private Context context;

    private OnItemClickListener mItemClickListener;
    private List<ApplicationOverDueList> applicationlist;
    private List<ApplicationOverDueList> originallist;

    public ActiveLoanListAdapter(Context context, List<ApplicationOverDueList> applicationlist) {
        this.context = context;
        this.applicationlist = applicationlist;
        this.originallist = applicationlist;

    }

    public void dateFilter(int selectedDate) {

        if (selectedDate == 0) {
            applicationlist = originallist;
            this.notifyDataSetChanged();
            return;
        }
        List<ApplicationOverDueList> temp = new ArrayList<>();
        for (ApplicationOverDueList list : originallist) {
            long days = AppUtils.getDaysBetween(list.duedate);
            if (selectedDate == 1) {

                if (days <= 3) {
                    temp.add(list);
                }
            } else if (selectedDate == 2) {

                if (days <= 5) {
                    temp.add(list);
                }
            } else if (selectedDate == 3) {
                if (days <= 10) {
                    temp.add(list);
                }
            }
            if (list.duedate.equals(selectedDate)) {
                temp.add(list);
            }
        }

        applicationlist = temp;
        notifyDataSetChanged();
        return;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements OnItemClickListener {


        TextView tvname, tvPhoneno, tvStatus, tvdays, tvrecoveryDate, tvSerialno;
        RelativeLayout rlStatus;


//        TextView tvRequestAmount;

        public ViewHolder(View itemView) {

            super(itemView);

            tvSerialno = (TextView) itemView.findViewById(R.id.serialno);
            tvrecoveryDate = (TextView) itemView.findViewById(R.id.recoverydate);
            tvname = (TextView) itemView.findViewById(R.id.name);
            tvPhoneno = (TextView) itemView.findViewById(R.id.phoneno);
//            rlStatus = (RelativeLayout) itemView.findViewById(R.id.status);
            rlStatus = (RelativeLayout) itemView.findViewById(R.id.loginas);

            tvdays = (TextView) itemView.findViewById(R.id.days);


        }

        @Override
        public void onItemClick(View view, int position) {
            mItemClickListener.onItemClick(view, getPosition());
        }
    }

    @Override
    public ActiveLoanListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.active_borrower_list, parent, false);

        ActiveLoanListAdapter.ViewHolder vh = new ActiveLoanListAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveLoanListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        holder.tvSerialno.setText(position + 1 + ". ");
        holder.tvname.setText(applicationlist.get(position).fullname);
        holder.tvPhoneno.setText(applicationlist.get(position).phoneno);
//        holder.tvname.setText(applicationlist.get(position).);
//        holder.tvStatus.setText(applicationlist.get(position).duedate);
        holder.tvdays.setText("Due in: " + applicationlist.get(position).recoverydays + " Days");
        if (applicationlist.get(position).recoverydays <= 0) {
            holder.tvrecoveryDate.setTextColor(Color.parseColor("#FF0000"));
            holder.tvdays.setTextColor(Color.parseColor("#FF0000"));
        } else {
            holder.tvrecoveryDate.setTextColor(Color.parseColor("#000000"));
            holder.tvdays.setTextColor(Color.parseColor("#000000"));
        }
        holder.tvrecoveryDate.setText("Due date" + applicationlist.get(position).duedate);


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

                List<ApplicationOverDueList> filteredResults;
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
                applicationlist = (List<ApplicationOverDueList>) filterResults.values;
                ActiveLoanListAdapter.this.notifyDataSetChanged();
            }
        };
    }

    private List<ApplicationOverDueList> getFilterList(String valueStr) {

        int charLength = valueStr.length();

        List<ApplicationOverDueList> results = new ArrayList<>();
        for (Object item : originallist) {
            String regionName = ((ApplicationOverDueList) item).fullname.toLowerCase();
            String region = ((ApplicationOverDueList) item).cnic.toLowerCase();
            String area = ((ApplicationOverDueList) item).phoneno.toLowerCase();

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
                results.add((ApplicationOverDueList) item);
            } else if (areaLatter.equalsIgnoreCase(valueStr)) {
                results.add((ApplicationOverDueList) item);
            } else if (branchLatter.equalsIgnoreCase(valueStr)) {
                results.add((ApplicationOverDueList) item);
            }
        }
        return results;

    }

    public void filter(List<ApplicationOverDueList> applicationlist, long value) {

        if (value != -2000) {
            List<ApplicationOverDueList> applicationlistsortdummy = new ArrayList<>();
            for (int i = 0; i < applicationlist.size(); i++) {
                if (value == applicationlist.get(i).recoverydays) {
                    applicationlistsortdummy.add(applicationlist.get(i));
                }
            }
            this.applicationlist = applicationlistsortdummy;
//            if(applicationlistsortdummy.size() > 0) {
//                this.applicationlist = applicationlistsortdummy;
//            } else if(applicationlistsortdummy.size()== 0){
//                this.applicationlist = applicationlistsortdummy;
//            }
            ActiveLoanListAdapter.this.notifyDataSetChanged();
        } else {
            this.applicationlist = applicationlist;
            notifyDataSetChanged();
        }

    }

}
