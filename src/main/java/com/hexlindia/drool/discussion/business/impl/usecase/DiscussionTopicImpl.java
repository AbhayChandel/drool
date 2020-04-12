package com.hexlindia.drool.discussion.business.impl.usecase;

import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.discussion.business.api.usecase.DiscussionTopic;
import com.hexlindia.drool.discussion.data.doc.DiscussionTopicDoc;
import com.hexlindia.drool.discussion.data.repository.api.DiscussionTopicRepository;
import com.hexlindia.drool.discussion.dto.DiscussionTopicDto;
import com.hexlindia.drool.discussion.dto.mapper.DiscussionTopicDtoDocMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class DiscussionTopicImpl implements DiscussionTopic {

    private final DiscussionTopicRepository discussionTopicRepository;
    private final DiscussionTopicDtoDocMapper discussionTopicDtoDocMapper;

    @Override
    public DiscussionTopicDto post(DiscussionTopicDto discussionTopicDto) {
        DiscussionTopicDoc discussionTopicDoc = discussionTopicDtoDocMapper.toDoc(discussionTopicDto);
        LocalDateTime datePosted = LocalDateTime.now();
        discussionTopicDoc.setDatePosted(datePosted);
        discussionTopicDoc.setDateLastActive(datePosted);
        discussionTopicDoc.setActive(true);
        discussionTopicDoc = discussionTopicRepository.save(discussionTopicDoc);
        log.debug("DiscussionTopic: '{}', id: '{}' created", discussionTopicDoc.getTitle(), discussionTopicDoc.getId());
        return discussionTopicDtoDocMapper.toDto(discussionTopicDoc);
    }

    @Override
    public DiscussionTopicDto findById(String id) {
        return discussionTopicDtoDocMapper.toDto(discussionTopicRepository.findById(new ObjectId(id)));
    }

    @Override
    public boolean updateTopicTitle(String title, String id) {
        return discussionTopicRepository.updateTopicTitle(title, new ObjectId(id));
    }

    @Override
    public String incrementViews(String id) {
        DiscussionTopicDoc discussionTopicDoc = discussionTopicRepository.incrementViews(new ObjectId(id));
        if (discussionTopicDoc != null) {
            return MetaFieldValueFormatter.getCompactFormat(discussionTopicDoc.getViews());
        }
        return null;
    }

    @Override
    public String incrementLikes(String id, String userId) {
        DiscussionTopicDoc discussionTopicDoc = discussionTopicRepository.incrementLikes(new ObjectId(id));
        if (discussionTopicDoc != null) {
            return MetaFieldValueFormatter.getCompactFormat(discussionTopicDoc.getLikes());
        }
        return null;
    }

    @Override
    public String decrementLikes(String id, String userId) {
        DiscussionTopicDoc discussionTopicDoc = discussionTopicRepository.decrementLikes(new ObjectId(id));
        if (discussionTopicDoc != null) {
            return MetaFieldValueFormatter.getCompactFormat(discussionTopicDoc.getLikes());
        }
        return null;
    }
}
