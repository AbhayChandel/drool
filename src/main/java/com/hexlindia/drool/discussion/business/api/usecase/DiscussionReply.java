package com.hexlindia.drool.discussion.business.api.usecase;

import com.hexlindia.drool.discussion.dto.DiscussionReplyDto;

public interface DiscussionReply {

    DiscussionReplyDto saveOrUpdate(DiscussionReplyDto discussionReplyDto);

    String incrementLikes(String replyId, String discussionId, String userId);

    String decrementLikes(String replyId, String discussionId, String userId);

    boolean delete(String replyId, String discussionId);
}
