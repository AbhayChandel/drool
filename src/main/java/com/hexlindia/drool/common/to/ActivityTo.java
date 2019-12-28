package com.hexlindia.drool.common.to;

public class ActivityTo {

    private Long postId;
    private Long currentUserId;

    public Long getPostId() {
        return postId;
    }

    public ActivityTo(Long postId, Long currentUserId) {
        this.postId = postId;
        this.currentUserId = currentUserId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Long currentUserId) {
        this.currentUserId = currentUserId;
    }
}
