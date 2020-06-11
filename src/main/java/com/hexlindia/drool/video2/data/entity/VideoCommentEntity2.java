package com.hexlindia.drool.video2.data.entity;

import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "video_comment")
@Data
public class VideoCommentEntity2 {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "video_comment_id_generator")
    @SequenceGenerator(name = "video_comment_id_generator", sequenceName = "video_comment_id_seq", allocationSize = 1)
    private Integer id;

    private String comment;
    private LocalDateTime datePosted;
    private int likes;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccountEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    private VideoEntity2 video;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoCommentEntity2)) return false;
        return id != null && id.equals(((VideoCommentEntity2) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
