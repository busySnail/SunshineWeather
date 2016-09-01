package com.busysnail.sunshineweather;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * author: malong on 2016/9/1
 * email: malong_ilp@163.com
 * address: Xidian University
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>{

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ForecastViewHolder extends RecyclerView.ViewHolder{

        public ForecastViewHolder(View itemView) {
            super(itemView);
        }
    }
}
