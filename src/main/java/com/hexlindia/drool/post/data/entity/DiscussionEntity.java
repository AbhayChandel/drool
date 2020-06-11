package com.hexlindia.drool.post.data.entity;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "discussion")
@DiscriminatorValue("discussion")
@Data
public class DiscussionEntity extends PostEntity {

    private String text;
    private String coverPicture;
}