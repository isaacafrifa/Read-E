package com.blo.reade;

import android.content.Intent;
import android.preference.PreferenceFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AcknowledgementsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (new Settings().getNightMode(this)) {
            setTheme(R.style.DarkAppTheme);
        }

        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MyPreferenceFragment())
                .commit();


    }


    public static class MyPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.acknowledgepreferences);

        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, Settings.class));
        finish();
    }
}
