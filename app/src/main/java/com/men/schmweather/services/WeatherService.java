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
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Weather> processResults(Response response) {
        ArrayList<Daycast> daycasts = new ArrayList<>();
        ArrayList<Weather> weathers = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            if(response.isSuccessful()) {
                JSONObject weatherJSON = new JSONObject(jsonData);
                String city = weatherJSON.getJSONObject("city").getString("name");
                String country = weatherJSON.getJSONObject("city").getString("country");
                JSONArray daycastJSON = weatherJSON.getJSONArray("list");
                for (int i = 0; i < daycastJSON.length(); i++) {
                    JSONObject dayJSON = daycastJSON.getJSONObject(i);
                    String date = Integer.toString(dayJSON.getInt("dt"));
                    JSONObject mainJSON = dayJSON.getJSONObject("main");
                    String temp = mainJSON.getString("temp");
                    String tempMin = mainJSON.getString("temp_min");
                    String tempMax = mainJSON.getString("temp_max");
                    String pressure = mainJSON.getString("pressure");
                    String seaLevel = mainJSON.getString("sea_level");
                    String grndLevel = mainJSON.getString("grnd_level");
                    String humidity = mainJSON.getString("humidity");
                    String tempKf = mainJSON.getString("temp_kf");
                    JSONObject weatherDetailJSON = dayJSON.getJSONArray("weather").getJSONObject(0);
                    String weatherMain = weatherDetailJSON.getString("main");
                    String weatherDescription= weatherDetailJSON.getString("description");
                    String weatherIcon = weatherDetailJSON.getString("icon");
                    JSONObject cloudsJSON = dayJSON.getJSONObject("clouds");
                    String clouds = cloudsJSON.getString("all");
                    JSONObject windJSON = dayJSON.getJSONObject("wind");
                    String windSpeed = windJSON.getString("speed");
                    String windDeg = windJSON.getString("deg");
                    Daycast newDay = new Daycast(date, temp, tempMin, tempMax, pressure, seaLevel, grndLevel, humidity, tempKf, weatherMain, weatherDescription, weatherIcon, clouds, windSpeed, windDeg);
                    daycasts.add(newDay);
                }
                Weather weather = new Weather(city, country, daycasts);
                weathers.add(weather);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weathers;
    }


}
