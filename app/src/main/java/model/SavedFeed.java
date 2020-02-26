package model;

import java.io.Serializable;

public class SavedFeed implements Serializable {
    private String title,dateofpublication,description,link,imageUrl,source,timeSaved;
    private int id;

    public SavedFeed() {
    }
        //setting constructor
    public SavedFeed(String title, String dateofpublication, String link, String imageUrl, String source,String description) {
        this.title = title;
        this.dateofpublication = dateofpublication;
        this.link = link;
        this.imageUrl = imageUrl;
        this.source = source;
        this.description = description;
    }
    //getting constructor
    public SavedFeed(String title, String dateofpublication, String link, String imageUrl, String source, String timeSaved, int id,String description) {
        this.title = title;
        this.dateofpublication = dateofpublication;
        this.link = link;
        this.imageUrl = imageUrl;
        this.source = source;
        this.timeSaved = timeSaved;
        this.id = id;
        this.description = description;
    }

    @Override
    public String toString() {
        return "SavedFeed{" +
                "title='" + title + '\'' +
                ", dateofpublication='" + dateofpublication + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", source='" + source + '\'' +
                ", timeSaved='" + timeSaved + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;//if both of them points the same address in memory

        if(!(obj instanceof SavedFeed)) return false; // if "obj" is not a SavedFeed or a childclass

        SavedFeed thatPeople = (SavedFeed)obj; // than we can cast it to People safely

        return this.link.equals(thatPeople.link) && this.title.equals(thatPeople.title); // if they have the same link and same title,
        // then the 2 objects are equal unless they're pointing to different memory adresses

    }


    //Getters and Setters

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateofpublication() {
        return dateofpublication;
    }

    public void setDateofpublication(String dateofpublication) {
        this.dateofpublication = dateofpublication;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTimeSaved() {
        return timeSaved;
    }

    public void setTimeSaved(String timeSaved) {
        this.timeSaved = timeSaved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
