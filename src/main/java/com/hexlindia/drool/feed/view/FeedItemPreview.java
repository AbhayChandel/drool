package com.hexlindia.drool.feed.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hexlindia.drool.user.view.UserProfilePreview;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedItemPreview {
    private String id;
    private String itemType;
    private String title;
    private String sourceVideoId;
    private String likes;
    private String comments;
    private LocalDateTime datePosted;

    @JsonProperty("user")
    private UserProfilePreview userProfilePreview;


}
