package com.example.recipefinder;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Favourite favourite);

    @Query("SELECT * FROM favourites_table ORDER BY title ASC")
    LiveData<List<Favourite>> getAllFavourites();

    @Query("SELECT * FROM favourites_table LIMIT 1")
    Favourite[] getAnyFavourite();

    @Delete
    void deleteFavourite(Favourite favourite);
}
