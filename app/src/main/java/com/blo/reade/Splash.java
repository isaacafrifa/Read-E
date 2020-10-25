package com.blo.reade;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.Objects;

import gr.net.maroulis.library.EasySplashScreen;

public class Splash extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//First time launch code here
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean isAccessed = prefs.getBoolean("isFirstRun", false);
        if (!isAccessed) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean("isFirstRun", Boolean.TRUE);
            edit.apply();
            //show onboarder activity
            View easySplashScreenView = new EasySplashScreen(Splash.this)
                    .withFullScreen()
                    .withTargetActivity(OnboarderActivity.class)
                    .withSplashTimeOut(2000)
                    .withBackgroundResource(R.color.splashbackground)
                    //.withHeaderText("Header")
                    // .withFooterText("Loading..")
                    //  .withBeforeLogoText("My cool company")
                    .withLogo(R.drawable.logo)
                    //.withAfterLogoText("READ'E")
                    .create();

            setContentView(easySplashScreenView);
            Objects.requireNonNull(getSupportActionBar()).hide();
        } else {
            //start regular Activity
            View easySplashScreenView = new EasySplashScreen(Splash.this)
                    .withFullScreen()
                    .withTargetActivity(Main2Activity.class)
                    .withSplashTimeOut(2000)
                    .withBackgroundResource(R.color.splashbackground)
                    //.withHeaderText("Header")
                    // .withFooterText("Loading..")
                    //  .withBeforeLogoText("My cool company")
                    .withLogo(R.drawable.logo)
                    //.withAfterLogoText("READ'E")
                    .create();

            setContentView(easySplashScreenView);
            getSupportActionBar().hide();
        }


    }
}
