package com.hexlindia.drool.discussion.data.repository;

import com.hexlindia.drool.discussion.data.entity.DiscussionReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscussionReplyRepository extends JpaRepository<DiscussionReplyEntity, Long> {
}
