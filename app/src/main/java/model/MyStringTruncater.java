package model;

public class MyStringTruncater {

    public MyStringTruncater() {
    }

    //my String truncate method
    public String truncate(String value, int length) {
        // Ensure String length is longer than requested size.
        if (value.length() > length) {
            return value.substring(0, length)+"...";
        } else {
            return value;
        }
    }

}
