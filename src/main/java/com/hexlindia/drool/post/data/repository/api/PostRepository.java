package com.hexlindia.drool.post.data.repository.api;

import com.hexlindia.drool.post.data.entity.PostEntity;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostEntity, Long> {
}
