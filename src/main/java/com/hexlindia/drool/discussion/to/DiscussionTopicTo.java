package com.hexlindia.drool.discussion.to;

import com.hexlindia.drool.discussion.to.validation.DiscussionTopicCreateValidation;
import com.hexlindia.drool.discussion.to.validation.DiscussionTopicUpdateValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class DiscussionTopicTo {

    @NotNull(message = "Discussion Id cannot be null", groups = {DiscussionTopicUpdateValidation.class})
    private Long id;

    @NotEmpty(message = "Topic title cannot be empty", groups = {DiscussionTopicCreateValidation.class, DiscussionTopicUpdateValidation.class})
    private String topic;

    @NotNull(message = "User Id cannot be null", groups = {DiscussionTopicCreateValidation.class})
    private Long userId;

    private DiscussionTopicActivityTo discussionTopicActivityTo;

    private List<DiscussionReplyTo> discussionReplyToList;

    public DiscussionTopicTo(Long id, String topic, Long userId) {
        this.id = id;
        this.topic = topic;
        this.userId = userId;
    }

    public DiscussionTopicActivityTo getDiscussionTopicActivityTo() {
        return discussionTopicActivityTo;
    }

    public void setDiscussionTopicActivityTo(DiscussionTopicActivityTo discussionTopicActivityTo) {
        this.discussionTopicActivityTo = discussionTopicActivityTo;
    }

    public List<DiscussionReplyTo> getDiscussionReplyToList() {
        return discussionReplyToList;
    }

    public void setDiscussionReplyToList(List<DiscussionReplyTo> discussionReplyToList) {
        this.discussionReplyToList = discussionReplyToList;
    }

    public DiscussionTopicTo() {
    }

    public Long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
