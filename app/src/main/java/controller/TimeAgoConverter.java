package controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.TimeAgo;

public class TimeAgoConverter {
    private final static SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
    private static final String defaultDate = "Unavailable date";


    public static String convertToTimeAgo(String feedDate) {
        try {
            if (feedDate == null) {
                return defaultDate;
            }
            Date date = format.parse(feedDate);
            Timestamp timestamp = new Timestamp(date.getTime());
            String timeAgo = TimeAgo.getTimeAgo(timestamp.getTime());
            return timeAgo;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        //default date of feed
        return defaultDate;
    }

}

