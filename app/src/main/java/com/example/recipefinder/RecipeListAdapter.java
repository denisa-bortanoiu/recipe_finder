package com.example.recipefinder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.LinkedList;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    private static final String LOG_TAG = RecipeListAdapter.class.getSimpleName();
    private final LinkedList<Recipe> mRecipeList;
    private LayoutInflater mInflater;


    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mTitle;
        public final TextView mIngredients;
        public final TextView href;
        public final Button mLink;
        public final ImageView mFavouriteStatus;
        private boolean isFavourited = false;
        final RecipeListAdapter mAdapter;

        public RecipeViewHolder(View itemView, RecipeListAdapter adapter) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.recipe_title);
            mIngredients = itemView.findViewById(R.id.recipe_ingredients);
            mLink = itemView.findViewById(R.id.recipe_link);
            href = itemView.findViewById(R.id.recipe_href);
            mLink.setOnClickListener(this);
            mFavouriteStatus = itemView.findViewById(R.id.favourite_status);
            mFavouriteStatus.setClickable(true);
            mFavouriteStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFavourited) {
                        isFavourited = false;
                        mFavouriteStatus.setImageResource(R.drawable.ic_favourite_border);
                        Snackbar.make(v, R.string.unfavorited_message, Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    } else {
                        isFavourited = true;
                        mFavouriteStatus.setImageResource(R.drawable.ic_favourite_filled);
                        Snackbar.make(v, R.string.favorited_message, Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                }
            });

            mAdapter = adapter;
        }

        @Override
        public void onClick(View view) {
            String url = href.getText().toString();
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(view.getContext().getPackageManager()) != null) {
                view.getContext().startActivity(intent);
            } else {
                Snackbar.make(view, R.string.no_open_link, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
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
        Recipe mCurrent = mRecipeList.get(position);
        holder.mTitle.setText(mCurrent.title);
        holder.mIngredients.setText(mCurrent.ingredients);
        holder.href.setText(mCurrent.href);
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }
}
