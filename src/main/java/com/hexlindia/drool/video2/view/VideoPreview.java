package com.hexlindia.drool.video2.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.user.view.UserProfilePreview;
import lombok.Getter;

@Getter
public class VideoPreview {

    private String id;
    private String title;
    private String sourceVideoId;
    private String likes;
    private String comments;

    @JsonProperty("user")
    private UserProfilePreview userProfilePreview;

    public VideoPreview(int id, String title, String sourceVideoId, long likes, long comments, long userId, String username) {
        this.id = String.valueOf(id);
        this.title = title;
        this.sourceVideoId = sourceVideoId;
        this.likes = MetaFieldValueFormatter.getCompactFormat(likes);
        this.comments = MetaFieldValueFormatter.getCompactFormat(comments);
        this.userProfilePreview = new UserProfilePreview(String.valueOf(userId), username);
    }
}
