package com.hexlindia.drool.video.dto;

import java.util.List;

public class VideoThumbnailDataAggregation {

    Integer totalVideoCount;
    List<VideoThumbnail> videoThumbnailList;

    public VideoThumbnailDataAggregation(Integer totalVideoCount, List<VideoThumbnail> videoThumbnailList) {
        this.totalVideoCount = totalVideoCount;
        this.videoThumbnailList = videoThumbnailList;
    }

    public Integer getTotalVideoCount() {
        return totalVideoCount;
    }

    public List<VideoThumbnail> getVideoThumbnailList() {
        return videoThumbnailList;
    }
}
