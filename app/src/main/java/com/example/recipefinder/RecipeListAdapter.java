package com.example.recipefinder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    private static final String LOG_TAG = RecipeListAdapter.class.getSimpleName();
    private final LinkedList<Recipe> mRecipeList;
    private LayoutInflater mInflater;


    class RecipeViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTitle;
        public final TextView mIngredients;
        public final Button mLink;
        final RecipeListAdapter mAdapter;

        public RecipeViewHolder(View itemView, RecipeListAdapter adapter) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.recipe_title);
            mIngredients = itemView.findViewById(R.id.recipe_ingredients);
            mLink = itemView.findViewById(R.id.recipe_link);
            mAdapter = adapter;
        }

    }

    public RecipeListAdapter(Context context, LinkedList<Recipe> recipeList) {
        mInflater = LayoutInflater.from(context);
        this.mRecipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeListAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapter.RecipeViewHolder holder, int position) {
        Log.d(LOG_TAG, "POSITION" + position);
        Recipe mCurrent = mRecipeList.get(position);
        holder.mTitle.setText(mCurrent.title);
        holder.mIngredients.setText(mCurrent.ingredients);
        holder.mLink.setText("Recipe");
        Log.d(LOG_TAG, mCurrent.toString());

    }

    @Override
    public int getItemCount() {
        Log.d(LOG_TAG, "COUNT:" + mRecipeList.size());
        return mRecipeList.size();
    }
}
