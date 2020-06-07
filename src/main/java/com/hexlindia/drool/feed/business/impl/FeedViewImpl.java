package com.hexlindia.drool.feed.business.impl;

import com.hexlindia.drool.article.business.api.ArticleView;
import com.hexlindia.drool.article.view.ArticlePreview;
import com.hexlindia.drool.discussion2.business.api.DiscussionView;
import com.hexlindia.drool.discussion2.view.DiscussionPreview;
import com.hexlindia.drool.feed.business.api.FeedView;
import com.hexlindia.drool.feed.data.entity.FeedEntity;
import com.hexlindia.drool.feed.data.repository.api.FeedRepository;
import com.hexlindia.drool.feed.view.FeedItemPreview;
import com.hexlindia.drool.feed.view.mapper.ArticleFeedPreviewMapper;
import com.hexlindia.drool.feed.view.mapper.DiscussionFeedPreviewMapper;
import com.hexlindia.drool.feed.view.mapper.VideoFeedPreviewMapper;
import com.hexlindia.drool.video2.business.api.VideoView;
import com.hexlindia.drool.video2.view.VideoPreview;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class FeedViewImpl implements FeedView {

    private final FeedRepository feedRepository;
    private final ArticleView articleView;
    private final ArticleFeedPreviewMapper articleFeedPreviewMapper;
    private final VideoView videoView;
    private final VideoFeedPreviewMapper videoFeedPreviewMapper;
    private final DiscussionView discussionView;
    private final DiscussionFeedPreviewMapper discussionFeedPreviewMapper;

    @Override
    public List<FeedItemPreview> getFeedPage(int pageNumber, int pageSize) {
        Page<FeedEntity> feedItems = feedRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("datePosted").descending()));
        if (feedItems.isEmpty()) {
            return Collections.emptyList();
        }
        return getFeedItemPreviews(feedItems);
    }

    List<FeedItemPreview> getFeedItemPreviews(Page<FeedEntity> feedItems) {
        Map<Integer, Set<Integer>> itemGroups = getItemGroupMap(feedItems);
        List<FeedItemPreview> feedItemPreviews = new ArrayList<>();
        for (Map.Entry<Integer, Set<Integer>> group : itemGroups.entrySet()) {
            if (group.getKey().equals(1)) {
                feedItemPreviews.addAll(getVideoItems(group.getValue()));
            } else if (group.getKey().equals(2)) {
                feedItemPreviews.addAll(getArticleItems(group.getValue()));
            } else if (group.getKey().equals(3)) {
                feedItemPreviews.addAll(getDiscussionItems(group.getValue()));
            }
        }
        sortByDateDesc(feedItemPreviews);
        return feedItemPreviews;
    }

    List<FeedItemPreview> getVideoItems(Set<Integer> itemIds) {
        List<VideoPreview> videoPreviewList = videoView.getVideoPreviews(new ArrayList<>(itemIds));
        return videoFeedPreviewMapper.toFeedPreviewList(videoPreviewList);
    }

    List<FeedItemPreview> getArticleItems(Set<Integer> itemIds) {
        List<ArticlePreview> articlePreviewList = articleView.getArticlePreviews(new ArrayList<>(itemIds));
        return articleFeedPreviewMapper.toFeedPreviewList(articlePreviewList);
    }

    List<FeedItemPreview> getDiscussionItems(Set<Integer> itemIds) {
        List<DiscussionPreview> discussionPreviewList = discussionView.getDiscussionPreviews(new ArrayList<>(itemIds));
        return discussionFeedPreviewMapper.toFeedPreviewList(discussionPreviewList);
    }

    Map<Integer, Set<Integer>> getItemGroupMap(Page<FeedEntity> feedItems) {
        Map<Integer, Set<Integer>> itemGroups = new HashMap<>();
        for (FeedEntity feedEntity : feedItems.getContent()) {
            Set<Integer> items = null;
            int groupType = feedEntity.getFeedEntityId().getPostType();
            if (itemGroups.containsKey(groupType)) {
                items = itemGroups.get(groupType);
            } else {
                items = new HashSet<>();
            }
            items.add(feedEntity.getFeedEntityId().getPostId());
            itemGroups.put(groupType, items);
        }
        return itemGroups;
    }

    void sortByDateDesc(List<FeedItemPreview> feedItemPreviews) {
        feedItemPreviews.sort((m1, m2) -> {
            if (m1.getDatePosted() == m2.getDatePosted()) {
                return 0;
            }
            return m1.getDatePosted().isBefore(m2.getDatePosted()) ? 1 : -1;
        });
    }


}
