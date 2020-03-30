package com.hexlindia.drool.video.dto;

import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.product.dto.ProductRefDto;
import com.hexlindia.drool.video.dto.validation.VideoInsertValidation;

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
    private String datePosted;
    private String likes;
    private String views;

    @NotEmpty(message = "Product(s) are not tagged for the video", groups = {VideoInsertValidation.class})
    private List<ProductRefDto> productRefDtoList;

    @NotNull(message = "User info is missing", groups = {VideoInsertValidation.class})
    private UserRefDto userRefDto;
    private List<VideoCommentDto> videoCommentDtoList;

    public VideoDto(String type, String title, String description, String sourceId, List<ProductRefDto> productRefDtoList, UserRefDto userRefDto) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.sourceId = sourceId;
        this.productRefDtoList = productRefDtoList;
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

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public List<ProductRefDto> getProductRefDtoList() {
        return productRefDtoList;
    }

    public void setProductRefDtoList(List<ProductRefDto> productRefDtoList) {
        this.productRefDtoList = productRefDtoList;
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
