package com.hexlindia.drool.post.data.repository.api;

import com.hexlindia.drool.post.data.entity.PostTypeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostTypeRepository extends CrudRepository<PostTypeEntity, Integer> {

    Optional<PostTypeEntity> findByType(String type);
}
