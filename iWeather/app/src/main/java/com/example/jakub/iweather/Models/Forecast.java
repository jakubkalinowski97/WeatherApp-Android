package com.example.jakub.iweather.Models;

import java.util.ArrayList;

public class Forecast{

    private int dt;
    private String dt_txt;
    private MainForecast main;
    private ArrayList<Weather> weather;
    private Clouds clouds;
    private Wind wind;
    private SysForecast sysForecast;
    private Snow snow;

    public int getDt() {
        return dt;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public MainForecast getMain() {
        return main;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public SysForecast getSysForecast() {
        return sysForecast;
    }

    public Snow getSnow() {
        return snow;
    }
}
