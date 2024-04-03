package com.nano.liteloan.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.utils.OnSurveyOptionClick;

import java.util.List;


/**
 * Created by Muhammad Ahmad on 07/10/2020.
 */

public class SurveyOptionAdapter extends RecyclerView.Adapter<SurveyOptionAdapter.ViewHolder> {

    private Context context;
    List<String> mcqs;
    private OnSurveyOptionClick mItemClickListener;
    private int previousCheck = -1;

    public SurveyOptionAdapter(Context context, List<String> mcqs) {
        this.context = context;
        this.mcqs = mcqs;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements OnSurveyOptionClick {


        ImageView ivBullet;
        private TextView tvOpiton;
//        TextView tvRequestAmount;

        public ViewHolder(View itemView) {

            super(itemView);
            ivBullet = itemView.findViewById(R.id.ivstonglydisagree);
            tvOpiton = itemView.findViewById(R.id.tvstonglydisagree);

//            tvRequestAmount = itemView.findViewById(R.id.request_amount);
        }

        @Override
        public void onItemClick(View view, String position) {
            mItemClickListener.onItemClick(view, position);
        }
    }

    @Override
    public SurveyOptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.survey_options, parent, false);

        SurveyOptionAdapter.ViewHolder vh = new SurveyOptionAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final SurveyOptionAdapter.ViewHolder holder, final int position) {


        holder.tvOpiton.setText(mcqs.get(position));

        if (position == previousCheck) {
            holder.ivBullet.setBackgroundResource(R.drawable.ic_bullet_active);
        } else {
            holder.ivBullet.setBackgroundResource(R.drawable.ic_bullet);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.ivBullet.setBackgroundResource(R.drawable.ic_bullet_active);
                mItemClickListener.onItemClick(view, mcqs.get(position));
                previousCheck = position;
                //unckeckRest(holder, position);
                notifyDataSetChanged();
            }
        });


    }

    private void unckeckRest(ViewHolder holder, int position) {

        for (int i = 0; i < mcqs.size(); i++) {

            if (i != position) {
                holder.ivBullet.setBackgroundResource(R.drawable.ic_bullet);
            }
        }
    }

    public void setOnItemClickListener(final OnSurveyOptionClick mItemClickListener) {

        this.mItemClickListener = mItemClickListener;

    }


    @Override
    public int getItemCount() {
        return mcqs.size();
    }


}
