package com.hexlindia.drool.post.data.repository.impl;

import com.hexlindia.drool.common.constant.PostType2;
import com.hexlindia.drool.post.data.repository.api.PostViewRepository;
import com.hexlindia.drool.post.view.PostPageView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostViewRepositoryImpl implements PostViewRepository {

    private final EntityManager em;

    @Override
    public Optional<PostPageView> getPost(long id, PostType2 postType) {
        /*PostPageView postPageView = em.createQuery("select new PostPageView(p.id, p.type.type, p.title, p.datePosted, " +
                "p.likes, p.views, p.owner.id, p.owner.username, p., p.text, p.cover_picture, count(c.id)) " +
    "from PostEntity p LEFT OUTER JOIN VideoCommentEntity c on p.id = c.video" , PostPageView.class).getSingleResult();*/

        return Optional.empty();

        //FIXME
        // This use case is doable. Think about fetching all the post in a collection.
        // Can all be posts be fetched will all the columns using JPQL?
       /* StringBuilder query = new StringBuilder();
        setSelect(query);
        joinEntities(query, postType);
        setWhereClause(query);
        setGroupBy(query);
        try {
            PostPageView postPageView = (PostPageView) em.createNativeQuery(query.toString(), "PostPageViewMapping").setParameter("id", id)
                    .getSingleResult();
            return Optional.of(postPageView);
        } catch (NoResultException e) {
            return Optional.empty();
        }*/
    }

    void setSelect(StringBuilder query) {
        query.append("select p.id as id, pt.type as type, p.title as title, p.date_posted as date_posted, " +
                "p.likes as likes, p.views as views, u.id as owner_id, u.username as owner_username, " +
                "p.source_video_id as source_video_id, p.text as text, p.cover_picture as cover_picture, " +
                "count(c.id) as total_comments from post p");
        query.append("SELECT p, count(c.id) FROM PostEntity p JOIN VideoCommentEntity c where p.id = : id");
    }

    void joinEntities(StringBuilder query, PostType2 postType) {
        query.append(" inner join user_account u on p.owner = u.id left outer join post_type pt on p.type = pt.id");
        query.append(" left outer join " + getCommentTable(postType) + " c on p.id = c.post_id");
    }

    String getCommentTable(PostType2 postType) {
        switch (postType) {
            case ARTICLE:
                return "article_comment";
            case DISCUSSION:
                return "discussion_reply";
            default:
                return "video_comment";
        }
    }

    void setWhereClause(StringBuilder query) {
        query.append(" where p.id = :id");
    }

    void setGroupBy(StringBuilder query) {
        query.append(" group by select p.id, pt.type, p.title, p.date_posted, p.likes, p.views, u.id, u.username," +
                " p.source_video_id, p.text, p.cover_picture");
    }
}
