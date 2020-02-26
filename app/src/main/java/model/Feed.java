package model;

import java.io.Serializable;

public class Feed implements Serializable {
    private String title,date,description,link,source,guid;
    //Change image later to String
    private String image;

    public Feed() {
          }

    public Feed(String title, String date, String image) {
        this.title = title;
        this.date = date;
        this.image = image;
    }

    public Feed(String title, String date, String description, String link, String image, String source) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.link = link;
        this.image = image;
        this.source = source;
    }


    @Override
    public String toString() {
        return "Feed{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", source='" + source + '\'' +
                ", guid='" + guid + '\'' +
                ", image=" + image +
                '}';
    }


    //Equals
    ///Do Equals Method


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
