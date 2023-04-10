package com.example.collegenews.model;

import java.io.Serializable;

public class NewsModel implements Serializable {

    String category;
    String title;
    String description;
    String urlimage;
    String author;
    String uploadedat;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlimage() {
        return urlimage;
    }

    public void setUrlimage(String urlimage) {
        this.urlimage = urlimage;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUploadedat() {
        return uploadedat;
    }

    public void setUploadedat(String uploadedat) {
        this.uploadedat = uploadedat;
    }

    @Override
    public String toString() {
        return "NewsModel{" +
                "category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", urlimage='" + urlimage + '\'' +
                ", author='" + author + '\'' +
                ", uploadedat='" + uploadedat + '\'' +
                '}';
    }
}
