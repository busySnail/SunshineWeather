package com.busysnail.sunshineweather;

import android.app.Application;
import android.content.Context;

import com.busysnail.sunshineweather.model.HFService;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * author: malong on 2016/9/1
 * email: malong_ilp@163.com
 * address: Xidian University
 */

public class SunShineApplication extends Application{
    private HFService hfService;
    private Scheduler defaultSubscribeScheduler;

    public static SunShineApplication get(Context context){
        return (SunShineApplication)context.getApplicationContext();
    }

    public HFService getHFService() {
        if (hfService == null) {
            hfService = HFService.Factory.create();
        }
        return hfService;
    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
        this.defaultSubscribeScheduler = scheduler;
    }

}
