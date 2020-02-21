package com.hexlindia.drool.common.data.doc;

public class ProductRef {

    private String id;
    private String name;
    private String type;

    public ProductRef(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public ProductRef() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
}
