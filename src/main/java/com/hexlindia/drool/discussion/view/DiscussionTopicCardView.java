package com.hexlindia.drool.discussion.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
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

    public DiscussionTopicCardView(Long topicId, String topic, Long userId, LocalDateTime datePosted, LocalDateTime dateLastActive, Integer views, Integer likes, Integer replies, String username) {
        this.discussionTopicView = new DiscussionTopicView(Long.toString(topicId), topic, Long.toString(userId), MetaFieldValueFormatter.getDateInDayMonCommaYear(datePosted.toLocalDate()), MetaFieldValueFormatter.getDateInDayMonCommaYear(dateLastActive.toLocalDate()), MetaFieldValueFormatter.getCompactFormat(views), MetaFieldValueFormatter.getCompactFormat(likes), MetaFieldValueFormatter.getCompactFormat(replies));
        this.userProfileCardView = new UserProfileCardView(Long.toString(userId), username);
    }

    DiscussionTopicCardView() {
    }

    public DiscussionTopicView getDiscussionTopicView() {
        return discussionTopicView;
    }

    public UserProfileCardView getUserProfileCardView() {
        return userProfileCardView;
    }
}
