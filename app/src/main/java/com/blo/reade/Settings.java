package com.blo.reade;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    //Night Mode
    public static final String TAG = "App";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //check for NightMode
        if (new Settings().getNightMode(this)) {
            setTheme(R.style.DarkAppTheme);
        }

        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MyPreferenceFragment())
                .commit();

        final SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(Settings.this);

        String username = prefs.getString("username", "");
        boolean nightmodeCheckBox = prefs.getBoolean("NIGHT_MODE", false);
        //Toast.makeText(Settings.this, ""+nightmodeCheckBox, Toast.LENGTH_SHORT).show();

        //Register prefs to listen for change
        prefs.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("NIGHT_MODE")) {
                    //if checkbox is checked
                    if (prefs.getBoolean("NIGHT_MODE", false)) {
                        //Toast.makeText(Settings.this, "Night Mode Activated", Toast.LENGTH_SHORT).show();
                        //set theme here by restarting activity
                        restart();
                        //  recreate();
                    }
                    //if checkbox is not checked ie. false
                    else {
                        //Toast.makeText(Settings.this, "Default Theme Activated", Toast.LENGTH_SHORT).show();
                        //set theme here by restarting activity
                        restart();
                    }
                }
            }
        });

    }


    //Night Mode Methods
    public boolean getNightMode(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        return prefs.getBoolean("NIGHT_MODE", false);
    }

    //Display Name Methods
    public String getDisplayName(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        return prefs.getString("username", "");
    }

    public int getStartAppPreference() {
        SharedPreferences settings = getSharedPreferences("MyApp_Settings", MODE_PRIVATE);
        // Reading from SharedPreferences
        return settings.getInt("START_APP_PREF", 0);
    }

    //Start App PREF Methods
    public void setStartAppPreference(int number) {
        SharedPreferences settings = getSharedPreferences("MyApp_Settings", MODE_PRIVATE);
        // Writing data to SharedPreferences
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("START_APP_PREF", number);
        editor.apply();
    }

    //my restart activity method
    private void restart() {
        finishAffinity();
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        // finishAffinity(); Remove all the previous activities from the back stack.... requires API 16 and above
        finishAffinity();
        startActivity(new Intent(this, MainActivity.class));

    }

    public static class MyPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

        }
    }
}
