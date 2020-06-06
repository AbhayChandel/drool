package com.hexlindia.drool.article.data.entity;

import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "article")
@Data
public class ArticleEntity2 {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_id_generator")
    @SequenceGenerator(name = "article_id_generator", sequenceName = "article_id_seq", allocationSize = 1)
    private int id;

    private boolean active;
    private String title;
    private String body;
    private String coverPicture;
    private LocalDateTime datePosted;
    private int likes;
    private int views;

    @ManyToOne
    @JoinColumn(name = "owner")
    private UserAccountEntity owner;
}
