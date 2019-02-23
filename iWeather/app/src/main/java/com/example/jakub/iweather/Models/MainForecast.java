package com.example.jakub.iweather.Models;

public class MainForecast {
    private float temp;
    private float temp_min;
    private float temp_max;
    private float temp_pressure;
    private float sea_level;
    private float grnd_level;
    private int humidity;
    private float temp_kf;

    public float getTemp() {
        return temp;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }

    public float getTemp_pressure() {
        return temp_pressure;
    }

    public float getSea_level() {
        return sea_level;
    }

    public float getGrnd_level() {
        return grnd_level;
    }

    public int getHumidity() {
        return humidity;
    }

    public float getTemp_kf() {
        return temp_kf;
    }
}
