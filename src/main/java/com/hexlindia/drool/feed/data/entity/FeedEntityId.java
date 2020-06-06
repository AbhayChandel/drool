package com.hexlindia.drool.feed.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FeedEntityId implements Serializable {

    private int postId;
    private int postType;
}
