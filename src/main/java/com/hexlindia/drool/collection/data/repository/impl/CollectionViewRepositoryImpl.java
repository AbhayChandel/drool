package com.hexlindia.drool.collection.data.repository.impl;

import com.hexlindia.drool.article.view.ArticleMinimalPreview;
import com.hexlindia.drool.collection.data.repository.api.CollectionViewRepository;
import com.hexlindia.drool.video2.view.VideoMinimalPreview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CollectionViewRepositoryImpl implements CollectionViewRepository {

    private final EntityManager em;

    @Override
    public List<ArticleMinimalPreview> getArticles(int id, int firstResult, int resultSetSize) {
        return em.createQuery("select new com.hexlindia.drool.article.view.ArticleMinimalPreview(a.id, a.title, a.coverPicture) " +
                "from collection c JOIN c.posts.article a where c.id = :id and a.active = true")
                .setParameter("id", id)
                .setFirstResult(firstResult)
                .setMaxResults(resultSetSize)
                .getResultList();
    }

    @Override
    public List<VideoMinimalPreview> getVideos(int id, int firstResult, int resultSetSize) {
        return em.createQuery("select new com.hexlindia.drool.video2.view.VideoMinimalPreview(v.id, v.title, v.sourceVideoId) " +
                "from collection c JOIN c.posts.video v where c.id = :id and v.active = true")
                .setParameter("id", id)
                .setFirstResult(firstResult)
                .setMaxResults(resultSetSize)
                .getResultList();
    }
}
