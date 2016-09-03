package com.busysnail.sunshineweather.view;

import com.busysnail.sunshineweather.model.Weather;

/**
 * author: malong on 2016/9/3
 * email: malong_ilp@163.com
 * address: Xidian University
 */

public interface MainMvpView extends MvpView{

    void showForecast(Weather weather);

    void showMessage(int stringId);

    void showProgressIndicator();
}
