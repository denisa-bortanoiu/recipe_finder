package com.example.recipefinder;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class ListRecipesActivity extends AppCompatActivity {
    private LinkedList<Recipe> recipes = new LinkedList<>();
    private RecyclerView mRecylerView;
    private RecipeListAdapter mAdapter;
    private FavouriteViewModel mFavouriteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recipes);

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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        String data = intent.getStringExtra(MainActivity.SEARCH_RESULTS);
        parseData(data);

        mFavouriteViewModel = ViewModelProviders.of(this).get(FavouriteViewModel.class);

        mRecylerView = findViewById(R.id.recipes_recycler_view);
        mAdapter = new RecipeListAdapter(this, recipes, mFavouriteViewModel);
        mRecylerView.setAdapter(mAdapter);
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void parseData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("results");

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject jsonRecipe = itemsArray.getJSONObject(i);

                try {
                    Recipe recipe = new Recipe(
                        jsonRecipe.getString("title").trim(),
                        jsonRecipe.getString("href").trim(),
                        jsonRecipe.getString("ingredients").trim()
                    );
                    recipes.addLast(recipe);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
