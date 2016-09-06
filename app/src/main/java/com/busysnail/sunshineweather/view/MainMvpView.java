package com.busysnail.sunshineweather.view;

import com.busysnail.sunshineweather.model.Weather;


public interface MainMvpView extends MvpView {

    void showForecast(Weather weather);

    void showMessage(int stringId);

    void showProgressIndicator();
}
