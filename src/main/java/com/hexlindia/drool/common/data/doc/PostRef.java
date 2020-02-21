package com.hexlindia.drool.common.data.doc;

public class PostRef {

    private String id;
    private String title;
    private String type;

    public PostRef(String id, String title, String type) {
        this.id = id;
        this.title = title;
        this.type = type;
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
}
