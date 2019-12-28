package com.hexlindia.drool.discussion.data.repository;

import com.hexlindia.drool.discussion.data.entity.DiscussionTopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscussionTopicRepository extends JpaRepository<DiscussionTopicEntity, Long> {
}
