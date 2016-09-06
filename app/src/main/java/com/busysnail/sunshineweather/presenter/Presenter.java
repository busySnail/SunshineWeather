package com.busysnail.sunshineweather.presenter;


public interface Presenter<V> {
    void attach(V view);

    void detachView();
}
