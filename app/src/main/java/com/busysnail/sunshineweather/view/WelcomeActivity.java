package com.busysnail.sunshineweather.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

/**
 * author: malong on 2016/9/6
 * email: malong_ilp@163.com
 * address: Xidian University
 */

public class WelcomeActivity extends AppCompatActivity {

    private DelayHandler mHandler=new DelayHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler.sendEmptyMessageDelayed(1,1000);
    }

    class DelayHandler extends Handler{
        private WeakReference<WelcomeActivity> mActivity;

        public DelayHandler(WelcomeActivity activity) {
            super();
            mActivity=new WeakReference<WelcomeActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
            mActivity.get().startActivity(intent);
            mActivity.get().finish();
        }
    }
}
