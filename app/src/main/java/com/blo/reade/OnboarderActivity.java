package com.blo.reade;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.chyrta.onboarder.OnboarderPage;

import java.util.ArrayList;
import java.util.List;

public class OnboarderActivity extends com.chyrta.onboarder.OnboarderActivity {

    List<OnboarderPage> onboarderPages;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onboarderPages = new ArrayList<>();

        // Create your first page
        // OnboarderPage onboarderPage1 = new OnboarderPage("Topics", "Numerous topics to read articles from");
        OnboarderPage onboarderPage1 = new OnboarderPage("Welcome to Read'E", "Your all-in-one news feed app.", R.drawable.icon);
        OnboarderPage onboarderPage2 = new OnboarderPage("Topics", "A plethora of topics to read articles from", R.drawable.icon);
        OnboarderPage onboarderPage3 = new OnboarderPage("Share", " Share articles with friends on social media", R.drawable.icon);
        OnboarderPage onboarderPage4 = new OnboarderPage("Star Feeds", "Save articles for later reading", R.drawable.icon);

        // Add your pages to the list
        onboarderPages.add(onboarderPage1);
        onboarderPages.add(onboarderPage2);
        onboarderPages.add(onboarderPage3);
        onboarderPages.add(onboarderPage4);

        for (OnboarderPage page : onboarderPages) {
            // You can define title and description colors (by default white)
            page.setTitleColor(R.color.white);
            page.setDescriptionColor(R.color.white);

            // Don't forget to set background color for your page
            page.setBackgroundColor(R.color.colorPrimary);

        }
        // And pass your pages to 'setOnboardPagesReady' method
        setOnboardPagesReady(onboarderPages);

    }


    @Override
    public void onSkipButtonPressed() {
        // Optional: by default it skips onboarder to the end
        super.onSkipButtonPressed();
        // Define your actions when the user press 'Skip' button
        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
        finishAffinity();
        startActivity(new Intent(this, Main2Activity.class));
        overridePendingTransition(0, 0);
    }

    @Override
    public void onFinishButtonPressed() {
        //moving to next page
        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
        finishAffinity();
        startActivity(new Intent(this, Main2Activity.class));
        overridePendingTransition(0, 0);
    }
}
