package com.busysnail.sunshineweather.presenter;

/**
 * author: malong on 2016/9/3
 * email: malong_ilp@163.com
 * address: Xidian University
 */

public interface Presenter<V> {
    void attach(V view);
    void detachView();
}
