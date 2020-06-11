package com.hexlindia.drool.discussion2.business.api;

import com.hexlindia.drool.discussion2.data.repository.api.DiscussionViewRepository;
import com.hexlindia.drool.discussion2.view.DiscussionPreview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DiscussionViewImpl implements DiscussionView {

    private final DiscussionViewRepository discussionViewRepository;

    @Override
    public List<DiscussionPreview> getDiscussionPreviews(List<Integer> idList) {
        return discussionViewRepository.getDiscussionPreviews(idList);
    }
}
