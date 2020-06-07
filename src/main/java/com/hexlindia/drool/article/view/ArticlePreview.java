package com.hexlindia.drool.article.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.user.view.UserProfilePreview;
import lombok.Getter;

@Getter
public class ArticlePreview {

    private String id;
    private String title;
    private String likes;
    private String comments;

    @JsonProperty("user")
    private UserProfilePreview userProfilePreview;

    public ArticlePreview(int id, String title, long likes, long comments, long userId, String username) {
        this.id = String.valueOf(id);
        this.title = title;
        this.likes = MetaFieldValueFormatter.getCompactFormat(likes);
        this.comments = MetaFieldValueFormatter.getCompactFormat(comments);
        this.userProfilePreview = new UserProfilePreview(String.valueOf(userId), username);
    }
}
