package com.hexlindia.drool.article.data.repository.impl;

import com.hexlindia.drool.article.data.repository.api.ArticleViewRepository;
import com.hexlindia.drool.article.view.ArticlePreview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArticleViewRepositoryImpl implements ArticleViewRepository {

    private final EntityManager em;

    @Override
    public List<ArticlePreview> getArticlePreviews(List<Integer> idList) {
        System.out.println(em.createQuery("select count(c.id) from ArticleCommentEntity2 c where c.article = '2000001'")
                .getSingleResult());

        return em.createQuery("select new com.hexlindia.drool.article.view.ArticlePreview(a.id, a.title, count(DISTINCT l.id)," +
                " count(DISTINCT c.id), o.id, o.username)" +
                " from ArticleEntity2 a JOIN a.owner o" +
                " LEFT JOIN a.likes l" +
                " LEFT JOIN a.comments c" +
                " where a.id IN (:idList) and a.active = true" +
                " group by a.id")
                .setParameter("idList", idList)
                .getResultList();
    }
}
