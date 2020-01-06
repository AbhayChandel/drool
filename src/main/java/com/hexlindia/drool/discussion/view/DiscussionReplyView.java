package com.hexlindia.drool.discussion.view;

import java.time.LocalDateTime;

public class DiscussionReplyView {

    private Long replyId;
    private Long discussionTopicId;
    private String reply;
    private Long userId;
    private LocalDateTime datePosted;
    private int likes;

    public DiscussionReplyView(Long replyId, Long discussionTopicId, String reply, Long userId, LocalDateTime datePosted, int likes) {
        this.replyId = replyId;
        this.discussionTopicId = discussionTopicId;
        this.reply = reply;
        this.userId = userId;
        this.datePosted = datePosted;
        this.likes = likes;
    }

    public Long getReplyId() {
        return replyId;
    }

    public Long getDiscussionTopicId() {
        return discussionTopicId;
    }

    public String getReply() {
        return reply;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public int getLikes() {
        return likes;
    }
}
