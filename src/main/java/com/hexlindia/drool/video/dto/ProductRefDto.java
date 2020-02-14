package com.hexlindia.drool.video.dto;

public class ProductRefDto {

    private String id;
    private String name;
    private String type;

    public ProductRefDto(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public ProductRefDto() {
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
}
