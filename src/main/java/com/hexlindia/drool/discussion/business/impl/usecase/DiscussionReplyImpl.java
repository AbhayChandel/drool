package com.hexlindia.drool.discussion.business.impl.usecase;

import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.discussion.business.api.usecase.DiscussionReply;
import com.hexlindia.drool.discussion.data.doc.DiscussionReplyDoc;
import com.hexlindia.drool.discussion.data.repository.api.DiscussionReplyRepository;
import com.hexlindia.drool.discussion.dto.DiscussionReplyDto;
import com.hexlindia.drool.discussion.dto.mapper.DiscussionReplyDtoDocMapper;
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

    @Override
    public DiscussionReplyDto saveOrUpdate(DiscussionReplyDto discussionReplyDto) {
        if (discussionReplyDto.getId() != null) {
            return updateReply(discussionReplyDto);
        }
        return saveReply(discussionReplyDto);
    }

    private DiscussionReplyDto updateReply(DiscussionReplyDto discussionReplyDto) {
        boolean result = discussionReplyRepository.updateReply(discussionReplyDto.getReply(), new ObjectId(discussionReplyDto.getId()), new ObjectId(discussionReplyDto.getDiscussionId()));
        if (result) {
            return discussionReplyDto;
        }
        log.warn("Discussion reply was not updated");
        return null;
    }

    private DiscussionReplyDto saveReply(DiscussionReplyDto discussionReplyDto) {
        DiscussionReplyDoc discussionReplyDoc = discussionReplyDtoDocMapper.toDoc(discussionReplyDto);
        discussionReplyDoc.setDatePosted(LocalDateTime.now());
        discussionReplyDoc.setActive(true);
        boolean result = discussionReplyRepository.saveReply(discussionReplyDoc, new ObjectId(discussionReplyDto.getDiscussionId()));
        if (result) {
            return discussionReplyDtoDocMapper.toDto(discussionReplyDoc);
        }
        log.warn("Discussion reply not saved");
        return null;
    }

    @Override
    public String incrementLikes(String replyId, String discussionId, String userId) {
        return MetaFieldValueFormatter.getCompactFormat(discussionReplyRepository.incrementLikes(new ObjectId(replyId), new ObjectId(discussionId)));
    }

    @Override
    public String decrementLikes(String replyId, String discussionId, String userId) {
        return MetaFieldValueFormatter.getCompactFormat(discussionReplyRepository.decrementLikes(new ObjectId(replyId), new ObjectId(discussionId)));
    }

    @Override
    public boolean setStatus(Boolean status, String replyId, String discussionId) {
        return discussionReplyRepository.setStatus(status, new ObjectId(replyId), new ObjectId(discussionId));
    }
}
