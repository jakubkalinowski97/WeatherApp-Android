package com.example.jakub.iweather;

import android.app.Application;
import android.content.Context;

public class iWeather extends Application {
    private static iWeather instance;

    public iWeather() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }
}
