package com.example.jakub.iweather.Models;

import java.util.ArrayList;

public class WeatherInfo {

    private ArrayList<Weather> weather;
    private Coord coord;
    private Main main;
    private int dt;
    private String name;
    private String base;
    private int visibility;
    private Wind wind;
    private Clouds clouds;
    private int id;
    private int cod;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDate() {
        return dt;
    }

    public void setDate(int date) {
        this.dt = date;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
    }
}
