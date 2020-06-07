package com.hexlindia.drool.discussion2.data.entity;

import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "discussion")
@Data
public class DiscussionEntity2 {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discussion_id_generator")
    @SequenceGenerator(name = "discussion_id_generator", sequenceName = "discussion_id_seq", allocationSize = 1)
    private Integer id;

    private boolean active;
    private String title;
    private String details;
    private String coverPicture;
    private LocalDateTime datePosted;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "discussion_like",
            joinColumns = @JoinColumn(name = "discussion_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserAccountEntity> likes = new HashSet<>();

    @OneToMany(
            mappedBy = "discussion",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<DiscussionReplyEntity> replies = new HashSet<>();

    private int views;

    @ManyToOne
    @JoinColumn(name = "owner")
    private UserAccountEntity owner;

    public void addReply(DiscussionReplyEntity reply) {
        replies.add(reply);
        reply.setDiscussion(this);
    }

    public void removeComment(DiscussionReplyEntity reply) {
        replies.remove(reply);
        reply.setDiscussion(null);
    }
}
