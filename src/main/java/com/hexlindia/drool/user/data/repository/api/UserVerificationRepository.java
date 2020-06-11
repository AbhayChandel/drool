package com.hexlindia.drool.user.data.repository.api;

import com.hexlindia.drool.user.data.entity.UserAccountVerificationEntity;
import com.hexlindia.drool.user.data.entity.UserAccountVerificationId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserVerificationRepository extends JpaRepository<UserAccountVerificationEntity, UserAccountVerificationId> {
    List<UserAccountVerificationEntity> findByIdUserId(long userId);

    UserAccountVerificationEntity findByIdUserIdAndIdVerificationTypeId(long userId, int verificationTypeId);
}
