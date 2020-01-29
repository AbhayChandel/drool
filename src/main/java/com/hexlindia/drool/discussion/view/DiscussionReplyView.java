package com.hexlindia.drool.discussion.view;

public class DiscussionReplyView {

    private String replyId;
    private String discussionTopicId;
    private String reply;
    private String userId;
    private String datePosted;
    private String likes;

    public DiscussionReplyView(String replyId, String discussionTopicId, String reply, String userId, String datePosted, String likes) {
        this.replyId = replyId;
        this.discussionTopicId = discussionTopicId;
        this.reply = reply;
        this.userId = userId;
        this.datePosted = datePosted;
        this.likes = likes;
    }

    public String getReplyId() {
        return replyId;
    }

    public String getDiscussionTopicId() {
        return discussionTopicId;
    }

    public String getReply() {
        return reply;
    }

    public String getUserId() {
        return userId;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public String getLikes() {
        return likes;
    }
}
