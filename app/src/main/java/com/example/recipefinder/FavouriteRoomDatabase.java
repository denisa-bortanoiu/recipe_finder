package com.example.recipefinder;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Favourite.class}, version = 1, exportSchema = false)
public abstract class FavouriteRoomDatabase extends RoomDatabase {
    public abstract FavouriteDao favouriteDao();
    private static FavouriteRoomDatabase INSTANCE;

    public static FavouriteRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FavouriteRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FavouriteRoomDatabase.class, "favourite_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
