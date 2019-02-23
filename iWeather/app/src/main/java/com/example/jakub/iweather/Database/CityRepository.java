package com.example.jakub.iweather.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class CityRepository {
    private CityDao mCityDao;
    private LiveData<List<CityEntity>> mAllCities;

    CityRepository(Application application){
        CityRoomDatabase db = CityRoomDatabase.getDatabase(application);
        mCityDao = db.cityDao();
        mAllCities = mCityDao.getAllCities();
    }

    LiveData<List<CityEntity>> getAllCities() {
        return mAllCities;
    }

    public void insert(CityEntity cityEntity) {
        new insertAsyncTask(mCityDao).execute(cityEntity);
    }

    private static class insertAsyncTask extends AsyncTask<CityEntity, Void, Void> {

        private CityDao mAsyncTaskDao;

        insertAsyncTask(CityDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CityEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
