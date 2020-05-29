package com.hexlindia.drool.post.data.repository.impl;

import com.hexlindia.drool.post.data.repository.api.PostViewRepository;
import com.hexlindia.drool.post.view.PostPageView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostViewRepositoryImpl implements PostViewRepository {

    private final EntityManager em;

    @Override
    public Optional<PostPageView> getPost(long id) {

        try {
            PostPageView postPageView = (PostPageView) em.createNativeQuery("select p.id as id, pt.type as type, p.title as title, p.date_posted as date_posted, " +
                    "p.likes as likes, p.views as views, u.id as owner_id, u.username as owner_username, " +
                    "p.source_video_id as source_video_id, p.text as text, p.cover_picture as cover_picture from post p " +
                    "inner join user_account u on p.owner = u.id " +
                    "left outer join POST_TYPE pt on p.type = pt.id " +
                    "where p.id = :id", "PostPageViewMapping")
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.of(postPageView);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
