package com.blo.reade;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import controller.IoC;
import datalayer.DatabaseManager;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import model.Feed;
import model.MyErrorTracker;
import model.SavedFeed;
import controller.ShareTextUrl;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/*
This Activity is no longer in use. Refactor later
 */

public class FavouritesFeed extends AppCompatActivity {
    private SavedFeed bundleObject;
    private Bundle bundle;
    private Intent intent;
    private DatabaseManager databaseManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //check for dark theme
        if (new Settings().getNightMode(this)) {
            setTheme(R.style.DarkAppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_feed);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set back icon on action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView savedDatetextView = findViewById(R.id.favfeed_savedDatetextView);
        TextView datetextView = findViewById(R.id.favfeed_DatetextView);
        TextView titletextView = findViewById(R.id.favfeed_TitletextView);
        TextView descriptiontextView = findViewById(R.id.favfeed_DescriptiontextView);
        ImageView feedimageView = findViewById(R.id.favfeed_imageView);
        TextView sourcetextView = findViewById(R.id.favfeed_SourcetextView);
        TextView linktextView = findViewById(R.id.favfeed_LinktextView);
        Button btn = findViewById(R.id.favfeed_button);

        databaseManager= new DatabaseManager(FavouritesFeed.this);


        //get Bundle obj
        bundleObject = (SavedFeed) getIntent().getExtras().get("BundleObject");

        assert bundleObject != null;
        //------ Set obj params to views
        //saved date
        if (bundleObject.getTimeSaved()!=null)
            savedDatetextView.setText("Starred on : "+bundleObject.getTimeSaved());

        //date
        if (bundleObject.getDateofpublication()!=null)
            datetextView.setText("Published on: "+bundleObject.getDateofpublication());
        //title
        if (bundleObject.getTitle()!=null)
            titletextView.setText(bundleObject.getTitle());
        //desc
        if (bundleObject.getDescription()!=null)
            descriptiontextView.setText(bundleObject.getDescription());

        //image
        if (bundleObject.getImageUrl()!= null) {

            Glide.with(FavouritesFeed.this)
                    .load(bundleObject.getImageUrl()) // Remote URL of image.
                    .error(R.drawable.testimage)  //  image in case of error
                    .transition(withCrossFade()) //added cross fade animation
                    .into(feedimageView); //ImageView to set.
        } else {
            IoC.bbcImageCheck.imagecheck(bundleObject.getLink(), feedimageView);
        }

        //link
        if (bundleObject.getLink()!=null) {
            //Use my Truncater here
          //  linktextView.setText(myStringTruncater.truncate(bundleObject.getLink(),20));
            linktextView.setText(bundleObject.getLink());

        }

        //source
        if (bundleObject.getLink()!=null) {
            if (bundleObject.getLink().contains("www.")) {
                sourcetextView.setText(StringUtils.substringBetween(bundleObject.getLink(), "www.", ".co").toUpperCase());
            }
            else if (bundleObject.getLink().contains("rss.")){
                sourcetextView.setText(StringUtils.substringBetween(bundleObject.getLink(), "rss.", ".co").toUpperCase());
            }
            //customized for consequenceofsound
            else if (bundleObject.getLink().contains("consequenceofsound.net")) {
                sourcetextView.setText(StringUtils.substringBetween(bundleObject.getLink(), "://", ".net").toUpperCase());
            } else {
                sourcetextView.setText(StringUtils.substringBetween(bundleObject.getLink(), "://", ".co").toUpperCase());
            }

        }



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //moving to next page
                intent= new Intent(FavouritesFeed.this,WebViewActivity.class);
                bundle= new Bundle();
                //check empty link
                if (bundleObject.getLink()!=null) {
                    bundle.putSerializable("BundleObject", bundleObject.getLink());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(FavouritesFeed.this, "" + MyErrorTracker.EMPTY_URL, Toast.LENGTH_SHORT).show();
                }

            }
        });




//Custom Floating button
        final FabSpeedDial fabSpeedDial = findViewById(R.id.fabSpeedDial);
        fabSpeedDial.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(final MenuItem menuItem) {
                int id = menuItem.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.fab_delete) {
                    deletemethod(bundleObject);
                    return true;
                }
                if (id == R.id.fab_share) {
                    //convert savedfeed to feed
                    Feed feed=new Feed(bundleObject.getTitle(),bundleObject.getDateofpublication(),bundleObject.getDescription(),
                            bundleObject.getLink(),bundleObject.getImageUrl(), bundleObject.getSource());
                    ShareTextUrl.shareTextUrl(feed, FavouritesFeed.this);
                    return true;
                }

                return false;
            }

            @Override
            public void onMenuClosed() {
                //  Toast.makeText(TopicFeed.this, "Menu Closed", Toast.LENGTH_SHORT).show();
            }
        });



    }




    ///Starred Button method
    private void deletemethod(SavedFeed savedFeed) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FavouritesFeed.this);
        builder.setMessage("Are you sure you want to delete this article?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // fabSpeedDial.setBackground(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.star_big_on));
                        //  fabSpeedDial.setEnabled(false);

                        //delete in db here
                        int result =databaseManager.DELETE_FEED(bundleObject.getId());
                        if (result==1){
                            Toast.makeText(FavouritesFeed.this, "Deleted", Toast.LENGTH_SHORT).show();
                                                    finish();

                        }
                        else {
                            Toast.makeText(FavouritesFeed.this, "Failed to Delete", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
