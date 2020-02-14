package com.hexlindia.drool.video.dto;

import com.hexlindia.drool.video.services.validation.VideoInsertValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class VideoDto {

    private String id;

    @NotEmpty(message = "Video type is missing", groups = {VideoInsertValidation.class})
    private String type;

    private boolean active;

    @NotEmpty(message = "Video title is missing", groups = {VideoInsertValidation.class})
    private String title;
    private String description;

    @NotEmpty(message = "Video Source ID is missing", groups = {VideoInsertValidation.class})
    private String sourceId;
    private int likes;
    private int views;

    @NotNull(message = "Product is not tagged for the video", groups = {VideoInsertValidation.class})
    private ProductRefDto productRefDto;

    @NotNull(message = "User info is missing", groups = {VideoInsertValidation.class})
    private UserRefDto userRefDto;
    private List<VideoCommentDto> videoCommentDtoList;

    public VideoDto(String type, String title, String description, String sourceId, ProductRefDto productRefDto, UserRefDto userRefDto) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.sourceId = sourceId;
        this.productRefDto = productRefDto;
        this.userRefDto = userRefDto;
    }

    public VideoDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public ProductRefDto getProductRefDto() {
        return productRefDto;
    }

    public void setProductRefDto(ProductRefDto productRefDto) {
        this.productRefDto = productRefDto;
    }

    public UserRefDto getUserRefDto() {
        return userRefDto;
    }

    public void setUserRefDto(UserRefDto userRefDto) {
        this.userRefDto = userRefDto;
    }

    public List<VideoCommentDto> getVideoCommentDtoList() {
        return videoCommentDtoList;
    }

    public void setVideoCommentDtoList(List<VideoCommentDto> videoCommentDtoList) {
        this.videoCommentDtoList = videoCommentDtoList;
    }
}
