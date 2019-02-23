package com.example.jakub.iweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jakub.iweather.Http.httpApi;
import com.example.jakub.iweather.Models.WeatherInfo;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchCity extends AppCompatActivity {

    private String city;
    private String country;
    private TextView errorTextView;
    private httpApi weatherApi;
    private Intent mainActivity;
    private EditText inputCity;
    private EditText inputCountry;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherApi = retrofit.create(httpApi.class);
        errorTextView = findViewById(R.id.error_text);
        inputCity = findViewById(R.id.input_city);
        inputCountry = findViewById(R.id.input_country);
        btn = findViewById(R.id.button_search);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeatherInfo(inputCity.getText().toString(), inputCountry.getText().toString());
            }
        });

    }

    public void getWeatherInfo(String city, String country) {
        city = city.replace(" ", "+");
        country = country.replace(" ", "+");
        String location = city + "," + country;
        Call<WeatherInfo> call = weatherApi.getWeatherByCity(location);

        call.enqueue(new Callback<WeatherInfo>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                if (!response.isSuccessful()) {
                    errorTextView.setText(getString(R.string.not_found_city));
                    return;
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("city", inputCity.getText().toString());
                    intent.putExtra("country", inputCountry.getText().toString());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }

            }
            @Override
            public void onFailure(Call<WeatherInfo> call, Throwable t) {
            }
        });
    }
}
