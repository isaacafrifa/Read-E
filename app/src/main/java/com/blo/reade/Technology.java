package com.blo.reade;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import controller.FetchFeedTask;
import model.Feed;
import model.MyErrorTracker;
import model.MyRssFeedProvider;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Technology.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Technology#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Technology extends Fragment {

    final static String urlAddress = "http://feeds.bbci.co.uk/news/technology/rss.xml";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String url = "http://feeds.reuters.com/reuters/technologyNews";
    GridView gridView;
    Feed selectedFeed;
    Intent intent;
    Bundle bundle;
    SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar ProgressBarLoading;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Technology() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Technology.
     */
    // TODO: Rename and change types and number of parameters
    public static Technology newInstance(String param1, String param2) {
        Technology fragment = new Technology();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_technology, container, false);

        gridView = rootView.findViewById(R.id.gridView);
        ProgressBarLoading = rootView.findViewById(R.id.progressbar);
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_container);

        //Fetch feeds
        // new FetchFeedTask().execute((Void) null);
        new FetchFeedTask(getContext(), urlAddress, gridView, ProgressBarLoading).execute();


        //Handling Swipe Refresh Layout
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_dark,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FetchFeedTask(getContext(), urlAddress, gridView, ProgressBarLoading).execute();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        //on click of GridView
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedFeed = (Feed) parent.getItemAtPosition(position);
                Snackbar snackbar = Snackbar.make(view, "View Article Online", Snackbar.LENGTH_SHORT)
                        .setAction("PROCEED", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //                //moving to next page
                                intent = new Intent(getContext(), WebViewActivity.class);
                                bundle = new Bundle();
                                //check empty link
                                if (selectedFeed.getLink() != null) {
                                    bundle.putSerializable("BundleObject", selectedFeed.getLink());
                                    intent.putExtras(bundle);
                                    getContext().startActivity(intent);
                                } else {
                                    Toast.makeText(getContext(), "" + MyErrorTracker.EMPTY_URL, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                snackbar.show();
            }
        });
        return rootView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
