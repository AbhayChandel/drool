package com.hexlindia.drool.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hexlindia.drool.video.dto.VideoThumbnailDataDto;

public class ContributionSummaryDto {

    @JsonProperty("videoData")
    private VideoThumbnailDataDto videoThumbnailDataDto;

    public ContributionSummaryDto(VideoThumbnailDataDto videoThumbnailDataDto) {
        this.videoThumbnailDataDto = videoThumbnailDataDto;
    }

    public ContributionSummaryDto() {
    }

    public VideoThumbnailDataDto getVideoThumbnailDataDto() {
        return videoThumbnailDataDto;
    }
}
