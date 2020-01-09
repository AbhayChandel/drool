package com.hexlindia.drool.discussion.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hexlindia.drool.common.view.UserProfileCardView;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;

@MappedSuperclass
@SqlResultSetMapping(name = "discussionReplyCardView",
        classes = {
                @ConstructorResult(
                        targetClass = DiscussionReplyCardView.class,
                        columns = {
                                @ColumnResult(name = "replyId", type = Long.class),
                                @ColumnResult(name = "discussionTopicId", type = Long.class),
                                @ColumnResult(name = "reply", type = String.class),
                                @ColumnResult(name = "userId", type = Long.class),
                                @ColumnResult(name = "datePosted", type = String.class),
                                @ColumnResult(name = "likes", type = Integer.class),
                                @ColumnResult(name = "username", type = String.class)
                        })
        })
public class DiscussionReplyCardView {

    @JsonProperty("replyDetails")
    private DiscussionReplyView discussionReplyView;

    @JsonProperty("userCard")
    private UserProfileCardView userProfileCardView;

    public DiscussionReplyCardView(Long replyId, Long discussionTopicId, String reply, Long userId, String datePosted, int likes, String username) {
        this.discussionReplyView = new DiscussionReplyView(replyId, discussionTopicId, reply, userId, datePosted, likes);
        this.userProfileCardView = new UserProfileCardView(userId, username);
    }

    public DiscussionReplyView getDiscussionReplyView() {
        return discussionReplyView;
    }

    public UserProfileCardView getUserProfileCardView() {
        return userProfileCardView;
    }
}
