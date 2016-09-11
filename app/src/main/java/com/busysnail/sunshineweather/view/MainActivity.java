package com.busysnail.sunshineweather.view;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.busysnail.sunshineweather.common.Constants;
import com.busysnail.sunshineweather.view.adapter.ForecastAdapter;
import com.busysnail.sunshineweather.R;
import com.busysnail.sunshineweather.model.Weather;
import com.busysnail.sunshineweather.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainMvpView {

    private MainPresenter presenter;

    private RecyclerView forecastRecyclerView;
    private Toolbar toolbar;
    private EditText editTextUsername;
    private ProgressBar progressBar;
    private TextView infoTextView;
    private ImageButton searchButton;
    private ImageView imageView;
    private View rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        presenter = new MainPresenter();
        presenter.attach(this);

        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        infoTextView = (TextView) findViewById(R.id.text_info);
        imageView = (ImageView) findViewById(R.id.imageview);
        rootLayout = findViewById(R.id.layout_root);
        //Set up ToolBar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Set up RecyclerView
        forecastRecyclerView = (RecyclerView) findViewById(R.id.repos_recycler_view);
        setupRecyclerView(forecastRecyclerView);
        // Set up search button
        searchButton = (ImageButton) findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadForecast(editTextUsername.getText().toString());
            }
        });
        //Set up username EditText
        editTextUsername = (EditText) findViewById(R.id.edit_text_username);
        editTextUsername.addTextChangedListener(mHideShowButtonTextWatcher);
        editTextUsername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    presenter.loadForecast(editTextUsername.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        ForecastAdapter adapter = new ForecastAdapter(this.getApplicationContext());
        adapter.setCallback(new ForecastAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Weather weather) {
                startActivity(DetailActivity.newIntent(MainActivity.this, weather),
                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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

    @Override
    public void showForecast(Weather weather) {
        ForecastAdapter adapter = (ForecastAdapter) forecastRecyclerView.getAdapter();
        adapter.setWeather(weather);
        adapter.notifyDataSetChanged();
        forecastRecyclerView.requestFocus();
        hideSoftKeyboard();
        progressBar.setVisibility(View.INVISIBLE);
        infoTextView.setVisibility(View.INVISIBLE);
        forecastRecyclerView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMessage(int stringId) {
        progressBar.setVisibility(View.INVISIBLE);
        infoTextView.setVisibility(View.VISIBLE);
        forecastRecyclerView.setVisibility(View.INVISIBLE);
        infoTextView.setText(getString(stringId));
    }
    // View.VISIBLE--->可见
    //View.INVISIBLE--->不可见，但这个View仍然会占用在xml文件中所分配的布局空间，不重新layout
    //View.GONE---->不可见，但这个View在ViewGroup中不保留位置，会重新layout，不再占用空间，那后面的view就会取代他的位置，

    //这里遇到一个坑，重构为MVP后，一直出现adapter中weather空指针异常
    //最终祸源是这里的View.GONE和View.INVISIBLE的区别引起的
    //在获的资源（weather）之前，如果设置为INVISIBLE，那么recyclerview是会被初始化的，只不过是不可见，而getItemCount()又和weather相关
    //而GONE则不被初始化
    //解决办法1.用GONE替代INVISIBLE 2.getItemCount()中判空，为空的话返回0

    @Override
    public void showProgressIndicator() {
        progressBar.setVisibility(View.VISIBLE);
        infoTextView.setVisibility(View.GONE);
        forecastRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Snackbar.make(rootLayout, "test", Snackbar.LENGTH_SHORT)
                        .setAction("这是Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "你点击了action", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                break;
            case R.id.action_github:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.GITHUB_URL)));
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
