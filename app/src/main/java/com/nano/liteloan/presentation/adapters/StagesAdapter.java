package com.nano.liteloan.presentation.adapters;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.info.StagesInfo;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.AppConstantsUtils;
import com.nano.liteloan.utils.OnItemClickListener;

import java.util.List;

/**
 * Created by Muhammad Ahmad on 07/04/2020.
 */
public class StagesAdapter extends RecyclerView.Adapter<StagesAdapter.ViewHolder> {

    private List<StagesInfo> infoList;
    private MainActivity context;
    private OnItemClickListener onItemClickListener;

    public StagesAdapter(MainActivity context, List<StagesInfo> stagesInfos) {

        this.context = context;
        this.infoList = stagesInfos;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.stages_rv_items, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        StagesInfo stage = infoList.get(position);
        holder.status.setVisibility(View.GONE);
        holder.tvHeading.setText(stage.getHeading());
        if (stage.getTitle() == null) {
            holder.tvDesc.setText(stage.getTitleChar());
        } else {
            holder.tvDesc.setText(stage.getTitle());
        }



        holder.ivStageLogo.setImageDrawable(getDrawable(stage.getLogo()));

        if (stage.getName().equalsIgnoreCase(AppConstantsUtils.StageUtils.FEE)) {

            holder.btnStartStage.setText("Pay");


        } else  if (stage.getName().equalsIgnoreCase(AppConstantsUtils.StageUtils.STATUS_LOANAPPLICATIONREJECTED)){
            holder.btnStartStage.setVisibility(View.VISIBLE);
            holder.btnStartStage.setText("Apply Again");
        }
        else if (stage.getName().equalsIgnoreCase("recovery_issue")) {
            holder.btnStartStage.setVisibility(View.VISIBLE);
            holder.btnStartStage.setText("Repay");
        } else if (stage.getName().equalsIgnoreCase(AppConstantsUtils.StageUtils.WALLET)) {

            holder.btnStartStage.setText("Add");

        } else if (stage.getName().equalsIgnoreCase(AppConstantsUtils.StageUtils.FEE_CONFIRMATION)) {
            holder.status.setVisibility(View.VISIBLE);
            holder.status.setText("Status ! Pending ");
            holder.status.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_alert_red, 0);
            holder.btnStartStage.setText("Apply Loan");

            holder.btnStartStage.setAlpha(Float.parseFloat("0.2"));

        } else if (stage.getName().equalsIgnoreCase(AppConstantsUtils.StageUtils.WALLET)) {

            holder.btnStartStage.setVisibility(View.VISIBLE);
            holder.btnStartStage.setText("TAKE SURVEY");

        } else if (stage.getName().equalsIgnoreCase(AppConstantsUtils.StageUtils.STATUS_NOT_EVALUATION)) {

            holder.btnStartStage.setVisibility(View.GONE);

        } else if (stage.getName().equalsIgnoreCase(AppConstantsUtils.StageUtils.WALLET)) {

            holder.btnStartStage.setText("ADD");

        } else if (stage.getName().equalsIgnoreCase(AppConstantsUtils.StageUtils.STATUS_REPAY)) {

            if (stage.getId() == 1) {
                holder.btnStartStage.setVisibility(View.VISIBLE);
                holder.btnStartStage.setText("REPAY NOW");
            } else if(stage.getId() == 0){
                holder.btnStartStage.setVisibility(View.VISIBLE);
                holder.btnStartStage.setText("REPAY NOW");
            }
                else if (stage.getId() == 3) {
                holder.btnStartStage.setVisibility(View.VISIBLE);
                holder.btnStartStage.setText("APPLY AGAIN");
            } else {
                holder.btnStartStage.setVisibility(View.GONE);
            }

        } else if (stage.getName()
                .equalsIgnoreCase(AppConstantsUtils
                        .StageUtils.STATUS_LOANAPPLICATION) || stage.getName()
                .equalsIgnoreCase("recovery_issue")) {
            holder.btnStartStage.setVisibility(View.VISIBLE);
            holder.btnStartStage.setText("Find Out More");



        }
        else if (stage.getName()
                .equalsIgnoreCase(AppConstantsUtils
                        .StageUtils.STATUS_LOANAPPLICATIONR)){
            holder.btnStartStage.setVisibility(View.VISIBLE);
            holder.btnStartStage.setText("Apply Again");


        }
        else if (stage.getName().equalsIgnoreCase(AppConstantsUtils.StageUtils.IN_PROCESS)) {

            holder.btnStartStage.setText("Apply Loan");
        } else if (stage.getName().equalsIgnoreCase(AppConstantsUtils.StageUtils.STATUS_LOANAPPLICATION)){

        } else {
            holder.btnStartStage.setText("Start");
        }


        holder.btnStartStage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onItemClickListener.onItemClick(view, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvHeading, tvDesc, status;
        ImageView ivStageLogo;
        Button btnStartStage;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            status = itemView.findViewById(R.id.status);
            tvHeading = itemView.findViewById(R.id.tv_step_title);
            tvDesc = itemView.findViewById(R.id.tv_step_desc);
            ivStageLogo = itemView.findViewById(R.id.iv_stage_logo);
            btnStartStage = itemView.findViewById(R.id.btn_start_profile);
        }
    }

    private Drawable getDrawable(String name) {
        try {
            int resourceId = context.getResources().getIdentifier(name,
                    "drawable", context.getPackageName());
            return context.getResources().getDrawable(resourceId);
        } catch (RuntimeException e) {
            return null;
        }

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
