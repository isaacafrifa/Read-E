package controller;

import org.apache.commons.lang3.StringUtils;

/*
Class to derive source from Feed URLs
 */
public class SourceGetter {

    public static String getSourceFromURL(String feedURL) {
        //return the string output
        if (feedURL.contains("www.")) {
            // sourcetextView.setText(StringUtils.substringBetween(bundleObject.getLink(), "www.", ".co").toUpperCase());
            return StringUtils.substringBetween(feedURL, "www.", ".co").toUpperCase();
        } else if (feedURL.contains("rss.")) {
            //  sourcetextView.setText(StringUtils.substringBetween(bundleObject.getLink(), "rss.", ".co").toUpperCase());
            return StringUtils.substringBetween(feedURL, "rss.", ".co").toUpperCase();
        }
        //customized for consequenceofsound
        else if (feedURL.contains("consequenceofsound.net")) {
            // sourcetextView.setText(StringUtils.substringBetween(bundleObject.getLink(), "://", ".net").toUpperCase());
            return StringUtils.substringBetween(feedURL, "://", ".net").toUpperCase();
        } else {
            // sourcetextView.setText(StringUtils.substringBetween(bundleObject.getLink(), "://", ".co").toUpperCase());
            return StringUtils.substringBetween(feedURL, "://", ".co").toUpperCase();
        }
    }
}
