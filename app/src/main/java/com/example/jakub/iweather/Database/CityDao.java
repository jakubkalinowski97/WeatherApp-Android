package com.example.jakub.iweather.Database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CityDao {

    @Insert
    void insert(CityEntity cityEntity);

    @Query("DELETE FROM city_table")
    void deleteAll();

    @Query("SELECT * FROM city_table ORDER BY name ASC")
    LiveData<List<CityEntity>> getAllCities();
}
