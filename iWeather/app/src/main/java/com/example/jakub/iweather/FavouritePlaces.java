package com.example.jakub.iweather;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.jakub.iweather.Database.CityEntity;
import com.example.jakub.iweather.Database.CityViewModel;

import java.util.List;

public class FavouritePlaces extends AppCompatActivity implements CityListAdapter.OnItemClickListener {
    private CityViewModel mCityViewModel;
    private List<CityEntity> mCities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_places);
        createMenu();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final CityListAdapter adapter = new CityListAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(FavouritePlaces.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCityViewModel = ViewModelProviders.of(this).get(CityViewModel.class);
        mCityViewModel.getAllCities().observe(this, new Observer<List<CityEntity>>() {
            @Override
            public void onChanged(@Nullable final List<CityEntity> cities) {
                adapter.setCities(cities);
                mCities = cities;
            }
        });
    }

    public void createMenu() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.favourite_places_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.addCity) {
                    Intent intent = new Intent(FavouritePlaces.this, AddCity.class);
                    startActivityForResult(intent, 1000);
                }
                return false;
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                CityEntity newCity = new CityEntity(data.getStringExtra("city"),data.getStringExtra("country"));
                mCityViewModel.insert(newCity);
            }
            if(resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent();
        intent.putExtra("city", mCities.get(position).getName());
        intent.putExtra("country", mCities.get(position).getCountry());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
