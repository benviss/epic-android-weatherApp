package com.men.schmweather.models;


import java.util.ArrayList;

public class Weather {
    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    private String city;
    private String country;
    private ArrayList<Daycast> forecasts = new ArrayList<>();
}
