package com.hexlindia.drool.article.data.entity;

import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "article_comment")
@Data
public class ArticleCommentEntity2 {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_comment_id_generator")
    @SequenceGenerator(name = "article_comment_id_generator", sequenceName = "article_comment_id_seq", allocationSize = 1)
    private Integer id;

    private String comment;
    private LocalDateTime datePosted;
    private int likes;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccountEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    private ArticleEntity2 article;

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleCommentEntity2 )) return false;
        return id != null && id.equals(((ArticleCommentEntity2) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }*/
}
