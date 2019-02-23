package com.example.jakub.iweather.Models;

public class City {
    private int id;
    private String name;
    private Coord coord;
    private String country;
    private int population;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coord getCoord() {
        return coord;
    }

    public String getCountry() {
        return country;
    }

    public int getPopulation() {
        return population;
    }
}
