package com.example.jakub.iweather.Models;

import com.google.gson.annotations.SerializedName;

public class Snow {
    @SerializedName("3h")
    private float h3;

    public float getH3() {
        return h3;
    }
}
