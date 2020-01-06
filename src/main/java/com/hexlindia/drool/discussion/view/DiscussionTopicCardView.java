package com.hexlindia.drool.discussion.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hexlindia.drool.common.view.UserProfileCardView;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;
import java.time.LocalDateTime;

@MappedSuperclass
@SqlResultSetMapping(name = "discussionTopicCardView",
        classes = {
                @ConstructorResult(
                        targetClass = DiscussionTopicCardView.class,
                        columns = {
                                @ColumnResult(name = "topicId", type = Long.class),
                                @ColumnResult(name = "topic", type = String.class),
                                @ColumnResult(name = "userId", type = Long.class),
                                @ColumnResult(name = "datePosted", type = LocalDateTime.class),
                                @ColumnResult(name = "dateLastActive", type = LocalDateTime.class),
                                @ColumnResult(name = "views", type = Integer.class),
                                @ColumnResult(name = "likes", type = Integer.class),
                                @ColumnResult(name = "replies", type = Integer.class),
                                @ColumnResult(name = "username", type = String.class)
                        })
        })
public class DiscussionTopicCardView {

    @JsonProperty("topicDetails")
    private DiscussionTopicView discussionTopicView;

    @JsonProperty("userCard")
    private UserProfileCardView userProfileCardView;

    public DiscussionTopicCardView(Long topicId, String topic, Long userId, LocalDateTime datePosted, LocalDateTime dateLastActive, int views, int likes, int replies, String username) {
        this.discussionTopicView = new DiscussionTopicView(topicId, topic, userId, datePosted, dateLastActive, views, likes, replies);
        this.userProfileCardView = new UserProfileCardView(userId, username);
    }

    public DiscussionTopicView getDiscussionTopicView() {
        return discussionTopicView;
    }

    public UserProfileCardView getUserProfileCardView() {
        return userProfileCardView;
    }
}
