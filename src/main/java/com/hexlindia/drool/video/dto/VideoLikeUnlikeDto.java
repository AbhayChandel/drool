package com.hexlindia.drool.video.dto;

import com.hexlindia.drool.video.dto.validation.VideoDecrementLikesValidation;
import com.hexlindia.drool.video.dto.validation.VideoIncrementLikesValidation;

import javax.validation.constraints.NotEmpty;

public class VideoLikeUnlikeDto {

    @NotEmpty(message = "User Id is missing", groups = {VideoIncrementLikesValidation.class, VideoDecrementLikesValidation.class})
    private String userId;

    @NotEmpty(message = "Video Id is missing", groups = {VideoIncrementLikesValidation.class, VideoDecrementLikesValidation.class})
    private String videoId;

    @NotEmpty(message = "Video title is missing", groups = {VideoIncrementLikesValidation.class})
    private String videoTitle;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
