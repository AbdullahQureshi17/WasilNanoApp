package com.nano.liteloan.correspondant.correspondantadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.info.businesscorrespondant.ApplicationOverDueList;
import com.nano.liteloan.utils.AppUtils;
import com.nano.liteloan.utils.OnItemClickListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OverDueListAdapter extends RecyclerView.Adapter<OverDueListAdapter.ViewHolder> implements Filterable {
    private Context context;

    private OnItemClickListener mItemClickListener;
    private List<ApplicationOverDueList> applicationlist;
    private List<ApplicationOverDueList> originallist;

    public OverDueListAdapter(Context context, List<ApplicationOverDueList> applicationlist) {
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


        TextView tvname, tvPhoneno, tvDays, tvRecoveryDate, tvSerialno;
        RelativeLayout rlLogin;


//        TextView tvRequestAmount;

        public ViewHolder(View itemView) {

            super(itemView);

            tvSerialno = (TextView) itemView.findViewById(R.id.serialno);
            tvRecoveryDate = (TextView) itemView.findViewById(R.id.recoverydate);
            tvname = (TextView) itemView.findViewById(R.id.name);
            tvPhoneno = (TextView) itemView.findViewById(R.id.phoneno);
            tvDays = (TextView) itemView.findViewById(R.id.days);
            rlLogin = (RelativeLayout) itemView.findViewById(R.id.loginas);


        }

        @Override
        public void onItemClick(View view, int position) {
            mItemClickListener.onItemClick(view, getPosition());
        }
    }

    @Override
    public OverDueListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.active_borrower_list, parent, false);

        OverDueListAdapter.ViewHolder vh = new OverDueListAdapter.ViewHolder(view);
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull OverDueListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.tvSerialno.setText(position + 1 + ". ");

        holder.tvname.setText(applicationlist.get(position).fullname);
        holder.tvPhoneno.setText(applicationlist.get(position).phoneno);
        long diff = getDate(position);
        if (diff != -100) {
            holder.tvDays.setText("Overdue days: " + diff);
            holder.tvRecoveryDate.setText("Due Date" + applicationlist.get(position).duedate);
            if (applicationlist.get(position).recoverydays <= 0) {
                holder.tvDays.setTextColor(Color.parseColor("#FF0000"));
                holder.tvRecoveryDate.setTextColor(Color.parseColor("#FF0000"));
            } else {
                holder.tvRecoveryDate.setTextColor(Color.parseColor("#000000"));
                holder.tvDays.setTextColor(Color.parseColor("#000000"));
            }
        }


        holder.rlLogin.setOnClickListener(new View.OnClickListener() {
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

    private long getDate(int position) {

        try {
            Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(applicationlist.get(position).duedate);
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date2 = null;
            date2 = new SimpleDateFormat("dd-MM-yyyy").parse(dateFormat.format(new Date()));
            long dif = date2.getTime() - date1.getTime();
            return TimeUnit.DAYS.convert(dif, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
            return -100;
        }

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
                OverDueListAdapter.this.notifyDataSetChanged();
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

}
