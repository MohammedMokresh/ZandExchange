package com.sarafizand.sarafizand.models;

/**
 * Created by DR.RICH on 24/10/2016.
 */
public class Notifications {
    private String title, content, year;

    public Notifications() {
    }

    public Notifications(String title, String genre, String year) {
        this.title = title;
        this.content = genre;
        this.year = year;}


    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return content;
    }

    public void setGenre(String genre) {
        this.content = genre;
    }
}
