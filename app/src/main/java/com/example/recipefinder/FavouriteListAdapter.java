package com.example.recipefinder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class FavouriteListAdapter extends RecyclerView.Adapter<FavouriteListAdapter.FavouriteViewHolder> {

    private final LayoutInflater mInflater;
    private List<Favourite> mFavourites;
    private FavouriteViewModel mFavouriteViewModel;

    class FavouriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // not very smart because I'm basically duplicating code from RecipeViewHolder
        // but can't think now of a method to make this better
        public final TextView mTitle;
        public final TextView mIngredients;
        public final TextView href;
        public final Button mLink;
        public final ImageView mFavouriteStatus;
        Favourite mFavourite;
        final FavouriteListAdapter mAdapter;

        public void onFavourite(View v) {
            mFavourite = new Favourite(
                    href.getText().toString(),
                    mTitle.getText().toString(),
                    mIngredients.getText().toString());
            mFavouriteViewModel.delete(mFavourite);
            Snackbar.make(v, R.string.unfavorited_message, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }

        public FavouriteViewHolder(View itemView, FavouriteListAdapter adapter) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.recipe_title);
            mIngredients = itemView.findViewById(R.id.recipe_ingredients);
            mLink = itemView.findViewById(R.id.recipe_link);
            href = itemView.findViewById(R.id.recipe_href);
            mLink.setOnClickListener(this);
            mFavouriteStatus = itemView.findViewById(R.id.favourite_status);
            mFavouriteStatus.setClickable(true);
            mFavouriteStatus.setImageResource(R.drawable.ic_favourite_filled);
            mFavouriteStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFavourite(v);
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

    FavouriteListAdapter(Context context, FavouriteViewModel viewModel) {
        mInflater = LayoutInflater.from(context);
        mFavouriteViewModel = viewModel;
    }

    @Override
    public FavouriteListAdapter.FavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recipe_item, parent, false);
        return new FavouriteViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(FavouriteViewHolder holder, int position) {
        if (mFavourites != null) {
            Favourite current = mFavourites.get(position);
            holder.mTitle.setText(current.getTitle());
            holder.mIngredients.setText(current.getIngredients());
            holder.href.setText(current.getHref());
        }
    }

    void setFavourites(List<Favourite> favourites){
        mFavourites = favourites;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mFavourites != null)
            return mFavourites.size();
        else return 0;
    }
}