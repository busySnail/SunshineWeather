package com.busysnail.sunshineweather.presenter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.busysnail.sunshineweather.Constants;
import com.busysnail.sunshineweather.ForecastAdapter;
import com.busysnail.sunshineweather.R;
import com.busysnail.sunshineweather.SunShineApplication;
import com.busysnail.sunshineweather.model.HFService;
import com.busysnail.sunshineweather.model.Weather;
import com.busysnail.sunshineweather.model.WeatherAPI;
import com.busysnail.sunshineweather.view.MainMvpView;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * author: malong on 2016/9/3
 * email: malong_ilp@163.com
 * address: Xidian University
 */

public class MainPresenter implements Presenter<MainMvpView> {

    private MainMvpView mainMvpView;
    private Subscription subscription;
    private Weather weather;

    @Override
    public void attach(MainMvpView view) {

        this.mainMvpView=view;
    }

    @Override
    public void detachView() {

        this.mainMvpView=null;
        if(subscription!=null){
            subscription.unsubscribe();
        }
    }

    public void loadForecast(String cityNameEntered){
        String cityName=cityNameEntered.trim();
        if(cityName.isEmpty()) return;
        Log.d("findbug","loadforecast cityname:"+cityName);

        mainMvpView.showProgressIndicator();
        if (subscription != null) subscription.unsubscribe();
        SunShineApplication application=SunShineApplication.get(mainMvpView.getContext());
        HFService hfService=application.getHFService();
        Log.d("findbug","subscription begin");
        subscription=hfService.hfWeather(cityName, Constants.KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .filter(new Func1<WeatherAPI, Boolean>() {
                    @Override
                    public Boolean call(WeatherAPI weatherAPI) {
                        Log.d("findbug","weatherAPI"+weatherAPI);
                        return !weatherAPI.mHeWeatherDataService30s.get(0).status.equals("unknown city");
                    }
                })
                .map(new Func1<WeatherAPI, Weather>() {
                    @Override
                    public Weather call(WeatherAPI weatherAPI) {
                        Log.d("findbug","map"+weatherAPI.mHeWeatherDataService30s.get(0));
                        return weatherAPI.mHeWeatherDataService30s.get(0);
                    }
                })
                .subscribe(new Subscriber<Weather>() {
                    @Override
                    public void onCompleted() {

                        Log.d("busysnailtest-complete","weather"+weather);
                        if(weather!=null){
                            mainMvpView.showForecast(weather);
                        }else{
                            mainMvpView.showMessage(R.string.text_empty_repos);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if(isHttp404(e)){
                            mainMvpView.showMessage(R.string.error_username_not_found);

                        }else{
                            mainMvpView.showMessage(R.string.error_loading_repos);
                        }
                    }

                    @Override
                    public void onNext(Weather weather) {

                        Log.d("busysnailtest-next","weather"+weather);
                        MainPresenter.this.weather=weather;
                    }
                });

    }

    private static boolean isHttp404(Throwable error) {
        return error instanceof HttpException && ((HttpException) error).code() == 404;
    }


}
