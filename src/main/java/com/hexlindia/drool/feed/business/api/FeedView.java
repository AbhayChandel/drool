package com.hexlindia.drool.feed.business.api;


import com.hexlindia.drool.feed.view.FeedItemPreview;

import java.util.List;

public interface FeedView {

    List<FeedItemPreview> getFeedPage(int pageNumber, int pageSize);
}
