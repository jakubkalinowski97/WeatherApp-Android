package com.example.jakub.iweather.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


@Database(entities = {CityEntity.class}, version = 2)
public abstract class CityRoomDatabase extends RoomDatabase {

    public abstract CityDao cityDao();

    private static volatile CityRoomDatabase INSTANCE;

    static CityRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CityRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CityRoomDatabase.class,
                            "city_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final CityDao mDao;

        PopulateDbAsync(CityRoomDatabase db) {
            mDao = db.cityDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            CityEntity word = new CityEntity("Bialystok", "Poland");
            mDao.insert(word);
            return null;
        }
    }
}
