package com.example.recipefinder;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String>{
    public static final String SEARCH_RESULTS = "com.example.recipefinder.extra.SEARCH_RESULTS";
    private EditText mSearchQuery;
    private boolean isLoaded = false;
    private List<String> commonIngredients = Arrays.asList(
            "chicken", "beef", "fish",
            "egg", "milk", "cheese",
            "onion", "tomato", "pepper", "carrot", "celery", "lemon",
            "potato", "pasta", "rice");
    private ArrayList<String> chosenIngredients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSearchQuery = (EditText)findViewById(R.id.searchQuery);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchRecipes(view);
            }
        });

        // load chips with common ingredients
        ChipGroup chipGroup = findViewById(R.id.ingredient_chip_group);
        for (int i = 0; i < commonIngredients.size(); i++) {
            Chip mChip = (Chip) getLayoutInflater().inflate(R.layout.chip_filter, null, false);
            mChip.setText(commonIngredients.get(i));
            mChip.setClickable(true);
            mChip.setCheckable(true);
            mChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    String ingredient = compoundButton.getText().toString();
                    if (chosenIngredients.contains(ingredient)) {
                        chosenIngredients.remove(ingredient);
                    } else {
                        chosenIngredients.add(ingredient);
                    }
                }
            });
            chipGroup.addView(mChip);
        }
    }

    public void searchRecipes(View view) {
        String searchQuery = mSearchQuery.getText().toString();
        mSearchQuery.setText("");
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputManager != null ) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        if (networkInfo != null && networkInfo.isConnected()
                && (searchQuery.length() != 0 || chosenIngredients.size() != 0)) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", searchQuery);
            queryBundle.putStringArrayList("ingredientList", chosenIngredients);
            LoaderManager.getInstance(this).restartLoader(0, queryBundle, this);
            isLoaded = true;

            Snackbar.make(view, R.string.search_loading, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        } else {
            if (searchQuery.length() == 0 || chosenIngredients.size() == 0) {
                Snackbar.make(view, R.string.no_search_term, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                Snackbar.make(view, R.string.no_network, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favourites) {
            Intent intent = new Intent(MainActivity.this, ListFavouriteActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";
        String ingredients = "";
        if (args != null) {
            queryString = args.getString("queryString");
            ArrayList<String> ingredientList = args.getStringArrayList("ingredientList");
            if (ingredientList != null) {
                ingredients = TextUtils.join(",", ingredientList);
            }
        }

        return new RecipeLoader(this, queryString, ingredients);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if (isLoaded) {
            Intent intent = new Intent(MainActivity.this, ListRecipesActivity.class);
            intent.putExtra(SEARCH_RESULTS, data);
            startActivity(intent);
            isLoaded = false;
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
