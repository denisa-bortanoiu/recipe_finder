package com.example.recipefinder;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourites_table")
public class Favourite {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "href")
    private String mHref;

    @ColumnInfo(name="title")
    private String mTitle;

    @ColumnInfo(name="ingredients")
    private String mIngredients;


    public Favourite(@NonNull String href, String title, String ingredients) {
        this.mHref = href;
        this.mTitle = title;
        this.mIngredients = ingredients;
    }

    public String getHref() { return this.mHref; }
    public String getTitle() { return this.mTitle; }
    public String getIngredients() { return this.mIngredients; }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + mTitle + '\'' +
                ", href='" + mHref + '\'' +
                ", ingredients='" + mIngredients + '\'' +
                '}';
    }
}
