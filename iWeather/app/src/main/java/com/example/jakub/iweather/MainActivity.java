package com.example.jakub.iweather;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jakub.iweather.Http.httpApi;
import com.example.jakub.iweather.Models.Forecast;
import com.example.jakub.iweather.Models.ForecastInfo;
import com.example.jakub.iweather.Models.Weather;
import com.example.jakub.iweather.Models.WeatherInfo;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewCity;
    private TextView textViewDayTemperature;
    private TextView textViewNightTemperature;
    private TextView textViewDate;
    private TextView textFirstDayTemp;
    private TextView textSecondDayTemp;
    private TextView textThirdDayTemp;
    private TextView textFourthDayTemp;
    private TextView textFifthDayTemp;
    private TextView textFirstDayName;
    private TextView textSecondDayName;
    private TextView textThirdDayName;
    private TextView textFourthDayName;
    private TextView textFifthDayName;
    private ImageView firstImageIcon;
    private ImageView secondImageIcon;
    private ImageView thirdImageIcon;
    private ImageView fourthImageIcon;
    private ImageView fifthImageIcon;
    private ImageView imageViewIcon;
    private httpApi weatherApi;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private String yourLocationLatitude;
    private String yourLocationLongitude;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewCity = findViewById(R.id.location);
        textViewDayTemperature = findViewById(R.id.dayTemperature);
        textViewNightTemperature = findViewById(R.id.nightTemperature);
        textViewDate = findViewById(R.id.date);
        imageViewIcon = findViewById(R.id.weatherIcon);
        textFirstDayTemp = findViewById(R.id.firstDayTemp);
        textFirstDayName = findViewById(R.id.firstDayName);
        firstImageIcon = findViewById(R.id.firstDayImage);

        textSecondDayTemp = findViewById(R.id.secondDayTemp);
        textSecondDayName = findViewById(R.id.secondDayName);
        secondImageIcon = findViewById(R.id.secondDayImage);

        textThirdDayTemp = findViewById(R.id.thirdDayTemp);
        textThirdDayName = findViewById(R.id.thirdDayName);
        thirdImageIcon = findViewById(R.id.thirdDayImage);

        textFourthDayTemp = findViewById(R.id.fourthDayTemp);
        textFourthDayName = findViewById(R.id.fourthDayName);
        fourthImageIcon = findViewById(R.id.fourthDayImage);

        textFifthDayTemp = findViewById(R.id.fifthDayTemp);
        textFifthDayName = findViewById(R.id.fifthDayName);
        fifthImageIcon = findViewById(R.id.fifthDayImage);

        createMenu();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherApi = retrofit.create(httpApi.class);
        getWeatherInfo("bialystok", "poland");
        getForecast("bialystok", "poland");
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                yourLocationLatitude = String.valueOf(location.getLatitude());
                yourLocationLongitude = String.valueOf(location.getLongitude());
                Log.d("lat:", yourLocationLatitude);
                Log.d("lon:", yourLocationLongitude);
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {

        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    public void createMenu() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.where_am_i) {
                    getWeatherByCoord(yourLocationLatitude, yourLocationLongitude);
                    getForecastByCoord(yourLocationLatitude, yourLocationLongitude);
                } else if(menuItem.getItemId() == R.id.search_city){
                    intent = new Intent(getApplicationContext(), SearchCity.class);
                    startActivityForResult(intent, 1000);
                } else if(menuItem.getItemId() == R.id.favourite_places){
                    intent = new Intent(getApplicationContext(), FavouritePlaces.class);
                    startActivityForResult(intent, 1000);
                }
                return false;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                getWeatherInfo(data.getStringExtra("city"), data.getStringExtra("country"));
                getForecast(data.getStringExtra("city"), data.getStringExtra("country"));
            }
            if(resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }
    public void getWeatherByCoord(String lat, String lon) {
        Call<WeatherInfo> call = weatherApi.getWeatherByCoord(lat, lon);
        call.enqueue(new Callback<WeatherInfo>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {

                if (!response.isSuccessful()) {
                    textViewCity.setText("Code: " + response.code());
                    return;
                }
                WeatherInfo weather = response.body();
                String dayTemp = String.format("%.0f", weather.getMain().getTemp());
                String nightTemp = String.format("/%.0f", weather.getMain().getTemp_min());
                Date date = Date.from( Instant.ofEpochSecond(weather.getDate()));
                String formatedDate = new SimpleDateFormat("EEEE, MMMM DD").format(date);
                textViewDayTemperature.setText(dayTemp + (char) 0x00B0);
                textViewNightTemperature.setText(nightTemp + (char) 0x00B0);
                textViewDate.setText(formatedDate);
                imageViewIcon.setImageDrawable(getIcon(("ic_" + weather.getWeather().get(0).getIcon())));
            }
            @Override
            public void onFailure(Call<WeatherInfo> call, Throwable t) {
                textViewCity.setText(t.getMessage());
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
                    textViewCity.setText("Code: " + response.code());
                    return;
                }
                WeatherInfo weather = response.body();
                String dayTemp = String.format("%.0f", weather.getMain().getTemp());
                String nightTemp = String.format("/%.0f", weather.getMain().getTemp_min());
                Date date = Date.from( Instant.ofEpochSecond(weather.getDate()));
                String formatedDate = new SimpleDateFormat("EEEE, MMMM DD").format(date);
                textViewDayTemperature.setText(dayTemp + (char) 0x00B0);
                textViewNightTemperature.setText(nightTemp + (char) 0x00B0);
                textViewDate.setText(formatedDate);
                imageViewIcon.setImageDrawable(getIcon(("ic_" + weather.getWeather().get(0).getIcon())));
            }
            @Override
            public void onFailure(Call<WeatherInfo> call, Throwable t) {
                textViewCity.setText(t.getMessage());
            }
        });
    }

    public void getForecast(String city, String country) {
        city = city.replace(" ", "+");
        country = country.replace(" ", "+");
        String location = city + "," + country;
        Call<ForecastInfo> call = weatherApi.getForecastByCity(location);

        call.enqueue(new Callback<ForecastInfo>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ForecastInfo> call, Response<ForecastInfo> response) {

                if (!response.isSuccessful()) {
                    return;
                }
                ForecastInfo forecast = response.body();
                getDataForecast(forecast);
            }
            @Override
            public void onFailure(Call<ForecastInfo> call, Throwable t) {
                textViewCity.setText(t.getMessage());
            }
        });
    }

    public void getForecastByCoord(String lat, String lon) {
        Call<ForecastInfo> call = weatherApi.getForecastByCoord(lat, lon);
        call.enqueue(new Callback<ForecastInfo>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ForecastInfo> call, Response<ForecastInfo> response) {
                if (!response.isSuccessful()) {
                    textViewCity.setText("Code:" + response.message());
                    return;
                }
                getDataForecast(response.body());
            }
            @Override
            public void onFailure(Call<ForecastInfo> call, Throwable t) {
                textViewCity.setText(t.getMessage());
            }
        });
    }

    public static Drawable getIcon(String name) {
        Context context = iWeather.getContext();
        int resourceId = context.getResources().getIdentifier(name, "drawable", iWeather.getContext().getPackageName());
        return context.getResources().getDrawable(resourceId);
    }

    public String getNameOfDay(String date) {
        switch (date.toLowerCase()){
            case "mon":
                return getString(R.string.mon);
            case "tue":
                return getString(R.string.tue);
            case "wed":
                return getString(R.string.wed);
            case "thu":
                return getString(R.string.thu);
            case "fri":
                return getString(R.string.fri);
            case "sat":
                return getString(R.string.sat);
            case "sun":
                return getString(R.string.sun);
        }
        return "No data";
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getDataForecast(ForecastInfo forecast) {
        ArrayList<Forecast> weatherFiveDays = new ArrayList<>();
        for(int i = 0; i < forecast.getList().size(); i++){
            if(forecast.getList().get(i).getDt_txt().substring(11).equals("12:00:00")){
                weatherFiveDays.add(forecast.getList().get(i));
                Log.d("temp:", String.valueOf(forecast.getList().get(i).getMain().getTemp()));
            }
        }

        textViewCity.setText(forecast.getCity().getName());
        textFirstDayTemp.setText(String.format("%.0f", weatherFiveDays.get(0).getMain().getTemp()) + (char) 0x00B0);
        Date date1 = Date.from( Instant.ofEpochSecond(weatherFiveDays.get(0).getDt()));
        String formatedDate = new SimpleDateFormat("EEE").format(date1);
        textFirstDayName.setText(getNameOfDay(formatedDate));
        firstImageIcon.setImageDrawable(getIcon(("ic_" + weatherFiveDays.get(0).getWeather().get(0).getIcon())));

        textSecondDayTemp.setText(String.format("%.0f", weatherFiveDays.get(1).getMain().getTemp()) + (char) 0x00B0);
        Date date2 = Date.from( Instant.ofEpochSecond(weatherFiveDays.get(1).getDt()));
        String formatedDate1 = new SimpleDateFormat("EEE").format(date2);
        textSecondDayName.setText(getNameOfDay(formatedDate1));
        secondImageIcon.setImageDrawable(getIcon(("ic_" + weatherFiveDays.get(1).getWeather().get(0).getIcon())));

        textThirdDayTemp.setText(String.format("%.0f", weatherFiveDays.get(2).getMain().getTemp()) + (char) 0x00B0);
        Date date3 = Date.from( Instant.ofEpochSecond(weatherFiveDays.get(2).getDt()));
        String formatedDate2 = new SimpleDateFormat("EEE").format(date3);
        textThirdDayName.setText(getNameOfDay(formatedDate2));
        thirdImageIcon.setImageDrawable(getIcon(("ic_" + weatherFiveDays.get(2).getWeather().get(0).getIcon())));

        textFourthDayTemp.setText(String.format("%.0f", weatherFiveDays.get(3).getMain().getTemp()) + (char) 0x00B0);
        Date date4 = Date.from( Instant.ofEpochSecond(weatherFiveDays.get(3).getDt()));
        String formatedDate3 = new SimpleDateFormat("EEE").format(date4);
        textFourthDayName.setText(getNameOfDay(formatedDate3));
        fourthImageIcon.setImageDrawable(getIcon(("ic_" + weatherFiveDays.get(3).getWeather().get(0).getIcon())));

        textFifthDayTemp.setText(String.format("%.0f", weatherFiveDays.get(4).getMain().getTemp()) + (char) 0x00B0);
        Date date5 = Date.from( Instant.ofEpochSecond(weatherFiveDays.get(4).getDt()));
        String formatedDate4 = new SimpleDateFormat("EEE").format(date5);
        textFifthDayName.setText(getNameOfDay(formatedDate4));
        fifthImageIcon.setImageDrawable(getIcon(("ic_" + weatherFiveDays.get(4).getWeather().get(0).getIcon())));

    }
}
