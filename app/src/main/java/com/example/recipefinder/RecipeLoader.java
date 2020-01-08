package com.example.recipefinder;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class RecipeLoader extends AsyncTaskLoader<String> {
    private String mQueryString;
    private String mIngredients;

    public RecipeLoader(@NonNull Context context, String queryString, String ingredients) {
        super(context);
        mQueryString = queryString;
        mIngredients = ingredients;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getRecipesInfo(mQueryString, mIngredients);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        onForceLoad();
    }
}
