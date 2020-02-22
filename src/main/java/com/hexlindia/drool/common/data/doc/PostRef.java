package com.hexlindia.drool.common.data.doc;

import java.time.LocalDateTime;

public class PostRef {

    private String id;
    private String title;
    private String type;
    private String medium;
    private LocalDateTime datePosted;

    public PostRef(String id, String title, String type, String medium, LocalDateTime datePosted) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.medium = medium;
        this.datePosted = datePosted;
    }

    public PostRef() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }
}
