package com.example.recipefinder;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class RecipeLoader extends AsyncTaskLoader<String> {
    private String mQueryString;

    public RecipeLoader(@NonNull Context context, String queryString) {
        super(context);
        mQueryString = queryString;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getRecipesInfo(mQueryString);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        onForceLoad();
    }
}
