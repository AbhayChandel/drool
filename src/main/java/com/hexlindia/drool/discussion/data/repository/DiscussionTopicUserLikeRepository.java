package com.hexlindia.drool.discussion.data.repository;

import com.hexlindia.drool.discussion.data.entity.DiscussionTopicUserLikeEntity;
import com.hexlindia.drool.discussion.data.entity.DiscussionTopicUserLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscussionTopicUserLikeRepository extends JpaRepository<DiscussionTopicUserLikeEntity, DiscussionTopicUserLikeId> {

    void deleteById(DiscussionTopicUserLikeId discussionTopicUserLikeId);

    Optional<DiscussionTopicUserLikeEntity> findById(DiscussionTopicUserLikeId discussionTopicUserLikeId);
}
