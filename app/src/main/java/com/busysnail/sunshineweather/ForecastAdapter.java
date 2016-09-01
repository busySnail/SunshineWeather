package com.busysnail.sunshineweather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.busysnail.sunshineweather.model.Weather;

import java.util.List;

/**
 * author: malong on 2016/9/1
 * email: malong_ilp@163.com
 * address: Xidian University
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private Callback mCallback;
    private List<Weather.DailyForecastEntity > mForcastInfo;



    public ForecastAdapter() {

    }

    public void setWeather( List<Weather.DailyForecastEntity > mForcastInfo) {
        this.mForcastInfo = mForcastInfo;
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast, parent, false);
        final ForecastViewHolder viewHolder = new ForecastViewHolder(itemView);
        viewHolder.contentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onItemClick();
            }
        });

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        Weather.DailyForecastEntity dailyForecastEntity=mForcastInfo.get(position);
        Context context=holder.contentLayout.getContext();

        holder.forcastDate.setText(dailyForecastEntity.date);
        holder.forcastTemp.setText(String.format("%s° %s°",
                dailyForecastEntity.tmp.min,
                dailyForecastEntity.tmp.max));
        holder.forcastText.setText( String.format("%s。 最高%s℃。 %s %s %s km/h。 降水几率 %s%%。",
                dailyForecastEntity.cond.txtD,
                dailyForecastEntity.tmp.max,
                dailyForecastEntity.wind.sc,
                dailyForecastEntity.wind.dir,
                dailyForecastEntity.wind.spd,
                dailyForecastEntity.pop));
        holder.forcastIcon.setImageResource(R.drawable.octocat);
    }

    @Override
    public int getItemCount() {
        return mForcastInfo.size();
    }

    interface Callback {
        void onItemClick();
    }

    class ForecastViewHolder extends RecyclerView.ViewHolder {

        View contentLayout;
        TextView forcastDate;
        TextView forcastTemp;
        TextView forcastText;
        ImageView forcastIcon;


        ForecastViewHolder(View itemView) {
            super(itemView);
            contentLayout = itemView.findViewById(R.id.layout_content);
            forcastDate = (TextView) itemView.findViewById(R.id.forecast_date);
            forcastTemp = (TextView) itemView.findViewById(R.id.forecast_temp);
            forcastText = (TextView) itemView.findViewById(R.id.forecast_txt);
            forcastIcon = (ImageView) itemView.findViewById(R.id.forecast_icon);

        }
    }


}
