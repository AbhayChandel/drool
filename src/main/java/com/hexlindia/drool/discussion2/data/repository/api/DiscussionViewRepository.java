package com.hexlindia.drool.discussion2.data.repository.api;

import com.hexlindia.drool.discussion2.view.DiscussionPreview;

import java.util.List;

public interface DiscussionViewRepository {

    List<DiscussionPreview> getDiscussionPreviews(List<Integer> idList);
}
