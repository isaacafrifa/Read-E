package com.blo.reade;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.apache.commons.io.FileUtils;


public class Main2Activity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//check for dark theme
        if (new Settings().getNightMode(this)) {
            setTheme(R.style.DarkAppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        FloatingActionButton fab = findViewById(R.id.fab);


        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        // tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), true);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Toast.makeText(Main2Activity.this, tab.getText()+" selected", Toast.LENGTH_SHORT).show();
                //scroll to top
                FragmentManager fm = getSupportFragmentManager();
                Fragment f = mSectionsPagerAdapter.getFragment(mViewPager, tab.getPosition(), fm);

                if (f != null) {
                    //first tab
                    if (tab.getPosition() == 0) {
                        World fragment = (World) f;
                        fragment.gridView.smoothScrollToPosition(0);
                    }
                    //second tab
                    if (tab.getPosition() == 1) {
                        Entertainment fragment = (Entertainment) f;
                        fragment.gridView.smoothScrollToPosition(0);
                    }
                    //third tab
                    if (tab.getPosition() == 2) {

                        Sports fragment = (Sports) f;
                        fragment.gridView.smoothScrollToPosition(0);
                    }
                    //4th tab
                    if (tab.getPosition() == 3) {
                        Technology fragment = (Technology) f;
                        fragment.gridView.smoothScrollToPosition(0);
                    }

                }
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Main2Activity.this, Topics.class);
                // Check if we're running on Android 5.0 or higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent);

                } else {
                    // Swap without transition
                    startActivity(intent);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(Main2Activity.this, Settings.class));
            finish();
            return true;
        }
        if (id == R.id.action_favorites) {
            startActivity(new Intent(Main2Activity.this, Favorites.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit this application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // If you use file cache for images, regularly clean the cache dir when the application exits.
                        // Delete local cache dir (ignoring any errors):
                        FileUtils.deleteQuietly(Main2Activity.this.getCacheDir());
                        // finishAffinity(); Remove all the previous activities from the back stack.... requires API 16 and above
                        finishAffinity();
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


    //onBackPressed

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    // World fragment activity
                    return new World();
                case 1:
                    // Entertainment fragment activity
                    return new Entertainment();
                case 2:
                    // Sports fragment activity
                    return new Sports();
                case 3:
                    // Tech fragment activity
                    return new Technology();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        Fragment getFragment(ViewPager container, int position, FragmentManager fm) {
            String name = makeFragmentName(container.getId(), position);
            return fm.findFragmentByTag(name);
        }

        private String makeFragmentName(int viewId, int index) {
            return "android:switcher:" + viewId + ":" + index;
        }
    }
}
