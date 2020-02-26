package controller;
/*class that houses method for starring feed
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import datalayer.DatabaseManager;
import model.Feed;
import model.SavedFeed;

public class StarFeed {
    private static SavedFeed savedFeed;
    private static DatabaseManager databaseManager;

    ///Star method
    public static void starFeed(final Context context, final Feed feed) {
        databaseManager = new DatabaseManager(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to star article?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // fabSpeedDial.setBackground(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.star_big_on));
                        //  fabSpeedDial.setEnabled(false);

                        //place in db here
                        //check for empty objects and check for successful add
                        savedFeed = new SavedFeed(feed.getTitle(), feed.getDate(), feed.getLink(),
                                feed.getImage(), feed.getSource(), feed.getDescription());
                        //check if already starred
                        int resultInt = databaseManager.GET_SINGLE_FEED_usinglink(savedFeed.getLink());
                        // Log.e("-------",savedFeed+"");
                        //  Toast.makeText(context, "SOURCE : "+savedFeed.getSource(), Toast.LENGTH_SHORT).show();
                        if (resultInt == 1) {
                            Toast.makeText(context, "Article already starred", Toast.LENGTH_SHORT).show();
                        } else {
                            boolean result = databaseManager.ADD_FEED(savedFeed);
                            if (result)
                                Toast.makeText(context, "Article Starred", Toast.LENGTH_SHORT).show();
                            else {
                                Toast.makeText(context, "Article not starred", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        dialog.show();


    }


}
