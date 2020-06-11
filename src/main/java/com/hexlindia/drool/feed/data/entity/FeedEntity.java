package com.hexlindia.drool.feed.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "feed")
@Getter
public class FeedEntity {

    @EmbeddedId
    private FeedEntityId feedEntityId;

    private LocalDateTime datePosted;
}
