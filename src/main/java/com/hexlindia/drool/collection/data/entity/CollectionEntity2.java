package com.hexlindia.drool.collection.data.entity;

import com.hexlindia.drool.article.data.entity.ArticleEntity2;
import com.hexlindia.drool.common.data.entity.Posts;
import com.hexlindia.drool.common.data.entity.VisibilityEntity;
import com.hexlindia.drool.discussion2.data.entity.DiscussionEntity2;
import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import com.hexlindia.drool.video2.data.entity.VideoEntity2;
import lombok.Data;

import javax.annotation.Nullable;
import javax.persistence.*;

@Entity(name = "collection")
@Table(name = "collection")
@Data
public class CollectionEntity2 {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "collection_id_generator")
    @SequenceGenerator(name = "collection_id_generator", sequenceName = "collection_id_seq", allocationSize = 1)
    private int id;
    private String name;
    private String about;

    @ManyToOne
    @JoinColumn(name = "visibility")
    private VisibilityEntity visibility;

    @ManyToOne
    @JoinColumn(name = "owner")
    private UserAccountEntity owner;

    @Embedded
    /*@AttributeOverrides({
            @AttributeOverride(name="video", column = @Column(name="video_id", nullable = false)),
            @AttributeOverride(name="article", column = @Column(name="id", nullable = false)),
            @AttributeOverride(name="discussion", column = @Column(name="id", nullable = false))
    })*/
    @Nullable
    private Posts posts;

    public void addVideo(VideoEntity2 video) {
        posts.getVideo().add(video);
    }

    public void addArticle(ArticleEntity2 article) {
        posts.getArticle().add(article);
    }

    public void addDiscussion(DiscussionEntity2 discussion) {
        posts.getDiscussion().add(discussion);
    }

}
