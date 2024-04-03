package com.nano.liteloan.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.presentation.activity.MainActivity;

/**
 * Created by Muhammad Ahmad.
 */
public class IndicatorAdapter extends RecyclerView.Adapter<IndicatorAdapter.ViewHolder> {


    private int size = 0;
    private int selectedPosition = 0;
    private MainActivity context;

    public IndicatorAdapter(MainActivity context, int size) {
        this.size = size;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.indicator_layout,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (position == selectedPosition) {

            holder.ivIndicator
                    .setBackground(ContextCompat.getDrawable(context, R.drawable.bt_gradient));

            holder.ivIndicator.setLayoutParams(new LinearLayout.LayoutParams(
                    context.getResources().getDimensionPixelOffset(R.dimen.indicator_width_selected),
                    context.getResources().getDimensionPixelOffset(R.dimen.indicator_height_selected)));

        } else {
            holder.ivIndicator
                    .setBackground(ContextCompat.getDrawable(context, R.drawable.round_light_white_background));
            holder.ivIndicator.setLayoutParams(new LinearLayout.LayoutParams(
                    context.getResources().getDimensionPixelOffset(R.dimen.indicator_width),
                    context.getResources().getDimensionPixelOffset(R.dimen.indicator_height)));
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout ivIndicator;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIndicator = itemView.findViewById(R.id.iv_indicator);

        }
    }

    public void changeIndicator(int position) {

        this.selectedPosition = position;
        this.notifyDataSetChanged();
    }
}
