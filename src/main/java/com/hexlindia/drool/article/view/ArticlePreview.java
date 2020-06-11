package com.hexlindia.drool.article.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.user.view.UserProfilePreview;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticlePreview {

    private String id;
    private String title;
    private String likes;
    private String comments;
    private String datePosted;

    @JsonProperty("user")
    private UserProfilePreview userProfilePreview;

    public ArticlePreview(int id, String title, long likes, long comments, LocalDateTime datePosted, long userId, String username) {
        this.id = String.valueOf(id);
        this.title = title;
        this.likes = MetaFieldValueFormatter.getCompactFormat(likes);
        this.comments = MetaFieldValueFormatter.getCompactFormat(comments);
        this.datePosted = MetaFieldValueFormatter.getDateTimeInDayMonCommaYear(datePosted);
        this.userProfilePreview = new UserProfilePreview(String.valueOf(userId), username);
    }
}
