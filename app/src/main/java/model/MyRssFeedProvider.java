package model;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyRssFeedProvider {
    private static final String PUB_DATE = "pubDate";
    private static final String DESCRIPTION = "description";
    private static final String CHANNEL = "channel";
    static final String ENCLOSURE  = "enclosure";
    private static final String IMAGE = "thumbnail";
    private static final String LINK = "link";
    private static final String GUID = "guid";
    private static final String TITLE = "title";
    private static final String ITEM = "item";


    public MyRssFeedProvider() {

    }

    //XML PARSER
    public static List<Feed> parseFeed(String rssFeed) {
        List<Feed> list = new ArrayList<>();
        XmlPullParser parser = Xml.newPullParser();
        InputStream stream = null;
        try {
            // auto-detect the encoding from the stream
            stream = new URL(rssFeed).openConnection().getInputStream();
            parser.setInput(stream, null);
            int eventType = parser.getEventType();
            boolean done = false;
            Feed item = null;
            while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                String name = null;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        Log.e("******------ ",name);
                        if (name.equalsIgnoreCase(ITEM)) {
                            Log.i("new item", "Create new item");
                            item = new Feed();
                        } else if (item != null) {
                            if (name.equalsIgnoreCase(LINK)) {
                                Log.i("Attribute", "setLink");
                                item.setLink(parser.nextText());
                            } else if (name.equalsIgnoreCase(DESCRIPTION)) {
                                Log.i("Attribute", "description");
                                item.setDescription(parser.nextText().trim());
                            } else if (name.equalsIgnoreCase(PUB_DATE)) {
                                Log.i("Attribute", "date");
                                item.setDate(parser.nextText());
                            }
                            else if (name.equalsIgnoreCase(GUID )) {
                                Log.i("Attribute", "guid ");
                                //change source to image soon
                                item.setGuid(parser.nextText());
                            }
                           /* else if (name.equalsIgnoreCase(ENCLOSURE )) {
                                Log.i("Attribute", "enclosure ");
                                //change source to image soon
                                item.setSource(parser.nextText());
                            }*/
                            else if (name.equalsIgnoreCase(IMAGE )) {
                                Log.i("Attribute", "thumbnail");
              //------------scan and get attribute u want
                                /***** READ ABOUT AQUERY CACHING FROM AQUERY PDF
                                 * //return a cached copy if the data is recently fetched within 15 minutes****/
                                String imgUrl = parser.getAttributeValue(null, "url");
                                item.setImage(imgUrl);
                            }
                            else if (name.equalsIgnoreCase(TITLE)) {
                                Log.i("Attribute", "title");
                                item.setTitle(parser.nextText().trim());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        Log.i("End tag", name);
                        if (name.equalsIgnoreCase(ITEM) && item != null) {
                            Log.i("Added", item.toString());
                            list.add(item);
                        } else if (name.equalsIgnoreCase(CHANNEL)) {
                            done = true;
                        }
                        break;
                }
                eventType = parser.next();//move to next element
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }



}
