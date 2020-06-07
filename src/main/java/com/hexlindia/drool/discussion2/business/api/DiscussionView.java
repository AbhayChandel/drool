package com.hexlindia.drool.discussion2.business.api;

import com.hexlindia.drool.discussion2.view.DiscussionPreview;

import java.util.List;

public interface DiscussionView {

    List<DiscussionPreview> getDiscussionPreviews(List<Integer> idList);
}
