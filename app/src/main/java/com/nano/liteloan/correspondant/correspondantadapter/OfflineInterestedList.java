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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.info.BorrowerList;
import com.nano.liteloan.info.GetBorrowerList;
import com.nano.liteloan.info.UserProfile;
import com.nano.liteloan.info.businesscorrespondant.ApplicationOverDueList;
import com.nano.liteloan.info.businesscorrespondant.MyApplicants;
import com.nano.liteloan.utils.OfflineOnItemClick;
import com.nano.liteloan.utils.OnItemClickListener;
import com.nano.liteloan.utils.PreferenceUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OfflineInterestedList
        extends RecyclerView.Adapter<OfflineInterestedList.ViewHolder>{
    private Context context;

    private OfflineOnItemClick mItemClickListener;

    private ArrayList<BorrowerList> getBorrowerList;

    public OfflineInterestedList(Context context, ArrayList<BorrowerList> getBorrowerList) {
        this.context = context;
        this.getBorrowerList = getBorrowerList;

    }

    class ViewHolder extends RecyclerView.ViewHolder implements OfflineOnItemClick {


        TextView tvname, tvPhoneno , tvSerialno , tvOffline , tvstatus, tvSyncStatus;
        RelativeLayout rlStatus;


//        TextView tvRequestAmount;

        public ViewHolder(View itemView) {

            super(itemView);

            tvOffline = (TextView) itemView.findViewById(R.id.offline);
            tvSerialno = (TextView) itemView.findViewById(R.id.serialno);
            tvSyncStatus = (TextView) itemView.findViewById(R.id.syncstatus);

            tvstatus = (TextView) itemView.findViewById(R.id.loanstatus);
            tvname = (TextView) itemView.findViewById(R.id.name);
            tvPhoneno = (TextView) itemView.findViewById(R.id.cnic);

            rlStatus = (RelativeLayout) itemView.findViewById(R.id.loginas);


        }

        @Override
        public void onItemClick(View view, int position , String cnic) {
            mItemClickListener.onItemClick(view, getPosition() , cnic);
        }
    }

    @Override
    public OfflineInterestedList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.offline_applicants, parent, false);

        OfflineInterestedList.ViewHolder vh = new OfflineInterestedList.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull OfflineInterestedList.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.tvOffline.setText("Offline");

        HashMap<String, UserProfile> map = new HashMap<>();
        if(PreferenceUtils.getInstance().getUserProfileList() != null){
            map = PreferenceUtils.getInstance().getUserProfileList();
            UserProfile userProfile = null;
            userProfile = map.get(getBorrowerList.get(position).cnic);
            if(userProfile != null){
                holder.tvSyncStatus.setText("Draft");
                holder.tvSyncStatus.setTextColor(Color.GRAY);
            } else {
                holder.tvSyncStatus.setText("Not Draft");
                holder.tvSyncStatus.setTextColor(Color.RED);
            }
        } else {
            holder.tvSyncStatus.setTextColor(Color.RED);
        }

        holder.tvSerialno.setText(position+1 + ". ");
        holder.tvname.setText(getBorrowerList.get(position).name);
        holder.tvPhoneno.setText(getBorrowerList.get(position).phoneno);
        holder.tvstatus.setText(getBorrowerList.get(position).borrowerstatus);
//        holder.tvname.setText(applicationlist.get(position).);
//        holder.tvStatus.setText(applicationlist.get(position).duedate);



        holder.rlStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view, position , getBorrowerList.get(position).cnic);
            }
        });
//
//        if (position == 0) {
//
//            this.viewHolder = holder;
//        }
    }

    public void setOnItemClickListener(final OfflineOnItemClick mItemClickListener) {

        this.mItemClickListener = mItemClickListener;

    }


    @Override
    public int getItemCount() {
        return getBorrowerList.size();
    }



}
