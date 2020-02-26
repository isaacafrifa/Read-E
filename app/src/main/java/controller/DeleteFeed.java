package controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import datalayer.DatabaseManager;
import model.SavedFeed;

/*
This class is used to delete starred or saved feeds from the DB
 */
public class DeleteFeed {
    //private DatabaseManager databaseManager;


    ///delete Button method
    static void deletemethod(final SavedFeed savedFeed, final DatabaseManager databaseManager,
                             final Context context, final List<SavedFeed> feedList,
                             final FavoritesGridAdapter favoritesGridAdapter,
                             final ProgressDialog progressDialog, final GridView gridView,
                             final TextView emptyTextView, final ImageView emptyImageView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to delete this article?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // fabSpeedDial.setBackground(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.star_big_on));
                        //  fabSpeedDial.setEnabled(false);

                        //delete in db here
                        int result = databaseManager.DELETE_FEED(savedFeed.getId());
                        if (result == 1) {
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            // finish();
                            feedList.remove(savedFeed);
                            favoritesGridAdapter.notifyDataSetChanged();
                            gridView.setAdapter(favoritesGridAdapter);
                            //check and display imageholder if list is empty
                            if (feedList.isEmpty()) {
                                emptyTextView.setVisibility(View.VISIBLE);
                                emptyImageView.setVisibility(View.VISIBLE);
                            }
                            //cancel progressDialog after
                            progressDialog.cancel();
                        } else {
                            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
                            //cancel progressDialog after
                            progressDialog.cancel();
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        //cancel progressDialog after
                        progressDialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        dialog.show();

    }


}
