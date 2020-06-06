package com.hexlindia.drool.video2.data.entity;

import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "video")
@Data
public class VideoEntity2 {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "video_id_generator")
    @SequenceGenerator(name = "video_id_generator", sequenceName = "video_id_seq", allocationSize = 1)
    private int id;

    private boolean active;
    private String title;
    private String description;
    private String sourceVideoId;
    private LocalDateTime datePosted;
    private int likes;
    private int views;

    @ManyToOne
    @JoinColumn(name = "owner")
    private UserAccountEntity owner;
}
