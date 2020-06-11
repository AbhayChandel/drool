package com.hexlindia.drool.common.data.repository;

import com.hexlindia.drool.common.data.entity.VisibilityEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VisibilityRepository extends CrudRepository<VisibilityEntity, Integer> {

    Optional<VisibilityEntity> findByVisibility(String visibility);
}
