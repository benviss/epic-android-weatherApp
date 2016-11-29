package com.men.schmweather.services;
import android.util.Log;

import com.men.schmweather.Constants;
import com.men.schmweather.models.Daycast;
import com.men.schmweather.models.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;


public class WeatherService {

    public static void findWeathers(String location, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
            .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.WEATHER_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.WEATHER_API_QUERY, Constants.WEATHER_KEY);
        urlBuilder.addQueryParameter(Constants.WEATHER_LOCATION_QUERY, location);
        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
            .url(url)
            .build();
        Log.d("test", request.toString());
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public Weather processResults(Response response) {
        ArrayList<Daycast> daycasts = new ArrayList<>();
        Weather weather;

        try {
            String jsonData = response.body().string();
            if(response.isSuccessful()) {
                JSONObject weatherJSON = new JSONObject(jsonData);
                String city = weatherJSON.getJSONObject("city").getString("name");
                JSONObject daycastJSON = weatherJSON.getJSONObject("list");
                for (int i = 0; i < daycastJSON.length(); i++) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }


}
