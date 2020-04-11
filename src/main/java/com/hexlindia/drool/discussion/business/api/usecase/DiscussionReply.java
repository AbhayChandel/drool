package com.hexlindia.drool.discussion.business.api.usecase;

import com.hexlindia.drool.discussion.dto.DiscussionReplyDto;

public interface DiscussionReply {

    DiscussionReplyDto saveReply(DiscussionReplyDto discussionReplyDto);

    boolean updateReply(String reply, String replyId, String discussionId);

    String incrementLikes(String replyId, String discussionId, String userId);

    String decrementLikes(String replyId, String discussionId, String userId);

    boolean setStatus(Boolean status, String replyId, String discussionId);
}
