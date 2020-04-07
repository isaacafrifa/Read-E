package controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import model.Feed;
import model.MyGridViewAdapter;

import static model.MyErrorTracker.PASS_ERROR;

public class MyRSSParser extends AsyncTask<Void, Void, Boolean> {

    /*
    Main Responsibility : DOWNLOAD XML FEEDS.

It receives raw XML feeds from FetchFeedTask class.
It then processes/parses these feeds.
We use XmlPullParser to parse the feeds.
It then fills an arraylist of Feed Objects with our results.
It then sends this arraylist to adapter for binding purposes.
     */
    private static final String PUB_DATE = "pubDate";
    private static final String DESCRIPTION = "description";
    private static final String CHANNEL = "channel";
    private static final String MEDIA_CONTENT_IMG = "media:content";
    private static final String IMAGE = "media:thumbnail";
    private static final String ENCLOSURE = "enclosure";
    private static final String LINK = "link";
    private static final String GUID = "guid";
    private static final String TITLE = "title";
    private static final String ITEM = "item";


    Context c;
    InputStream is;
    GridView rv;
    ProgressDialog pd;
    private ProgressBar progressBar;
    private ArrayList<Feed> articles = new ArrayList<>();

    public MyRSSParser(Context c, InputStream is, GridView rv, ProgressBar progressBar) {
        this.c = c;
        this.is = is;
        this.rv = rv;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return this.parseRSS();
    }

    @Override
    protected void onPostExecute(Boolean isParsed) {
        super.onPostExecute(isParsed);
        if (isParsed) {            //sort the list here
//            Collections.sort(articles);

            //BIND
            rv.setAdapter(new MyGridViewAdapter(c, articles));
//dismiss progressbar
            progressBar.setVisibility(View.GONE);

        } else {
//dismiss progressbar
            progressBar.setVisibility(View.GONE);
            Toast.makeText(c, PASS_ERROR, Toast.LENGTH_SHORT).show();
        }
    }


    //My amended code
    private Boolean parseRSS() {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(is, null);
            int event = parser.getEventType();

            boolean done = false;

            articles.clear();
            Feed article = null;
            String name;
//tagName tagValue

            while (event != XmlPullParser.END_DOCUMENT && !done) {


                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        Log.e("******------ ", name);
                        if (name.equalsIgnoreCase(ITEM)) {
                            Log.i("new feed", "Created new feed here");
                            article = new Feed();


                        } else if (article != null) {

                            if (name.equalsIgnoreCase(LINK)) {
                                //  Log.i("Attribute", "setLink");
                                //trim Article URL
                                article.setLink(parser.nextText().trim());

                            } else if (name.equalsIgnoreCase(DESCRIPTION)) {
                                // Log.i("Attribute", "description");
                                // article.setDescription(parser.nextText().trim());

                                //format text to cater for html tags
                                String desc = parser.nextText().trim();
                               /* //img capture,commented out cos images are of poor quality
                                Document document = Jsoup.parse(desc);
                                Elements img = document.select("img");
                                String url = img.attr("src");
                                //set article image here,assuming there is no img tag
                                article.setImage(url);
*/
                                //check api 24
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    //set article desc
                                    article.setDescription(Html.fromHtml(desc, Html.FROM_HTML_MODE_LEGACY) + "");
                                } else {
                                    article.setDescription(Html.fromHtml(desc) + "");
                                }

                            } else if (name.equalsIgnoreCase(PUB_DATE)) {
                                //Log.i("Attribute", "date");
                                //trim pubDate
                                article.setDate(parser.nextText().trim());
                            } else if (name.equalsIgnoreCase(IMAGE)) {
                                //Log.i("Attribute", "thumbnail");
                                //------------scan and get attribute u want
                                /***** READ ABOUT AQUERY CACHING FROM AQUERY PDF
                                 * //return a cached copy if the data is recently fetched within 15 minutes****/
                                String imgUrl = parser.getAttributeValue(null, "url");
                                article.setImage(imgUrl);

                            }
                            //IMAGE ALTERNATIVE
                            else if (name.equalsIgnoreCase(MEDIA_CONTENT_IMG)) {
                                // Log.i("Attribute", "media:content ");
                                String imgUrl = parser.getAttributeValue(null, "url");
                                article.setImage(imgUrl);
                            }
                            //IMAGE ALTERNATIVE
                            else if (name.equalsIgnoreCase(ENCLOSURE)) {
                                // Log.i("Attribute", "media:content ");
                                String imgUrl = parser.getAttributeValue(null, "url");
                                article.setImage(imgUrl);
                            } else if (name.equalsIgnoreCase(GUID)) {
                                // Log.i("Attribute", "guid ");
                                //change source to image soon
                                article.setGuid(parser.nextText());
                            } else if (name.equalsIgnoreCase(TITLE)) {
                                Log.i("Attribute", "title");
                                // article.setTitle(parser.nextText().trim());

                                //format text to cater for html tags
                                String title = parser.nextText().trim();
                                //check api 24
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    article.setTitle(Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY) + "");
                                } else {
                                    article.setTitle(Html.fromHtml(title) + "");
                                }
                            }
                        }
                        break;

                    //Not included u can take out
                   /* case XmlPullParser.TEXT:
                        tagValue=parser.getText();
                        break;
                        //*/

                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        Log.i("End tag", name);
                        if (name.equalsIgnoreCase(ITEM) && article != null) {
                            //Output feeds here
                            Log.i("Added feed------>>>", article.toString() + "\n");
                            articles.add(article);


                        } else if (name.equalsIgnoreCase(CHANNEL)) {
                            done = true;
                        }
                        break;


                }

                event = parser.next();//move to next element
            }
            return true;

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
