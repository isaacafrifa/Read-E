package com.blo.reade;

import android.content.Intent;

import com.google.android.material.snackbar.Snackbar;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Objects;

import controller.FetchFeedTask;
import model.Feed;
import model.MyErrorTracker;

import static androidx.swiperefreshlayout.widget.SwipeRefreshLayout.LARGE;

public class SelectedTopic extends AppCompatActivity {
    String bundleObject;
    GridView gridView;
    Feed selectedFeed;
    Intent intent;
    Bundle bundle;
    SwipeRefreshLayout swipeRefreshLayout;
    String urlAddress;
    private ProgressBar ProgressBarLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //check for dark theme
        if (new Settings().getNightMode(this)) {
            setTheme(R.style.DarkAppTheme);
            //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); //For night mode theme
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_topic);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set back icon on action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

//get Views
        gridView = findViewById(R.id.gridView);
        ProgressBarLoading = findViewById(R.id.progressbar);
        swipeRefreshLayout = findViewById(R.id.swipe_container);

        //get Bundle obj
        bundleObject = (String) Objects.requireNonNull(getIntent().getExtras()).get("BundleObject");
        //Set Actionbar Title with Topic title
        toolbar.setTitle(bundleObject);

        //-------------- do a null check for empty bundle later
        //provide associated urls to Bundle objects
        if (bundleObject.equalsIgnoreCase("business")) {
            urlAddress = "http://feeds.bbci.co.uk/news/business/rss.xml";
            // Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        }
        //Null check for empty bundle
        else if (bundleObject.isEmpty()) {
            //Toast not needed, Wrong URL format msg shows
            finish();
        } else if (bundleObject.equalsIgnoreCase("uk politics")) {
            urlAddress = "http://feeds.bbci.co.uk/news/politics/rss.xml";
            // Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("health")) {
            urlAddress = "http://feeds.bbci.co.uk/news/health/rss.xml";
            //  Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("soccer")) {
            urlAddress = "http://feeds.bbci.co.uk/sport/0/football/rss.xml";
            //   Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("tennis")) {
            urlAddress = "http://feeds.bbci.co.uk/sport/0/tennis/rss.xml";
            //  Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("cricket")) {
            urlAddress = "http://feeds.bbci.co.uk/sport/0/cricket/rss.xml";
            //  Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("boxing")) {
            urlAddress = "http://feeds.bbci.co.uk/sport/0/boxing/rss.xml";
            // Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("Racing")) {
            urlAddress = "http://feeds.bbci.co.uk/sport/0/motorsport/rss.xml";
            // Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("athletics")) {
            urlAddress = "http://feeds.bbci.co.uk/sport/athletics/rss.xml";
            // Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("science")) {
            urlAddress = "http://feeds.bbci.co.uk/news/science_and_environment/rss.xml";
            //  Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("gaming")) {
            urlAddress = "https://www.gamespot.com/feeds/game-news/";
            // Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("travel")) {
            urlAddress = "http://rss.cnn.com/rss/edition_travel.rss";
            // Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("basketball")) {
            urlAddress = "https://www.cbssports.com/rss/headlines/nba/";
            //  Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("music")) {
            urlAddress = "https://consequenceofsound.net/feed/";
            //  Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("TV & Movies")) {
            urlAddress = "https://movieweb.com/rss/all-news/";
            //  Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("africa")) {
            urlAddress = "http://feeds.bbci.co.uk/news/world/africa/rss.xml";
            // Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("fashion")) {
            urlAddress = "https://www.esquire.com/rss/style.xml/";
            // Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("nfl")) {
            urlAddress = "https://api.foxsports.com/v1/rss?partnerKey=zBaFxRyGKCfxBagJG9b8pqLyndmvo7UU&tag=nfl";
            // Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("UFC")) {
            urlAddress = "https://api.foxsports.com/v1/rss?partnerKey=zBaFxRyGKCfxBagJG9b8pqLyndmvo7UU&tag=ufc";
            //  Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("Europe")) {
            urlAddress = "http://feeds.bbci.co.uk/news/world/europe/rss.xml";
            //    Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("Asia")) {
            urlAddress = "http://feeds.bbci.co.uk/news/world/asia/rss.xml";
            // Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("Middle East")) {
            urlAddress = "http://feeds.bbci.co.uk/news/world/middle_east/rss.xml";
            //     Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("Trending")) {
            urlAddress = "http://feeds.bbci.co.uk/news/rss.xml";
            //     Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        } else if (bundleObject.equalsIgnoreCase("Food")) {
            urlAddress = " https://www.mnn.com/feeds/category/food/";
            //     Toast.makeText(this, ""+urlAddress, Toast.LENGTH_SHORT).show();
        }
//        else if (bundleObject.equalsIgnoreCase("Arts & Culture")) {
//            urlAddress = "https://www.voanews.com/api/zp$ove-vir";
//            //    Toast.makeText(this, "" + urlAddress, Toast.LENGTH_SHORT).show();
//        } else if (bundleObject.equalsIgnoreCase("US Politics")) {
//            urlAddress = "https://www.voanews.com/api/zuriqiepuiqm";
//            //   Toast.makeText(this, "" + urlAddress, Toast.LENGTH_SHORT).show();
//        }
//        else if (bundleObject.equalsIgnoreCase("Security")) {
//            urlAddress = "https://www.wired.com/feed/category/security/latest/rss";
//        }
//        else if (bundleObject.equalsIgnoreCase("Ideas")) {
//            urlAddress = "https://www.wired.com/feed/category/ideas/latest/rss";
//        }
        else if (bundleObject.equalsIgnoreCase("Lifestyle")) {
            urlAddress = "https://www.esquire.com/rss/lifestyle.xml/";
        } else if (bundleObject.equalsIgnoreCase("Relationships")) {
            urlAddress = "https://www.elle.com/rss/life-love.xml/";
        } else if (bundleObject.equalsIgnoreCase("Nature")) {
            urlAddress = "https://www.mnn.com/rss/all-mnn-content";
        } else if (bundleObject.equalsIgnoreCase("Beauty")) {
            urlAddress = "https://www.elle.com/rss/beauty.xml/";
        }

        //Fetch feeds
        new FetchFeedTask(SelectedTopic.this, urlAddress, gridView, ProgressBarLoading).execute();


        //Handling Swipe Refresh Layout
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_dark,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setSize(LARGE);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FetchFeedTask(SelectedTopic.this, urlAddress, gridView, ProgressBarLoading).execute();
                swipeRefreshLayout.setRefreshing(false);

            }
        });


        //on click of GridView
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedFeed = (Feed) parent.getItemAtPosition(position);
//                //moving to next page

                Snackbar snackbar = Snackbar.make(view, "View Article Online", Snackbar.LENGTH_SHORT)
                        .setAction("PROCEED", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //                //moving to next page
                                intent = new Intent(SelectedTopic.this, WebViewActivity.class);
                                bundle = new Bundle();
                                //check empty link
                                if (selectedFeed.getLink() != null) {
                                    bundle.putSerializable("BundleObject", selectedFeed.getLink());
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(SelectedTopic.this, "" + MyErrorTracker.EMPTY_URL, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                snackbar.show();

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
