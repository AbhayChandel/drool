package com.hexlindia.drool.common.data.entity;

import com.hexlindia.drool.article.data.entity.ArticleEntity2;
import com.hexlindia.drool.discussion2.data.entity.DiscussionEntity2;
import com.hexlindia.drool.video2.data.entity.VideoEntity2;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Embeddable
@Getter
public class Posts {
    @ManyToMany
    private Set<VideoEntity2> video = new HashSet<>();

    @ManyToMany
    private Set<ArticleEntity2> article = new HashSet<>();

    @ManyToMany
    private Set<DiscussionEntity2> discussion = new HashSet<>();
}
