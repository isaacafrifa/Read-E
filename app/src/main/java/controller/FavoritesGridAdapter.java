package controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.blo.reade.R;
import com.blo.reade.WebViewActivity;
import com.bumptech.glide.Glide;

import java.util.List;

import datalayer.DatabaseManager;
import model.Feed;
import model.MyErrorTracker;
import model.SavedFeed;
import ru.katso.livebutton.LiveButton;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class FavoritesGridAdapter extends BaseAdapter {
    private Context context;
    private ProgressDialog pd;
    private GridView gridView;
    private TextView feedEmpty_textView;
    private ImageView imagePlaceholder;
    private Intent intent;
    private Bundle bundle;
    //we are storing all the products in a list
    private List<SavedFeed> feedList;


    public FavoritesGridAdapter(Context context, List<SavedFeed> feedList, GridView gridView,
                                TextView feedEmpty_textView, ImageView imageplaceholder) {
        this.context = context;
        this.feedList = feedList;
        this.gridView = gridView;
        this.feedEmpty_textView = feedEmpty_textView;
        this.imagePlaceholder = imageplaceholder;
    }

    @Override
    public int getCount() {
        return feedList.size();
    }

    @Override
    public Object getItem(int position) {
        return feedList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get obj using view position
        final SavedFeed feed = feedList.get(position);
        View mygrid;
        final DatabaseManager databaseManager = new DatabaseManager(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (convertView == null) {
            assert inflater != null;
            mygrid = inflater.inflate(R.layout.favourites_card_layoutt, null);
        } else {
            mygrid = convertView;
        }
        //get obj using view position
//        TextView feedTitle = mygrid.findViewById(R.id.Favourites_feedTitle_textView);
//        //TextView feedDate = (TextView) mygrid.findViewById(R.id.Favourites_feedDate_textView);
//        TextView feedSavedDate = mygrid.findViewById(R.id.Favourites_feedSavedDate_textView);
//        ImageView feedimageView = mygrid.findViewById(R.id.Favourites_feedimageView);
        TextView title_textView = mygrid.findViewById(R.id.Favourites_feedTitle_textVieww);
        TextView savedDate_textView = mygrid.findViewById(R.id.Favourites_feedSavedDate_textVieww);
        TextView pubDate_textView = mygrid.findViewById(R.id.Favourites_feedPubDate_textVieww);
        final TextView description_textView = mygrid.findViewById(R.id.Favourites_feedDescription_textVieww);
        TextView source_textView = mygrid.findViewById(R.id.Favourites_feedSource_textVieww);
        ImageView imageView = mygrid.findViewById(R.id.Favourites_feedimageVieww);
        final TextView showMore_textView = mygrid.findViewById(R.id.showMore_textView);
        final TextView showLess_textView = mygrid.findViewById(R.id.showLess_textView);
        LiveButton viewOnlineBtn = mygrid.findViewById(R.id.favourites_feedView_button);
        //ImageButton deleteBtn= mygrid.findViewById(R.id.favourites_feedDelete_button);
        //ImageButton shareBtn= mygrid.findViewById(R.id.favourites_feedShare_button);
        TextView popupMenuBtn = mygrid.findViewById(R.id.fav_popup_menu);

        //set Feed Variables to Views
        title_textView.setText(feed.getTitle());

        //pubDate_textView.setText(feed.getDateofpublication()); //-- change pubDate to TimeAgo
        //Convert pubDate to TimeAgo
        pubDate_textView.setText(String.format("%s ago", TimeAgoConverter.convertToTimeAgo(feed.getDateofpublication())));

        savedDate_textView.setText(String.format("Saved on: %s", feed.getTimeSaved()));
        description_textView.setText(feed.getDescription());
        source_textView.setText(feed.getSource());


        //Image Check
        if (feed.getImageUrl() != null) {

            Glide.with(context)
                    .load(feed.getImageUrl()) // Remote URL of image.
                    .error(R.drawable.testimage)  //  image in case of error
                    .transition(withCrossFade()) //added cross fade animation
                    .into(imageView); //ImageView to set.


        } else {
            IoC.myImageConfig.imagecheck(feed.getLink(), imageView);
        }

        //Toggle between Show More or Less button
//        if (description_textView.getLineCount() > 4) {
//            description_textView.setMaxLines(4);
//            //description_textView.setText(description_textView.getText().toString()+" ...");
//            showMore_textView.setVisibility(View.VISIBLE);
//        }
//        //On Click for Show More TextView
//        showMore_textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //make Show Less textView visible
//                showMore_textView.setVisibility(View.GONE);
//                showLess_textView.setVisibility(View.VISIBLE);
//                //make the description textView a fuller textView
//                description_textView.setMaxLines(Integer.MAX_VALUE);
//            }
//        });
//        showLess_textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //make Show More textView visible
//                showLess_textView.setVisibility(View.GONE);
//                showMore_textView.setVisibility(View.VISIBLE);
//                description_textView.setMaxLines(4);
//            }
//        });

        //onClicks
        popupMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.fav_cardview_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //do your things in each of the following cases
                        switch (item.getItemId()) {
                            case R.id.action_delete:
                                pd = new ProgressDialog(context);
                                pd.setMessage("Loading...Please wait");
                                pd.show();
                                DeleteFeed.deletemethod(feed, databaseManager, context, feedList, FavoritesGridAdapter.this, pd, gridView, feedEmpty_textView, imagePlaceholder);
                                return true;
                            case R.id.action_share:
                                //convert savedfeed to feed
                                Feed sharedFeed = new Feed(feed.getTitle(), feed.getDateofpublication(), feed.getDescription(),
                                        feed.getLink(), feed.getImageUrl(), feed.getSource());
                                ShareTextUrl.shareTextUrl(sharedFeed, context);
                                return true;

                            default:
                                return false;
                        }
                    }
                });
                popup.show();

            }
        });

        //delete btn
//        deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Toast.makeText(context, "Delete Clicked!", Toast.LENGTH_SHORT).show();
//                pd=new ProgressDialog(context);
//                pd.setMessage("Loading...Please wait");
//                pd.show();
//                DeleteFeed.deletemethod(feed,databaseManager,context,feedList,FavoritesGridAdapter.this,pd,gridView,feedEmpty_textView,imagePlaceholder);
//
//            }
//        });
//
//        shareBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //convert savedfeed to feed
//                Feed sharedFeed=new Feed(feed.getTitle(),feed.getDateofpublication(),feed.getDescription(),
//                        feed.getLink(),feed.getImageUrl(), feed.getSource());
//               ShareTextUrl.shareTextUrl(sharedFeed,context);
//            }
//        });

        viewOnlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //moving to next page
                intent = new Intent(context, WebViewActivity.class);
                bundle = new Bundle();
                //check empty link
                if (feed.getLink() != null) {
                    bundle.putSerializable("BundleObject", feed.getLink());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "" + MyErrorTracker.EMPTY_URL, Toast.LENGTH_SHORT).show();
                }

            }
        });

        return mygrid;
    }


}
