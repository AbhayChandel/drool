package com.hexlindia.drool.post.data.entity;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "article")
@DiscriminatorValue("article")
@Data
public class ArticleEntity extends PostEntity {

    private String text;
    private String coverPicture;
}