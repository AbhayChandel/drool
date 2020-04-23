package com.hexlindia.drool.discussion.business.impl.usecase;

import com.hexlindia.drool.activity.FeedDocFields;
import com.hexlindia.drool.activity.business.api.usecase.ActivityFeed;
import com.hexlindia.drool.common.dto.mapper.PostRefMapper;
import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.discussion.business.api.usecase.DiscussionReply;
import com.hexlindia.drool.discussion.data.doc.DiscussionReplyDoc;
import com.hexlindia.drool.discussion.data.repository.api.DiscussionReplyRepository;
import com.hexlindia.drool.discussion.dto.DiscussionReplyDto;
import com.hexlindia.drool.discussion.dto.mapper.DiscussionReplyDtoDocMapper;
import com.hexlindia.drool.discussion.dto.mapper.ReplyDtoToRefMapper;
import com.hexlindia.drool.user.business.api.usecase.UserActivity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class DiscussionReplyImpl implements DiscussionReply {

    private final DiscussionReplyRepository discussionReplyRepository;
    private final DiscussionReplyDtoDocMapper discussionReplyDtoDocMapper;
    private final UserActivity userActivity;
    private final PostRefMapper postRefMapper;
    private final ActivityFeed activityFeed;
    private final ReplyDtoToRefMapper replyDtoToRefMapper;

    @Override
    public DiscussionReplyDto saveOrUpdate(DiscussionReplyDto discussionReplyDto) {
        if (discussionReplyDto.getId() != null) {
            return updateReply(discussionReplyDto);
        }
        return saveReply(discussionReplyDto);
    }

    private DiscussionReplyDto updateReply(DiscussionReplyDto discussionReplyDto) {
        boolean result = discussionReplyRepository.updateReply(discussionReplyDto.getReply(), new ObjectId(discussionReplyDto.getId()), new ObjectId(discussionReplyDto.getPostRefDto().getId()));
        if (result) {
            userActivity.updateDiscussionReply(new ObjectId(discussionReplyDto.getUserRefDto().getId()), replyDtoToRefMapper.toReplyRef(discussionReplyDto));
            return discussionReplyDto;
        }
        log.warn("Discussion reply was not updated");
        return null;
    }

    private DiscussionReplyDto saveReply(DiscussionReplyDto discussionReplyDto) {
        DiscussionReplyDoc discussionReplyDoc = discussionReplyDtoDocMapper.toDoc(discussionReplyDto);
        discussionReplyDoc.setDatePosted(LocalDateTime.now());
        boolean result = discussionReplyRepository.saveReply(discussionReplyDoc, new ObjectId(discussionReplyDto.getPostRefDto().getId()));
        if (result) {
            userActivity.addDiscussionReply(discussionReplyDoc.getUserRef().getId(), replyDtoToRefMapper.toReplyRef(discussionReplyDto));
            activityFeed.incrementDecrementField(new ObjectId(discussionReplyDto.getPostRefDto().getId()), FeedDocFields.comments, 1);
            return discussionReplyDtoDocMapper.toDto(discussionReplyDoc);
        }
        log.warn("Discussion reply not saved");
        return null;
    }

    @Override
    public String incrementLikes(DiscussionReplyDto discussionReplyDto) {
        String likes = MetaFieldValueFormatter.getCompactFormat(discussionReplyRepository.incrementLikes(new ObjectId(discussionReplyDto.getId()), new ObjectId(discussionReplyDto.getPostRefDto().getId())));
        userActivity.addDiscussionReplyLike(new ObjectId(discussionReplyDto.getUserRefDto().getId()), replyDtoToRefMapper.toReplyRef(discussionReplyDto));
        return likes;
    }

    @Override
    public String decrementLikes(String replyId, String discussionId, String userId) {
        String likes = MetaFieldValueFormatter.getCompactFormat(discussionReplyRepository.decrementLikes(new ObjectId(replyId), new ObjectId(discussionId)));
        userActivity.deleteDiscussionReplyLike(new ObjectId(userId), new ObjectId(replyId));
        return likes;
    }

    @Override
    public boolean delete(String replyId, String discussionId, String userId) {
        boolean result = discussionReplyRepository.delete(new ObjectId(replyId), new ObjectId(discussionId));
        if (result) {
            userActivity.deleteDiscussionReply(new ObjectId(userId), new ObjectId(replyId));
            activityFeed.incrementDecrementField(new ObjectId(discussionId), FeedDocFields.comments, -1);
            return result;
        }
        log.warn("Discussion reply not deleted");
        return false;
    }
}
