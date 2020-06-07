package com.hexlindia.drool.video2.data.repository.impl;

import com.hexlindia.drool.video2.data.repository.api.VideoViewRepository;
import com.hexlindia.drool.video2.view.VideoPreview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class VideoViewRepositoryImpl implements VideoViewRepository {

    private final EntityManager em;

    @Override
    public List<VideoPreview> getVideoPreviews(List<Integer> idList) {
        return em.createQuery("select new com.hexlindia.drool.video2.view.VideoPreview(v.id, v.title, v.sourceVideoId," +
                " count(DISTINCT l.id), count(DISTINCT c.id), o.id, o.username)" +
                " from VideoEntity2 v JOIN v.owner o" +
                " LEFT JOIN v.likes l" +
                " LEFT JOIN v.comments c" +
                " where v.id IN (:idList) and v.active = true" +
                " group by v.id")
                .setParameter("idList", idList)
                .getResultList();
    }
}
