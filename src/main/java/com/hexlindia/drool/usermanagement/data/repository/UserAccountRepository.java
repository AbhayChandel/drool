package com.hexlindia.drool.usermanagement.data.repository;

import com.hexlindia.drool.usermanagement.data.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {

    Optional<UserAccountEntity> findByEmail(String email);
}
