package com.hexlindia.drool.discussion2.data.entity;

import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "discussion_reply")
@Data
public class DiscussionReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discussion_reply_id_generator")
    @SequenceGenerator(name = "discussion_reply_id_generator", sequenceName = "discussion_reply_id_seq", allocationSize = 1)
    private Integer id;

    private String reply;
    private LocalDateTime datePosted;
    private int likes;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccountEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    private DiscussionEntity2 discussion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiscussionReplyEntity)) return false;
        return id != null && id.equals(((DiscussionReplyEntity) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
