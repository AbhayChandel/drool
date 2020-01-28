package com.hexlindia.drool.video.data.repository.impl;

import com.hexlindia.drool.video.data.repository.api.VideoViewRepository;
import com.hexlindia.drool.video.view.VideoCardView;
import com.hexlindia.drool.video.view.VideoCommentCardView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class VideoViewRepositoryImpl implements VideoViewRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public VideoCardView getVideoCardView(Long videoId) {
        String queryString = "Select * from video_card_view where videoId = :videoId";
        Query query = entityManager.createNativeQuery(queryString, "videoCardView");
        query.setParameter("videoId", videoId);
        return (VideoCardView) query.getSingleResult();
    }

    @Override
    public List<VideoCommentCardView> getVideoCommentCardViews(Long videoId) {
        String queryString = "Select * from video_comment_card_view where videoId = :videoId";
        Query query = entityManager.createNativeQuery(queryString, "videoCommentCardView");
        query.setParameter("videoId", videoId);
        return query.getResultList();
    }


}
