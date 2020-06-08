package com.example.logindemo.model;

import java.util.Date;

public class Announcements {
private String _id;
private Date date;
private String[] tags;
private String title;
private String description;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String subject;
private String link;
private String image;



    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public Date getDate() {
        return date;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
