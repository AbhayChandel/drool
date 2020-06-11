package com.hexlindia.drool.discussion2.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.user.view.UserProfilePreview;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DiscussionPreview {
    private String id;
    private String title;
    private String likes;
    private String replies;
    private String datePosted;

    @JsonProperty("user")
    private UserProfilePreview userProfilePreview;

    public DiscussionPreview(int id, String title, long likes, long replies, LocalDateTime datePosted, long userId, String username) {
        this.id = String.valueOf(id);
        this.title = title;
        this.likes = MetaFieldValueFormatter.getCompactFormat(likes);
        this.replies = MetaFieldValueFormatter.getCompactFormat(replies);
        this.datePosted = MetaFieldValueFormatter.getDateTimeInDayMonCommaYear(datePosted);
        this.userProfilePreview = new UserProfilePreview(String.valueOf(userId), username);
    }
}
