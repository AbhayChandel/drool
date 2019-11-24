package com.hexlindia.drool.usermanagement.security.data.repository;

import com.hexlindia.drool.usermanagement.security.data.entity.UserAuthenticationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserAuthenticationRepository extends CrudRepository<UserAuthenticationEntity, Long> {

    Optional<UserAuthenticationEntity> findByEmail(String email);
}
