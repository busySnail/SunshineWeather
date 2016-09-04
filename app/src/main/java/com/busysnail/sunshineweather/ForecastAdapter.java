package com.busysnail.sunshineweather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.busysnail.sunshineweather.model.Weather;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * author: malong on 2016/9/1
 * email: malong_ilp@163.com
 * address: Xidian University
 */

public class ForecastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private OnItemClickListener listener;
    private LayoutInflater inflater;
    private Weather mWeather;
    private List<Weather.DailyForecastEntity> mForcastInfo;

    public static final int TYPE_BASIC = 0x0;
    public static final int TYPE_FORECAST = 0x1;


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BASIC;
        }
        return TYPE_FORECAST;
    }

    public ForecastAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public void setWeather(Weather weather) {
        Log.d("busysnail",weather.toString());
        this.mWeather = weather;
        mForcastInfo = weather.dailyForecast;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_BASIC:
                return new BasicViewHolder(inflater.inflate(R.layout.item_basic, parent, false));
            case TYPE_FORECAST:
                return new ForecastViewHolder(inflater.inflate(R.layout.item_forecast, parent, false));
            default:
                return null;
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        switch (itemType) {
            case TYPE_BASIC:
                BasicViewHolder basicHolder = (BasicViewHolder) holder;

                basicHolder.cityname.setText(String.format(" %s . %s", mWeather.basic.cnty, mWeather.basic.city));
                basicHolder.nowTemp.setText(String.format("%s℃  ", mWeather.now.tmp));
                basicHolder.maxTemp.setText(String.format("↑ %s °", mWeather.dailyForecast.get(0).tmp.max));
                basicHolder.minTemp.setText(String.format("↓ %s °", mWeather.dailyForecast.get(0).tmp.min));
                Picasso.with(mContext)
                        .load(Constants.ICON_URL + mWeather.now.cond.code + ".png")
                        .placeholder(R.drawable.holding_icon)
                        .error(R.drawable.holding_icon)
                        .into(basicHolder.weatherIcon);
                //这里有个坑，如果查询外国城市，那么Weather.aqi这一项是没有的，所以setText之前必须判空，否则会有空指针异常
                if (mWeather.aqi != null) {
                    basicHolder.airPM.setText(String.format("PM2.5： %s", mWeather.aqi.city.pm25));
                    basicHolder.airQuality.setText(String.format("空气质量： %s", mWeather.aqi.city.qlty));
                } else {
                    basicHolder.airPM.setText(String.format("PM2.5： %s", "无资料"));
                    basicHolder.airQuality.setText(String.format("空气质量： %s", "无资料"));
                }

                break;

            case TYPE_FORECAST:
                Weather.DailyForecastEntity dailyForecastEntity = mForcastInfo.get(position);
                ForecastViewHolder forecastHolder = (ForecastViewHolder) holder;

                try {
                    if(position==1){
                        forecastHolder.forcastDate.setText("明天");
                    }else{
                        forecastHolder.forcastDate.setText(Util.dayForWeek(dailyForecastEntity.date));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                forecastHolder.forcastTemp.setText(String.format("↓%s° ↑%s°",
                        dailyForecastEntity.tmp.min,
                        dailyForecastEntity.tmp.max));
                forecastHolder.forcastText.setText(String.format("%s  %s %s %s km/h 降水概率 %s%%",
                        dailyForecastEntity.cond.txtD,
                        dailyForecastEntity.wind.sc,
                        dailyForecastEntity.wind.dir,
                        dailyForecastEntity.wind.spd,
                        dailyForecastEntity.pop));
                Picasso.with(mContext)
                        .load(Constants.ICON_URL + dailyForecastEntity.cond.codeD + ".png")
                        .placeholder(R.drawable.holding_icon)
                        .error(R.drawable.holding_icon)
                        .into(forecastHolder.forcastIcon);
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        if(mForcastInfo!=null){
            return mForcastInfo.size();
        }
       return 0;
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

    class BasicViewHolder extends RecyclerView.ViewHolder {
        View contentlayout;
        ImageView weatherIcon;
        TextView cityname;
        TextView nowTemp;
        TextView minTemp;
        TextView maxTemp;
        TextView airPM;
        TextView airQuality;

        public BasicViewHolder(View itemView) {
            super(itemView);
            contentlayout = itemView.findViewById(R.id.cardview);
            weatherIcon = (ImageView) itemView.findViewById(R.id.weather_icon);
            cityname = (TextView) itemView.findViewById(R.id.city_name);
            nowTemp = (TextView) itemView.findViewById(R.id.temp_now);
            maxTemp = (TextView) itemView.findViewById(R.id.temp_max);
            minTemp = (TextView) itemView.findViewById(R.id.temp_min);
            airPM = (TextView) itemView.findViewById(R.id.air_pm25);
            airQuality = (TextView) itemView.findViewById(R.id.air_quality);

            contentlayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(mWeather);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Weather weather);
    }

    public void setCallback(OnItemClickListener listener) {
        this.listener = listener;
    }

}
