package com.hexlindia.drool.video.dto;

import java.util.List;

public class VideoThumbnailDataDto {

    Integer totalVideoCount;
    List<VideoThumbnailDto> videoThumbnailList;

    public VideoThumbnailDataDto() {
    }

    public Integer getTotalVideoCount() {
        return totalVideoCount;
    }

    public void setTotalVideoCount(Integer totalVideoCount) {
        this.totalVideoCount = totalVideoCount;
    }

    public List<VideoThumbnailDto> getVideoThumbnailList() {
        return videoThumbnailList;
    }

    public void setVideoThumbnailList(List<VideoThumbnailDto> videoThumbnailList) {
        this.videoThumbnailList = videoThumbnailList;
    }
}
