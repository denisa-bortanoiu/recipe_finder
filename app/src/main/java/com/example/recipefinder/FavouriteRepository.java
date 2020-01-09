package com.example.recipefinder;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FavouriteRepository {
    private FavouriteDao mFavouriteDao;
    private LiveData<List<Favourite>> mAllFavourites;

    FavouriteRepository(Application application) {
        FavouriteRoomDatabase db = FavouriteRoomDatabase.getDatabase(application);
        mFavouriteDao = db.favouriteDao();
        mAllFavourites = mFavouriteDao.getAllFavourites();
    }

    LiveData<List<Favourite>> getmAllFavourites() { return mAllFavourites; }

    private static class insertAsyncTask extends AsyncTask<Favourite, Void, Void> {

        private FavouriteDao mAsyncTaskDao;

        insertAsyncTask(FavouriteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Favourite... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void insert(Favourite favourite) {
        new insertAsyncTask(mFavouriteDao).execute(favourite);
    }


    private static class deleteWordAsyncTask extends AsyncTask<Favourite, Void, Void> {
        private FavouriteDao mAsyncTaskDao;

        deleteWordAsyncTask(FavouriteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Favourite... params) {
            mAsyncTaskDao.deleteFavourite(params[0]);
            return null;
        }
    }

    public void deleteFavourite(Favourite word) {
        new deleteWordAsyncTask(mFavouriteDao).execute(word);
    }
}
