package model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Objects;

public class CheckOnlineConnectivity {

    public CheckOnlineConnectivity() {
    }

    public boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNet = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        return activeNet != null && activeNet.isConnected();
    }

}
