package com.hexlindia.drool.video.dto;

import com.hexlindia.drool.user.data.doc.UserRef;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoThumbnailDto {

    private String id;
    private String sourceId;
    private String title;
    private String views;
    private String likes;
    private UserRef userRef;
}
