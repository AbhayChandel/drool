package com.hexlindia.drool.common.dto;

import com.hexlindia.drool.common.dto.validation.PostRefEditValidation;

import javax.validation.constraints.NotEmpty;

public class PostRefDto {

    @NotEmpty(message = "Post Id is missing", groups = {PostRefEditValidation.class})
    private String id;
    private String title;
    private String type;
    private String medium;
    private String datePosted;

    public PostRefDto(@NotEmpty(message = "Post Id is missing", groups = {PostRefEditValidation.class}) String id, String title, String type, String medium, String datePosted) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.medium = medium;
        this.datePosted = datePosted;
    }

    public PostRefDto() {
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

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }
}
