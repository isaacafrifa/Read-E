package com.blo.reade;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import controller.FavoritesGridAdapter;
import datalayer.DatabaseManager;
import model.MyErrorTracker;
import model.SavedFeed;

public class Favorites extends AppCompatActivity {

    private SavedFeed savedFeed;
    private Intent intent;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (new Settings().getNightMode(this)) {
            setTheme(R.style.DarkAppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set back icon on action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        GridView gridView = findViewById(R.id.favorites_gridView);
        TextView feedEmpty_textView = findViewById(R.id.emptytextView);
        ImageView imageplaceholder = findViewById(R.id.imageplaceholder);




        DatabaseManager databaseManager = new DatabaseManager(Favorites.this);
        //Getting List from DB
        List<SavedFeed> savedFeedList = databaseManager.GET_ALL_FEEDS();
        if (savedFeedList.isEmpty()) {
            feedEmpty_textView.setVisibility(View.VISIBLE);
            imageplaceholder.setVisibility(View.VISIBLE);
        }
        FavoritesGridAdapter favoritesGridAdapter = new FavoritesGridAdapter(Favorites.this, savedFeedList, gridView, feedEmpty_textView, imageplaceholder);
        gridView.setAdapter(favoritesGridAdapter);


        //on click of GridView
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                savedFeed = (SavedFeed) parent.getItemAtPosition(position);
                Snackbar snackbar = Snackbar.make(view, "View Article Online", Snackbar.LENGTH_SHORT)
                        .setAction("PROCEED", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //                //moving to next page
                                intent = new Intent(Favorites.this, WebViewActivity.class);
                                bundle = new Bundle();
                                //check empty link
                                if (savedFeed.getLink() != null) {
                                    bundle.putSerializable("BundleObject", savedFeed.getLink());
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Favorites.this, "" + MyErrorTracker.EMPTY_URL, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                snackbar.show();

            }
        });


    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        savedFeedList.clear();
//        databaseManager = new DatabaseManager(Favorites.this);
//        //Getting List from DB
//        savedFeedList = databaseManager.GET_ALL_FEEDS();
//        if (savedFeedList.isEmpty()) {
//            feedEmpty_textView.setVisibility(View.VISIBLE);
//            imageplaceholder.setVisibility(View.VISIBLE);
//        }
//        favoritesGridAdapter = new FavoritesGridAdapter(Favorites.this, savedFeedList);
//        gridView.setAdapter(favoritesGridAdapter);
//        favoritesGridAdapter.notifyDataSetChanged();
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}


