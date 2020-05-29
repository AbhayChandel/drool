package com.hexlindia.drool.post.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "post_type")
@Data
public class PostTypeEntity {

    @Id
    private int id;
    private String type;
}
