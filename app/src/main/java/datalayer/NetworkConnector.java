package datalayer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import model.MyErrorTracker;

public class NetworkConnector {

    /*
    Main Responsibility : ESTABLISH CONNECTION.

Establishes connection to our server for us.
We then set up connection properties like Request method.
In this case we are making a HTTP GET request to our server.
We shall use HttpURLConnection so our only method shall return its instance .
     */

    public static Object connect(String urlAddress)
    {
        try
        {
            URL url=new URL(urlAddress);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();

            //set properties
            con.setRequestMethod("GET");
            con.setConnectTimeout(15000);//15 secs
            con.setReadTimeout(15000);
            con.setDoInput(true);

            return con;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return MyErrorTracker.WRONG_URL_FORMAT;

        } catch (IOException e) {
            e.printStackTrace();
            return MyErrorTracker.CONNECTION_ERROR;
        }
    }
}
