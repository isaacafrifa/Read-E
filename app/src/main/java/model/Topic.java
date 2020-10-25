package model;

import androidx.annotation.NonNull;

public class Topic  implements Comparable{
    private String title;
    private int image;

    //constructor
    public Topic(String title, int image) {
        this.title = title;
        this.image = image;
    }

//Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


    //toString


    @Override
    public String toString() {
        return "Topic{" +
                "title='" + title + '\'' +
                ", image=" + image +
                '}';
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return this.getTitle().compareTo(((Topic) o).getTitle());

    }
}
