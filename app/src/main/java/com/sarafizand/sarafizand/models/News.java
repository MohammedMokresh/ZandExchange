package com.sarafizand.sarafizand.models;

/**
 * Created by Mohammed on 21/10/2017.
 */

public class News {
    public News(String newsTitle, String newsThumpnail,String description) {
        this.newsTitle = newsTitle;
        this.newsThumpnail = newsThumpnail;
        this.description=description;
    }
    public News(){}

    String newsTitle;
    String newsThumpnail;
    String description;
    int categoryId;
    String createdAt;
    Curencies c;

    public Curencies getC() {
        return c;
    }

    public void setC(Curencies c) {
        this.c = c;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsThumpnail() {
        return newsThumpnail;
    }

    public void setNewsThumpnail(String newsThumpnail) {
        this.newsThumpnail = newsThumpnail;
    }
}
