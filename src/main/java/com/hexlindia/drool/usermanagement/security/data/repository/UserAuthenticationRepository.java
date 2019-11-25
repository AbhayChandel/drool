package com.hexlindia.drool.usermanagement.security.data.repository;

import com.hexlindia.drool.usermanagement.security.data.entity.UserAuthenticationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthenticationRepository extends JpaRepository<UserAuthenticationEntity, Long> {

    Optional<UserAuthenticationEntity> findByEmail(String email);
}
