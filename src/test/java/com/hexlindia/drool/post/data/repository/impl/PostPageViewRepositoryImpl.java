package com.hexlindia.drool.post.data.repository.impl;

import com.hexlindia.drool.post.data.repository.api.PostPageViewRepository;
import com.hexlindia.drool.post.view.PostPageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class PostPageViewRepositoryImpl implements PostPageViewRepository {

    @Autowired
    EntityManager em;

    @Override
    public PostPageView getPost(long id) {

        return (PostPageView) em.createNativeQuery("select p.id as id, pt.type as type, p.title as title, p.date_posted as date_posted, " +
                "p.likes as likes, p.views as views, u.id as owner_id, u.username as owner_username, " +
                "p.source_video_id as source_video_id, p.text as text, p.cover_picture as cover_picture from post p " +
                "inner join user_account u on p.owner = u.id " +
                "left outer join POST_TYPE pt on p.type = pt.id " +
                "where p.id = :id", "PostPageViewMapping")
                .setParameter("id", id)
                .getSingleResult();
    }
}
