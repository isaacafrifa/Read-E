package com.blo.reade;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import model.Topic;

public class Topics extends AppCompatActivity {
    private Topic selectedTopic;
    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (new Settings().getNightMode(this)) {
            setTheme(R.style.DarkAppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set back icon on action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        List<Topic> topicList = new ArrayList<>();


        GridView gridView = findViewById(R.id.topics_gridView);
        //initializing the feedList
        topicList.add(new Topic("Trending", R.drawable.trending));
        topicList.add(new Topic("Basketball", R.drawable.basketball));
        topicList.add(new Topic("Business", R.drawable.business2));
        topicList.add(new Topic("Music", R.drawable.music));
        topicList.add(new Topic("Health", R.drawable.health));
        topicList.add(new Topic("Science", R.drawable.science));
        topicList.add(new Topic("Soccer", R.drawable.soccer));
        topicList.add(new Topic("TV & Movies", R.drawable.movies));
        topicList.add(new Topic("UK Politics", R.drawable.uk));
        topicList.add(new Topic("Cricket", R.drawable.cricket));
        topicList.add(new Topic("Tennis", R.drawable.tennis));
        topicList.add(new Topic("Boxing", R.drawable.boxing));
        topicList.add(new Topic("Racing", R.drawable.motorsport));
        topicList.add(new Topic("Athletics", R.drawable.athletics));
        topicList.add(new Topic("Africa", R.drawable.africa));
        topicList.add(new Topic("Fashion", R.drawable.fashion));
        topicList.add(new Topic("Gaming", R.drawable.game));
        topicList.add(new Topic("Travel", R.drawable.travel));
        topicList.add(new Topic("NFL", R.drawable.nfl));
        topicList.add(new Topic("UFC", R.drawable.ufc));
        topicList.add(new Topic("Europe", R.drawable.europe));
        topicList.add(new Topic("Asia", R.drawable.asia2));
        topicList.add(new Topic("Middle East", R.drawable.middle_east));
        topicList.add(new Topic("Food", R.drawable.food));
        //commented out cos VoaNews wasnt loading images properly
//        topicList.add(new Topic("Arts & Culture", R.drawable.arts));
//        topicList.add(new Topic("US Politics", R.drawable.america));
        // commented out because Wired.com doesn't support CORS policy
//        topicList.add(new Topic("Security", R.drawable.security));
//        topicList.add(new Topic("Ideas", R.drawable.ideas));
        topicList.add(new Topic("Lifestyle", R.drawable.lifestyle));
        topicList.add(new Topic("Relationships", R.drawable.dating));
        topicList.add(new Topic("Nature", R.drawable.nature));
        topicList.add(new Topic("Beauty", R.drawable.beauty));
        //sort the list
        Collections.sort(topicList);

        gridView.setAdapter(new myGridAdapter(Topics.this, topicList));

        //on click of GridView
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTopic = (Topic) parent.getItemAtPosition(position);
                //moving to next page
                intent = new Intent(Topics.this, SelectedTopic.class);
                bundle = new Bundle();
                bundle.putSerializable("BundleObject", selectedTopic.getTitle());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}


class myGridAdapter extends BaseAdapter {
    private final Context context;

    //we are storing all the products in a list
    private final List<Topic> topicList;


    public myGridAdapter(Context context, List<Topic> topicList) {
        this.context = context;
        this.topicList = topicList;

    }

    @Override
    public int getCount() {
        return topicList.size();
    }

    @Override
    public Object getItem(int position) {
        return topicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get obj using view position
        Topic topic = topicList.get(position);
        View mygrid ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            assert inflater != null;
            mygrid = inflater.inflate(R.layout.topics_card_layout, null);

        } else {
            mygrid = convertView;
        }
        TextView textView = mygrid.findViewById(R.id.topic_title_textView);
        ImageView imageView = mygrid.findViewById(R.id.topic_imageView);
        textView.setText(topic.getTitle());
        imageView.setImageResource(topic.getImage());
        return mygrid;
    }


}
