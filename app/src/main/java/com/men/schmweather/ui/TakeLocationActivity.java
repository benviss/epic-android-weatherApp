package com.men.schmweather.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.men.schmweather.R;
import com.men.schmweather.models.Weather;
import com.men.schmweather.services.WeatherService;
import java.io.IOException;
import java.util.ArrayList;
import java.io.Serializable;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TakeLocationActivity extends AppCompatActivity {
    ArrayList<Weather> mWeathers = new ArrayList<>();
    @Bind(R.id.userSubmitZipcode) EditText mUserSubmitZipcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_location);

        ButterKnife.bind(this);
    }


    public void onClickSubmitUserZipCode (View v) {
        String userZipCode = mUserSubmitZipcode.getText().toString();
        if(userZipCode.length() == 5) {
           getWeathers(userZipCode);
        } else {
            Toast.makeText(TakeLocationActivity.this, "Please Enter Valid Zipcode", Toast.LENGTH_SHORT).show();
        }
    }

    private void getWeathers(String location) {
        final WeatherService weatherService = new WeatherService();
        weatherService.findWeathers(location, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) {
                mWeathers = weatherService.processResults(response);
                TakeLocationActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] cityNames = new String[mWeathers.size()];
                        for (int i = 0; i < cityNames.length; i++) {
                            cityNames[i] = mWeathers.get(i).getCity();
                            Log.d("Ben and Matt's city", cityNames[i]);
                        }
                    }

                });
                if(mWeathers.size() > 0) {
                    Log.d("onLocation", Integer.toString(mWeathers.size()));
                    Weather weather = mWeathers.get(0);
                    Log.d("city name Test", weather.getCity());
                    Intent newIntent = new Intent(TakeLocationActivity.this, ForecastActivity.class);
                    newIntent.putExtra("weather", weather);
                    startActivity(newIntent);
                }
//                stop laodin visual
            }
        });
    }
}
