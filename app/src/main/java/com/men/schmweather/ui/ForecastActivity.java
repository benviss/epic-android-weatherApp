package com.men.schmweather.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.men.schmweather.R;
import com.men.schmweather.models.Weather;
import com.men.schmweather.services.WeatherService;

import java.io.IOException;
import java.util.ArrayList;
import java.io.Serializable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ForecastActivity extends AppCompatActivity {
    ArrayList<Weather> mWeathers = new ArrayList<>();
    Weather weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        Intent intent = getIntent();
        weather = (Weather) intent.getSerializableExtra("weather");
        Log.d("success",weather.getCity());
    }

}
