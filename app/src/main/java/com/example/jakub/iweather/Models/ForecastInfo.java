package com.example.jakub.iweather.Models;

import java.util.ArrayList;

public class ForecastInfo{
    private float message;
    private int cnt;
    private int cod;
    private ArrayList<Forecast> list;
    private City city;

    public float getMessage() {
        return message;
    }

    public int getCnt() {
        return cnt;
    }

    public int getCod() {
        return cod;
    }

    public City getCity() {
        return city;
    }

    public ArrayList<Forecast> getList() {
        return list;
    }
}
