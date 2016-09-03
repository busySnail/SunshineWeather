package com.busysnail.sunshineweather.presenter;

import com.busysnail.sunshineweather.model.Weather;
import com.busysnail.sunshineweather.view.DetailMvpView;

/**
 * author: malong on 2016/9/3
 * email: malong_ilp@163.com
 * address: Xidian University
 */

public class DetailPresenter implements Presenter<DetailMvpView> {

    private DetailMvpView detailMvpView;

    @Override
    public void attach(DetailMvpView view) {
        this.detailMvpView=view;
    }

    @Override
    public void detachView() {
        this.detailMvpView=null;

    }

    public void showDetail(Weather weather){
        detailMvpView.showCity(weather.basic);

        detailMvpView.showUpdateTime(weather.basic);
        detailMvpView.showAirQuality(weather.aqi);
        detailMvpView.showSuggestion(weather.suggestion);
    }

}
