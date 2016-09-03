package com.busysnail.sunshineweather.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.busysnail.sunshineweather.Constants;
import com.busysnail.sunshineweather.R;
import com.busysnail.sunshineweather.model.Weather;
import com.busysnail.sunshineweather.presenter.DetailPresenter;

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
    public void showUpdateTime(Weather.BasicEntity basicEntity) {
        updateTime.setText(String.format("更新时间：%s",basicEntity.update.loc));
    }

    @Override
    public void showAirQuality(Weather.AqiEntity aqiEntity) {

    }

    @Override
    public void showSuggestion(Weather.SuggestionEntity suggestionEntity) {

    }
}
