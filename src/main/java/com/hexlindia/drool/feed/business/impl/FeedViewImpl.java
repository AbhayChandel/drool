package com.hexlindia.drool.feed.business.impl;

import com.hexlindia.drool.article.business.api.ArticleView;
import com.hexlindia.drool.article.view.ArticlePreview;
import com.hexlindia.drool.feed.business.api.FeedView;
import com.hexlindia.drool.feed.data.entity.FeedEntity;
import com.hexlindia.drool.feed.data.repository.api.FeedRepository;
import com.hexlindia.drool.feed.view.FeedItemPreview;
import com.hexlindia.drool.feed.view.mapper.ArticleFeedPreviewMapper;
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
        //Collections.sort(feedItemPreviews);
        return feedItemPreviews;
    }

    List<FeedItemPreview> getVideoItems(Set<Integer> itemIds) {
        return Collections.emptyList();
    }

    List<FeedItemPreview> getArticleItems(Set<Integer> itemIds) {
        List<ArticlePreview> articlePreviewList = articleView.getArticlePreviews(new ArrayList<>(itemIds));
        return articleFeedPreviewMapper.toFeedPreviewList(articlePreviewList);
    }

    List<FeedItemPreview> getDiscussionItems(Set<Integer> itemIds) {
        return Collections.emptyList();
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


}
