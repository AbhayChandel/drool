package com.hexlindia.drool.article.data.entity;

import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "article")
@Data
public class ArticleEntity2 {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_id_generator")
    @SequenceGenerator(name = "article_id_generator", sequenceName = "article_id_seq", allocationSize = 1)
    private Integer id;

    private boolean active;
    private String title;
    private String body;
    private String coverPicture;
    private LocalDateTime datePosted;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "article_like",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserAccountEntity> likes = new HashSet<>();

    @OneToMany(
            mappedBy = "article",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ArticleCommentEntity2> comments = new HashSet<>();

    private int views;

    @ManyToOne
    @JoinColumn(name = "owner")
    private UserAccountEntity owner;

    public void addComment(ArticleCommentEntity2 comment) {
        comments.add(comment);
        comment.setArticle(this);
    }

    public void removeComment(ArticleCommentEntity2 comment) {
        comments.remove(comment);
        comment.setArticle(null);
    }
}
