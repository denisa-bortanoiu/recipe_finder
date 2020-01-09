package com.example.recipefinder;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FavouriteViewModel extends AndroidViewModel {
    private FavouriteRepository mRepository;
    private LiveData<List<Favourite>> mAllFavourites;

    public FavouriteViewModel(Application application) {
        super(application);
        mRepository = new FavouriteRepository(application);
        mAllFavourites = mRepository.getmAllFavourites();
    }

    LiveData<List<Favourite>> getAllFavourites() {
        return mAllFavourites;
    }

    public void insert(Favourite favourite) {
        mRepository.insert(favourite);
    }

    public void delete(Favourite favourite) {
        mRepository.deleteFavourite(favourite);
    }
}
