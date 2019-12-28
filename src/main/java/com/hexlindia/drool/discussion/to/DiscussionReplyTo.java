package com.hexlindia.drool.discussion.to;

import com.hexlindia.drool.discussion.to.validation.DiscussionReplyPostValidation;
import com.hexlindia.drool.discussion.to.validation.DiscussionReplyUpdateValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DiscussionReplyTo {

    @NotNull(message = "Reply Id cannot be null", groups = {DiscussionReplyUpdateValidation.class})
    private Long id = null;

    @NotNull(message = "Discussion Id cannot be null", groups = {DiscussionReplyPostValidation.class})
    private Long discussionTopicId = null;

    @NotEmpty(message = "Reply cannot be empty", groups = {DiscussionReplyPostValidation.class, DiscussionReplyUpdateValidation.class})
    private String reply;

    @NotNull(message = "User Id cannot be null", groups = {DiscussionReplyPostValidation.class})
    private Long userId = null;

    private DiscussionReplyActivityTo discussionReplyActivityTo;

    public DiscussionReplyTo(Long id, Long discussionTopicId, @NotEmpty(message = "Reply cannot be empty") String reply, Long userId) {
        this.id = id;
        this.discussionTopicId = discussionTopicId;
        this.reply = reply;
        this.userId = userId;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDiscussionTopicId() {
        return discussionTopicId;
    }

    public void setDiscussionTopicId(Long discussionTopicId) {
        this.discussionTopicId = discussionTopicId;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public DiscussionReplyTo() {
    }

    public DiscussionReplyActivityTo getDiscussionReplyActivityTo() {
        return discussionReplyActivityTo;
    }

    public void setDiscussionReplyActivityTo(DiscussionReplyActivityTo discussionReplyActivityTo) {
        this.discussionReplyActivityTo = discussionReplyActivityTo;
    }
}
