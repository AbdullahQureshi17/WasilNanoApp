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
import com.nano.liteloan.info.businesscorrespondant.ApplicationOverDueList;
import com.nano.liteloan.info.businesscorrespondant.MyApplicants;
import com.nano.liteloan.utils.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class MyApplicantListAdapter
        extends RecyclerView.Adapter<MyApplicantListAdapter.ViewHolder> implements Filterable {
    private Context context;

    private OnItemClickListener mItemClickListener;
    private List<ApplicationOverDueList> borrowerlist;
    private List<ApplicationOverDueList> originallist;
    String value;

    public MyApplicantListAdapter(Context context, List<ApplicationOverDueList> borrowerlist , String value) {
        this.context = context;
        this.borrowerlist = borrowerlist;
        this.originallist = borrowerlist;
        this.value = value;

    }

    class ViewHolder extends RecyclerView.ViewHolder implements OnItemClickListener {


        TextView tvname, tvCnic , tvSerialno , tvStatus, tvPsid;
        LinearLayout llPsid;
        RelativeLayout rlLogin;


//        TextView tvRequestAmount;

        public ViewHolder(View itemView) {

            super(itemView);

            tvSerialno = (TextView) itemView.findViewById(R.id.serialno);
            tvname = (TextView) itemView.findViewById(R.id.name);
            tvCnic = (TextView) itemView.findViewById(R.id.cnic);
            tvStatus = (TextView) itemView.findViewById(R.id.loanstatus);
            rlLogin = (RelativeLayout) itemView.findViewById(R.id.rlLogin);
            llPsid = itemView.findViewById(R.id.ll_psid);
            tvPsid = itemView.findViewById(R.id.psid);


        }

        @Override
        public void onItemClick(View view, int position) {
            mItemClickListener.onItemClick(view, getPosition());
        }
    }

    @Override
    public MyApplicantListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.my_borrower_list, parent, false);

        MyApplicantListAdapter.ViewHolder vh = new MyApplicantListAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyApplicantListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        if (borrowerlist.get(position).psid != null
                && !borrowerlist.get(position).psid.equals("")) {

            holder.llPsid.setVisibility(VISIBLE);
            holder.tvPsid.setText(borrowerlist.get(position).psid);

        } else {

            holder.llPsid.setVisibility(View.GONE);
        }

        if(value.equalsIgnoreCase("all")){
            holder.tvname.setText(borrowerlist.get(position).fullname);
            holder.tvCnic.setText(borrowerlist.get(position).cnic);


            holder.tvStatus.setText(borrowerlist.get(position).status);
            holder.tvSerialno.setText(position+1 + ". ");


            holder.rlLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(view, position);
                }
            });
        } else if(value.equalsIgnoreCase(borrowerlist.get(position).status)) {
            holder.tvname.setText(borrowerlist.get(position).fullname);
            holder.tvCnic.setText(borrowerlist.get(position).cnic);


            holder.tvStatus.setText(borrowerlist.get(position).status);
            holder.tvSerialno.setText(position+1 + ". ");


            holder.rlLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(view, position);
                }
            });
        }

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
                borrowerlist = (List<ApplicationOverDueList>) filterResults.values;
                MyApplicantListAdapter.this.notifyDataSetChanged();
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

    public void filter(List<ApplicationOverDueList> borrowerlist  , String value){
        this.value = value;
        List<ApplicationOverDueList> applicationlistsortdummy = new ArrayList<>();
        for(int i = 0 ; i<borrowerlist.size() ; i++){
            if(value.equalsIgnoreCase(borrowerlist.get(i).status)){
                applicationlistsortdummy.add(borrowerlist.get(i));
            } else if(value.equalsIgnoreCase("all")){
                applicationlistsortdummy.add(borrowerlist.get(i));
            }
        }
        this.borrowerlist = applicationlistsortdummy;
//        if(applicationlistsortdummy.size() > 0) {
//            this.borrowerlist = applicationlistsortdummy;
//        }
        MyApplicantListAdapter.this.notifyDataSetChanged();
    }

}
