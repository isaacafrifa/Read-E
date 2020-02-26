package controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import datalayer.NetworkConnector;
import model.MyErrorTracker;

public class FetchFeedTask extends AsyncTask<Void, Void, Object> {
    ProgressDialog dialog;
    private Context c;
    private String urlAddress;
    private GridView rv;
    private ProgressBar progressBar;

    public FetchFeedTask(Context c, String urlAddress, GridView rv, ProgressBar progressBar) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.rv = rv;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       /* pd=new ProgressDialog(c);
        pd.setTitle("Fetch Article");
        pd.setMessage("Fetching...Please wait");
        pd.show();*/
        if (!rv.isShown()) {
            // Toast.makeText(c, "Not Shown", Toast.LENGTH_SHORT).show();
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
        // pd.dismiss();

        //check for error
        if (data.toString().startsWith("Error")) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(c, data.toString(), Toast.LENGTH_SHORT).show();
        } else {
            //PARSE
            new MyRSSParser(c, (InputStream) data, rv, progressBar).execute();

        }
    }

    private Object downloadData() {
        //create an instance of your network connection
        Object connection = NetworkConnector.connect(urlAddress);
        if (connection.toString().startsWith("Error")) {
            return connection.toString();
        }

        try {
            HttpURLConnection con = (HttpURLConnection) connection;
            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return new BufferedInputStream(con.getInputStream());
            }

            return MyErrorTracker.RESPONSE_EROR + con.getResponseMessage();

        } catch (IOException e) {
            e.printStackTrace();
            return MyErrorTracker.IO_EROR;
        }
    }

}
