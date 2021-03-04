package controller;

import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import datalayer.NetworkConnector;
import model.Feed;
import model.MyErrorTracker;
import model.MyGridViewAdapter;

import static model.MyErrorTracker.PASS_ERROR;

public class FetchFeedTask extends AsyncTask<Void, Void, Object> {
    //using WeakReference to avoid memory leaks in AsyncTask
    private final WeakReference<Context> contextRef;
    private final WeakReference<ProgressBar> progressBarRef;
    private final WeakReference<GridView> gridViewRef;
    private  ArrayList<String> urlAddressList;
    //    private final ProgressBar progressBar;
    //    private final Context context;
    //    private final GridView gridView;

    public FetchFeedTask(Context context, ArrayList<String> urlAddressList, GridView gridView, ProgressBar progressBar) {
        // this.context = c;
        // this.gridView = rv;
        // this.progressBar = progressBar;
        this.urlAddressList = urlAddressList;
        this.contextRef = new WeakReference<>(context);
        this.gridViewRef = new WeakReference<>(gridView);
        this.progressBarRef = new WeakReference<>(progressBar);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Setting WeakReferences to Objects
        GridView gridView = gridViewRef.get();
        ProgressBar progressBar = progressBarRef.get();
        if (!gridView.isShown()) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected Object doInBackground(Void... voids) {
        return downloadData();
    }

    @Override
    protected void onPostExecute(Object data) {
        super.onPostExecute(data);
        Context context = contextRef.get();
        GridView gridView = gridViewRef.get();
        ProgressBar progressBar = progressBarRef.get();
        //check for error
        if (data.toString().startsWith("Error")) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(context, data.toString(), Toast.LENGTH_SHORT).show();
        } else {
            //PARSE
            // new MyRSSParser(c, (InputStream) data, rv, progressBar).execute();

            /*
            Below lines of code prevents Network exception because application attempts to perform a networking operation in the main thread.
            To avoid this error, call your networking operations (getting data from web server) request in thread or Async class.
             */
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //Get articleList from RSSParser
            ArrayList<Feed> articleList = new MyRSSParser((InputStream) data).parseRSS();
            if (!articleList.isEmpty()) {
                gridView.setAdapter(new MyGridViewAdapter(context, articleList));
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context, PASS_ERROR, Toast.LENGTH_SHORT).show();
            }

        }
    }

    private Object downloadData() {
        //create an instance of your network connection
        Object connection = NetworkConnector.connect(urlAddressList.get(0));
        if (connection.toString().startsWith("Error")) {
            return connection.toString();
        }
        try {
            HttpURLConnection con = (HttpURLConnection) connection;
            int responseCode = con.getResponseCode();
            Log.e("------->>>>>>", "Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return new BufferedInputStream(con.getInputStream());
            }
            return MyErrorTracker.RESPONSE_ERROR + con.getResponseMessage();

        } catch (IOException e) {
            e.printStackTrace();
            return MyErrorTracker.IO_ERROR;
        }
    }


}
