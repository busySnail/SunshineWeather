package com.busysnail.sunshineweather;

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
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView test= (TextView) findViewById(R.id.tv_test);
        Intent intent=getIntent();
        Weather weather= (Weather) intent.getSerializableExtra(Constants.WEATHER);

        if(weather!=null){
            Log.d("detailactivity",weather.toString());
            test.setText(weather.toString());
        }
    }
}
