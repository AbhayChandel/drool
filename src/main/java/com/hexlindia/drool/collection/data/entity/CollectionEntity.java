package com.hexlindia.drool.collection.data.entity;

import com.hexlindia.drool.common.data.entity.VisibilityEntity;
import com.hexlindia.drool.post.data.entity.PostEntity;
import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "collection")
@Data
public class CollectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "collection_id_generator")
    @SequenceGenerator(name = "collection_id_generator", sequenceName = "collection_id_seq", allocationSize = 1)
    private int id;
    private String name;
    private String about;

    @ManyToOne
    @JoinColumn(name = "visibility")
    private VisibilityEntity visibility;

    @ManyToOne
    @JoinColumn(name = "owner")
    private UserAccountEntity owner;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "POST_COLLECTION",
            joinColumns = @JoinColumn(name = "collection_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<PostEntity> posts = new HashSet<>();

    public void addPost(PostEntity post) {
        posts.add(post);
    }

    public void removePost(PostEntity post) {
        posts.remove(post);
    }


}
