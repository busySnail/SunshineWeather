package com.busysnail.sunshineweather.view;

import android.graphics.drawable.Drawable;

import com.busysnail.sunshineweather.model.Weather;

import java.util.List;


public interface DetailMvpView extends MvpView{

    void showUpdateTime(Weather.BasicEntity basicEntity);
    void showAirQuality(Weather.AqiEntity aqiEntity);
    void showSuggestion(Weather.SuggestionEntity suggestionEntity);
    void showCity(Weather.BasicEntity basicEntity);
    void showBasic(Weather weather) throws Exception;
}
