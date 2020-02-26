package com.blo.reade;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.asksira.webviewsuite.WebViewSuite;

import java.util.Objects;

import model.CopyLink;


public class WebViewActivity extends AppCompatActivity {
    // private WebView webview;
    private WebViewSuite webViewSuite;
    private ProgressDialog pd;
    private long lastBackPressTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Action Bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //setting customized icon as back button
        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);

        //get Bundle obj
        String bundleObject = (String) Objects.requireNonNull(getIntent().getExtras()).get("BundleObject");
        if (TextUtils.isEmpty(bundleObject)) {
            finish();
        }
        // webview = findViewById(R.id.webview);
        //  progressBar = findViewById(R.id.progressBar);
//        webview.loadUrl(bundleObject);
        webViewSuite = findViewById(R.id.webViewSuite);

        pd = new ProgressDialog(this);
        pd.setMessage("Loading...Please wait");
        pd.show();

        webViewSuite.startLoading(bundleObject);

        webViewSuite.customizeClient(new WebViewSuite.WebViewSuiteCallback() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //Do your own stuffs. These will be executed after default onPageStarted().
                //set title here, topic isn't loaded yet so set title to ""
                Objects.requireNonNull(getSupportActionBar()).setTitle("");
                // Update the action bar
                getSupportActionBar().setSubtitle(url);
                // invalidateOptionsMenu()- Declare that the options menu has changed, so should be recreated.
                invalidateOptionsMenu();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //Do your own stuffs. These will be executed after default onPageFinished().
                // Update the action bar
                Objects.requireNonNull(getSupportActionBar()).setTitle(view.getTitle());
                getSupportActionBar().setSubtitle(url);
                // progressBar.setVisibility(View.GONE);
                invalidateOptionsMenu();
                pd.cancel();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //Override those URLs you need and return true.
                //dont refresh page if urls are the same
                if (url.equals(view.getUrl())) {
                    return true;
                }

                //Return false if you don't need to override that URL.
                return false;
            }
        });

        webViewSuite.interfereWebViewSetup(new WebViewSuite.WebViewSetupInterference() {
            @Override
            public void interfereWebViewSetup(WebView webView) {
                WebSettings webSettings = webView.getSettings();
                //Change your WebView settings here
            }
        });
    }


    //clear cache in onPause just in case user moves from the activity to eg. another app
    @Override
    protected void onPause() {
        super.onPause();
        webViewSuite.getWebView().clearCache(true);
    }

    //clear webview cache to decrease app user data
    @Override
    protected void onDestroy() {
        webViewSuite.getWebView().clearCache(true);
        webViewSuite.getWebView().clearHistory();
        webViewSuite.getWebView().destroy();
        super.onDestroy();
    }


    //BACK AND FORWARD METHODS
    private void back() {
        if (!webViewSuite.goBackIfPossible()) super.onBackPressed();
    }

    private void forward() {
//        if (webview.canGoForward()) { webview.goForward();  }
        if (webViewSuite.getWebView().canGoForward()) webViewSuite.getWebView().goForward();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.webview_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        if (item.getItemId() == R.id.action_back) {
            back();
        }

        if (item.getItemId() == R.id.action_forward) {
            forward();
        }
        if (item.getItemId() == R.id.action_openinbrowser) {
            openWebPage(webViewSuite.getWebView().getUrl());
        }

        if (item.getItemId() == R.id.action_copylink) {
            //Use your CopyLink class
            new CopyLink(WebViewActivity.this).copyLink(webViewSuite.getWebView().getUrl());
        }

        if (item.getItemId() == R.id.action_refreshpage) {
            Toast.makeText(this, "Refreshing Page", Toast.LENGTH_SHORT).show();
            //webview.loadUrl(webview.getUrl());
            webViewSuite.refresh();
        }

        return super.onOptionsItemSelected(item);
    }


    //onBack method
    @Override
    public void onBackPressed() {
        back();
    }

    //Open in Browser Method
    private void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            Toast.makeText(this, "Opening Browser", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        } else {
            Toast.makeText(this, "Error Opening Browser", Toast.LENGTH_SHORT).show();
        }

    }


}
