package model;

public class TimeAgo {

    //use like this externally
    //TimeAgo.getTimeAgo(TIMESTAMP);

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
//private static final int WEEK_MILLIS = 7 * DAY_MILLIS;

    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }


        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            //return "just now";
            return "now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            // return "a minute ago";
            return "1m";
        } else if (diff < 50 * MINUTE_MILLIS) {
            // return diff / MINUTE_MILLIS + " minutes ago";
            return diff / MINUTE_MILLIS + "m";
        } else if (diff < 90 * MINUTE_MILLIS) {
            // return "an hour ago";
            return "1h";
        } else if (diff < 24 * HOUR_MILLIS) {
            // return diff / HOUR_MILLIS + " hour(s) ago";
            return diff / HOUR_MILLIS + "h";
        } else if (diff < 48 * HOUR_MILLIS) {
            // return "yesterday";
            return "1d";
        } else {
            // return diff / DAY_MILLIS + " days ago";
            return diff / DAY_MILLIS + "d";
        }
    }
}
