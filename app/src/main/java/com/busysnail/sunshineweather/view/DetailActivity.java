package com.busysnail.sunshineweather.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.busysnail.sunshineweather.Constants;
import com.busysnail.sunshineweather.R;
import com.busysnail.sunshineweather.Util;
import com.busysnail.sunshineweather.model.Weather;
import com.busysnail.sunshineweather.presenter.DetailPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * author: malong on 2016/9/1
 * email: malong_ilp@163.com
 * address: Xidian University
 */

public class DetailActivity extends AppCompatActivity implements DetailMvpView {

    private DetailPresenter presenter;

    private Toolbar toolbar;
    private TextView updateTime;
    private TextView airIndex;
    private TextView airInfo1;
    private TextView airInfo2;
    private TextView dressIndex;
    private TextView dressDes;
    private TextView sportIndex;
    private TextView sportDes;
    private TextView travalIndex;
    private TextView travalDes;
    private ImageView weatherIcon;
    private TextView basicCond;
    private TextView basicWind;
    private TextView basicOther;
    private TextView basicInfo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DetailPresenter();
        presenter.attach(this);

        setContentView(R.layout.activity_detail);
        initView();

        Weather weather = (Weather) getIntent().getSerializableExtra(Constants.WEATHER);
       presenter.showDetail(weather);
    }

    private void initView() {
        updateTime= (TextView) findViewById(R.id.update_time);
        airIndex = (TextView) findViewById(R.id.air_index);
        airInfo1 = (TextView) findViewById(R.id.air_info_1);
        airInfo2 = (TextView) findViewById(R.id.air_info_2);
        dressIndex = (TextView) findViewById(R.id.dress_index);
        dressDes = (TextView) findViewById(R.id.dress_des);
        sportIndex = (TextView) findViewById(R.id.sport_index);
        sportDes = (TextView) findViewById(R.id.sport_des);
        travalIndex = (TextView) findViewById(R.id.travel_index);
        travalDes = (TextView) findViewById(R.id.travel_des);
        weatherIcon= (ImageView) findViewById(R.id.weather_icon);
        basicCond= (TextView) findViewById(R.id.basic_cond);
        basicWind= (TextView) findViewById(R.id.basic_wind);
        basicOther= (TextView) findViewById(R.id.basic_other);
        basicInfo= (TextView) findViewById(R.id.basic_info);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }



    public static Intent newIntent(Context context, Weather weather) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(Constants.WEATHER, weather);
        return intent;
    }


    @Override
    public Context getContext() {
        return this;
    }

    public void showCity(Weather.BasicEntity basicEntity) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(String.format("%s 详细天气信息",basicEntity.city));
        }
    }

    @Override
    public void showBasic(Weather weather) throws Exception {
        Weather.DailyForecastEntity entity=weather.dailyForecast.get(0);
        Picasso.with(this)
                .load(Constants.ICON_URL + weather.now.cond.code + ".png")
                .placeholder(R.drawable.holding_icon)
                .error(R.drawable.holding_icon)
                .into(weatherIcon);
        basicInfo.setText(String.format("今天是：%s  %s",
                weather.dailyForecast.get(0).date,
                Util.dayForWeek( entity.date)));
        basicCond.setText(String.format("白天：%5s     夜间：%5s",
                entity.cond.txtD,
                entity.cond.txtN));
        basicWind.setText(String.format("风力：%5s    风向：%5s\n风速：%4skm/h",
                entity.wind.sc,
                entity.wind.dir,
                entity.wind.spd
                ));
        basicOther.setText(String.format("湿度：%5s%%     气压%5s hPa",
                entity.hum,
                entity.pres));
    }

    @Override
    public void showUpdateTime(Weather.BasicEntity basicEntity) {
        updateTime.setText(String.format("更新时间：%s",basicEntity.update.loc.split(" ")[1]));
    }

    @Override
    public void showAirQuality(Weather.AqiEntity aqiEntity) {
        airIndex.setText(String.format("空气污染指数：    %s          %s",aqiEntity.city.aqi,aqiEntity.city.qlty));
        airInfo1.setText(String.format("CO    ：%10s       NO2：%10s       SO2：%10s",aqiEntity.city.co,aqiEntity.city.no2,aqiEntity.city.so2));
        airInfo2.setText(String.format("PM2.5：%10s   PM10：%8s     O3： %10s",aqiEntity.city.pm25,aqiEntity.city.pm10,aqiEntity.city.o3));

    }

    @Override
    public void showSuggestion(Weather.SuggestionEntity suggestionEntity) {
        dressIndex.setText(String.format("穿衣指数---%s", suggestionEntity.drsg.brf));
        dressDes.setText(suggestionEntity.drsg.txt);

        sportIndex.setText(String.format("运动指数---%s", suggestionEntity.sport.brf));
        sportDes.setText(suggestionEntity.sport.txt);

        travalIndex.setText(String.format("旅游指数---%s",suggestionEntity.trav.brf));
        travalDes.setText(suggestionEntity.trav.txt);

    }
}
