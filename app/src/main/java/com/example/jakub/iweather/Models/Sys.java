package com.example.jakub.iweather.Models;

public class Sys {
    private String type;
    private int id;
    private float message;
    private String country;
    private int sunrise;
    private int sunset;

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public float getMessage() {
        return message;
    }

    public String getCountry() {
        return country;
    }

    public int getSunrise() {
        return sunrise;
    }

    public int getSunset() {
        return sunset;
    }
}
