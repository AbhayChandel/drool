package com.hexlindia.drool.video.dto;

import com.hexlindia.drool.common.data.constant.PostType;
import lombok.Data;

@Data
public class VideoDto {
    private String id;
    private String title;
    private String sourceId;
    private String description;
    private String views;
    private String likes;
    private String datePosted;
    private String userId;
    private PostType postType;
}
