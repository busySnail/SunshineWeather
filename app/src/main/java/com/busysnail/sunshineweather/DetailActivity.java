package com.busysnail.sunshineweather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.busysnail.sunshineweather.model.Weather;




/**
 * author: malong on 2016/9/1
 * email: malong_ilp@163.com
 * address: Xidian University
 */

public class DetailActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView test= (TextView) findViewById(R.id.tv_test);
        Weather weather= (Weather) getIntent().getSerializableExtra(Constants.WEATHER);
        test.setText(weather.toString());
    }

    public static Intent newIntent(Context context,Weather weather){
        Intent intent=new Intent(context,DetailActivity.class);
        intent.putExtra(Constants.WEATHER,weather);
        return intent;
    }
}
