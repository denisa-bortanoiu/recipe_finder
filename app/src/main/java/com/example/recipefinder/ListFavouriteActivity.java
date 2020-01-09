package com.example.recipefinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class ListFavouriteActivity extends AppCompatActivity {
    private FavouriteViewModel mFavouriteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_favourite);

        // set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mFavouriteViewModel = ViewModelProviders.of(this).get(FavouriteViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.favourite_recycler_view);
        final FavouriteListAdapter adapter = new FavouriteListAdapter(this, mFavouriteViewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFavouriteViewModel.getAllFavourites().observe(this, new Observer<List<Favourite>>() {
            @Override
            public void onChanged(@Nullable final List<Favourite> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setFavourites(words);
            }
        });
    }
}
