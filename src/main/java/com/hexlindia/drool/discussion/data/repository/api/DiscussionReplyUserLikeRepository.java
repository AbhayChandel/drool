package com.hexlindia.drool.discussion.data.repository.api;

import com.hexlindia.drool.discussion.data.entity.DiscussionReplyUserLikeEntity;
import com.hexlindia.drool.discussion.data.entity.DiscussionReplyUserLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscussionReplyUserLikeRepository extends JpaRepository<DiscussionReplyUserLikeEntity, DiscussionReplyUserLikeId> {

    void deleteById(DiscussionReplyUserLikeId id);

    Optional<DiscussionReplyUserLikeEntity> findById(DiscussionReplyUserLikeId id);
}
