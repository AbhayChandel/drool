package com.hexlindia.drool.video.data.entity;

import com.hexlindia.drool.post.data.entity.PostEntity;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "video")
@DiscriminatorValue("video")
@Data
public class VideoEntity extends PostEntity {

    private String sourceVideoId;
    private String text;
}
