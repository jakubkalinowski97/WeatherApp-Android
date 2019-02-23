package com.example.jakub.iweather.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class CityViewModel extends AndroidViewModel {
    private CityRepository mRepository;
        private LiveData<List<CityEntity>> mAllCities;

    public CityViewModel(Application application){
        super(application);
        mRepository = new CityRepository(application);
        mAllCities = mRepository.getAllCities();
    }

    public LiveData<List<CityEntity>> getAllCities() {
        return mAllCities;
    }

    public void insert(CityEntity cityEntity) {
        mRepository.insert(cityEntity);
    }

}
