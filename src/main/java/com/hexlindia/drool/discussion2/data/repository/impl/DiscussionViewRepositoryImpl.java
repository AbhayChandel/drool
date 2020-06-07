package com.hexlindia.drool.discussion2.data.repository.impl;

import com.hexlindia.drool.discussion2.data.repository.api.DiscussionViewRepository;
import com.hexlindia.drool.discussion2.view.DiscussionPreview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DiscussionViewRepositoryImpl implements DiscussionViewRepository {

    private final EntityManager em;

    @Override
    public List<DiscussionPreview> getDiscussionPreviews(List<Integer> idList) {
        return em.createQuery("select new com.hexlindia.drool.discussion2.view.DiscussionPreview(d.id, d.title, count(DISTINCT l.id)," +
                " count(DISTINCT r.id), d.datePosted, o.id, o.username)" +
                " from DiscussionEntity2 d JOIN d.owner o" +
                " LEFT JOIN d.likes l" +
                " LEFT JOIN d.replies r" +
                " where d.id IN (:idList) and d.active = true" +
                " group by d.id, o.id")
                .setParameter("idList", idList)
                .getResultList();
    }
}
