package com.example.recipefinder;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String>{
    public static final String SEARCH_RESULTS = "com.example.recipefinder.extra.SEARCH_RESULTS";
    private EditText mSearchQuery;

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
    }

    public void searchRecipes(View view) {
        String searchQuery = mSearchQuery.getText().toString();
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
                && searchQuery.length() != 0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", searchQuery);
            LoaderManager.getInstance(this).restartLoader(0, queryBundle, this);

            Snackbar.make(view, R.string.search_loading, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        } else {
            if (searchQuery.length() == 0) {
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";
        if (args != null) {
            queryString = args.getString("queryString");
        }

        return new RecipeLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Intent intent = new Intent(MainActivity.this, ListRecipesActivity.class);
        intent.putExtra(SEARCH_RESULTS, data);
        startActivity(intent);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
