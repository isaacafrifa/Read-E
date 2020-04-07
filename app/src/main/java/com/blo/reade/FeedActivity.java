package com.blo.reade;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Objects;

import controller.IoC;
import controller.ShareTextUrl;
import controller.SourceGetter;
import controller.StarFeed;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import model.Feed;
import model.MyErrorTracker;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


/*
This Activity is no longer in use. Kindly Refactor later
 */
public class FeedActivity extends AppCompatActivity {
    private Feed bundleObject;
    private Bundle bundle;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //check for dark theme
        if (new Settings().getNightMode(this)) {
            setTheme(R.style.DarkAppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set back icon on action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ProgressDialog pd = new ProgressDialog(this);
        //  pd.setTitle("Article Page");
        pd.setMessage("Loading...Please wait");
        pd.show();
        //get Bundle obj
        bundleObject = (Feed) Objects.requireNonNull(getIntent().getExtras()).get("BundleObject");

        TextView datetextView=findViewById(R.id.feed_DatetextView);
        TextView titletextView=findViewById(R.id.feed_TitletextView);
        TextView descriptiontextView=findViewById(R.id.feed_DescriptiontextView);
        ImageView feedimageView=findViewById(R.id.feed_imageView);
        TextView sourcetextView=findViewById(R.id.feed_SourcetextView);
        TextView linktextView=findViewById(R.id.feed_LinktextView);
        Button btn = findViewById(R.id.feed_button);


        /*       SET OBJECT VALUES TO ALL VIEWS        */
        //link
        if (bundleObject.getLink() != null) {
            //Use my Truncater here
            //  linktextView.setText(myStringTruncater.truncate(bundleObject.getLink(),20));
            linktextView.setText(bundleObject.getLink());
            //Get Source from link
            bundleObject.setSource(SourceGetter.getSourceFromURL(bundleObject.getLink()));
            sourcetextView.setText(bundleObject.getSource());
        }
        //date
        if (bundleObject.getDate() != null) {
            datetextView.setText(bundleObject.getDate());
            //implementing TimeAgo Converter class
            // Toast.makeText(this, ""+ TimeAgoConverter.convertToTimeAgo(bundleObject.getDate()), Toast.LENGTH_LONG).show();

        }
        //title
        if (bundleObject.getTitle()!=null)
            titletextView.setText(bundleObject.getTitle());
        //desc
        if (bundleObject.getDescription()!=null)
            descriptiontextView.setText(bundleObject.getDescription());

        //image
        if (bundleObject.getImage()!= null) {

            Glide.with(FeedActivity.this)
                    .load(bundleObject.getImage()) // Remote URL of image.
                    .error(R.drawable.testimage)  //  image in case of error
                    .transition(withCrossFade()) //added cross fade animation
                    .into(feedimageView); //ImageView to set.
        } else {
            IoC.myImageConfig.imagecheck(bundleObject.getLink(),feedimageView);
            //  feedimageView.setBackgroundResource(R.drawable.africa);
        }

        //cancel progressDialog after setting to views
        pd.cancel();




//View Article Button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //moving to next page
                intent= new Intent(FeedActivity.this,WebViewActivity.class);
                bundle= new Bundle();
                //check empty link
                if (bundleObject.getLink()!=null) {
                    bundle.putSerializable("BundleObject", bundleObject.getLink());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(FeedActivity.this, ""+ MyErrorTracker.EMPTY_URL, Toast.LENGTH_SHORT).show();
                }

            }
        });


//Custom Floating button
        final FabSpeedDial fabSpeedDial = findViewById(R.id.fabSpeedDial2);
        fabSpeedDial.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(final MenuItem menuItem) {
                int id = menuItem.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.fab_star) {
                    StarFeed.starFeed(FeedActivity.this, bundleObject);
                    return true;
                }
                if (id == R.id.fab_share) {
                    ShareTextUrl.shareTextUrl(bundleObject, FeedActivity.this);
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
