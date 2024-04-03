package com.nano.liteloan.utils;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.presentation.adapters.IndicatorAdapter;

/**
 * Created by Muhammad Ahmad.
 */
public class SnapHelperOneByOne extends LinearSnapHelper {
    private IndicatorAdapter indicatorAdapter;

    public SnapHelperOneByOne() {

    }

    public SnapHelperOneByOne(IndicatorAdapter indicatorAdapter) {

        this.indicatorAdapter = indicatorAdapter;
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager
                                              layoutManager, int velocityX, int velocityY) {

        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return RecyclerView.NO_POSITION;
        }

        final View currentView = findSnapView(layoutManager);

        if (currentView == null) {
            return RecyclerView.NO_POSITION;
        }

        LinearLayoutManager myLayoutManager = (LinearLayoutManager) layoutManager;

        int position1 = myLayoutManager.findFirstVisibleItemPosition();
        int position2 = myLayoutManager.findLastVisibleItemPosition();

        int currentPosition = layoutManager.getPosition(currentView);

        if (velocityX > 400) {
            currentPosition = position2;
        } else if (velocityX < 400) {
            currentPosition = position1;
        }

        if (currentPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION;
        }
        if (indicatorAdapter != null) {
            indicatorAdapter.changeIndicator(currentPosition);
        }

        return currentPosition;
    }

}
