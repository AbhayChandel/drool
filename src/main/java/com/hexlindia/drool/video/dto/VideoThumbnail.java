package com.hexlindia.drool.video.dto;

import com.hexlindia.drool.common.data.doc.UserRef;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoThumbnail {

    private String id;
    private String sourceId;
    private String title;
    private Integer views;
    private Integer likes;
    private UserRef userRef;

    public VideoThumbnail(String id, String sourceId, String title, Integer views, Integer likes, UserRef userRef) {
        this.id = id;
        this.sourceId = sourceId;
        this.title = title;
        this.views = views;
        this.likes = likes;
        this.userRef = userRef;
    }
}
