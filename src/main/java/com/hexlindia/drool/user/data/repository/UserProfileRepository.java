package com.hexlindia.drool.user.data.repository;

import com.hexlindia.drool.user.data.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {

    Optional<UserProfileEntity> findByUsername(String username);
}
