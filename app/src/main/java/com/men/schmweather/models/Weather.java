package com.men.schmweather.models;


import java.io.Serializable;
import java.util.ArrayList;

public class Weather implements Serializable{
    private String city;
    private String country;
    private ArrayList<Daycast> forecasts = new ArrayList<>();

    public Weather(String city, String country, ArrayList<Daycast> days) {
        this.forecasts = days;
        this.city = city;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

}
