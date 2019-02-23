package com.example.jakub.iweather.Http;

import com.example.jakub.iweather.Models.ForecastInfo;
import com.example.jakub.iweather.Models.Weather;
import com.example.jakub.iweather.Models.WeatherInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface httpApi {

    @GET("weather?APPID=ed1c233e0003d5043faaf83d827e916e&units=metric")
    Call<WeatherInfo> getWeatherByCity(
            @Query("q") String location);

    @GET("weather?APPID=ed1c233e0003d5043faaf83d827e916e&units=metric")
    Call<WeatherInfo> getWeatherByCoord(
            @Query("lat") String lat,
            @Query("lon") String lon);

    @GET("forecast?APPID=ed1c233e0003d5043faaf83d827e916e&units=metric")
    Call<ForecastInfo> getForecastByCity(
            @Query("q") String location);

    @GET("forecast?APPID=ed1c233e0003d5043faaf83d827e916e&units=metric")
    Call<ForecastInfo> getForecastByCoord(
            @Query("lat") String lat,
            @Query("lon") String lon);
}
