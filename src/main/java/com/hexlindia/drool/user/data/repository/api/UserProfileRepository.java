package com.hexlindia.drool.user.data.repository.api;

import com.hexlindia.drool.user.data.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {


}
