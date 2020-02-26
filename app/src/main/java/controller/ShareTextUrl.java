package controller;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import model.Feed;

public class ShareTextUrl {

    //share Text Url
    public static void shareTextUrl(Feed bundleObject, Context context) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        //check for empty object title and link
        if (bundleObject.getTitle()!=null && bundleObject.getLink()!=null){
            Toast.makeText(context, "Please Wait..", Toast.LENGTH_SHORT).show();
            // Add data to the intent, the receiving app will decide
            // what to do with it.
            share.putExtra(Intent.EXTRA_SUBJECT, ""+bundleObject.getTitle());
            share.putExtra(Intent.EXTRA_TEXT, ""+bundleObject.getLink());
            context.startActivity(Intent.createChooser(share, "Share"));}

        else {
            Toast.makeText(context, "Share Failed", Toast.LENGTH_SHORT).show();
        }
    }


}
