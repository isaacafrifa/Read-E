package model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.blo.reade.R;
import com.blo.reade.WebViewActivity;

import com.bumptech.glide.Glide;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.util.List;
import java.util.Objects;

import controller.IoC;
import controller.ShareTextUrl;
import controller.SourceGetter;
import controller.StarFeed;
import controller.TimeAgoConverter;
import ru.katso.livebutton.LiveButton;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

///----------------my Grid View Adapter
public class MyGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<Feed> feedList;
    private Intent intent;
    private Bundle bundle;

    public MyGridViewAdapter(Context context, List<Feed> feedList) {
        this.context = context;
        this.feedList = feedList;

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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final Feed feed = feedList.get(position);
        View mygrid;

        if (convertView == null) {
            mygrid = Objects.requireNonNull(inflater).inflate(R.layout.feed_card_layoutt, null);
        } else {
            mygrid = convertView;
        }
        //get obj using view position
//        TextView feedTitle = mygrid.findViewById(R.id.Title_textView);
//        TextView feedDate = mygrid.findViewById(R.id.Date_textView);
//        ImageView feedimageView = mygrid.findViewById(R.id.Feed_imageView);
//        feedTitle.setText(feed.getTitle());
//        feedDate.setText(feed.getDate());
        TextView title_textView = mygrid.findViewById(R.id.title_textVieww);
        TextView timeAgo_textView = mygrid.findViewById(R.id.date_timeAgo_textVieww);
        //  TextView pubDate_textView = mygrid.findViewById(R.id.pubDate_textVieww);
        final TextView description_textView = mygrid.findViewById(R.id.feedDescription_textVieww);
        TextView source_textView = mygrid.findViewById(R.id.source_textVieww);
        ImageView imageView = mygrid.findViewById(R.id.feed_imageVieww);
        LiveButton viewOnlineBtn = mygrid.findViewById(R.id.feedView_buttonn);
        //SparkButton starBtn= mygrid.findViewById(R.id.spark_button);
        //ImageButton shareBtn= mygrid.findViewById(R.id.favourites_feedShare_button);
        TextView popupMenuBtn = mygrid.findViewById(R.id.feed_popup_menu);
        final TextView showMore_textView = mygrid.findViewById(R.id.showMore_textView);
        final TextView showLess_textView = mygrid.findViewById(R.id.showLess_textView);

        //set Feed Variables to Views
        title_textView.setText(feed.getTitle());
        // pubDate_textView.setText(feed.getDate());
        //Convert pubDate to TimeAgo
        timeAgo_textView.setText(TimeAgoConverter.convertToTimeAgo(feed.getDate()));
        description_textView.setText(feed.getDescription());

        //getting Source from Feed URL
        if (feed.getLink() != null) {
            feed.setSource(SourceGetter.getSourceFromURL(feed.getLink()));
        }
        source_textView.setText(feed.getSource());

        if (feed.getImage() != null) {
            //AQuery is outdated
            //this image is huge, avoid memory caching
//            aq.id(imageView).progress(R.id.progressBar).
//                    image(feed.getImage(), false, true, 0, R.drawable.testimage, null, AQuery.FADE_IN);

            Glide.with(context)
                    .load(feed.getImage()) // Remote URL of image.
                    .error(R.drawable.testimage)  //  image in case of error
                    .transition(withCrossFade()) //added cross fade animation
                   //.centerCrop() // get center cropped image as result
                   // .placeholder() // Image until Image is loaded
                    .into(imageView); //ImageView to set.

        } else {
            IoC.bbcImageCheck.imagecheck(feed.getLink(), imageView);
        }


        //onClicks
        popupMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.feed_cardview_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //do your things in each of the following cases
                        switch (item.getItemId()) {
                            case R.id.action_star:
                                StarFeed.starFeed(context, feed);
                                return true;
                            case R.id.action_share:
                                ShareTextUrl.shareTextUrl(feed, context);
                                return true;

                            default:
                                return false;
                        }
                    }
                });
                popup.show();

            }
        });


//        starBtn.setEventListener(new SparkEventListener(){
//            @Override
//            public void onEvent(ImageView button, boolean buttonState) {
//                if (buttonState) {
//                   // Toast.makeText(context, "Active", Toast.LENGTH_SHORT).show();
//                    StarFeed.starFeed(context,feed);
//                } else {
//                   // Toast.makeText(context, "Inactive", Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
//            }
//            @Override
//            public void onEventAnimationStart(ImageView button, boolean buttonState) {
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