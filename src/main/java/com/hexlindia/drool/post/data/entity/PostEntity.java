package com.hexlindia.drool.post.data.entity;

import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "d_type")
@Data
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_generator")
    @SequenceGenerator(name = "post_id_generator", sequenceName = "post_id_seq", allocationSize = 1)
    private long id;

    private boolean active;
    private String title;
    private LocalDateTime datePosted;
    private int likes;
    private int views;

    @ManyToOne
    @JoinColumn(name = "type")
    private PostTypeEntity type;

    @ManyToOne
    @JoinColumn(name = "owner")
    private UserAccountEntity owner;

}
