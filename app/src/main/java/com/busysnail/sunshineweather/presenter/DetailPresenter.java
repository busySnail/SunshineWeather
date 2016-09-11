package com.busysnail.sunshineweather.presenter;

import com.busysnail.sunshineweather.model.Weather;
import com.busysnail.sunshineweather.view.DetailMvpView;


public class DetailPresenter implements Presenter<DetailMvpView> {

    private DetailMvpView detailMvpView;

    @Override
    public void attach(DetailMvpView view) {
        this.detailMvpView = view;
    }

    @Override
    public void detachView() {
        this.detailMvpView = null;

    }

    public void showDetail(Weather weather) {
        detailMvpView.showCity(weather.basic);

        try {
            detailMvpView.showBasic(weather);
        } catch (Exception e) {
            e.printStackTrace();
        }
        detailMvpView.showUpdateTime(weather.basic);
        if(weather.aqi!=null){
            detailMvpView.showAirQuality(weather.aqi);
            detailMvpView.showSuggestion(weather.suggestion);
        }


    }

}
