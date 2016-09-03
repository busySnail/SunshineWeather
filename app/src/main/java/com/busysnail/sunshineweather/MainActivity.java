package com.busysnail.sunshineweather;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.busysnail.sunshineweather.model.HFService;
import com.busysnail.sunshineweather.model.Weather;
import com.busysnail.sunshineweather.model.WeatherAPI;

import java.util.List;
import java.util.Observable;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String KEY = "f1eec20bca1e4c899dff787c8eeed52f";

    private Subscription subscription;
    private RecyclerView reposRecycleView;
    private Toolbar toolbar;
    private EditText editTextUsername;
    private ProgressBar progressBar;
    private TextView infoTextView;
    private ImageButton searchButton;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        infoTextView = (TextView) findViewById(R.id.text_info);
        imageView= (ImageView) findViewById(R.id.imageview);
        //Set up ToolBar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //有问题
        //Set up RecyclerView
        reposRecycleView = (RecyclerView) findViewById(R.id.repos_recycler_view);
        setupRecyclerView(reposRecycleView);
        // Set up search button
        searchButton = (ImageButton) findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadForecastInfo(editTextUsername.getText().toString());
            }
        });
        //Set up username EditText
        editTextUsername = (EditText) findViewById(R.id.edit_text_username);
        editTextUsername.addTextChangedListener(mHideShowButtonTextWatcher);
        editTextUsername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String username = editTextUsername.getText().toString();
                    if (username.length() > 0) loadForecastInfo(username);
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null) subscription.unsubscribe();
    }

    public void loadForecastInfo(String city) {
        progressBar.setVisibility(View.VISIBLE);
        reposRecycleView.setVisibility(View.GONE);
        infoTextView.setVisibility(View.GONE);
        SunShineApplication application = SunShineApplication.get(this);
        HFService hfService = application.getHFService();
        subscription = hfService.hfWeather(city, KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .filter(new Func1<WeatherAPI, Boolean>() {
                    @Override
                    public Boolean call(WeatherAPI weatherAPI) {
                        return !weatherAPI.mHeWeatherDataService30s.get(0).status.equals("unknown city");
                    }
                })
                .map(new Func1<WeatherAPI, Weather>() {
                    @Override
                    public Weather call(WeatherAPI weatherAPI) {
                        return weatherAPI.mHeWeatherDataService30s.get(0);
                    }
                })
                .subscribe(new Subscriber<Weather>() {
                    @Override
                    public void onCompleted() {

                        progressBar.setVisibility(View.GONE);
                        if (reposRecycleView.getAdapter().getItemCount() > 0) {
                            reposRecycleView.requestFocus();
                            hideSoftKeyboard();
                            reposRecycleView.setVisibility(View.VISIBLE);
                        } else {
                            infoTextView.setText(R.string.text_empty_repos);
                            infoTextView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error loading GitHub repos ", e);
                        progressBar.setVisibility(View.GONE);
                        if (e instanceof HttpException
                                && ((HttpException) e).code() == 404) {
                            infoTextView.setText(R.string.error_username_not_found);
                        } else {
                            infoTextView.setText(R.string.error_loading_repos);
                        }
                        infoTextView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(Weather weather) {
                        Log.i(TAG, "Repos loaded " + weather);
                        ForecastAdapter adapter =
                                (ForecastAdapter) reposRecycleView.getAdapter();
                        adapter.setWeather(weather);
                        adapter.notifyDataSetChanged();
                    }
                });

        imageView.setVisibility(View.GONE);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        ForecastAdapter adapter = new ForecastAdapter(this.getApplicationContext());
        adapter.setCallback(new ForecastAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Weather weather) {
                startActivity(DetailActivity.newIntent(MainActivity.this,weather));
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextUsername.getWindowToken(), 0);
    }

    private TextWatcher mHideShowButtonTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            searchButton.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
