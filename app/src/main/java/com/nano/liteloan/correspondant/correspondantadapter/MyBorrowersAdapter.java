package com.nano.liteloan.correspondant.correspondantadapter;

import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.info.businesscorrespondant.MyApplicants;
import com.nano.liteloan.utils.LoginAsItemClick;

import java.util.ArrayList;
import java.util.List;

public class MyBorrowersAdapter
        extends RecyclerView.Adapter<MyBorrowersAdapter.ViewHolder> implements Filterable {
    private Context context;

    private LoginAsItemClick mItemClickListener;
    private List<MyApplicants> applicationlist;
    private List<MyApplicants> originallist;
    private String value;

    public MyBorrowersAdapter(Context context, List<MyApplicants> applicationlist, String value) {
        this.context = context;
        this.applicationlist = applicationlist;
        this.originallist = applicationlist;
        this.value = value;

    }


    class ViewHolder extends RecyclerView.ViewHolder implements LoginAsItemClick {


        TextView tvname, tvCnic, tvStatus, tvSerialno, tvPsid;
        LinearLayout llPsid;
        RelativeLayout rlLoginas;


//        TextView tvRequestAmount;

        public ViewHolder(View itemView) {

            super(itemView);

            tvSerialno = (TextView) itemView.findViewById(R.id.serialno);
            rlLoginas = (RelativeLayout) itemView.findViewById(R.id.loginas);
            tvname = (TextView) itemView.findViewById(R.id.name);
            tvCnic = (TextView) itemView.findViewById(R.id.cnic);
            tvStatus = (TextView) itemView.findViewById(R.id.loanstatus);
            llPsid = itemView.findViewById(R.id.ll_psid);
            tvPsid = itemView.findViewById(R.id.psid);


        }

        @Override
        public void onItemClick(View view, int position, MyApplicants applicants) {
            mItemClickListener.onItemClick(view, getPosition(), applicants);
        }
    }

    @Override
    public MyBorrowersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.my_applicants_list, parent, false);

        MyBorrowersAdapter.ViewHolder vh = new MyBorrowersAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyBorrowersAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        if (applicationlist.get(position).psid != null
                && !applicationlist.get(position).psid.equals("")) {

            holder.llPsid.setVisibility(VISIBLE);
            holder.tvPsid.setText(applicationlist.get(position).psid);

        } else {

            holder.llPsid.setVisibility(View.GONE);
        }

        if (value.equalsIgnoreCase("all")) {
            holder.tvSerialno.setText(position + 1 + ". ");
            holder.tvname.setText(applicationlist.get(position).name);
            holder.tvCnic.setText(applicationlist.get(position).phoneno);
            if(applicationlist.get(position).userstatus.equals("pending")){
                holder.tvStatus.setText("Pending Verification");
            }  else if (applicationlist.get(position).userstatus.equals("verified")) {
                holder.tvStatus.setText("Ready To Disbursed");
            }
            else {
                holder.tvStatus.setText(applicationlist.get(position).userstatus);
            }



            holder.rlLoginas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(view, position, applicationlist.get(position));
                }
            });
        } else if (value.equalsIgnoreCase(applicationlist.get(position).userstatus)) {

            holder.tvSerialno.setText(position + 1 + ". ");
            holder.tvname.setText(applicationlist.get(position).name);
            holder.tvCnic.setText(applicationlist.get(position).phoneno);
            if(applicationlist.get(position).userstatus.equals("pending")){
                holder.tvStatus.setText("InProcess");
            } else {
                holder.tvStatus.setText(applicationlist.get(position).userstatus);
            }


            holder.rlLoginas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(view, position, applicationlist.get(position));
                }
            });
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mItemClickListener.onItemClick(v, position, applicationlist.get(position));
            }
        });


//
//        if (position == 0) {
//
//            this.viewHolder = holder;
//        }
    }

    public void setOnItemClickListener(final LoginAsItemClick mItemClickListener) {

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

                List<MyApplicants> filteredResults;
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
                applicationlist = (List<MyApplicants>) filterResults.values;
                MyBorrowersAdapter.this.notifyDataSetChanged();
            }
        };
    }

    public void filter(List<MyApplicants> applicationlistsort, String value) {
        this.value = value;
        List<MyApplicants> applicationlistsortdummy = new ArrayList<>();
        for (int i = 0; i < applicationlistsort.size(); i++) {
            if (value.equalsIgnoreCase(applicationlistsort.get(i).userstatus)) {
                applicationlistsortdummy.add(applicationlistsort.get(i));
            } else if (value.equalsIgnoreCase("all")) {
                applicationlistsortdummy.add(applicationlistsort.get(i));
            }
        }
        this.applicationlist = applicationlistsortdummy;
//        if(applicationlistsortdummy.size() > 0) {
//            this.applicationlist = applicationlistsortdummy;
//        }
        MyBorrowersAdapter.this.notifyDataSetChanged();
    }

    private List<MyApplicants> getFilterList(String valueStr) {

        int charLength = valueStr.length();

        List<MyApplicants> results = new ArrayList<>();
        for (Object item : originallist) {
            String regionName = ((MyApplicants) item).name.toLowerCase();
            String region = ((MyApplicants) item).cnic.toLowerCase();
            String area = ((MyApplicants) item).phoneno.toLowerCase();

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
                results.add((MyApplicants) item);
            } else if (areaLatter.equalsIgnoreCase(valueStr)) {
                results.add((MyApplicants) item);
            } else if (branchLatter.equalsIgnoreCase(valueStr)) {
                results.add((MyApplicants) item);
            }
        }
        return results;

    }


}
