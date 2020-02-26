package com.blo.reade;

import android.app.ProgressDialog;
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

import com.androidquery.AQuery;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import controller.IoC;
import controller.SourceGetter;
import controller.StarFeed;
import datalayer.DatabaseManager;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import model.Feed;
import model.MyErrorTracker;
import model.MyStringTruncater;
import model.SavedFeed;
import controller.ShareTextUrl;

public class TopicFeed extends AppCompatActivity {
    private Feed bundleObject;
    private ProgressDialog pd;
    private AQuery aQuery;
    private MyStringTruncater myStringTruncater;
    private Bundle bundle;
    private Intent intent;
    private SavedFeed savedFeed;
    private SavedFeed checksavedFeed;
    private DatabaseManager databaseManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (new Settings().getNightMode(this)) {
            setTheme(R.style.DarkAppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_feed);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set back icon on action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        pd=new ProgressDialog(this);
        //  pd.setTitle("Article Page");
        pd.setMessage("Loading...Please wait");
        pd.show();

        TextView datetextView=findViewById(R.id.topicfeed_DatetextView);
        TextView titletextView=findViewById(R.id.topicfeed_TitletextView);
        TextView descriptiontextView=findViewById(R.id.topicfeed_DescriptiontextView);
        ImageView feedimageView=findViewById(R.id.topicfeed_imageView);
        TextView sourcetextView=findViewById(R.id.topicfeed_SourcetextView);
        TextView linktextView=findViewById(R.id.topicfeed_LinktextView);
        Button btn = findViewById(R.id.topicfeed_button);

        aQuery=new AQuery(TopicFeed.this);
        databaseManager= new DatabaseManager(TopicFeed.this);

        //get Bundle obj
        bundleObject = (Feed) getIntent().getExtras().get("BundleObject");
        assert bundleObject != null;
        //------ Set obj params to views
        //link
        if (bundleObject.getLink() != null) {
            //Use my Truncater here
            linktextView.setText(bundleObject.getLink());
            //Get Source from link
            sourcetextView.setText(SourceGetter.getSourceFromURL(bundleObject.getLink()));
        }

        //date
        if (bundleObject.getDate() != null) {
            datetextView.setText(bundleObject.getDate());
        }

        //title
        if (bundleObject.getTitle()!=null)
        titletextView.setText(bundleObject.getTitle());
        //desc
        if (bundleObject.getDescription()!=null)
        descriptiontextView.setText(bundleObject.getDescription());

        //image
        if (bundleObject.getImage()!= null) {
            //if we are not able to load the image, use a default image (R.drawable.default_image)
            //this image is huge, avoid memory caching
            aQuery.id(feedimageView).progress(R.id.progressBar).
                    image(bundleObject.getImage(), false, true, 0, R.drawable.testimage, null, AQuery.FADE_IN);
        } else {
            //empty or null Image
            IoC.bbcImageCheck.imagecheck(bundleObject.getLink(),feedimageView);
        }

        //--------------get Source textView and assign to feed Source variable
        bundleObject.setSource(sourcetextView.getText().toString());

//cancel progressDialog after setting to views
        pd.cancel();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //moving to next page
                intent= new Intent(TopicFeed.this,WebViewActivity.class);
                bundle= new Bundle();
                //check empty link
                if (bundleObject.getLink()!=null) {
                    bundle.putSerializable("BundleObject", bundleObject.getLink());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(TopicFeed.this, ""+ MyErrorTracker.EMPTY_URL, Toast.LENGTH_SHORT).show();
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
                if (id == R.id.fab_star) {
                    StarFeed.starFeed(TopicFeed.this, bundleObject);
                    return true;
                }
                if (id == R.id.fab_share) {
                    ShareTextUrl.shareTextUrl(bundleObject, TopicFeed.this);
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
