package com.nano.liteloan.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.info.SurveyQuestions;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.OnSurveyOptionClick;
import com.nano.liteloan.utils.SurvayAdapterListener;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */


public class SurveyAdapter
        extends RecyclerView.Adapter<SurveyAdapter.ViewHolder> {

    String answer = "strongly disagree";
    private SurvayAdapterListener mItemClickListener;

    private Context context;
    private int optionn = 0;
    private SurveyQuestions body;


    public SurveyAdapter(SurveyQuestions body, MainActivity context) {
        this.context = context;
        this.body = body;

    }

    class ViewHolder extends RecyclerView.ViewHolder implements SurvayAdapterListener {

        private TextView tvQuestion;
        private LinearLayout llMainLayout;
        private RecyclerView recyclerView;


        public ViewHolder(View itemView) {
            super(itemView);

            tvQuestion = itemView.findViewById(R.id.question);

            llMainLayout = itemView.findViewById(R.id.main_layout);
            recyclerView = itemView.findViewById(R.id.recyclerview);

            recyclerView.setLayoutManager(new LinearLayoutManager(context));


        }

        @Override
        public void onItemClick(int position, String option) {
            mItemClickListener.onItemClick(getPosition(), option);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.survey_items, parent, false);

        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        holder.tvQuestion.setText(body.questionnaire.get(position).question);
        SurveyOptionAdapter surveyoptAdapter = new SurveyOptionAdapter(context, body.questionnaire.get(position).mcqs);
        holder.recyclerView.setAdapter(surveyoptAdapter);

        surveyoptAdapter.setOnItemClickListener(new OnSurveyOptionClick() {
            @Override
            public void onItemClick(View view, String option) {
                mItemClickListener.onItemClick(body.questionnaire.get(position).id, option);
            }
        });


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }


    public void setOnItemClickListener(final SurvayAdapterListener mItemClickListener) {

        this.mItemClickListener = mItemClickListener;

    }


    @Override
    public int getItemCount() {
        return body.questionnaire.size();
    }


}

