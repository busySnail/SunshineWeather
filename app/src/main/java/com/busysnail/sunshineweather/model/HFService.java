package com.busysnail.sunshineweather.model;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author: malong on 2016/9/1
 * email: malong_ilp@163.com
 * address: Xidian University
 */

public interface HFService {

    String HOST = "https://api.heweather.com/x3/";

    @GET("weather")
    Observable<WeatherAPI> mWeatherAPI(@Query("city") String city, @Query("key") String key);

    class Factory {
        public static HFService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(" https://api.heweather.com/x3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(HFService.class);
        }
    }
}
